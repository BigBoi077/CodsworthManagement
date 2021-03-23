package cegepst.example.codsworthmanagement.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vault")
data class Vault(
        @PrimaryKey(autoGenerate = false) val id: Long,
        @ColumnInfo(name = "caps") var nbrCaps: Int,
        @ColumnInfo(name = "water") var waterUpgrades: Int,
        @ColumnInfo(name = "food") var steakUpgrades: Int,
        @ColumnInfo(name = "nuka_cola") var nukaColaUpgrades: Int,
        @ColumnInfo(name = "mr_handy") var mrHandy: Int,
        @ColumnInfo(name = "bought_water") var hasBoughtWater: Boolean,
        @ColumnInfo(name = "bought_steak") var hasBoughtSteak: Boolean,
        @ColumnInfo(name = "bought_cola") var hasBoughtCola: Boolean,
        @ColumnInfo(name = "water_collect_delay") var waterCollectDelay: Double,
        @ColumnInfo(name = "steal_collect_delay") var steakCollectDelay: Double,
        @ColumnInfo(name = "cola_collect_delay") var colaCollectDelay: Double
)