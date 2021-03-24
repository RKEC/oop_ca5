package com.dkit.oopca5.BusinessObjects;

import com.dkit.oopca5.core.Colours;
import com.dkit.oopca5.core.CourseMenu;
import com.dkit.oopca5.core.Menu;
import com.dkit.oopca5.core.Student;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * OOP 2021
 * This App demonstrates the use of a Data Access Object (DAO)
 * to separate Business logic from Database specific logic.
 * It uses DAOs, Data Transfer Objects (DTOs), and
 * a DaoInterface to define a contract between Business Objects
 * and DAOs.
 * <p>
 * "Use a Data Access Object (DAO) to abstract and encapsulate all
 * access to the data source. The DAO manages the connection with
 * the data source to obtain and store data" Ref: oracle.com
 * <p>
 * Here we use one DAO per database table.
 * <p>
 * Use the SQL script included with this project to create the
 * required MySQL user_database and user table
 */


public class App {

    public static void main(String[] args) {
        StudentManager studentManager = new StudentManager();
        CourseManager courseManager = new CourseManager();
        doMenuLoop();
//
//        int caoNum = 22223333;
//        String dob = "2000-11-26";
//        String pw = "w22231fd";
//        studentManager.addStudent(new Student(caoNum, dob, pw));
//        Student s = studentManager.getStudent(22223333);
//
//        System.out.println("Student: 12345678: " + s);
    }

    private static void doMenuLoop() {
        Scanner keyboard = new Scanner(System.in);
        boolean loop = true;
        Menu menuOption;
        int option;
        while (loop) {
            printMenu();
            try {
                String input = keyboard.nextLine();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException();
                } else {
                    option = Integer.parseInt(input);
                }
                menuOption = Menu.values()[option];
                switch (menuOption) {
                    case QUIT_APPLICATION:
                        loop = false;
                        break;
                    case REGISTER:

                        break;
                    case LOGIN:

                        break;
                }
            } catch (IllegalArgumentException iae) {
                System.out.println(Colours.RED + "Please enter a valid option" + Colours.RESET);
            } catch (Exception e) {
                System.out.println(Colours.RED + "Please enter a valid option" + Colours.RESET);
            }
        }
        System.out.println("Thanks for using the app");
    }

    private static void doCourseMenuLoop() {
        Scanner keyboard = new Scanner(System.in);
        boolean loop = true;
        CourseMenu courseMenu;
        int option;
        while (loop) {
            printCourseMenu();
            try {
                String input = keyboard.nextLine();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException();
                } else {
                    option = Integer.parseInt(input);
                }
                courseMenu = CourseMenu.values()[option];
                switch (courseMenu) {
                    case QUIT:
                        loop = false;
                        break;
                    case LOGOUT:

                        break;
                    case DISPLAY_COURSE:

                        break;
                    case DISPLAY_ALL_COURSES:

                        break;
                    case DISPLAY_CURRENT_CHOICES:

                        break;
                    case UPDATE_CURRENT_CHOICES:

                        break;
                }
            } catch (IllegalArgumentException iae) {
                System.out.println(Colours.RED + "Please enter a valid option" + Colours.RESET);
            } catch (Exception e) {
                System.out.println(Colours.RED + "Please enter a valid option" + Colours.RESET);
            }
        }
        System.out.println("Thanks for using the app");
    }

    private static void printMenu() {
        System.out.println("\n Options to select");
        for (int i = 0; i < Menu.values().length; i++) {
            System.out.println("\t" + Colours.PURPLE + i + ". " + Menu.values()[i].toString() + Colours.RESET);
        }
        System.out.println("Enter a number to select option (enter 0 to quit):>");

    }

    private static void printCourseMenu() {
        System.out.println("\n Options to select");
        for (int i = 0; i < CourseMenu.values().length; i++) {
            System.out.println("\t" + Colours.PURPLE + i + ". " + CourseMenu.values()[i].toString() + Colours.RESET);
        }
        System.out.println("Enter a number to select option (enter 0 to quit):>");

    }
}
