package za.ac.tut.ptms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbc;

    public DatabaseInitializer(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void run(String... args) {
        jdbc.execute("CREATE TABLE IF NOT EXISTS teacher (" +
                "teacher_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, surname TEXT, email TEXT, password TEXT)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS parent (" +
                "parent_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, surname TEXT, email TEXT, password TEXT)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS student (" +
                "student_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, surname TEXT, grade TEXT, class_id INTEGER)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS class (" +
                "class_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "class_name TEXT, grade_level INTEGER)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS subject (" +
                "subject_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "subject_name TEXT, teacher_id INTEGER, class_id INTEGER)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS assessment (" +
                "assessment_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT, assessment_date TEXT, subject_id INTEGER)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS mark (" +
                "mark_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "student_id INTEGER, assessment_id INTEGER, mark_value INTEGER)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS attendance (" +
                "attendance_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "student_id INTEGER, subject_id INTEGER, date TEXT, status TEXT)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS meeting (" +
                "meeting_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "parent_id INTEGER, teacher_id INTEGER," +
                "meeting_date TEXT, meeting_time TEXT, notes TEXT)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS announcement (" +
                "announcement_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT, message TEXT, date_posted TEXT, teacher_id INTEGER)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS parent_student (" +
                "parent_id INTEGER, student_id INTEGER)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS teacher_student (" +
                "teacher_id INTEGER, student_id INTEGER, " +
                "PRIMARY KEY (teacher_id, student_id))");

        System.out.println("✓ Database tables ready.");
    }
}