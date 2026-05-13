CREATE TABLE IF NOT EXISTS teacher (
                                       teacher_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                       name TEXT, surname TEXT, email TEXT, password TEXT
);
CREATE TABLE IF NOT EXISTS parent (
                                      parent_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                      name TEXT, surname TEXT, email TEXT, password TEXT
);
CREATE TABLE IF NOT EXISTS student (
                                       student_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                       name TEXT, surname TEXT, grade TEXT, class_id INTEGER
);
CREATE TABLE IF NOT EXISTS class (
                                     class_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                     class_name TEXT, grade_level INTEGER
);
CREATE TABLE IF NOT EXISTS subject (
                                       subject_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                       subject_name TEXT, teacher_id INTEGER, class_id INTEGER
);
CREATE TABLE IF NOT EXISTS assessment (
                                          assessment_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                          title TEXT, assessment_date TEXT, subject_id INTEGER
);
CREATE TABLE IF NOT EXISTS mark (
                                    mark_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                    student_id INTEGER, assessment_id INTEGER, mark_value INTEGER
);
CREATE TABLE IF NOT EXISTS attendance (
                                          attendance_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                          student_id INTEGER, subject_id INTEGER, date TEXT, status TEXT
);
CREATE TABLE IF NOT EXISTS meeting (
                                       meeting_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                       parent_id INTEGER, teacher_id INTEGER,
                                       meeting_date TEXT, meeting_time TEXT, notes TEXT
);
CREATE TABLE IF NOT EXISTS announcement (
                                            announcement_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                            title TEXT, message TEXT, date_posted TEXT, teacher_id INTEGER
);
CREATE TABLE IF NOT EXISTS parent_student (
                                              parent_id INTEGER, student_id INTEGER
);