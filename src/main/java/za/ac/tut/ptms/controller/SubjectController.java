package za.ac.tut.ptms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Subject;
import za.ac.tut.ptms.repository.SubjectRepository;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectRepository repo;
    public SubjectController(SubjectRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Subject> getAll() { return repo.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getById(@PathVariable int id) {
        Subject s = repo.getById(id);
        return s != null ? ResponseEntity.ok(s) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Subject s) {
        repo.insert(s);
        return ResponseEntity.ok("Subject created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Subject s) {
        s.setSubjectId(id);
        repo.update(s);
        return ResponseEntity.ok("Subject updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        repo.delete(id);
        return ResponseEntity.ok("Subject deleted");
    }
}
