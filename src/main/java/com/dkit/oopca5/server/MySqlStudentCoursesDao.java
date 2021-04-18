package com.dkit.oopca5.server;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Colours;
import com.dkit.oopca5.core.Course;
import com.dkit.oopca5.core.CourseChoices;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * d00230925
 *Richard Collins
 */

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

    @Override
     public void registerChoices(List<CourseChoices> choices) throws DaoException
    {for(CourseChoices c : choices)
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean success = false;

        try
        {

                //Get connection object using the methods in the super class (MySqlDao.java)...
                con = this.getConnection();

                String query = "INSERT INTO STUDENT_COURSES VALUES (?,?)";
                ps = con.prepareStatement(query);
//
//            System.out.println(c.getCaoNumber());
//            System.out.println(c.getCourseId());
//                    ps.setInt(1, c.getCaoNumber());
//                    ps.setString(2, c.getCourseId());


                //Using a PreparedStatement to execute SQL - UPDATE...
            success = (ps.executeUpdate() == 1);


        } catch (SQLException e)
        {
            throw new DaoException("insertCourse() " + e.getMessage());
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
                throw new DaoException("insertCourse() " + e.getMessage());
            }
        }

    }
    }

    @Override
    public List<String> getStudentChoices(int caoNum) throws DaoException {
        return null;
    }

    @Override
    public boolean addChoice(int caoNum,String courseId) throws DaoException
    {
        Connection con = null;
        PreparedStatement prep = null;
        boolean success = true;
        try
        {
            con = this.getConnection();
            String query = "INSERT INTO STUDENT_COURSES (caoNumber,courseId) VALUES (?,?)";
            prep = con.prepareStatement(query);
            prep.setInt(1,caoNum);
            prep.setString(2,courseId);
            prep.executeUpdate();
        }
        catch (SQLException e)
        {
            success = false;
        }
        finally
        {
            try
            {
                if (prep != null)
                {
                    prep.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("updateChoice() "+e.getMessage());
            }
        }
        return  success;
    }

    @Override
    public boolean removeChoice(int caoNum, String courseId) throws DaoException
    {
        boolean success = true;
        Connection con = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try
        {
            con = this.getConnection();
            String query = "DELETE FROM STUDENT_COURSES WHERE caoNumber = ? AND courseId = ?";
            prep = con.prepareStatement(query);
            prep.setInt(1,caoNum);
            prep.setString(2,courseId);
            prep.executeUpdate();

        } catch (SQLException e)
        {
            success = false;
        }
        return success;
    }
}
