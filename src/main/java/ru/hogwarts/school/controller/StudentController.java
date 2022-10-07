package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}") // GET https://localhost:8080/students/23
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping // GET https://localhost:8080/student/
    public ResponseEntity getStudentByAge(@RequestParam(required=false) Integer age, @RequestParam(required=false) Integer min, @RequestParam(required=false) Integer max) {
        if (age != null && age > 0) {
            return ResponseEntity.ok(studentService.findStudentByAge(age));
        }

        if ((min != null & min > 0) && (max != null && max > 0)) {
            return ResponseEntity.ok(studentService.findStudentByAgeBetween(min, max));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping ("facultyId/{facultyId}")// GET https://localhost:8080/student/{facultyId}}
    public ResponseEntity getStudentByFacultyId(@PathVariable Long facultyId) {
        if (facultyId != null) {
            return ResponseEntity.ok(studentService.findStudentByFacultyId(facultyId));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping ("{id}/facultyId")// GET https://localhost:8080/student/{facultyId}}
    public ResponseEntity getStudentFacultyId(@PathVariable Long id) {
        if (id != null) {
            return ResponseEntity.ok(studentService.findStudentById(id).getFaculty());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping // POST https://localhost:8080/students
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping // PUT https://localhost:8080/students
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")// DELETE https://localhost:8080/students/23
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
