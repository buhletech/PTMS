package za.ac.tut.ptms.model;
public class SchoolClass {
    private int classId, gradeLevel;
    private String className;
    public SchoolClass() {}
    public int    getClassId()    { return classId; }
    public String getClassName()  { return className; }
    public int    getGradeLevel() { return gradeLevel; }
    public void setClassId(int v)       { classId = v; }
    public void setClassName(String v)  { className = v; }
    public void setGradeLevel(int v)    { gradeLevel = v; }
}
