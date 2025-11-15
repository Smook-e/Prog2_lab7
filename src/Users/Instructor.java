package Users;

import java.util.*;

public class Instructor extends User {
    private List<String> createdCourses;
    public Instructor(String userID,String password,String userName,String email)
    {
        super( userID,  password,  userName, "Instructor", email);
        this.createdCourses=new ArrayList<>();
    }
    public List<String> getCreatedCourses()
    {
        return createdCourses;
    }
    public void addCreatedCourse(String courseId)
    {
        createdCourses.add(courseId);
    }
    public void removeCourse(String courseId)
    {
        createdCourses.remove(courseId);
    }
}
