package za.ac.tut.ptms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Student;
import za.ac.tut.ptms.repository.StudentRepository;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository repo;
    public StudentController(StudentRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Student> getAll() { return repo.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable int id) {
        Student s = repo.getById(id);
        return s != null ? ResponseEntity.ok(s) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Student s) {
        repo.insert(s);
        return ResponseEntity.ok("Student created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Student s) {
        s.setStudentId(id);
        repo.update(s);
        return ResponseEntity.ok("Student updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        repo.delete(id);
        return ResponseEntity.ok("Student deleted");
    }
}
