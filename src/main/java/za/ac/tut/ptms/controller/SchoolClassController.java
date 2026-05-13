package za.ac.tut.ptms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.SchoolClass;
import za.ac.tut.ptms.repository.SchoolClassRepository;
import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class SchoolClassController {

    private final SchoolClassRepository repo;
    public SchoolClassController(SchoolClassRepository repo) { this.repo = repo; }

    @GetMapping
    public List<SchoolClass> getAll() { return repo.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolClass> getById(@PathVariable int id) {
        SchoolClass c = repo.getById(id);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody SchoolClass c) {
        repo.insert(c);
        return ResponseEntity.ok("Class created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody SchoolClass c) {
        c.setClassId(id);
        repo.update(c);
        return ResponseEntity.ok("Class updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        repo.delete(id);
        return ResponseEntity.ok("Class deleted");
    }
}
