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

public class MySqlCourseDao extends MySqlDao implements CourseDaoInterface {

    @Override
    public void getCourse(String courseId) throws DaoException {
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
                System.out.println(Colours.GREEN + c + Colours.RESET);
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
    }



}
