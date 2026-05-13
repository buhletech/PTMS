package za.ac.tut.ptms.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import za.ac.tut.ptms.model.Assessment;
import java.util.List;

@Repository
public class AssessmentRepository {

    private final JdbcTemplate jdbc;
    public AssessmentRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Assessment> mapper = (rs, rn) -> {
        Assessment a = new Assessment();
        a.setAssessmentId(rs.getInt("assessment_id"));
        a.setTitle(rs.getString("title"));
        a.setAssessmentDate(rs.getString("assessment_date"));
        a.setSubjectId(rs.getInt("subject_id"));
        a.setSubjectName(rs.getString("subject_name"));
        a.setClassName(rs.getString("class_name"));
        return a;
    };

    // JOIN now includes the class table so we get class_name
    private static final String JOIN =
            "SELECT a.*, s.subject_name, c.class_name " +
                    "FROM assessment a " +
                    "LEFT JOIN subject s ON a.subject_id = s.subject_id " +
                    "LEFT JOIN class c   ON s.class_id   = c.class_id ";

    public List<Assessment> getAll() {
        return jdbc.query(JOIN + "ORDER BY a.assessment_date DESC", mapper);
    }

    public Assessment getById(int id) {
        return jdbc.query(JOIN + "WHERE a.assessment_id = ?", mapper, id)
                .stream().findFirst().orElse(null);
    }

    /** Only assessments for subjects taught by this teacher */
    public List<Assessment> getByTeacherId(int teacherId) {
        return jdbc.query(
                JOIN + "WHERE s.teacher_id = ? ORDER BY a.assessment_date DESC",
                mapper, teacherId);
    }

    public void insert(Assessment a) {
        jdbc.update(
                "INSERT INTO assessment(title, assessment_date, subject_id) VALUES (?,?,?)",
                a.getTitle(), a.getAssessmentDate(), a.getSubjectId());
    }

    public void update(Assessment a) {
        jdbc.update(
                "UPDATE assessment SET title=?, assessment_date=?, subject_id=? WHERE assessment_id=?",
                a.getTitle(), a.getAssessmentDate(), a.getSubjectId(), a.getAssessmentId());
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM assessment WHERE assessment_id=?", id);
    }
}