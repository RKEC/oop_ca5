package com.dkit.oopca5.core.johnloane;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.dkit.oopca5.BusinessObjects.CourseManager;
import com.dkit.oopca5.BusinessObjects.StudentManager;
import com.dkit.oopca5.core.Course;
import com.dkit.oopca5.core.Student;
import com.dkit.oopca5.server.MySqlStudentDao;
import com.dkit.oopca5.server.StudentDaoInterface;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void testAddStudentToStudentManager() {
        StudentManager studentManager = new StudentManager();

        int caoNum = 22223333;
        String dob = "2000-11-26";
        String pw = "w22231fd";
        Student expected = new Student(caoNum, dob, pw);
        studentManager.addStudent(expected);

        Student actual = studentManager.getStudent(22223333);

        assertEquals(actual, expected);
    }

//    @Test
//    public void testStudentDao(){
//        StudentDaoInterface studentDao = new MySqlStudentDao();
//        studentDao.findAllStudents()
//
//    }

    @Test
    public void testAddCourseToCourseManager(){
        CourseManager courseManager = new CourseManager();

        String courseId = "DK800";   // e.g. DK821
        int level = 8;      // e.g. 7, 8, 9, 10
        String title = "Business";      // e.g. BSc in Computing in Software Development
        String institution = "DkIT";
        Course expected = new Course(courseId, level, title, institution);
        courseManager.addCourse(expected);

        Course actual = courseManager.getCourse("DK800");

        assertEquals(actual, expected);
    }
}
