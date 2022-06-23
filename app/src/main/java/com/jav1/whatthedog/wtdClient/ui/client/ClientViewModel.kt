package com.jav1.whatthedog.data.ui.client

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jav1.whatthedog.data.db.AppDatabase
import com.jav1.whatthedog.data.db.entities.Client
import com.jav1.whatthedog.data.model.ClientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Provide data to UI and survive configuration changes
class ClientViewModel(application: Application) : AndroidViewModel(application){
    private val readAllData: LiveData<List<Client>>
    private val repository: ClientRepository

    init {
        val clientDao = AppDatabase.getDatabase(application).clientDao()
        repository = ClientRepository(clientDao)
        readAllData = repository.readAllData
    }

    fun addClient(client: Client){
        // Run this code on background threads
        viewModelScope.launch(Dispatchers.IO){
            repository.addClient(client)
        }
    }
}