package com.dkit.oopca5.server;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Course;

import java.util.List;

public interface CourseDaoInterface {
    public void getCourse(String courseId) throws DaoException;

}
