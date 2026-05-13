package za.ac.tut.ptms.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import za.ac.tut.ptms.model.Student;
import java.util.List;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbc;
    public StudentRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Student> mapper = (rs, rn) -> {
        Student s = new Student();
        s.setStudentId(rs.getInt("student_id"));
        s.setName(rs.getString("name"));
        s.setSurname(rs.getString("surname"));
        s.setGrade(rs.getString("grade"));
        s.setClassId(rs.getInt("class_id"));
        s.setClassName(rs.getString("class_name"));
        return s;
    };

    private static final String JOIN =
        "SELECT s.*, c.class_name FROM student s LEFT JOIN class c ON s.class_id = c.class_id ";

    public List<Student> getAll() {
        return jdbc.query(JOIN + "ORDER BY s.student_id", mapper);
    }

    public Student getById(int id) {
        return jdbc.query(JOIN + "WHERE s.student_id = ?", mapper, id)
                   .stream().findFirst().orElse(null);
    }

    public void insert(Student s) {
        jdbc.update("INSERT INTO student(name, surname, grade, class_id) VALUES (?,?,?,?)",
                s.getName(), s.getSurname(), s.getGrade(), s.getClassId());
    }

    public void update(Student s) {
        jdbc.update("UPDATE student SET name=?, surname=?, grade=?, class_id=? WHERE student_id=?",
                s.getName(), s.getSurname(), s.getGrade(), s.getClassId(), s.getStudentId());
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM student WHERE student_id=?", id);
    }

    public List<Student> getByTeacherId(int teacherId) {
        String sql = "SELECT s.*, c.class_name FROM student s " +
                "LEFT JOIN class c ON s.class_id = c.class_id " +
                "WHERE s.student_id IN " +
                "(SELECT student_id FROM teacher_student WHERE teacher_id = ?) " +
                "ORDER BY s.student_id";
        return jdbc.query(sql, mapper, teacherId);
    }

    public List<Student> getByClassId(int classId) {
        return jdbc.query(JOIN + "WHERE s.class_id = ?", mapper, classId);
    }

    public List<Student> getBySurname(String surname) {
        return jdbc.query(JOIN + "WHERE LOWER(s.surname) = LOWER(?)", mapper, surname);
    }
}
