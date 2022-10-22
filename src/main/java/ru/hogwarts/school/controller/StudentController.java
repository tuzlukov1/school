package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.OptionalDouble;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}") // GET https://localhost:8080/students/23
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/byAge") // GET https://localhost:8080/student/
    public ResponseEntity<Collection<Student>> getStudentByAge(@RequestParam Integer age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.findStudentByAge(age));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/byAgeBetween") // GET https://localhost:8080/student/
    public ResponseEntity<Collection<Student>> getStudentByAgeBetween(@RequestParam Integer min, @RequestParam Integer max) {
        if (min > 0 && max > 0) {
            return ResponseEntity.ok(studentService.findStudentByAgeBetween(min, max));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("faculty/{id}")// GET https://localhost:8080/student/facultyId/{facultyId}}
    public ResponseEntity<Collection<Student>> getStudentByFacultyId(@PathVariable Long id) {
        if (id != null) {
            return ResponseEntity.ok(studentService.findStudentByFacultyId(id));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/faculty")// GET https://localhost:8080/student/{facultyId}}
    public ResponseEntity<Faculty> getStudentFacultyId(@PathVariable Long id) {
        if (id != null) {
            return ResponseEntity.ok(studentService.findStudentById(id).getFaculty());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getByFirstSymbol")// GET https://localhost:8080/student/getByFirstSymbol
    public ResponseEntity<List<Student>> getStudentByFirstSymbolInNameA() {
        return ResponseEntity.ok(studentService.findStudentByFirstSymbol("A"));
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/allInThreads")
    public void getAllStudentsInThreads() {
        studentService.getAllStudentsInThreads();
    }

    @GetMapping("/allInSyncThreadsSync")
    public void getAllStudentsInThreadsSync() {
        studentService.getAllStudentsInThreadsSync();
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

    @GetMapping("/count")// GET https://localhost:8080/students/count
    public int countStudent() {
        return studentService.countStudents();
    }

    @GetMapping("/averageAge")// GET https://localhost:8080/students/averageAge
    public OptionalDouble countAverageAgeOfStudents() {
        return studentService.countStudentsAverageAge();
    }

    @GetMapping("/last-5")// GET https://localhost:8080/students/last-5
    public ResponseEntity<List<Student>> getLastFiveStudents() {
        return ResponseEntity.ok(studentService.getLastFiveStudents());
    }
}
