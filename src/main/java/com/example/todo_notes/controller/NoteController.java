package com.example.todo_notes.controller;
import com.example.todo_notes.dto.NoteEditDto;
import com.example.todo_notes.model.Note;
import com.example.todo_notes.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("notes", noteService.listAll());
        return "note-list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id) {
        noteService.deleteById(id);
        return "redirect:/note/list";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam("id") long id, Model model) {
        Note note = noteService.getById(id);

        NoteEditDto dto = new NoteEditDto();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());

        model.addAttribute("note", dto);
        return "note-edit";
    }

    @PostMapping("/edit")
    public String editSubmit(
            @ModelAttribute("note") @Valid NoteEditDto dto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "note-edit"; //дебаг
        }

        Note updated = new Note();
        updated.setId(dto.getId());
        updated.setTitle(dto.getTitle());
        updated.setContent(dto.getContent());

        noteService.update(updated);
        return "redirect:/note/list";
    }
}