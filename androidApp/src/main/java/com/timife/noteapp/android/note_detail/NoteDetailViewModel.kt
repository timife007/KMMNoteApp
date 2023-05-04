package com.timife.noteapp.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.timife.noteapp.domain.note.NoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource:NoteDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val noteTitle = savedStateHandle.getStateFlow("noteTitle","")
}