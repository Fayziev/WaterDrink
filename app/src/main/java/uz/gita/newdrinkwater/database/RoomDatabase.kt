package uz.gita.newdrinkwater

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.newdrinkwater.app.App
import uz.gita.newdrinkwater.database.dao.AppDao
import uz.gita.newdrinkwater.database.entity.Data

@Database(entities = [Data::class], version = 1)
abstract class RoomData : RoomDatabase() {
    abstract fun getDao(): AppDao

    companion object {
        private lateinit var instance: RoomData
        fun getDatabase(): RoomData {

            if (!Companion::instance.isInitialized) {
                instance = Room.databaseBuilder(App.instance, RoomData::class.java, "DrinkWater")
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}