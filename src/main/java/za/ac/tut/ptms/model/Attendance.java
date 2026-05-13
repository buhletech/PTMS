package za.ac.tut.ptms.model;
public class Attendance {
    private int attendanceId, studentId, subjectId;
    private String date, status, studentName, subjectName;
    public Attendance() {}
    public int    getAttendanceId() { return attendanceId; }
    public int    getStudentId()    { return studentId; }
    public int    getSubjectId()    { return subjectId; }
    public String getDate()         { return date; }
    public String getStatus()       { return status; }
    public String getStudentName()  { return studentName; }
    public String getSubjectName()  { return subjectName; }
    public void setAttendanceId(int v)    { attendanceId = v; }
    public void setStudentId(int v)       { studentId = v; }
    public void setSubjectId(int v)       { subjectId = v; }
    public void setDate(String v)         { date = v; }
    public void setStatus(String v)       { status = v; }
    public void setStudentName(String v)  { studentName = v; }
    public void setSubjectName(String v)  { subjectName = v; }
}
