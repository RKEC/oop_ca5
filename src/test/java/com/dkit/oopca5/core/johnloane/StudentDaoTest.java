package com.dkit.oopca5.core.johnloane;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Student;
import com.dkit.oopca5.server.MySqlStudentDao;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StudentDaoTest {
    @Test
    public void testRegisterStudent() throws DaoException {
        MySqlStudentDao sqlStudentDao = new MySqlStudentDao();

        int caoNum = 99993333;
        String dob = "2000-11-26";
        String pw = "w22231fd";
        Student expected = new Student(caoNum, dob, pw);
        sqlStudentDao.registerStudent(expected);

        Student actual = sqlStudentDao.findStudent(99993333);

        assertEquals(actual, expected);
    }

    @Test
    public void testFindStudent() throws DaoException {
        MySqlStudentDao sqlStudentDao = new MySqlStudentDao();

        int caoNum = 11119990;
        String dob = "2000-11-26";
        String pw = "w22231fd";
        Student expected = new Student(caoNum, dob, pw);
        sqlStudentDao.registerStudent(expected);

        Student actual = sqlStudentDao.findStudent(11119990);

        assertEquals(actual, expected);
    }
}
