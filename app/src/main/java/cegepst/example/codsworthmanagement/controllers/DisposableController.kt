package cegepst.example.codsworthmanagement.controllers

import cegepst.example.codsworthmanagement.models.Vault
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

private const val SAVE_INTERVAL = 60

class DisposableController(mainController: MainController, vault: Vault) {

    private lateinit var isSaving: AtomicBoolean
    private lateinit var saveObserver: Disposable

    private val mainController = mainController

    init {
        isSaving.set(true)
    }

    fun placeSaveEvents() {
        saveObserver = Observable.interval(SAVE_INTERVAL.toLong(), TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { isSaving.get() }
                .map { mainController.saveVault() }
                .subscribe { mainController.refreshContent() }
    }

    fun dispose() {
        isSaving.set(false)
    }
}