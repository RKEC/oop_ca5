package com.dkit.oopca5.BusinessObjects;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Colours;
import com.dkit.oopca5.core.Course;
import com.dkit.oopca5.core.Student;
import com.dkit.oopca5.server.CourseDaoInterface;
import com.dkit.oopca5.server.MySqlCourseDao;
import com.dkit.oopca5.server.MySqlStudentDao;
import com.dkit.oopca5.server.StudentDaoInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * CoursesManager
 * This software component Encapsulates the storage and management of
 * all courses available through the CAO system.
 * Only administrators would typically be allowed to update this data,
 * but other users can get a COPY of all the courses by calling getAllCourses().
 * The Web Client would need this data to display the course codes,
 * course titles and institutions to the student.
 */

/**
 * Notes:
 * Richard collins
 * d00230925 */

public class CourseManager {

    // Store all the Course details.
    // Requires fast access given courseId.

    private HashMap<String, Course> courseMap = new HashMap<>();

    public CourseManager() {
        CourseDaoInterface courseDao = new MySqlCourseDao();

        try {
            List<Course> courseList = courseDao.findAllCourses();

            for(Course course : courseList) {
                courseMap.put(course.getCourseId(), course); //[caoNumber->Student]
            }

            System.out.println("courseMap dump: " + courseMap);

        }catch (DaoException e){
            e.printStackTrace();
        }
    }

public Course getCourse(String courseId) {
    Course course = courseMap.get(courseId);
    Course clone = null;
    if(course != null){
        clone = new Course(course);
    }
    return clone;
}

    public void addCourse(Course course) {
        String courseId = course.getCourseId();
        courseMap.put(courseId, new Course(course));    //clone the student
    }

    public List<Course> getAllCourses() {
        ArrayList<Course> clonedList = new ArrayList<>(courseMap.size());

        for (Map.Entry<String, Course> entry : courseMap.entrySet()) {
            Course course = entry.getValue();
            clonedList.add(new Course(course));
        }
        return clonedList;
    }

    public void removeCourse(String courseId) {
        for (Map.Entry<String, Course> entry : courseMap.entrySet()) {
            Course course = entry.getValue();
            courseMap.remove(courseId);
        }
    }

//    protected void loadPlayersFromFile() {
//        try (Scanner coursesFile = new Scanner(new BufferedReader(new FileReader("Courses.txt")))) {
//            String input;
//            while (coursesFile.hasNextLine()) {
//                input = coursesFile.nextLine();
//                String[] data = input.split(", ");
//                String id = data[0];
//                String lvl = data[1];
//                String name = data[2];
//                String institute = data[3];
//
//                Course readInPlayer = new Course(id, lvl, name, institute);
//                courseMap.put(id, readInPlayer);
//            }
//
//        } catch (FileNotFoundException e) {
//            System.out.println(Colours.RED + "File not found" + Colours.RESET);
//        }
//    }

    // editCourse(courseId);       // not required for this iteration

}







