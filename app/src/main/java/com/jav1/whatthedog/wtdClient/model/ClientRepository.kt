package com.jav1.whatthedog.data.model

import androidx.lifecycle.LiveData
import com.jav1.whatthedog.data.db.dao.ClientDao
import com.jav1.whatthedog.data.db.entities.Client

// Abstract access to multiple data sources
class ClientRepository (private val clientDao: ClientDao){

    val readAllData: LiveData<List<Client>> = clientDao.readAllData()

    suspend fun addClient(client: Client){
        clientDao.addClient(client)
    }

}