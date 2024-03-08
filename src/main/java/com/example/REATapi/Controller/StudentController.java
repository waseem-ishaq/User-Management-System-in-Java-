package com.example.REATapi.Controller;

import com.example.REATapi.Entities.Student;
import com.example.REATapi.Entities.User;
import com.example.REATapi.Services.StudentService;
import com.example.REATapi.Services.UserService;
import com.example.REATapi.dao.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void commonUser(Principal p, Model m)
    {
        System.out.println(p.getName());
        if (p != null)
        {
            String email = p.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("Temp",user);
            System.out.println("commonUser method call inside Student Controller");
        }
    }

    @GetMapping(value="/students")
    public String getStudents(Model model){
        log.info("/students");
        model.addAttribute("students",studentService.getAllStudents());
        return "students";
    }

    @GetMapping(value = "/students/new")
    public String createStudentForm(Model model)
    {
        Student student = new Student();
        model.addAttribute("students",student);
        return "create_student";
    }

    @PostMapping(value="/students")
    public String saveStudent(@ModelAttribute("student") Student student,HttpSession session)
    {
        studentService.addStudent(student);
        session.setAttribute("msg","Student Added Successfully..");
        return "redirect:/students";
    }

    @GetMapping(value = "/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model)
    {
        model.addAttribute("students",studentService.getStudentById(id));
        return "edit_student";
    }

    @GetMapping(value = "/students/delete/{id}")
    public String deleteStudentForm(@PathVariable Long id, Model model)
    {
        model.addAttribute("students",studentService.getStudentById(id));
        return "delete_student";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student,Model model,HttpSession session)
    {
        Student existingStudent = studentService.getStudentById(id);
        existingStudent.setId(id);
        existingStudent.setFirst_name(student.getFirst_name());
        existingStudent.setLast_name(student.getLast_name());
        existingStudent.setAge(student.getAge());

        studentService.updateStudent(existingStudent,id);
        session.setAttribute("msg","Student Updated Successfully..");
        return "redirect:/students";
    }

    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id,HttpSession session)
    {
        studentService.deleteStudent(id);
        session.setAttribute("msg","Student Deleted Successfully..");
        return "redirect:/students";
    }

    @GetMapping(value = "/students/view/{id}")
    public String viewStudentForm(@PathVariable Long id, Model model)
    {
        model.addAttribute("students",studentService.getStudentById(id));
        return "view_student";
    }

}

//    @GetMapping(value="/students")
//    public ResponseEntity<List<Student>> getStudents(){
//        List<Student> allStudents = studentService.getAllStudents();
//        log.info(allStudents.toString());
//        if(allStudents.size()<=0)
//        {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        return ResponseEntity.of(Optional.of(allStudents));
//    }

//    @GetMapping(value="/students/{id}")
//    public ResponseEntity<Student> getStudent(@PathVariable("id") Long id){
//        Student student = studentService.getStudentById(id);
//        log.info(student.toString());
//        if (student==null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        return ResponseEntity.of(Optional.of(student));
//
//    }

//    @PostMapping(value = "/students")
//    public ResponseEntity<Student> addStudent(@RequestBody Student st)
//    {
//        try {
//            studentService.addStudent(st);
//            log.info(st.toString());
//            return ResponseEntity.status(HttpStatus.CREATED).build();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

//    @DeleteMapping(value = "/students/{Id}")
//    public ResponseEntity<Void> deleteStudent(@PathVariable ("Id") Long Id )
//    {
//        try {
//            studentService.deleteStudent(Id);
//            return ResponseEntity.ok().build();
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @PatchMapping(value = "/students/{Id}")
//    public ResponseEntity<Student> updateStudent(@RequestBody Student st,@PathVariable ("Id") Long Id)
//    {
//        try{
//            studentService.updateStudent(st,Id);
//            log.info(st.toString());
//            return ResponseEntity.ok().body(st);
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }