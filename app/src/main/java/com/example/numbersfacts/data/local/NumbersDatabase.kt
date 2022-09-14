package com.example.numbersfacts.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.numbersfacts.data.local.models.NumberFactEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [NumberFactEntity::class,], version = 1)
abstract class NumbersDatabase : RoomDatabase() {

    public abstract fun dao() : NumbersDao

    companion object {
        @Volatile private var instance: NumbersDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room
            .databaseBuilder(context, NumbersDatabase::class.java, "numbers.db")
            .fallbackToDestructiveMigration().build()



    }

}