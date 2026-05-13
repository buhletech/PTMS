package za.ac.tut.ptms.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import za.ac.tut.ptms.model.Parent;
import java.util.List;

@Repository
public class ParentRepository {

    private final JdbcTemplate jdbc;
    public ParentRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Parent> mapper = (rs, rn) -> {
        Parent p = new Parent();
        p.setParentId(rs.getInt("parent_id"));
        p.setName(rs.getString("name"));
        p.setSurname(rs.getString("surname"));
        p.setEmail(rs.getString("email"));
        p.setPassword(rs.getString("password"));
        return p;
    };

    public List<Parent> getAll() {
        return jdbc.query("SELECT * FROM parent ORDER BY parent_id", mapper);
    }

    public Parent getById(int id) {
        return jdbc.query("SELECT * FROM parent WHERE parent_id = ?", mapper, id)
                   .stream().findFirst().orElse(null);
    }

    public Parent getByEmail(String email) {
        return jdbc.query("SELECT * FROM parent WHERE LOWER(email) = LOWER(?)", mapper, email.trim())
                   .stream().findFirst().orElse(null);
    }

    public void insert(Parent p) {
        jdbc.update("INSERT INTO parent(name, surname, email, password) VALUES (?,?,?,?)",
                p.getName(), p.getSurname(), p.getEmail(), p.getPassword());
    }

    public void update(Parent p, boolean changePassword) {
        if (changePassword) {
            jdbc.update("UPDATE parent SET name=?, surname=?, email=?, password=? WHERE parent_id=?",
                    p.getName(), p.getSurname(), p.getEmail(), p.getPassword(), p.getParentId());
        } else {
            jdbc.update("UPDATE parent SET name=?, surname=?, email=? WHERE parent_id=?",
                    p.getName(), p.getSurname(), p.getEmail(), p.getParentId());
        }
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM parent WHERE parent_id=?", id);
    }
}
