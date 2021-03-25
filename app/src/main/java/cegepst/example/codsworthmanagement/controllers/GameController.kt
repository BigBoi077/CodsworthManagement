package cegepst.example.codsworthmanagement.controllers

import cegepst.example.codsworthmanagement.models.Collectible
import cegepst.example.codsworthmanagement.models.Vault
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

private const val SAVE_INTERVAL = 60

class GameController(mainController: MainController, vault: Vault) {

    private val elapsed = AtomicLong(0)
    private val vault = vault
    private val mainController = mainController

    private lateinit var lastTick: AtomicLong
    private lateinit var running: AtomicBoolean
    private lateinit var observer: Disposable
    private lateinit var collectibles: HashMap<String, Collectible>

    fun start() {
        lastTick = AtomicLong(System.nanoTime())
        lastTick.set(System.nanoTime())
        running = AtomicBoolean(false)
        running.set(true)
        initCollectibles()
        loop()
    }

    private fun initCollectibles() {
        collectibles = HashMap()
        collectibles["water"] = Collectible(vault.waterCollectDelay, elapsed)
        collectibles["steak"] = Collectible(vault.steakCollectDelay, elapsed)
        collectibles["cola"] = Collectible(vault.colaCollectDelay, elapsed)
    }

    fun dispose() {
        running.set(false)
        observer.dispose()
    }

    fun canCollect(collectible: String): Boolean {
        if (collectibles[collectible]!!.canCollect) {
            return true
        }
        return false
    }

    fun hasCollected(collectible: String) {
        collectibles[collectible]!!.canCollect = false
    }

    fun changeInterval(collectible: String, newInterval: Double) {
        collectibles[collectible]!!.interval = newInterval
    }

    private fun loop() {
        observer = Observable.interval(10, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { running.get() }
            .map { refreshElapsed() }
            .subscribe { refreshGame() }
    }

    private fun refreshElapsed(): Long {
        val tick = System.nanoTime()
        val newElapsed = elapsed.addAndGet(tick - lastTick.get())
        lastTick.set(tick)
        return newElapsed
    }

    private fun refreshGame() {
        checkSave()
        checkCollectibleIntervals()
    }

    private fun checkCollectibleIntervals() {
        for (collectible in collectibles) {
            if (canCollect(collectible.value)) {
                collectible.value.canCollect = true
            }
        }
    }

    private fun checkSave() {
        if (canSave()) {
            mainController.saveVault()
        }
    }

    private fun canCollect(collectible: Collectible): Boolean {
        return elapsed.toDouble() - collectible.lastCollectTimestamp.toDouble() >= collectible.interval
    }

    private fun canSave(): Boolean {
        return elapsed.toInt() % SAVE_INTERVAL == 0
    }
}