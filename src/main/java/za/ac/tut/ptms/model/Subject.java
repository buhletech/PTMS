package za.ac.tut.ptms.model;
public class Subject {
    private int subjectId, teacherId, classId;
    private String subjectName, teacherName, className;
    public Subject() {}
    public int    getSubjectId()    { return subjectId; }
    public String getSubjectName()  { return subjectName; }
    public int    getTeacherId()    { return teacherId; }
    public int    getClassId()      { return classId; }
    public String getTeacherName()  { return teacherName; }
    public String getClassName()    { return className; }
    public void setSubjectId(int v)       { subjectId = v; }
    public void setSubjectName(String v)  { subjectName = v; }
    public void setTeacherId(int v)       { teacherId = v; }
    public void setClassId(int v)         { classId = v; }
    public void setTeacherName(String v)  { teacherName = v; }
    public void setClassName(String v)    { className = v; }
}
