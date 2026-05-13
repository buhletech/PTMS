package za.ac.tut.ptms.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Replaces StoredProcedureDAO.
 *
 * SQLite does not support the {CALL ...} syntax used in the original project.
 * Each method here replicates the stored-procedure logic as a @Transactional
 * Spring service method — behaviour is identical, fully ACID-safe.
 */
@Service
public class StoredProcedureService {

    private final JdbcTemplate jdbc;
    public StoredProcedureService(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    /**
     * SP1 – EnrolStudentInClass
     * Inserts a new student and links them to a parent.
     */
    @Transactional
    public void enrolStudentInClass(String name, String surname,
                                     String grade, int classId, int parentId) {
        jdbc.update("INSERT INTO student(name, surname, grade, class_id) VALUES (?,?,?,?)",
                name, surname, grade, classId);

        // Retrieve the new student's generated ID
        Integer studentId = jdbc.queryForObject(
                "SELECT student_id FROM student WHERE name=? AND surname=? ORDER BY student_id DESC LIMIT 1",
                Integer.class, name, surname);

        jdbc.update("INSERT INTO parent_student(parent_id, student_id) VALUES (?,?)",
                parentId, studentId);
    }

    /**
     * SP2 – RecordAttendanceAndNotify
     * Records attendance and auto-schedules a meeting if the student is Absent.
     */
    @Transactional
    public void recordAttendanceAndNotify(int studentId, int subjectId,
                                           String date, String status,
                                           int parentId, int teacherId) {
        jdbc.update("INSERT INTO attendance(student_id, subject_id, date, status) VALUES (?,?,?,?)",
                studentId, subjectId, date, status);

        if ("Absent".equalsIgnoreCase(status)) {
            jdbc.update("INSERT INTO meeting(parent_id, teacher_id, meeting_date, meeting_time, notes) " +
                        "VALUES (?,?,?,?,?)",
                    parentId, teacherId, date, "08:00",
                    "Auto-scheduled: student was absent on " + date);
        }
    }

    /**
     * SP3 – ScheduleMeetingWithAnnouncement
     * Creates a meeting and posts a related announcement.
     */
    @Transactional
    public void scheduleMeetingWithAnnouncement(int parentId, int teacherId,
                                                 String date, String time, String notes) {
        jdbc.update("INSERT INTO meeting(parent_id, teacher_id, meeting_date, meeting_time, notes) VALUES (?,?,?,?,?)",
                parentId, teacherId, date, time, notes);

        jdbc.update("INSERT INTO announcement(title, message, date_posted, teacher_id) VALUES (?,?,?,?)",
                "Meeting Scheduled",
                "A meeting has been scheduled for " + date + " at " + time + ". Notes: " + notes,
                date, teacherId);
    }

    /**
     * SP4 – DeleteStudentCascade
     * Deletes a student and all related records.
     */
    @Transactional
    public void deleteStudentCascade(int studentId) {
        jdbc.update("DELETE FROM mark        WHERE student_id = ?", studentId);
        jdbc.update("DELETE FROM attendance  WHERE student_id = ?", studentId);
        jdbc.update("DELETE FROM parent_student WHERE student_id = ?", studentId);
        jdbc.update("DELETE FROM student     WHERE student_id = ?", studentId);
    }
}
