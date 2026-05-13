package za.ac.tut.ptms.model;
public class Student {
    private int studentId, classId;
    private String name, surname, grade, className;
    public Student() {}
    public int    getStudentId()  { return studentId; }
    public String getName()       { return name; }
    public String getSurname()    { return surname; }
    public String getGrade()      { return grade; }
    public int    getClassId()    { return classId; }
    public String getClassName()  { return className; }
    public void setStudentId(int v)    { studentId = v; }
    public void setName(String v)      { name = v; }
    public void setSurname(String v)   { surname = v; }
    public void setGrade(String v)     { grade = v; }
    public void setClassId(int v)      { classId = v; }
    public void setClassName(String v) { className = v; }
}
