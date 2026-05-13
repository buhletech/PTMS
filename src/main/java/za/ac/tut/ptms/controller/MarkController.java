package za.ac.tut.ptms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Mark;
import za.ac.tut.ptms.repository.MarkRepository;
import java.util.List;

@RestController
@RequestMapping("/api/marks")
public class MarkController {

    private final MarkRepository repo;
    public MarkController(MarkRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Mark> getAll() { return repo.getAll(); }

    @GetMapping("/student/{studentId}")
    public List<Mark> getByStudent(@PathVariable int studentId) {
        return repo.getByStudentId(studentId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mark> getById(@PathVariable int id) {
        Mark m = repo.getById(id);
        return m != null ? ResponseEntity.ok(m) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Mark m) {
        repo.insert(m);
        return ResponseEntity.ok("Mark created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Mark m) {
        m.setMarkId(id);
        repo.update(m);
        return ResponseEntity.ok("Mark updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        repo.delete(id);
        return ResponseEntity.ok("Mark deleted");
    }
}
