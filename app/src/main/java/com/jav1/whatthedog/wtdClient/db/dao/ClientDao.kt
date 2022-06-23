package com.jav1.whatthedog.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jav1.whatthedog.data.db.entities.Client

/**
 * Contains the methods used for accessing the database
 */
@Dao
interface ClientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addClient(client: Client)

    @Query("SELECT * FROM clients ORDER BY id ASC")
    fun readAllData(): LiveData<List<Client>>
}