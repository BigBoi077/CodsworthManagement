package cegepst.example.codsworthmanagement.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cegepst.example.codsworthmanagement.models.Vault

@Dao
interface VaultDAO {

    @Query("SELECT * FROM vault WHERE id=:id")
    fun get(id: Long): Vault

    @Insert
    fun insert(vault: Vault): Long

    @Update
    fun update(vault: Vault)

    @Query("DELETE FROM vault WHERE id=:id")
    fun delete(id: Long)
}