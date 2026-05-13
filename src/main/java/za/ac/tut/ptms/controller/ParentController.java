package za.ac.tut.ptms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Parent;
import za.ac.tut.ptms.repository.ParentRepository;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    private final ParentRepository repo;
    public ParentController(ParentRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Parent> getAll() { return repo.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Parent> getById(@PathVariable int id) {
        Parent p = repo.getById(id);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    @GetMapping("/by-email")
    public ResponseEntity<Parent> getByEmail(@RequestParam String email) {
        Parent p = repo.getByEmail(email);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Parent p) {
        repo.insert(p);
        return ResponseEntity.ok("Parent created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id,
                                          @RequestBody Parent p,
                                          @RequestParam(defaultValue = "false") boolean changePassword) {
        p.setParentId(id);
        repo.update(p, changePassword);
        return ResponseEntity.ok("Parent updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        repo.delete(id);
        return ResponseEntity.ok("Parent deleted");
    }
}
