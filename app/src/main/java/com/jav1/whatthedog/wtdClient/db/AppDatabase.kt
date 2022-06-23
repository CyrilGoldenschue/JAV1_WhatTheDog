package com.jav1.whatthedog.data.db

import android.content.Context
import androidx.room.*
import com.jav1.whatthedog.data.db.dao.ClientDao
import com.jav1.whatthedog.data.db.dao.DogDao
import com.jav1.whatthedog.data.db.entities.Client
import com.jav1.whatthedog.data.db.entities.Dog

/**
 * Contains the database holder and serves as the main access point for the underlying connection to client data
 */
@Database(entities = [Client::class, Dog::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun clientDao(): ClientDao
    abstract fun dogDao(): DogDao

    /**
     * Everything within this companion object will be visible to other classes
     * It makes our client database a singleton class
     */
    companion object{
        // Volatile means rights to this field are visible to other threads
        @Volatile
        private var INSTANCE: AppDatabase?= null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            // If our instance already exists, we're returning that same instance.
            if(tempInstance != null){
                return tempInstance
            }
            // everything in that block will be protected from concurrence execution by multiple threads
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "client_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}