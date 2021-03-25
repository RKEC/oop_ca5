package com.dkit.oopca5.server;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Colours;
import com.dkit.oopca5.core.Course;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlStudentCoursesDao extends MySqlDao implements StudentCoursesDaoInterface {

        @Override
        public boolean login(int caoNumber, String dateOfBirth, String password) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean result = false;

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM `student`";
            ps = con.prepareStatement(query);

            StudentDaoInterface studentDao = new MySqlStudentDao();
            CourseDaoInterface courseDao = new MySqlCourseDao();

            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next())
            {
                int num = rs.getInt("caoNumber");
                String dob = rs.getString("dateOfBirth");
                String pw = rs.getString("password");

                if(studentDao.findStudent(caoNumber) == null)
                {
                    System.out.println(Colours.RED + "CAO Number cannot be found" + Colours.RESET);
                    result = false;
                }
                else if (!studentDao.findStudent(caoNumber).getPassword().equals(password))
                {
                    System.out.println(Colours.RED + "Incorrect Password" + Colours.RESET);
                    result = false;
                }
                else if (!studentDao.findStudent(caoNumber).getDateOfBirth().equals(dateOfBirth))
                {
                    System.out.println(Colours.RED + "Incorrect Date of Birth" + Colours.RESET);
                    result = false;
                }
                else if (!studentDao.findStudent(caoNumber).getDateOfBirth().equals(dateOfBirth) && !studentDao.findStudent(caoNumber).getPassword().equals(password))
                {
                    System.out.println(Colours.RED + "Incorrect Date of Birth and Password" + Colours.RESET);
                    result = false;
                }
                else{
                    result = true;
                }
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

            return result;     // may be empty
    }

    @Override
    public List<Course> getAllCourses() throws DaoException {
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
                Course c = new Course(courseId, level, title, institution);
                courses.add(c);
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
