package za.ac.tut.ptms.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import za.ac.tut.ptms.model.SchoolClass;
import java.util.List;

@Repository
public class SchoolClassRepository {

    private final JdbcTemplate jdbc;
    public SchoolClassRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<SchoolClass> mapper = (rs, rn) -> {
        SchoolClass c = new SchoolClass();
        c.setClassId(rs.getInt("class_id"));
        c.setClassName(rs.getString("class_name"));
        c.setGradeLevel(rs.getInt("grade_level"));
        return c;
    };

    public List<SchoolClass> getAll() {
        return jdbc.query("SELECT * FROM class ORDER BY grade_level, class_name", mapper);
    }

    public SchoolClass getById(int id) {
        return jdbc.query("SELECT * FROM class WHERE class_id = ?", mapper, id)
                   .stream().findFirst().orElse(null);
    }

    public void insert(SchoolClass c) {
        jdbc.update("INSERT INTO class(class_name, grade_level) VALUES (?,?)",
                c.getClassName(), c.getGradeLevel());
    }

    public void update(SchoolClass c) {
        jdbc.update("UPDATE class SET class_name=?, grade_level=? WHERE class_id=?",
                c.getClassName(), c.getGradeLevel(), c.getClassId());
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM class WHERE class_id=?", id);
    }

    public List<SchoolClass> getByTeacherId(int teacherId) {
        return jdbc.query(
                "SELECT DISTINCT c.* FROM class c " +
                        "JOIN subject s ON c.class_id = s.class_id " +
                        "WHERE s.teacher_id = ? ORDER BY c.grade_level, c.class_name",
                mapper, teacherId);
    }
}
