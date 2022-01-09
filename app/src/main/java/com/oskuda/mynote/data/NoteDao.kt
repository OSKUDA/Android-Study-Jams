package com.oskuda.mynote.data

import androidx.room.*
import com.oskuda.mynote.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_database")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note_database WHERE id = :id")
    fun getNote(id: Int): Flow<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)
}