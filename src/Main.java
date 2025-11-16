import Courses.Course;
import Courses.Lesson;
import JSON.CourseService;
import JSON.JsonDatabaseManager;
import JSON.UserService;
import Users.User;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        CourseService c1 = new CourseService("C:\\Users\\Mega Store\\Documents\\NetBeansProjects\\JavaProject7\\build\\classes\\JSON\\courses.json");
        Course c = new Course("2222","math","lablace","10940");
        Lesson l = new Lesson("4444","lesson1","lablace1");
        l.addResources("resourse");
        c.addLesson(l);
        c1.createCourse(c);
    }
}

