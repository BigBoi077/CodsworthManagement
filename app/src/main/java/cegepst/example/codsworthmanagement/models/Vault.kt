package cegepst.example.codsworthmanagement.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vault")
data class Vault(
    @ColumnInfo(name = "caps", defaultValue = "0") var nbrCaps: Int,
    @ColumnInfo(name = "water", defaultValue = "0") var waterUpgrades: Int,
    @ColumnInfo(name = "food", defaultValue = "0") var foodUpgrades: Int,
    @ColumnInfo(name = "nuka_cola", defaultValue = "0") var nukaColUpgrades: Int,
    @ColumnInfo(name = "mr_handy") var mrHandy: Int
) {
    @PrimaryKey(autoGenerate = false) var idVault: Long = 0
}