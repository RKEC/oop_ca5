package com.dkit.oopca5.BusinessObjects;
// StudentManager encapsulates the storage and ability
// to manipulate student objects
/**
 * Notes:
 * Richard collins
 * d00230925
 */


import com.dkit.oopca5.Exceptions.DaoException;
import com.dkit.oopca5.core.Student;
import com.dkit.oopca5.server.MySqlStudentDao;
import com.dkit.oopca5.server.StudentDaoInterface;


import java.util.HashMap;
import java.util.List;


public class StudentManager {

    // Store all students in data structure
    private HashMap<Integer, Student> studentMap = new HashMap<>();

    public StudentManager() {
        StudentDaoInterface studentDao = new MySqlStudentDao();

        try {
            List<Student> studentList = studentDao.findAllStudents();

            for(Student student : studentList) {
                studentMap.put(student.getCaoNumber(), student); //[caoNumber->Student]
            }

                System.out.println("studentMap dump: " + studentMap);

        }catch (DaoException e){
            e.printStackTrace();
        }
        // Hardcode some values to get started
//        studentMap.put(22224444, new Student(22224444, "1999-03-17", "dogsncats123"));
//        studentMap.put(22225555, new Student(22225555, "2002-10-20", "elements321"));
//        studentMap.put(33331111, new Student(33331111, "2000-01-11", "cakeface123"));
        // later, load from text file "students.dat" and populate studentsMap
    }

    public Student getStudent(int caoNumber) {
        Student student = studentMap.get(caoNumber);
        Student clone = null;
        if(student != null){
            clone = new Student(student);
        }
        return clone;
    }

    public void addStudent(Student student) {
        Integer caoNumber = student.getCaoNumber();
        studentMap.put(caoNumber, new Student(student));   //clone the student
    }

//    public List<Student> getAllStudents() {
//        ArrayList<Student> clonedList = new ArrayList<>(studentMap.size());
//
//        for (Map.Entry<String, Student> entry : studentMap.entrySet()) {
//            Student student = entry.getValue();
//            clonedList.add(new Student(student));
//        }
//        return clonedList;
//    }

    public void removeStudent(int caoNumber) {
            studentMap.remove(caoNumber);
    }

//    isRegistered( caoNumber)
//        students.isValid()
}