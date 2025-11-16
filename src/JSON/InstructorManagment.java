/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSON;

import Courses.Course;
import Courses.Lesson;
import Users.Instructor;

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
    
    public boolean createCourse(Instructor instructor,Course course)
    {
       return courseService.createCourse(course);
       
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
    public boolean createLesson(Instructor instructor,String courseId,Lesson lesson)
    {
        if(!instructor.getCreatedCourses().contains(courseId))
        {
            return false;
        }
        return courseService.addLesson(courseId, lesson);
    }
    
    
   }
    

