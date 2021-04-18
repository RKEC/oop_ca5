package com.dkit.oopca5.core;

// Stores all student CAO choices.
// Allows student to make course choices, save them and update them later.
//
// emphasis on speed of access when multiple users are accessing this at same time
//
// This component would interact with a Network component that would, in turn, send
// data over the internet to a web client.
//
// start with students implementing parts of the assignment
// e.g. set of cao numbers, then nest bit, etc....
// Clone all received and returned objects - encapsulation

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.server.MySqlCourseDao;
import com.dkit.oopca5.server.MySqlStudentDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseChoices {

    // reference to constructor injected studentManager
    private MySqlCourseDao sqlCourseDao;


    // reference to constructor injected courseManager
    private MySqlStudentDao sqlStudentDao;

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
    CourseChoices(MySqlStudentDao sqlStudentDao, MySqlCourseDao sqlCourseDao) throws DaoException {
        this.sqlStudentDao = sqlStudentDao;
        this.sqlCourseDao = sqlCourseDao;

        // studentChoicesMap - load from file, caoNumber, courseId1, courseId2, etc....

        List<Course> coursesList = sqlCourseDao.findAllCourses(); // get list of courses

        // Iterate over the List and populate the Map  //  Old way (before Java 8) - but easier to understand than streams
        for (Course course : coursesList) {
            coursesMap.put(course.getCourseId(), course);
        }

        // new way with streams and lambdas // toMap() returns HashMap by default
        //        coursesMap =  coursesList.stream()
        //                .collect(Collectors.toMap(Course::getCourseId, course -> course));

    }

    Student getStudentDetails(int caoNumber) throws DaoException {
        return sqlStudentDao.findStudent(caoNumber);
    }

    Course getCourseDetails(String courseId) throws DaoException {
        return sqlCourseDao.getCourse(courseId);
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



    public void setSqlStudentDao(MySqlStudentDao sqlStudentDao) {
        this.sqlStudentDao = sqlStudentDao;
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

    boolean login(int coaNumber,String dateOfBirth,String password) {
        //if(  dob and password match the caoNumber)
            return true;
       // else
          //  return false;
    }

//    public int getCaoNumber() {
//
//    }


    // RegEx for creation of new students and courses

}
