package com.example.todo_notes.service;
import com.example.todo_notes.model.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NoteServiceImpl implements NoteService {

    private final ConcurrentHashMap<Long, Note> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public List<Note> listAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Note add(Note note) {
        long id = idGenerator.incrementAndGet();
        note.setId(id);
        storage.put(id, note);
        return note;
    }

    @Override
    public void deleteById(long id) {
        Note removed = storage.remove(id);
        if (removed == null) {
            throw new NoSuchElementException("Note with id " + id + " not found");
        }
    }

    @Override
    public void update(Note note) {
        if (note.getId() == null) {
            throw new IllegalArgumentException("Note id must not be null for update");
        }

        Note existing = storage.get(note.getId());
        if (existing == null) {
            throw new NoSuchElementException("Note with id " + note.getId() + " not found");
        }

        existing.setTitle(note.getTitle());
        existing.setContent(note.getContent());
    }

    @Override
    public Note getById(long id) {
        Note note = storage.get(id);
        if (note == null) {
            throw new NoSuchElementException("Note with id " + id + " not found");
        }
        return note;
    }
}
