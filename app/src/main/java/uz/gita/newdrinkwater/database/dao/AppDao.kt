package uz.gita.newdrinkwater.database.dao

import androidx.room.*
import uz.gita.newdrinkwater.database.entity.Data

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data: Data)

    @Update
    fun update(data: Data)

    @Delete
    fun delete(data: Data)

    @Query("SELECT * FROM Data")
    fun getAll(): List<Data>
}