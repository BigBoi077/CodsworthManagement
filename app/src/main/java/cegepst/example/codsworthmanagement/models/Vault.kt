package cegepst.example.codsworthmanagement.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vault")
data class Vault(
        @PrimaryKey(autoGenerate = false) val id: Long,
        @ColumnInfo(name = "caps", defaultValue = "0") var nbrCaps: Int,
        @ColumnInfo(name = "water", defaultValue = "0") var waterUpgrades: Int,
        @ColumnInfo(name = "food", defaultValue = "0") var steakUpgrades: Int,
        @ColumnInfo(name = "nuka_cola", defaultValue = "0") var nukaColaUpgrades: Int,
        @ColumnInfo(name = "mr_handy", defaultValue = "0") var mrHandy: Int
)