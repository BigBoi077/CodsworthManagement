package cegepst.example.codsworthmanagement.stores

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cegepst.example.codsworthmanagement.dao.VaultDAO
import cegepst.example.codsworthmanagement.models.Vault

@Database(entities = [Vault::class], version = 1)
abstract class AppStore : RoomDatabase() {

    abstract fun vaultDAO(): VaultDAO

    companion object {
        @Volatile
        private var instance: AppStore? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context,
                AppStore::class.java,
                "CodsworthManagement-0"
        )
                .build()
    }
}