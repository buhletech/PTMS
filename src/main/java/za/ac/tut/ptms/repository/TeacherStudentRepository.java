package za.ac.tut.ptms.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TeacherStudentRepository {

    private final JdbcTemplate jdbc;
    public TeacherStudentRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public List<Integer> getStudentIdsByTeacherId(int teacherId) {
        return jdbc.queryForList(
                "SELECT student_id FROM teacher_student WHERE teacher_id = ?",
                Integer.class, teacherId);
    }

    public void allocate(int teacherId, int studentId) {
        jdbc.update(
                "INSERT OR IGNORE INTO teacher_student(teacher_id, student_id) VALUES (?,?)",
                teacherId, studentId);
    }

    public void removeAll(int teacherId) {
        jdbc.update("DELETE FROM teacher_student WHERE teacher_id = ?", teacherId);
    }
}
