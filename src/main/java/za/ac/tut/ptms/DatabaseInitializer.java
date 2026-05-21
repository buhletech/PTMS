package za.ac.tut.ptms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbc;
    public DatabaseInitializer(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @Override
    public void run(String... args) {

        jdbc.execute("CREATE TABLE IF NOT EXISTS teacher (" +
                "teacher_id SERIAL PRIMARY KEY," +
                "name VARCHAR(100)," +
                "surname VARCHAR(100)," +
                "email VARCHAR(150)," +
                "password VARCHAR(100))");

        jdbc.execute("CREATE TABLE IF NOT EXISTS parent (" +
                "parent_id SERIAL PRIMARY KEY," +
                "name VARCHAR(100)," +
                "surname VARCHAR(100)," +
                "email VARCHAR(150)," +
                "password VARCHAR(100))");

        jdbc.execute("CREATE TABLE IF NOT EXISTS class (" +
                "class_id SERIAL PRIMARY KEY," +
                "class_name VARCHAR(50)," +
                "grade_level INTEGER)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS student (" +
                "student_id SERIAL PRIMARY KEY," +
                "name VARCHAR(100)," +
                "surname VARCHAR(100)," +
                "grade VARCHAR(10)," +
                "class_id INTEGER)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS subject (" +
                "subject_id SERIAL PRIMARY KEY," +
                "subject_name VARCHAR(100)," +
                "teacher_id INTEGER," +
                "class_id INTEGER)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS assessment (" +
                "assessment_id SERIAL PRIMARY KEY," +
                "title VARCHAR(150)," +
                "assessment_date VARCHAR(20)," +
                "subject_id INTEGER)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS mark (" +
                "mark_id SERIAL PRIMARY KEY," +
                "student_id INTEGER," +
                "assessment_id INTEGER," +
                "mark_value INTEGER)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS attendance (" +
                "attendance_id SERIAL PRIMARY KEY," +
                "student_id INTEGER," +
                "subject_id INTEGER," +
                "date VARCHAR(20)," +
                "status VARCHAR(20))");

        jdbc.execute("CREATE TABLE IF NOT EXISTS meeting (" +
                "meeting_id SERIAL PRIMARY KEY," +
                "parent_id INTEGER," +
                "teacher_id INTEGER," +
                "meeting_date VARCHAR(20)," +
                "meeting_time VARCHAR(10)," +
                "notes TEXT)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS announcement (" +
                "announcement_id SERIAL PRIMARY KEY," +
                "title VARCHAR(150)," +
                "message TEXT," +
                "date_posted VARCHAR(20)," +
                "teacher_id INTEGER)");

        jdbc.execute("CREATE TABLE IF NOT EXISTS teacher_student (" +
                "teacher_id INTEGER," +
                "student_id INTEGER," +
                "PRIMARY KEY (teacher_id, student_id))");

        jdbc.execute("CREATE TABLE IF NOT EXISTS parent_student (" +
                "parent_id INTEGER," +
                "student_id INTEGER," +
                "PRIMARY KEY (parent_id, student_id))");

        System.out.println("✓ Database tables ready.");
    }
}