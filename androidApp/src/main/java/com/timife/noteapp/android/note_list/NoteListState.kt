package com.timife.noteapp.android.note_list

import com.timife.noteapp.domain.note.Note

data class NoteListState(
     val notes:List<Note> = emptyList(),
     val searchText:String = "",
     val isSearchActive:Boolean = false
)
