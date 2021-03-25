package com.dkit.oopca5.server;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCourseDao extends MySqlDao implements CourseDaoInterface {

    @Override
    public Course getCourse(String courseId) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Course c = null;

        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM course WHERE courseId = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, courseId);  // search based on the cao number

            rs = ps.executeQuery();
            if (rs.next())
            {
                courseId = rs.getString("courseid");
                int level = rs.getInt("level");
                String title = rs.getString("title");
                String institution = rs.getString("institution");

                c = new Course(courseId, level, title, institution);
            }
        } catch (SQLException e)
        {
            throw new DaoException("getCourse() " + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new DaoException("getCourse() " + e.getMessage());
            }
        }
        return c;
    }

    @Override
    public List<Course> findAllCourses() throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Course> courses = new ArrayList<>();
        Course s = null;

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM `course`";
            ps = con.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next())
            {
                String courseId = rs.getString("courseId");
                int level = rs.getInt("level");
                String title = rs.getString("title");
                String institution = rs.getString("institution");
                s = new Course(courseId, level, title, institution);
                courses.add(s);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllCourses() " + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findAllCourses() " + e.getMessage());
            }
        }
        return courses;     // may be empty
    }



}
