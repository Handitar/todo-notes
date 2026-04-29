package com.example.todo_notes.service;

import com.example.todo_notes.model.Note;
import com.example.todo_notes.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> listAll() {
        return noteRepository.findAll();
    }

    @Override
    public Note add(Note note) {
        //бд згенерить id
        note.setId(null);
        return noteRepository.save(note);
    }

    @Override
    public void deleteById(long id) {
        if (!noteRepository.existsById(id)) {
            throw new java.util.NoSuchElementException("Note with id " + id + " not found");
        }
        noteRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Note note) {
        if (note.getId() == null) {
            throw new IllegalArgumentException("Note id must not be null for update");
        }

        Note existing = noteRepository.findById(note.getId())
                .orElseThrow(() -> new java.util.NoSuchElementException("Note with id " + note.getId() + " not found"));

        existing.setTitle(note.getTitle());
        existing.setContent(note.getContent());
    }

    @Override
    public Note getById(long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new java.util.NoSuchElementException("Note with id " + id + " not found"));
    }
}
