package Courses;

import java.util.ArrayList;

public class Course {
    private int courseId;
    private String title;
    private String description;
    private int instructorId;
    private ArrayList <Lesson> lessons;
    private ArrayList <String> students;

    public Course(int courseId, String title, String description, int instructorId) {
        //handle course id error
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void addLesson(Lesson l)
    {
        lessons.add(l);
    }
    public Lesson getLessonById(int lessonId)
    {
        for(Lesson l : lessons)
        {
            if(l.getLessonId()==lessonId)
            {
                return l;
            }
        }
        return null;
    }
    
    public void removeLesson(int lessonId)
    {
        if(getLessonById(lessonId)!=null)
        {
            lessons.remove(getLessonById(lessonId));
        }
    }
    public boolean updateLesson(int lessonId, Lesson updatedLesson) {
        for(int i = 0; i < lessons.size(); i++) {
            if(lessons.get(i).getLessonId()==lessonId) {
                lessons.set(i, updatedLesson);
                return true;
            }
        }
        return false;
    }
    public void enrollStudent(String studentId) {
        if(!students.contains(studentId)) {
            students.add(studentId);
        }
    }
    
    public void unenrollStudent(String studentId) {
        students.remove(studentId);
    }
    
    public boolean isStudentEnrolled(String studentId) {
        return students.contains(studentId);
    }
    
    public int getEnrolledStudentsCount() {
        return students.size();
    }
 
}
