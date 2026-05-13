package za.ac.tut.ptms.model;

public class Assessment {
    private int assessmentId;
    private String title;
    private String assessmentDate;
    private int subjectId;
    private String subjectName;
    private String className;      // ← NEW — which class this assessment belongs to

    public Assessment() {}

    public int    getAssessmentId()   { return assessmentId; }
    public String getTitle()          { return title; }
    public String getAssessmentDate() { return assessmentDate; }
    public int    getSubjectId()      { return subjectId; }
    public String getSubjectName()    { return subjectName; }
    public String getClassName()      { return className; }

    public void setAssessmentId(int v)       { assessmentId = v; }
    public void setTitle(String v)           { title = v; }
    public void setAssessmentDate(String v)  { assessmentDate = v; }
    public void setSubjectId(int v)          { subjectId = v; }
    public void setSubjectName(String v)     { subjectName = v; }
    public void setClassName(String v)       { className = v; }
}