package za.ac.tut.ptms.model;
public class Parent {
    private int parentId;
    private String name, surname, email, password;
    public Parent() {}
    public int    getParentId()  { return parentId; }
    public String getName()      { return name; }
    public String getSurname()   { return surname; }
    public String getEmail()     { return email; }
    public String getPassword()  { return password; }
    public void setParentId(int v)    { parentId = v; }
    public void setName(String v)     { name = v; }
    public void setSurname(String v)  { surname = v; }
    public void setEmail(String v)    { email = v; }
    public void setPassword(String v) { password = v; }
}
