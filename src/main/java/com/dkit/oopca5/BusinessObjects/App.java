package com.dkit.oopca5.BusinessObjects;

import com.dkit.oopca5.core.Student;

/** OOP 2021
 * This App demonstrates the use of a Data Access Object (DAO)
 * to separate Business logic from Database specific logic.
 * It uses DAOs, Data Transfer Objects (DTOs), and
 * a DaoInterface to define a contract between Business Objects
 * and DAOs.
 *
 * "Use a Data Access Object (DAO) to abstract and encapsulate all
 * access to the data source. The DAO manages the connection with
 * the data source to obtain and store data" Ref: oracle.com
 *
 * Here we use one DAO per database table.
 *
 * Use the SQL script included with this project to create the
 * required MySQL user_database and user table
 */




public class App
{

    public static void main(String[] args)
    {
        StudentManager studentManager = new StudentManager();

        Student s = studentManager.getStudent(12345678);
        int caoNum = 22223333;
        String dob = "2000-11-26";
        String pw = "w22231fd";
        studentManager.addStudent(new Student(caoNum, dob, pw));
        s = studentManager.getStudent(22223333);
        System.out.println("Student: 12345678: " + s);
    }
}
