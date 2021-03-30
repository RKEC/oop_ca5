package com.dkit.oopca5.server;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Course;

import java.util.List;

/**
 * d00230925
 *Richard Collins
 */

public interface CourseDaoInterface {
    public Course getCourse(String courseId) throws DaoException;

    List<Course> findAllCourses() throws DaoException;
}
