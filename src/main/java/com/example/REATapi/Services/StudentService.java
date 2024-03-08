package com.example.REATapi.Services;

import com.example.REATapi.Entities.Student;
import com.example.REATapi.dao.StudentRepository;
import org.aspectj.lang.annotation.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
//    private static List<Student> list = new ArrayList<>();
//
//    static {
//        list.add(new Student(33,"Ali","Khan",25));
//        list.add(new Student(23,"Zaeem","Mavie",25));
//        list.add(new Student(34,"Umair","Aslam",30));
//    }

    @Autowired
    private StudentRepository studentRepository;

    //Get All Students
    public List<Student> getAllStudents(){
        List<Student> list =  (List<Student>) studentRepository.findAll();

        return list;
    }
    //Get Single Student by id
    public Student getStudentById(Long id){
        Student st = null;
        try{
            //st = list.stream().filter(e->e.getId()==id).findFirst().get();
            st = studentRepository.findById(id).get();
        }
        catch (Exception e)
        {
             e.printStackTrace();
        }
        return st;
    }

    //adding the Student
    public Student addStudent(Student s){
        Student result = studentRepository.save(s);
        return result;
    }

    //delete Student
    public void deleteStudent(Long id){

        //list = list.stream().filter(e -> e.getId() != id).collect(Collectors.toList());
         studentRepository.deleteById(id);
    }

    //update Student
    public void updateStudent(Student st,Long id)
    {
//        list = list.stream().map(e -> {
//            if (e.getId() == id) {
//                e.setFirst_name(st.getFirst_name());
//                e.setLast_name(st.getLast_name());
//                e.setAge(st.getAge());
//            }
//            return e;
//        }).collect(Collectors.toList());
        st.setId(id);
        studentRepository.save(st);
    }



}
