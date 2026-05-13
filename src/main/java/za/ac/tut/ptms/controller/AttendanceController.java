package za.ac.tut.ptms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Attendance;
import za.ac.tut.ptms.repository.AttendanceRepository;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceRepository repo;
    public AttendanceController(AttendanceRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Attendance> getAll() { return repo.getAll(); }

    @GetMapping("/student/{studentId}")
    public List<Attendance> getByStudent(@PathVariable int studentId) {
        return repo.getByStudentId(studentId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getById(@PathVariable int id) {
        Attendance a = repo.getById(id);
        return a != null ? ResponseEntity.ok(a) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Attendance a) {
        repo.insert(a);
        return ResponseEntity.ok("Attendance recorded");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Attendance a) {
        a.setAttendanceId(id);
        repo.update(a);
        return ResponseEntity.ok("Attendance updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        repo.delete(id);
        return ResponseEntity.ok("Attendance deleted");
    }
}
