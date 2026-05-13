package za.ac.tut.ptms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Meeting;
import za.ac.tut.ptms.repository.MeetingRepository;
import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    private final MeetingRepository repo;
    public MeetingController(MeetingRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Meeting> getAll() { return repo.getAll(); }

    @GetMapping("/parent/{parentId}")
    public List<Meeting> getByParent(@PathVariable int parentId) {
        return repo.getByParentId(parentId);
    }

    @GetMapping("/teacher/{teacherId}")
    public List<Meeting> getByTeacher(@PathVariable int teacherId) {
        return repo.getByTeacherId(teacherId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meeting> getById(@PathVariable int id) {
        Meeting m = repo.getById(id);
        return m != null ? ResponseEntity.ok(m) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Meeting m) {
        repo.insert(m);
        return ResponseEntity.ok("Meeting created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Meeting m) {
        m.setMeetingId(id);
        repo.update(m);
        return ResponseEntity.ok("Meeting updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        repo.delete(id);
        return ResponseEntity.ok("Meeting deleted");
    }
}
