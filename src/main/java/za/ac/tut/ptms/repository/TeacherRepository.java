package za.ac.tut.ptms.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import za.ac.tut.ptms.model.Teacher;
import java.util.List;

@Repository
public class TeacherRepository {

    private final JdbcTemplate jdbc;
    public TeacherRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Teacher> mapper = (rs, rn) -> new Teacher(
            rs.getInt("teacher_id"),
            rs.getString("name"),
            rs.getString("surname"),
            rs.getString("email"),
            rs.getString("password")
    );

    public List<Teacher> getAll() {
        return jdbc.query("SELECT * FROM teacher", mapper);
    }

    public Teacher getById(int id) {
        return jdbc.query("SELECT * FROM teacher WHERE teacher_id = ?", mapper, id)
                .stream().findFirst().orElse(null);
    }

    public Teacher getByEmail(String email) {
        return jdbc.query("SELECT * FROM teacher WHERE LOWER(email) = ?", mapper, email.toLowerCase())
                .stream().findFirst().orElse(null);
    }

    public void insert(Teacher t) {
        jdbc.update("INSERT INTO teacher(name, surname, email, password) VALUES (?,?,?,?)",
                t.getName(), t.getSurname(), t.getEmail(), t.getPassword());
    }

    public void update(Teacher t) {
        jdbc.update("UPDATE teacher SET name=?, surname=?, email=?, password=? WHERE teacher_id=?",
                t.getName(), t.getSurname(), t.getEmail(), t.getPassword(), t.getTeacherId());
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM teacher WHERE teacher_id=?", id);
    }
}