package za.ac.tut.ptms.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import za.ac.tut.ptms.model.Subject;
import java.util.List;

@Repository
public class SubjectRepository {

    private final JdbcTemplate jdbc;
    public SubjectRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Subject> mapper = (rs, rn) -> {
        Subject s = new Subject();
        s.setSubjectId(rs.getInt("subject_id"));
        s.setSubjectName(rs.getString("subject_name"));
        s.setTeacherId(rs.getInt("teacher_id"));
        s.setClassId(rs.getInt("class_id"));
        s.setTeacherName(rs.getString("teacher_name"));
        s.setClassName(rs.getString("class_name"));
        return s;
    };

    private static final String JOIN =
        "SELECT s.*, (t.name || ' ' || t.surname) AS teacher_name, c.class_name " +
        "FROM subject s " +
        "LEFT JOIN teacher t ON s.teacher_id = t.teacher_id " +
        "LEFT JOIN class c ON s.class_id = c.class_id ";

    public List<Subject> getAll() {
        return jdbc.query(JOIN + "ORDER BY s.subject_name", mapper);
    }

    public Subject getById(int id) {
        return jdbc.query(JOIN + "WHERE s.subject_id = ?", mapper, id)
                   .stream().findFirst().orElse(null);
    }

    public void insert(Subject s) {
        jdbc.update("INSERT INTO subject(subject_name, teacher_id, class_id) VALUES (?,?,?)",
                s.getSubjectName(), s.getTeacherId(), s.getClassId());
    }

    public void update(Subject s) {
        jdbc.update("UPDATE subject SET subject_name=?, teacher_id=?, class_id=? WHERE subject_id=?",
                s.getSubjectName(), s.getTeacherId(), s.getClassId(), s.getSubjectId());
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM subject WHERE subject_id=?", id);
    }

    public List<Subject> getByTeacherId(int teacherId) {
        return jdbc.query(JOIN + "WHERE s.teacher_id = ?", mapper, teacherId);
    }

    public List<Subject> getByTeacherAndClass(int teacherId, int classId) {
        return jdbc.query(JOIN + "WHERE s.teacher_id = ? AND s.class_id = ?",
                mapper, teacherId, classId);
    }
}
