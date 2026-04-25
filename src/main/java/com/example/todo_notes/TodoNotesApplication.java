package com.example.todo_notes;

import com.example.todo_notes.model.Note;
import com.example.todo_notes.service.NoteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoNotesApplication.class, args);
	}

	@Bean
	CommandLineRunner demo(NoteService noteService) {
		return args -> {
			Note n1 = new Note(null, "First", "Hello");
			noteService.add(n1);

			Note loaded = noteService.getById(n1.getId());
			System.out.println("Loaded note: id=" + loaded.getId() + ", title=" + loaded.getTitle());
			System.out.println("Total notes: " + noteService.listAll().size());
		};
	}
}
