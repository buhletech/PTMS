package za.ac.tut.ptms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.service.StoredProcedureService;

@RestController
@RequestMapping("/api/procedures")
public class StoredProcedureController {

    private final StoredProcedureService service;
    public StoredProcedureController(StoredProcedureService service) { this.service = service; }

    /** POST /api/procedures/enrol */
    @PostMapping("/enrol")
    public ResponseEntity<String> enrol(@RequestParam String name,
                                         @RequestParam String surname,
                                         @RequestParam String grade,
                                         @RequestParam int classId,
                                         @RequestParam int parentId) {
        service.enrolStudentInClass(name, surname, grade, classId, parentId);
        return ResponseEntity.ok("Student enrolled");
    }

    /** POST /api/procedures/record-attendance */
    @PostMapping("/record-attendance")
    public ResponseEntity<String> recordAttendance(@RequestParam int studentId,
                                                    @RequestParam int subjectId,
                                                    @RequestParam String date,
                                                    @RequestParam String status,
                                                    @RequestParam int parentId,
                                                    @RequestParam int teacherId) {
        service.recordAttendanceAndNotify(studentId, subjectId, date, status, parentId, teacherId);
        return ResponseEntity.ok("Attendance recorded");
    }

    /** POST /api/procedures/schedule-meeting */
    @PostMapping("/schedule-meeting")
    public ResponseEntity<String> scheduleMeeting(@RequestParam int parentId,
                                                   @RequestParam int teacherId,
                                                   @RequestParam String date,
                                                   @RequestParam String time,
                                                   @RequestParam String notes) {
        service.scheduleMeetingWithAnnouncement(parentId, teacherId, date, time, notes);
        return ResponseEntity.ok("Meeting scheduled with announcement");
    }

    /** DELETE /api/procedures/delete-student/{id} */
    @DeleteMapping("/delete-student/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        service.deleteStudentCascade(id);
        return ResponseEntity.ok("Student and all related records deleted");
    }
}
