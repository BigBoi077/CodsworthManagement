package cegepst.example.codsworthmanagement.dao

import androidx.room.*
import cegepst.example.codsworthmanagement.models.Vault

@Dao
interface VaultDAO {

    @Query("SELECT * FROM vault WHERE id=:id")
    fun get(id: Long): Vault

    @Insert
    fun insert(vault: Vault): Long

    @Update
    fun update(vault: Vault)

    @Delete
    fun delete(vault: Vault)
}