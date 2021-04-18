package com.dkit.oopca5.client;

import com.dkit.oopca5.Constants.CAOService;
import com.dkit.oopca5.Constants.CourseMenu;
import com.dkit.oopca5.Constants.Menu;
import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.*;
import com.dkit.oopca5.server.*;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CAOClient {
    public Scanner keyboard = new Scanner(System.in);
    public RegexChecker regexChecker = new RegexChecker();

    public static void main(String[] args) {
        CAOClient client = new CAOClient();
        client.start();
    }

    public void start() {
        try {

            Socket s = new Socket(CAOService.HOSTNAME, CAOService.PORT_NUM);
            OutputStream os = s.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);
            Scanner socketReader = new Scanner(s.getInputStream());
            System.out.println("Client : Port# of this client : " + s.getLocalPort());
            System.out.println("Client : Port# of Server : " + s.getPort());
            System.out.println("Client: The Client is running and has connected to the server");
            mainMenu(s, os, socketWriter, socketReader);
        } catch (IOException e) {
            System.out.println("Client : IOException : " + e);
        }

    }

    public void mainMenu(Socket s, OutputStream os, PrintWriter socketWriter, Scanner socketReader) {
        boolean loop = true;
        Menu options;
        int choose;
        while (loop) {
            printMenu();
            try {
                choose = keyboard.nextInt();
                options = Menu.values()[choose];
                switch (options) {
                    case QUIT_APPLICATION:
                        s.close();
                        os.close();
                        socketReader.close();
                        socketWriter.close();
                        loop = false;
                        break;
                    case LOGIN:
                        login(s, os, socketWriter, socketReader);
                        break;
                    case REGISTER:
                        registration(s, os, socketWriter, socketReader);
                        break;
                    default:
                        System.out.println("Invalid Input");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input");
                keyboard.nextLine();
            }catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid Input");
                keyboard.nextLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void registration(Socket s, OutputStream os, PrintWriter socketWriter, Scanner socketReader) {
        while (true || keyboard.nextInt() == 0) {
            try {
                System.out.println(Colours.BLUE + "Enter CAO Number e.g. 12345678: " + Colours.RESET);
                String pattern = "^[0-9]{8}$";
                int caoNum = keyboard.nextInt();
                String num = String.valueOf(caoNum);
                if (!regexChecker.regexCheck(num, pattern)) {
                    throw new IllegalArgumentException();
                }
                System.out.println(Colours.BLUE + "Enter Date of Birth e.g. 2001-12-07: " + Colours.RESET);
                pattern = "^\\d{4}-\\d{2}-\\d{2}$";
                String dob = keyboard.next();
                if (!regexChecker.regexCheck(dob, pattern)) {
                    throw new IllegalArgumentException();
                }
                System.out.println(Colours.BLUE + "Enter Password e.g. password123: " + Colours.RESET);
                pattern = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$";
                String pw = keyboard.next();
                if (!regexChecker.regexCheck(pw, pattern)) {
                    throw new IllegalArgumentException();
                }
                String task = CAOService.REGISTER_COMMAND;
                String breaks = CAOService.BREAKING_CHARACTER;
                String command = task + breaks + caoNum + breaks + dob + breaks + pw;
                os = s.getOutputStream();
                socketWriter = new PrintWriter(os, true);
                socketWriter.println(command);
                socketReader = new Scanner(s.getInputStream());
                String taskString = socketReader.nextLine();
                System.out.println("Client : Response from server: " + taskString);

                doCourseMenuLoop();
                break;
            } catch (IllegalArgumentException iae) {
                System.out.println(Colours.RED + "ERROR - Incorrect input detected!" + Colours.RESET);
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void login(Socket s, OutputStream os, PrintWriter socketWriter, Scanner socketReader) {
        while (true || keyboard.nextInt() == 0) {
            try {
                System.out.println(Colours.BLUE + "Enter CAO Number e.g. 12345678: " + Colours.RESET);
                int caoNum = keyboard.nextInt();
                String num = String.valueOf(caoNum);
                if (!regexChecker.regexCheck(num, CAOService.CAO_NUM)) {
                    throw new IllegalArgumentException();
                }
                System.out.println(Colours.BLUE + "Enter Date of Birth e.g. 2001-12-07: " + Colours.RESET);
                String dob = keyboard.next();
                if (!regexChecker.regexCheck(dob, CAOService.DOB)) {
                    throw new IllegalArgumentException();
                }
                System.out.println(Colours.BLUE + "Enter Password e.g. password123: " + Colours.RESET);
                String pw = keyboard.next();
                if (!regexChecker.regexCheck(pw, CAOService.PASSWORD)) {
                    throw new IllegalArgumentException();
                }
                String task = CAOService.LOGIN_COMMAND;
                String breaks = CAOService.BREAKING_CHARACTER;
                String command = task + breaks + caoNum + breaks + dob + breaks + pw;
                os = s.getOutputStream();
                socketWriter = new PrintWriter(os, true);
                socketWriter.println(command);
                socketReader = new Scanner(s.getInputStream());
                String taskString = socketReader.nextLine();
                System.out.println("Client : Response from server: " + taskString);

                doCourseMenuLoop();
                break;
            } catch (IllegalArgumentException iae) {
                System.out.println(Colours.RED + "ERROR - Incorrect input detected!" + Colours.RESET);
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void doMenuLoop(Socket s, OutputStream os, PrintWriter socketWriter, Scanner socketReader) {
        Scanner keyboard  = new Scanner(System.in);

                        while(true || keyboard.nextInt() != 0) {
                            try {
                                System.out.println(Colours.BLUE + "Enter CAO Number e.g. 12345678: " + Colours.RESET);
                                int caoNum = keyboard.nextInt();
                                System.out.println(Colours.BLUE + "Enter Date of Birth e.g. 2001-12-07: " + Colours.RESET);
                                String dob = keyboard.next();
                                System.out.println(Colours.BLUE + "Enter Password e.g. password123: " + Colours.RESET);
                                String pw = keyboard.next();

                                String task = CAOService.LOGIN_COMMAND;
                                String breaks = CAOService.BREAKING_CHARACTER;
                                String command = task + breaks + caoNum + breaks + dob + breaks + pw;
                                os = s.getOutputStream();
                                socketWriter = new PrintWriter(os, true);
                                socketWriter.println(command);
                                socketReader = new Scanner(s.getInputStream());
                                String taskString = socketReader.nextLine();
                                System.out.println("Client : Response from server: " + taskString);

                            } catch (IllegalArgumentException iae) {
                                System.out.println(Colours.RED + "ERROR - Incorrect input detected!" + Colours.RESET);
                                continue;
                            } catch (Exception e) {
                                System.out.println(Colours.RED + "ERROR - Please enter a valid input" + Colours.RESET);
                                continue;
                            }
                        }
                }

    private static void doCourseMenuLoop() {
        CourseDaoInterface courseDao = new MySqlCourseDao();
        StudentCoursesDaoInterface studentCoursesDaoInterface = new MySqlStudentCoursesDao();
        Scanner keyboard  = new Scanner(System.in);

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
                continue;
            } catch (Exception e) {
                System.out.println(Colours.RED + "Please enter a valid option" + Colours.RESET);
                continue;
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
