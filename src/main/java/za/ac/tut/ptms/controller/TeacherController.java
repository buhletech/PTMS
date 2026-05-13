package za.ac.tut.ptms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Teacher;
import za.ac.tut.ptms.repository.TeacherRepository;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherRepository repo;
    public TeacherController(TeacherRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Teacher> getAll() { return repo.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getById(@PathVariable int id) {
        Teacher t = repo.getById(id);
        return t != null ? ResponseEntity.ok(t) : ResponseEntity.notFound().build();
    }

    @GetMapping("/by-email")
    public ResponseEntity<Teacher> getByEmail(@RequestParam String email) {
        Teacher t = repo.getByEmail(email);
        return t != null ? ResponseEntity.ok(t) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Teacher t) {
        repo.insert(t);
        return ResponseEntity.ok("Teacher created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Teacher t) {
        t.setTeacherId(id);
        repo.update(t);
        return ResponseEntity.ok("Teacher updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        repo.delete(id);
        return ResponseEntity.ok("Teacher deleted");
    }
}
