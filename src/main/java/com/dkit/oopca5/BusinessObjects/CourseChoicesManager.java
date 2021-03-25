package com.dkit.oopca5.BusinessObjects;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Colours;
import com.dkit.oopca5.core.Course;
import com.dkit.oopca5.core.Student;
import com.dkit.oopca5.server.CourseDaoInterface;
import com.dkit.oopca5.server.StudentDaoInterface;

import java.util.*;

public class CourseChoicesManager {

    // reference to constructor injected studentManager
    private StudentManager studentManager;

    // reference to constructor injected courseManager
    private CourseManager courseManager;

    // Store all the Course details - HashMap for fast access
    // Map [courseId => course]
    // Loaded in constructor from CoursesManager and converted from List
    private Map<String, Course> coursesMap = new HashMap<>();

    // caoNumber, course selection list - HashMap for fast access
    // courses must be in List, to maintain the selected order.
    private Map<Integer, List<Course>> studentChoicesMap = new HashMap<>();

    // CourseChoicesManager DEPENDS on both the StudentManager and CourseManager to access
    // student details and course details.  So, we pass in a reference to each via
    // the constructor.
    // This is called "Dependency Injection", meaning that we
    // inject (or pass in) objects that this class requires to do its job.
    //
    CourseChoicesManager(StudentManager studentManager, CourseManager courseManager) throws DaoException {
        this.studentManager = studentManager;
        this.courseManager = courseManager;

        // studentChoicesMap - load from file, caoNumber, courseId1, courseId2, etc....

        List<Course> coursesList = courseManager.getAllCourses(); // get list of courses

        // Iterate over the List and populate the Map  //  Old way (before Java 8) - but easier to understand than streams
        for (Course course : coursesList) {
            coursesMap.put(course.getCourseId(), course);
        }

        // new way with streams and lambdas // toMap() returns HashMap by default
        //        coursesMap =  coursesList.stream()
        //                .collect(Collectors.toMap(Course::getCourseId, course -> course));

    }

    Student getStudentDetails(int caoNumber) {
        return studentManager.getStudent(caoNumber);
    }

    Course getCourseDetails(String courseId) {
        return courseManager.getCourse(courseId);
    }

    List<Course> getStudentChoices(int caoNumber) {
        return studentChoicesMap.get(caoNumber);    // to be sent to Web Interface normally
    }

    void updateChoices(int caoNumber, List<String> choiceList) { // data from web interface normally
        // choiceList is a list of courseId, as Strings.
        // List used as it preserves choice order.
        // studentChoices map requires course objects...
        // look up course object using courseId
        // add course to a list

        ArrayList<Course> coursesList = new ArrayList<>();

        for (String courseId : choiceList) {
            Course course = coursesMap.get(courseId);
            coursesList.add(course);

        }
        // finally, put the list of courses in the map using caoNumber as key
        studentChoicesMap.put(caoNumber, coursesList);
    }

    // Returns a *List* of Courses.
    // Courses are not in any particular order.
    // (would be sent to Web interface normally )
    public List<Course> getAllCourses() {

        ArrayList<Course> list = new ArrayList<>(); // new ArrayList to store copy of Map data

        // Iterate through all values in the courseMap, and add each course to the List
        for (Map.Entry<String, Course> entry : coursesMap.entrySet()) {
            Course course = entry.getValue();   // get course from map entry
            list.add(course);                   // add course to the List
        }
        return list;    // list of courses, in no particular order.
    }

    boolean login(int caoNumber, String dateOfBirth, String password) {
        if(studentManager.getStudent(caoNumber) == null)
        {
            System.out.println(Colours.RED + "CAO Number cannot be found" + Colours.RESET);
            return false;
        }
        else if (!studentManager.getStudent(caoNumber).getPassword().equals(password))
        {
            System.out.println(Colours.RED + "Incorrect Password" + Colours.RESET);
            return false;
        }
        else if (!studentManager.getStudent(caoNumber).getDateOfBirth().equals(dateOfBirth))
        {
            System.out.println(Colours.RED + "Incorrect Date of Birth" + Colours.RESET);
            return false;
        }
        else if (!studentManager.getStudent(caoNumber).getDateOfBirth().equals(dateOfBirth) && !studentManager.getStudent(caoNumber).getPassword().equals(password))
        {
            System.out.println(Colours.RED + "Incorrect Date of Birth and Password" + Colours.RESET);
            return false;
        }
        else{
            return true;
        }
    }
    // RegEx for creation of new students and courses

}
