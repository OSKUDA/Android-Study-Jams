package com.oskuda.mynote.ui.viewmodel

import androidx.lifecycle.*
import com.oskuda.mynote.data.NoteDao
import com.oskuda.mynote.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class NoteViewModel(private val noteDao: NoteDao) : ViewModel() {

    val allNote : LiveData<List<Note>> = noteDao.getNotes().asLiveData()

    fun retrieveNote(id : Long) : LiveData<Note>{
        return noteDao.getNote(id.toInt()).asLiveData()
    }

    fun addNote(
        title : String,
        description : String,
        isCompleted : Boolean = false,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.insert(Note(title = title, description = description, isCompleted = isCompleted))
        }
    }

    fun updateNote(
        id : Long,
        title: String,
        description: String,
        isCompleted: Boolean,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.update(Note(id = id, title = title, description = description, isCompleted = isCompleted))
        }
    }

    fun deleteNote(note : Note){
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.delete(note)
        }
    }

    fun isValidEntry(title: String, description: String) : Boolean{
        return title.isNotBlank() && description.isNotBlank()
    }
}

class NoteViewModelFactory (private val noteDao: NoteDao) :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(noteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}