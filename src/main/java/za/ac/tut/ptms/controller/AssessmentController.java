package za.ac.tut.ptms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Assessment;
import za.ac.tut.ptms.repository.AssessmentRepository;
import java.util.List;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentRepository repo;
    public AssessmentController(AssessmentRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Assessment> getAll() { return repo.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Assessment> getById(@PathVariable int id) {
        Assessment a = repo.getById(id);
        return a != null ? ResponseEntity.ok(a) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Assessment a) {
        repo.insert(a);
        return ResponseEntity.ok("Assessment created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Assessment a) {
        a.setAssessmentId(id);
        repo.update(a);
        return ResponseEntity.ok("Assessment updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        repo.delete(id);
        return ResponseEntity.ok("Assessment deleted");
    }
}
