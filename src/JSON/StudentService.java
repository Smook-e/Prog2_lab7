package JSON;

import Users.Student;
import Courses.Course;
import Courses.Lesson;

import java.util.*;

public class StudentService {

    private UserService userService;
    private CourseService courseService;

    public StudentService(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    
}
