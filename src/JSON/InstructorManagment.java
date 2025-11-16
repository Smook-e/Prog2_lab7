/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSON;

import Courses.Course;
import Courses.Lesson;
import Users.Instructor;
import java.util.ArrayList;

/**
 *
 * @author HP
 */
public class InstructorManagment {
    private CourseService courseService;
    public InstructorManagment(CourseService courseService)
    {
        this.courseService=courseService;
    }
    
    public boolean createCourse(Instructor instructor,String courseId,String title,String description)
    {
      
        Course course = new Course(courseId,instructor.getUserID(),title,description);
        boolean done=courseService.createCourse(course);
        if(done)
        {
            instructor.addCreatedCourse(courseId);
        } 
        return done;
       
    }
    public boolean updateCourse(Instructor instructor,Course course)
    {
        if(!course.getInstructorId().equals(instructor.getUserID()))
        {
            return false;
        }
        return courseService.updateCourse(course);
    }
    public boolean deleteCourse(Instructor instructor,String courseId)
    {
        if(!instructor.getCreatedCourses().contains(courseId))
        {
            return false;
        }
        return courseService.deleteCourse(courseId);
    }
    public boolean createLesson(Instructor instructor,String courseId,String lessonId,String content)
    {
        if(!instructor.getCreatedCourses().contains(courseId))
        {
            return false;
        }
        Lesson lesson=new Lesson(lessonId,content);
        return courseService.addLesson(courseId, lesson);
    }
    public boolean editLesson(Instructor instructor,String courseId,Lesson lesson,String lessonId)
    {
        if(!instructor.getCreatedCourses().contains(courseId))
        {
            return false;
        }
        return courseService.updateLesson(courseId,lessonId,lesson);
    }
    public boolean deleteLesson(Instructor instructor,String courseId,String lessonId)
    {
        if(!instructor.getCreatedCourses().contains(courseId))
        {
            return false;
        }
        return courseService.deleteLesson(courseId, lessonId);
    }
    public ArrayList<String> viewStudentsProgress(String courseId)
    {
        return courseService.getEnrolledStudents(courseId);
    }
    
   }
    

