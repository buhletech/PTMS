package za.ac.tut.ptms.model;

public class Teacher {
    private int teacherId;
    private String name;
    private String surname;
    private String email;
    private String password;

    public Teacher() {}

    public Teacher(int teacherId, String name, String surname, String email, String password) {
        this.teacherId = teacherId;
        this.name      = name;
        this.surname   = surname;
        this.email     = email;
        this.password  = password;
    }

    public int    getTeacherId()          { return teacherId; }
    public void   setTeacherId(int id)    { this.teacherId = id; }
    public String getName()               { return name; }
    public void   setName(String name)    { this.name = name; }
    public String getSurname()            { return surname; }
    public void   setSurname(String s)    { this.surname = s; }
    public String getEmail()              { return email; }
    public void   setEmail(String email)  { this.email = email; }
    public String getPassword()           { return password; }
    public void   setPassword(String p)   { this.password = p; }
}
