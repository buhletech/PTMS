package za.ac.tut.ptms.model;
public class Mark {
    private int markId, studentId, assessmentId, markValue;
    private String studentName, assessmentTitle;
    public Mark() {}
    public int    getMarkId()          { return markId; }
    public int    getStudentId()       { return studentId; }
    public int    getAssessmentId()    { return assessmentId; }
    public int    getMarkValue()       { return markValue; }
    public String getStudentName()     { return studentName; }
    public String getAssessmentTitle() { return assessmentTitle; }
    public void setMarkId(int v)            { markId = v; }
    public void setStudentId(int v)         { studentId = v; }
    public void setAssessmentId(int v)      { assessmentId = v; }
    public void setMarkValue(int v)         { markValue = v; }
    public void setStudentName(String v)    { studentName = v; }
    public void setAssessmentTitle(String v){ assessmentTitle = v; }
}
