package za.ac.tut.ptms.model;
public class Announcement {
    private int announcementId;
    private String title, message, datePosted;
    private int teacherId;
    private String teacherName;
    public Announcement() {}
    public int    getAnnouncementId() { return announcementId; }
    public String getTitle()          { return title; }
    public String getMessage()        { return message; }
    public String getDatePosted()     { return datePosted; }
    public int    getTeacherId()      { return teacherId; }
    public String getTeacherName()    { return teacherName; }
    public void setAnnouncementId(int v)  { announcementId = v; }
    public void setTitle(String v)        { title = v; }
    public void setMessage(String v)      { message = v; }
    public void setDatePosted(String v)   { datePosted = v; }
    public void setTeacherId(int v)       { teacherId = v; }
    public void setTeacherName(String v)  { teacherName = v; }
}
