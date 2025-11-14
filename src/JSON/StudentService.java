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

    public List<Course> browseCourses() {
        return courseService.getDb();  
    }
     public boolean enrollStudentInCourse(Student student, String courseId) {

        if (student.getEnrolledCourses().contains(courseId)) {
            return false; // already enrolled
        }

        student.getEnrolledCourses().add(courseId);
        userService.save(); // update users.json
        return true;
    }
     public List<Course> getEnrolledCourses(Student student) {
        List<Course> result = new ArrayList<>();

        for (String cid : student.getEnrolledCourses()) {
            Course c = courseService.getCourseByID(cid);
            if (c != null) result.add(c);
        }
        return result;
    }
     public List<Lesson> getLessonsForCourse(String courseId) {
        Course c = courseService.getCourseByID(courseId);
        if (c == null) return new ArrayList<>();
        return c.getLessons();  // lessons come FROM course object
    }
}
