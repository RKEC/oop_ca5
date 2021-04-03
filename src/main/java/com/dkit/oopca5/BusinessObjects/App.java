package com.dkit.oopca5.BusinessObjects;

import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.client.RegexChecker;
import com.dkit.oopca5.core.*;
import com.dkit.oopca5.server.*;


import java.util.List;
import java.util.Scanner;

/**
 * d00230925
 *Richard Collins
 */


public class App {

    public static void main(String[] args) {
        doMenuLoop();
//        int caoNum = 22223333;
//        String dob = "2000-11-26";
//        String pw = "w22231fd";
//        studentManager.addStudent(new Student(caoNum, dob, pw));
//        Student s = studentManager.getStudent(22223333);
//
//        System.out.println("Student: 12345678: " + s);
    }

    private static void doMenuLoop() {
        StudentDaoInterface studentDao = new MySqlStudentDao();
        StudentCoursesDaoInterface studentCoursesDaoInterface = new MySqlStudentCoursesDao();
        Scanner keyboard = new Scanner(System.in);
        RegexChecker regexChecker = new RegexChecker();
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
                        System.exit(0);
                        break;
                    case REGISTER:
                        while(true){
                            try {
                                System.out.println(Colours.BLUE + "Enter CAO Number e.g. 12345678: " + Colours.RESET);
                                String pattern = "^[0-9]{8}$";
                                int caoNum = keyboard.nextInt();
                                String num = String.valueOf(caoNum);
                                if(!regexChecker.regexCheck(num, pattern))
                                {
                                    throw new IllegalArgumentException();

                                }
                                System.out.println(Colours.BLUE + "Enter Date of Birth e.g. 2001-12-07: " + Colours.RESET);
                                pattern = "^\\d{4}-\\d{2}-\\d{2}$";
                                String dob = keyboard.next();
                                if(!regexChecker.regexCheck(dob, pattern))
                                {
                                    throw new IllegalArgumentException();
                                }
                                System.out.println(Colours.BLUE + "Enter Password e.g. password123: " + Colours.RESET);
                                pattern = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$";
                                String pw = keyboard.next();
                                if(!regexChecker.regexCheck(pw, pattern))
                                {
                                    throw new IllegalArgumentException();
                                }
                                studentDao.registerStudent(new Student(caoNum,dob,pw) );
                                System.out.println(Colours.GREEN + "Registration SUCCESSFUL" + Colours.RESET);

                                doCourseMenuLoop();
                                break;
                            } catch (DaoException e) {
                                e.printStackTrace();

                            }catch (IllegalArgumentException iae){
                                System.out.println(Colours.RED + "ERROR - Incorrect input detected!" + Colours.RESET);
                                continue;
                            }
                        }
                        break;
                    case LOGIN:
                        while(true){
                            try {
                                System.out.println(Colours.BLUE + "Enter CAO Number e.g. 12345678: " + Colours.RESET);
                                int caoNum = keyboard.nextInt();
                                String pattern = "^[0-9]{8}$";
                                String num = String.valueOf(caoNum);
                                if(!regexChecker.regexCheck(num, pattern)){
                                    throw new IllegalArgumentException();
                                }
                                System.out.println(Colours.BLUE + "Enter Date of Birth e.g. 2001-07-12: " + Colours.RESET);
                                String dob = keyboard.next();
                                pattern = "^\\d{4}-\\d{2}-\\d{2}$";
                                if(!regexChecker.regexCheck(dob, pattern)){
                                    throw new IllegalArgumentException();
                                }
                                System.out.println(Colours.BLUE + "Enter Password e.g. password123: " + Colours.RESET);
                                String pw = keyboard.next();
                                pattern = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$";
                                if(!regexChecker.regexCheck(pw, pattern)){
                                    throw new IllegalArgumentException();
                                }
                                if(!studentCoursesDaoInterface.login(caoNum, dob, pw)){
                                    throw new Exception();
                                }
                                System.out.println(Colours.GREEN + "Login SUCCESSFUL" + Colours.RESET);

                                doCourseMenuLoop();
                                break;
                            } catch (DaoException e) {
                                e.printStackTrace();
                            }catch (IllegalArgumentException iae){
                                System.out.println(Colours.RED + "ERROR - Incorrect input detected!" + Colours.RESET);
                            }catch (Exception e){
                                System.out.println(Colours.RED + "ERROR - Please enter a valid input" + Colours.RESET);
                            }
                        }
                        break;
                }
            } catch (IllegalArgumentException iae) {
                System.out.println(Colours.RED + "ERROR - Incorrect input detected!" + Colours.RESET);
            } catch (Exception ef) {
                System.out.println(Colours.RED + "Please enter a valid option" + Colours.RESET);
            }
        }
        System.out.println("Thanks for using the app");
    }

    private static void doCourseMenuLoop() {
        CourseDaoInterface courseDao = new MySqlCourseDao();
        StudentCoursesDaoInterface studentCoursesDaoInterface = new MySqlStudentCoursesDao();

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
                        System.exit(0);
                        break;
                    case LOGOUT:
                        loop = false;
                        break;
                    case DISPLAY_COURSE:
                        try {
                            System.out.println(Colours.BLUE + "Enter Course Id: " + Colours.RESET);
                            String course = keyboard.nextLine();
                            Course c = courseDao.getCourse(course);
                            if (c == null){
                                System.out.println(Colours.BLUE + "No Course with id of" + course + Colours.RESET);
                                break;
                            }
                            System.out.println(Colours.GREEN + c + Colours.RESET);
                        }catch (DaoException e){
                            e.printStackTrace();
                        }
                        break;
                    case DISPLAY_ALL_COURSES:
                        try{
                            List<Course> list = studentCoursesDaoInterface.getAllCourses();
                            System.out.println(Colours.GREEN + list + Colours.RESET);
                        }catch (DaoException e){
                            e.printStackTrace();
                        }
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
