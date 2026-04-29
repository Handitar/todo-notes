package com.example.todo_notes.repository;

import com.example.todo_notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
    //query-методи
}
