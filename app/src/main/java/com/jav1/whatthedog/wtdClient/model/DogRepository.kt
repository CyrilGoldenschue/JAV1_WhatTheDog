package com.jav1.whatthedog.data.model

import androidx.annotation.WorkerThread
import com.jav1.whatthedog.data.db.dao.DogDao
import com.jav1.whatthedog.data.db.entities.Dog

// Abstract access to multiple data sources
class DogRepository (private val dogDao: DogDao){

    val allDogs: List<Dog> = dogDao.getDogs()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(dog: Dog) {
        dogDao.insert(dog)
    }

}