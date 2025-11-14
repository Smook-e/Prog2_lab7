package Courses;

public class Lesson {
    private int lessonId;
    private String title;
    private String content;
    private String optionalResources[];

    public Lesson(int lessonId, String title) {
        this.lessonId = lessonId;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getLessonId() {
        return lessonId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getOptionalResources() {
        return optionalResources;
    }

    public void setOptionalResources(String[] optionalResources) {
        this.optionalResources = optionalResources;
    }
    
    public Lesson[] FetchLessons(Course c)
    {
        return c.getLessons();
    }
}
