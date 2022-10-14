package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentController studentController;

    @Test
    void getStudentInfo() {
        Student student = new Student();
        student.setName("test_name");
        student.setAge(1);

        ResponseEntity<Student> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + Objects.requireNonNull(responseEntity.getBody()).getId(), String.class))
                .contains(student.getName());

        restTemplate.delete("http://localhost:" + port + "/student/"+ Objects.requireNonNull(responseEntity.getBody()).getId(),  String.class);
    }

    @Test
    void getStudentByAge() {
        Student student = new Student();
        student.setName("test_name");
        student.setAge(1);

        ResponseEntity<Student> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/byAge?age=" + student.getAge(), String.class))
                .contains(student.getName());

        restTemplate.delete("http://localhost:" + port + "/student/"+ Objects.requireNonNull(responseEntity.getBody()).getId(),  String.class);
    }

    @Test
    void getStudentByAgeBetween() {
        Student student = new Student();
        student.setName("test_name");
        student.setAge(5);

        ResponseEntity<Student> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/byAgeBetween?min=" + (student.getAge() - 1) + "&max=" + (student.getAge() + 1), String.class))
                .contains(student.getName());

        restTemplate.delete("http://localhost:" + port + "/student/"+ Objects.requireNonNull(responseEntity.getBody()).getId(),  String.class);
    }

    @Test
    void getStudentByFacultyId() {
        Student student = new Student();
        student.setName("test_name");
        student.setAge(5);

        Faculty faculty = new Faculty();
        faculty.setName("test_faculty");
        faculty.setColor("test_color");

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        faculty.setId(facultyResponseEntity.getBody().getId());
        student.setFaculty(faculty);

        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);

        var listResponseEntity = restTemplate.getForObject("http://localhost:" + port + "/student/faculty/" + Objects.requireNonNull(facultyResponseEntity.getBody().getId()), String.class);
        Assertions
                .assertThat(listResponseEntity)
                .contains(student.getName());

        restTemplate.delete("http://localhost:" + port + "/student/"+ Objects.requireNonNull(studentResponseEntity.getBody()).getId(),  String.class);
        restTemplate.delete("http://localhost:" + port + "/faculty/"+ Objects.requireNonNull(facultyResponseEntity.getBody()).getId(),  String.class);
    }

    @Test
    void getStudentFacultyId() {
        Student student = new Student();
        student.setName("test_name");
        student.setAge(5);

        Faculty faculty = new Faculty();
        faculty.setName("test_faculty");
        faculty.setColor("test_color");

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        faculty.setId(Objects.requireNonNull(facultyResponseEntity.getBody()).getId());
        student.setFaculty(faculty);

        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);

        var responseEntity = restTemplate.getForObject("http://localhost:" + port + "/student/"+ Objects.requireNonNull(studentResponseEntity.getBody()).getId() +"/faculty", Faculty.class);
        Assertions
                .assertThat(responseEntity.getId())
                .isEqualTo(facultyResponseEntity.getBody().getId());

        restTemplate.delete("http://localhost:" + port + "/student/"+ Objects.requireNonNull(studentResponseEntity.getBody()).getId(),  String.class);
        restTemplate.delete("http://localhost:" + port + "/faculty/"+ Objects.requireNonNull(facultyResponseEntity.getBody()).getId(),  String.class);

    }

    @Test
    void getAllStudents() {
        Student student = new Student();
        student.setName("test_name");
        student.setAge(1);

        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .contains(student.getName());
    }

    @Test
    void createStudent() {
        Student student = new Student();
        student.setName("test_name");
        student.setAge(1);

        ResponseEntity<Student> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + Objects.requireNonNull(responseEntity.getBody()).getId(), String.class))
                .contains(student.getName());

        restTemplate.delete("http://localhost:" + port + "/student/"+ Objects.requireNonNull(responseEntity.getBody()).getId(),  String.class);
    }

    @Test
    void editStudent() {
        Student student = new Student();
        student.setName("test_name");
        student.setAge(1);

        Faculty faculty = new Faculty();
        faculty.setName("test_faculty");
        faculty.setColor("test_color");

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        faculty.setId(Objects.requireNonNull(facultyResponseEntity.getBody()).getId());
        student.setFaculty(faculty);

        ResponseEntity<Student> createResponseEntity = restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);

        student.setName("test_name_edited");
        student.setId(createResponseEntity.getBody().getId());

        restTemplate.put("http://localhost:" + port + "/student", student, Student.class);
        ResponseEntity<Student> getStudentResponseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/student/" + Objects.requireNonNull(createResponseEntity.getBody()).getId(), Student.class);
        Assertions
                .assertThat(Objects.requireNonNull(getStudentResponseEntity.getBody()).getName())
                .isEqualTo(student.getName());

        restTemplate.delete("http://localhost:" + port + "/student/"+ Objects.requireNonNull(createResponseEntity.getBody()).getId(),  String.class);
    }

    @Test
    void deleteStudent() {
        Student student = new Student();
        student.setName("test_name");
        student.setAge(1);

        ResponseEntity<Student> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + Objects.requireNonNull(responseEntity.getBody()).getId(), String.class))
                .contains(student.getName());

        restTemplate.delete("http://localhost:" + port + "/student/"+ Objects.requireNonNull(responseEntity.getBody()).getId(),  String.class);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + Objects.requireNonNull(responseEntity.getBody()).getId(), String.class))
                .isNull();
    }
}