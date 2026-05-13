package za.ac.tut.ptms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Announcement;
import za.ac.tut.ptms.repository.AnnouncementRepository;
import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementRepository repo;
    public AnnouncementController(AnnouncementRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Announcement> getAll() { return repo.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Announcement> getById(@PathVariable int id) {
        Announcement a = repo.getById(id);
        return a != null ? ResponseEntity.ok(a) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Announcement a) {
        repo.insert(a);
        return ResponseEntity.ok("Announcement created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Announcement a) {
        a.setAnnouncementId(id);
        repo.update(a);
        return ResponseEntity.ok("Announcement updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        repo.delete(id);
        return ResponseEntity.ok("Announcement deleted");
    }
}
