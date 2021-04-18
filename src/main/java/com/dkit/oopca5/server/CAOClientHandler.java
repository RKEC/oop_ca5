package com.dkit.oopca5.server;

/*
The CAOClientHandler will run as a thread. It should listen for messages from the Client and respond to them.There should be one CAOClientHandler per Client.
 */

import com.dkit.oopca5.Constants.CAOService;
import com.dkit.oopca5.Constants.CourseMenu;
import com.dkit.oopca5.core.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CAOClientHandler implements Runnable
{ // each ClientHandler communicates with one Client

    BufferedReader socketReader;
    PrintWriter socketWriter;
    Socket socket;
    int numOfClient;

    public CAOClientHandler(Socket clientSocket, int numOfClient)
    {
        try
        {
            InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
            this.socketReader = new BufferedReader(isReader);
            OutputStream os = clientSocket.getOutputStream();
            this.socketWriter = new PrintWriter(os,true);
            this.numOfClient = numOfClient;
            this.socket = clientSocket;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String message;
        try
        {
            while((message = socketReader.readLine())!=null)
            {
                String arr[] = message.split(CAOService.BREAKING_CHARACTER);
                if(arr[0].equals(CAOService.REGISTER_COMMAND))
                {
                    Student s = new Student((Integer.parseInt(arr[1])),arr[2],arr[3]);
                    MySqlStudentDao studentDao = new MySqlStudentDao();
                    MySqlStudentCoursesDao studentCoursesDao = new MySqlStudentCoursesDao();
                    if(studentDao.registerStudent(new Student(s.getCaoNumber(),s.getDateOfBirth(),s.getPassword())))
                    {
                        socketWriter.println("Welcome! You have successfully registered!");
                        System.out.println("Server: Register student result is " + CAOService.SUCCESSFUL_REGISTER );
                    }
                    else
                    {
                        socketWriter.println("This cao number has been taken!");
                        System.out.println("Server: Register student result is " + CAOService.FAILED_REGISTER);
                    }
                }
                else if(arr[0].equals(CAOService.LOGIN_COMMAND))
                {
                    int caoNum = Integer.parseInt(arr[1]);
                    String dob = arr[2];
                    String pwd = arr[3];
                    MySqlStudentCoursesDao studentCoursesDao = new MySqlStudentCoursesDao();
                    if(studentCoursesDao.login(caoNum,dob,pwd))
                    {
                        socketWriter.println("Login successful!");
                        System.out.println("Server: Login status is "+ CAOService.SUCCESSFUL_LOGIN);
                    }
                    else
                    {
                        socketWriter.println("Invalid login, credentials are wrong");
                        System.out.println("Server: Login status is "+ CAOService.FAILED_LOGIN);
                    }
                }
                else if(arr[0].equals(CourseMenu.DISPLAY_COURSE))
                {
                    MySqlCourseDao courseDao= new MySqlCourseDao();
                    Course c = courseDao.getCourse(arr[1]);
                    if(c!=null)
                    {
                        Map obj=new HashMap();
                        obj.put("course",courseDao.getCourse(arr[1]).toString());
                        socketWriter.println(obj);
                    }
                    else
                    {

                        socketWriter.println("No Such Course!");
                    }
                    System.out.println("Server Status [Display Specific Course]: Succeed");
                }
                else if(arr[0].equals(CourseMenu.DISPLAY_ALL_COURSES))
                {
                    MySqlCourseDao courseDao = new MySqlCourseDao();
                    if(courseDao.findAllCourses().isEmpty())
                    {
                        socketWriter.println("There is no course available");
                    }
                    else {
                        Map obj=new HashMap();
                        List<String> courseList = new ArrayList<>();
                        for(Course c : courseDao.findAllCourses())
                        {
                            courseList.add(c.toString());
                        }
                        obj.put("course",courseList);

                        socketWriter.println(obj);
                    }
                    System.out.println("Server Status [Display All Course]: Success");
                }
                else if(arr[0].equals(CourseMenu.DISPLAY_CURRENT_CHOICES)) {
                    MySqlStudentCoursesDao courseChoicesDao = new MySqlStudentCoursesDao();
                    List<String> studentChoice = courseChoicesDao.getStudentChoices(Integer.parseInt(arr[1]));
                    if (studentChoice.isEmpty())
                    {
                        socketWriter.println("You haven't added any courses yet");
                    }
                    else {
                        Map obj = new HashMap();
                        obj.put("choice", studentChoice);
                        socketWriter.println(obj);
                    }
                    System.out.println("Server status [Display current choice]: Succeed");
                }
                else if(arr[0].equals(CourseMenu.REMOVE_COURSE))
                {
                    MySqlStudentCoursesDao courseChoicesDao = new MySqlStudentCoursesDao();
                    if(courseChoicesDao.getStudentChoices(Integer.parseInt(arr[2])).contains(arr[1]))
                    {
                        if(courseChoicesDao.removeChoice(Integer.parseInt(arr[2]),arr[1]))
                        {
                            socketWriter.println("Course Removed");
                        }
                        else
                        {
                            socketWriter.println("Oops, something went wrong");
                        }
                    }
                    else
                    {
                        socketWriter.println("Please enter a correct courseID");
                    }
                    System.out.println("Server status [Remove Course]: Succeed");
                }
                else if(arr[0].equals(CourseMenu.ADD_COURSE))
                {
                    MySqlStudentCoursesDao courseChoicesDao = new MySqlStudentCoursesDao();
                    int caoNum = Integer.parseInt(arr[2]);
                    String courseId = arr[1];
                    if(courseChoicesDao.getStudentChoices(caoNum).contains(courseId))
                    {
                        socketWriter.println("You have already added this course");

                    }
                    else
                    {
                        if(courseChoicesDao.addChoice(caoNum,courseId))
                        {
                            socketWriter.println("Course Added");
                        }
                        else
                        {
                            socketWriter.println("Opps,Something went wrong");
                        }
                    }
                    System.out.println("Server status [Add Course]: Succeed");
                }
            }
            socket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Server: (ClientHandler): Handler for Client " + numOfClient + " is terminating .....");
    }
}

