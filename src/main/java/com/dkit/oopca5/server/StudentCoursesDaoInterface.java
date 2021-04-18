package com.dkit.oopca5.server;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Course;
import com.dkit.oopca5.core.CourseChoices;

import java.util.List;
import java.util.Map;


/**
 * d00230925
 *Richard Collins
 */
public interface StudentCoursesDaoInterface {
    public boolean login(int caoNumber, String dateOfBirth, String password) throws DaoException;
    public List<Course> getAllCourses() throws DaoException;
    void registerChoices(List<CourseChoices> choices) throws DaoException;
    public List<String> getStudentChoices(int caoNum) throws DaoException;
    public boolean addChoice(int caoNum,String courseId)throws DaoException;
    public boolean removeChoice(int caoNum,String courseId) throws  DaoException;
}
