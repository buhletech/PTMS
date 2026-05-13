package za.ac.tut.ptms.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import za.ac.tut.ptms.model.Attendance;
import java.util.List;

@Repository
public class AttendanceRepository {

    private final JdbcTemplate jdbc;
    public AttendanceRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Attendance> mapper = (rs, rn) -> {
        Attendance a = new Attendance();
        a.setAttendanceId(rs.getInt("attendance_id"));
        a.setStudentId(rs.getInt("student_id"));
        a.setSubjectId(rs.getInt("subject_id"));
        a.setDate(rs.getString("date"));
        a.setStatus(rs.getString("status"));
        a.setStudentName(rs.getString("student_name"));
        a.setSubjectName(rs.getString("subject_name"));
        return a;
    };

    private static final String JOIN =
        "SELECT att.*, (s.name || ' ' || s.surname) AS student_name, sub.subject_name " +
        "FROM attendance att " +
        "LEFT JOIN student s ON att.student_id = s.student_id " +
        "LEFT JOIN subject sub ON att.subject_id = sub.subject_id ";

    public List<Attendance> getAll() {
        return jdbc.query(JOIN + "ORDER BY att.date DESC", mapper);
    }

    public List<Attendance> getByStudentId(int studentId) {
        return jdbc.query(JOIN + "WHERE att.student_id = ? ORDER BY att.date DESC", mapper, studentId);
    }

    public Attendance getById(int id) {
        return jdbc.query(JOIN + "WHERE att.attendance_id = ?", mapper, id)
                   .stream().findFirst().orElse(null);
    }

    public void insert(Attendance a) {
        jdbc.update("INSERT INTO attendance(student_id, subject_id, date, status) VALUES (?,?,?,?)",
                a.getStudentId(), a.getSubjectId(), a.getDate(), a.getStatus());
    }

    public void update(Attendance a) {
        jdbc.update("UPDATE attendance SET student_id=?, subject_id=?, date=?, status=? WHERE attendance_id=?",
                a.getStudentId(), a.getSubjectId(), a.getDate(), a.getStatus(), a.getAttendanceId());
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM attendance WHERE attendance_id=?", id);
    }

    public List<Attendance> getByClassId(int classId, int teacherId) {
        String sql = "SELECT att.*, (s.name || ' ' || s.surname) AS student_name, " +
                "sub.subject_name " +
                "FROM attendance att " +
                "LEFT JOIN student s ON att.student_id = s.student_id " +
                "LEFT JOIN subject sub ON att.subject_id = sub.subject_id " +
                "WHERE s.class_id = ? AND sub.teacher_id = ? " +
                "ORDER BY att.date DESC LIMIT 50";
        return jdbc.query(sql, mapper, classId, teacherId);
    }
}
