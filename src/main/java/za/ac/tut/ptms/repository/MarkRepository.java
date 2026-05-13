package za.ac.tut.ptms.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import za.ac.tut.ptms.model.Mark;
import java.util.List;

@Repository
public class MarkRepository {

    private final JdbcTemplate jdbc;
    public MarkRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Mark> mapper = (rs, rn) -> {
        Mark m = new Mark();
        m.setMarkId(rs.getInt("mark_id"));
        m.setStudentId(rs.getInt("student_id"));
        m.setAssessmentId(rs.getInt("assessment_id"));
        m.setMarkValue(rs.getInt("mark_value"));
        m.setStudentName(rs.getString("student_name"));
        m.setAssessmentTitle(rs.getString("assessment_title"));
        return m;
    };

    private static final String JOIN =
        "SELECT m.*, (s.name || ' ' || s.surname) AS student_name, a.title AS assessment_title " +
        "FROM mark m " +
        "LEFT JOIN student s ON m.student_id = s.student_id " +
        "LEFT JOIN assessment a ON m.assessment_id = a.assessment_id ";

    public List<Mark> getAll() {
        return jdbc.query(JOIN + "ORDER BY m.mark_id", mapper);
    }

    public List<Mark> getByStudentId(int studentId) {
        return jdbc.query(JOIN + "WHERE m.student_id = ?", mapper, studentId);
    }

    public Mark getById(int id) {
        return jdbc.query(JOIN + "WHERE m.mark_id = ?", mapper, id)
                   .stream().findFirst().orElse(null);
    }

    public void insert(Mark m) {
        jdbc.update("INSERT INTO mark(student_id, assessment_id, mark_value) VALUES (?,?,?)",
                m.getStudentId(), m.getAssessmentId(), m.getMarkValue());
    }

    public void update(Mark m) {
        jdbc.update("UPDATE mark SET student_id=?, assessment_id=?, mark_value=? WHERE mark_id=?",
                m.getStudentId(), m.getAssessmentId(), m.getMarkValue(), m.getMarkId());
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM mark WHERE mark_id=?", id);
    }

    public List<Mark> getByClassId(int classId, int teacherId) {
        String sql = "SELECT m.*, (s.name || ' ' || s.surname) AS student_name, " +
                "a.title AS assessment_title " +
                "FROM mark m " +
                "LEFT JOIN student s ON m.student_id = s.student_id " +
                "LEFT JOIN assessment a ON m.assessment_id = a.assessment_id " +
                "WHERE s.class_id = ? AND a.subject_id IN " +
                "(SELECT subject_id FROM subject WHERE teacher_id = ?) " +
                "ORDER BY m.mark_id DESC";
        return jdbc.query(sql, mapper, classId, teacherId);
    }
}
