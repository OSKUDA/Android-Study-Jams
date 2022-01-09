package com.oskuda.mynote

import android.app.Application
import com.oskuda.mynote.data.NoteDatabase

class BaseApplication : Application() {
    val database: NoteDatabase by lazy {
        NoteDatabase.getDatabase(this)
    }
}