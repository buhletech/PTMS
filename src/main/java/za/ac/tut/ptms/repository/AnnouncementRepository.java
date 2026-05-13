package za.ac.tut.ptms.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import za.ac.tut.ptms.model.Announcement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AnnouncementRepository {

    private final JdbcTemplate jdbc;

    public AnnouncementRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Announcement> mapper = (rs, rowNum) -> {
        Announcement a = new Announcement();
        a.setAnnouncementId(rs.getInt("announcement_id"));
        a.setTitle(rs.getString("title"));
        a.setMessage(rs.getString("message"));
        a.setDatePosted(rs.getString("date_posted"));
        a.setTeacherId(rs.getInt("teacher_id"));
        a.setTeacherName(rs.getString("teacher_name"));
        return a;
    };

    public List<Announcement> getAll() {
        String sql = "SELECT ann.*, (t.name || ' ' || t.surname) AS teacher_name " +
                     "FROM announcement ann LEFT JOIN teacher t ON ann.teacher_id = t.teacher_id " +
                     "ORDER BY ann.date_posted DESC";
        return jdbc.query(sql, mapper);
    }

    public Announcement getById(int id) {
        String sql = "SELECT ann.*, (t.name || ' ' || t.surname) AS teacher_name " +
                     "FROM announcement ann LEFT JOIN teacher t ON ann.teacher_id = t.teacher_id " +
                     "WHERE ann.announcement_id = ?";
        return jdbc.query(sql, mapper, id).stream().findFirst().orElse(null);
    }

    public void insert(Announcement a) {
        jdbc.update("INSERT INTO announcement(title, message, date_posted, teacher_id) VALUES (?,?,?,?)",
                a.getTitle(), a.getMessage(), a.getDatePosted(), a.getTeacherId());
    }

    public void update(Announcement a) {
        jdbc.update("UPDATE announcement SET title=?, message=?, date_posted=?, teacher_id=? WHERE announcement_id=?",
                a.getTitle(), a.getMessage(), a.getDatePosted(), a.getTeacherId(), a.getAnnouncementId());
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM announcement WHERE announcement_id=?", id);
    }
}
