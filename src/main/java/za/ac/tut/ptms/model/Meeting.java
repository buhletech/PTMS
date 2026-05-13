package za.ac.tut.ptms.model;
public class Meeting {
    private int meetingId, parentId, teacherId;
    private String meetingDate, meetingTime, notes, parentName, teacherName;
    public Meeting() {}
    public int    getMeetingId()   { return meetingId; }
    public int    getParentId()    { return parentId; }
    public int    getTeacherId()   { return teacherId; }
    public String getMeetingDate() { return meetingDate; }
    public String getMeetingTime() { return meetingTime; }
    public String getNotes()       { return notes; }
    public String getParentName()  { return parentName; }
    public String getTeacherName() { return teacherName; }
    public void setMeetingId(int v)       { meetingId = v; }
    public void setParentId(int v)        { parentId = v; }
    public void setTeacherId(int v)       { teacherId = v; }
    public void setMeetingDate(String v)  { meetingDate = v; }
    public void setMeetingTime(String v)  { meetingTime = v; }
    public void setNotes(String v)        { notes = v; }
    public void setParentName(String v)   { parentName = v; }
    public void setTeacherName(String v)  { teacherName = v; }
}
