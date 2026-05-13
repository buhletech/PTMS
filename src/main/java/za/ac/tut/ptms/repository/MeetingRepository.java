package za.ac.tut.ptms.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import za.ac.tut.ptms.model.Meeting;
import java.util.List;

@Repository
public class MeetingRepository {

    private final JdbcTemplate jdbc;
    public MeetingRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Meeting> mapper = (rs, rn) -> {
        Meeting m = new Meeting();
        m.setMeetingId(rs.getInt("meeting_id"));
        m.setParentId(rs.getInt("parent_id"));
        m.setTeacherId(rs.getInt("teacher_id"));
        m.setMeetingDate(rs.getString("meeting_date"));
        m.setMeetingTime(rs.getString("meeting_time"));
        m.setNotes(rs.getString("notes"));
        m.setParentName(rs.getString("parent_name"));
        m.setTeacherName(rs.getString("teacher_name"));
        return m;
    };

    private static final String JOIN =
        "SELECT m.*, (p.name || ' ' || p.surname) AS parent_name, " +
        "(t.name || ' ' || t.surname) AS teacher_name " +
        "FROM meeting m " +
        "LEFT JOIN parent p ON m.parent_id = p.parent_id " +
        "LEFT JOIN teacher t ON m.teacher_id = t.teacher_id ";

    public List<Meeting> getAll() {
        return jdbc.query(JOIN + "ORDER BY m.meeting_date DESC", mapper);
    }

    public List<Meeting> getByParentId(int parentId) {
        return jdbc.query(JOIN + "WHERE m.parent_id = ? ORDER BY m.meeting_date DESC", mapper, parentId);
    }

    public List<Meeting> getByTeacherId(int teacherId) {
        return jdbc.query(JOIN + "WHERE m.teacher_id = ? ORDER BY m.meeting_date DESC", mapper, teacherId);
    }

    public Meeting getById(int id) {
        return jdbc.query(JOIN + "WHERE m.meeting_id = ?", mapper, id)
                   .stream().findFirst().orElse(null);
    }

    public void insert(Meeting m) {
        jdbc.update("INSERT INTO meeting(parent_id, teacher_id, meeting_date, meeting_time, notes) VALUES (?,?,?,?,?)",
                m.getParentId(), m.getTeacherId(), m.getMeetingDate(), m.getMeetingTime(), m.getNotes());
    }

    public void update(Meeting m) {
        jdbc.update("UPDATE meeting SET parent_id=?, teacher_id=?, meeting_date=?, meeting_time=?, notes=? WHERE meeting_id=?",
                m.getParentId(), m.getTeacherId(), m.getMeetingDate(), m.getMeetingTime(), m.getNotes(), m.getMeetingId());
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM meeting WHERE meeting_id=?", id);
    }
}
