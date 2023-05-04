package com.timife.noteapp.android.note_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timife.noteapp.domain.note.Note
import com.timife.noteapp.domain.note.NoteDataSource
import com.timife.noteapp.domain.note.SearchNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val searchNotes = SearchNotes()

    //Not every state would be needed to be restored on process death and restart
    //Also why savedStateHandle is being used in order to restore state.
    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val notes = savedStateHandle.getStateFlow("notes", emptyList<Note>())
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)

    val state = combine(notes, searchText, isSearchActive) { notes, searchText, isSearchActive ->
        NoteListState(
            notes = searchNotes.execute(notes, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),NoteListState())

    fun loadNotes(){
        viewModelScope.launch {
            savedStateHandle["notes"] = noteDataSource.getAllNotes()
        }
    }

    fun onSearchTextChange(text:String){
        savedStateHandle["searchText"] = text
    }
    fun  onToggleSearch() {
        savedStateHandle["isSearchActive"] = !isSearchActive.value
        //If the search is not active, the search text is expected to be blank
        if(!isSearchActive.value){
            savedStateHandle["searchText"] = ""
        }
    }

    fun deleteNoteById(id:Long){
        viewModelScope.launch {
            noteDataSource.deleteNoteById(id = id)
            //Since kotlin flow isn't being used in the sqldelight db, we have to reload the notes after deletion
            loadNotes()
        }
    }



}