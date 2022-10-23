package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Comparator.naturalOrder;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student findStudentById(long id) {
        logger.info("Was invoked method for find student by id = " + id);
        return studentRepository.findStudentById(id);
    }

    public List<String> findStudentByFirstSymbol(String symbol) {
        logger.info("Was invoked method for find student by first symbol = " + symbol);
        return studentRepository
                .findAll()
                .stream()
                .filter(student -> student.getName().toLowerCase().startsWith(symbol.toLowerCase()))
                .map(s->s.getName().toUpperCase())
                .sorted(naturalOrder())
                .collect(Collectors.toList());
    }

    public List<Student> findStudentByAge(int age) {
        logger.info("Was invoked method for find student by age = " + age);
        return studentRepository.findByAge(age);
    }

    public List<Student> findStudentByFacultyId(Long facultyId) {
        logger.info("Was invoked method for find all students with faculty id = " + facultyId);
        return studentRepository.findByFacultyId(facultyId);
    }

    public List<Student> findStudentByAgeBetween(int min, int max) {
        logger.info("Was invoked method for find student with age between {}, {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student " + student.getName());
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("Was invoked method for delete student with id =" + id);
        studentRepository.deleteById(id);
    }

    public List<Student> getAllStudents() {
        logger.info("Was invoked method for getting all students");
        return studentRepository.findAll();
    }

    public void getAllStudentsInThreads() {
        logger.info("Was invoked method for getting all students");
        List<Student> allStudents = studentRepository.findAll();

        System.out.println(allStudents.get(0));
        System.out.println(allStudents.get(1));

        new Thread(() -> {
            System.out.println(allStudents.get(2));
            System.out.println(allStudents.get(3));
        }).start();

        new Thread(() -> {
            System.out.println(allStudents.get(4));
            System.out.println(allStudents.get(5));
        }).start();
    }

    public synchronized void printStudents(Student student, Student student1) {
        System.out.println(student.toString());
        System.out.println(student1.toString());
    }

    public void getAllStudentsInThreadsSync() {
        logger.info("Was invoked method for getting all students");
        List<Student> allStudents = studentRepository.findAll();

        printStudents(allStudents.get(0),allStudents.get(1));

        new Thread(() -> {
            printStudents(allStudents.get(2), allStudents.get(3));
        }).start();

        new Thread(() -> {
            printStudents(allStudents.get(4), allStudents.get(5));
        }).start();
    }

    public int countStudents() {
        logger.info("Was invoked method for count students");
        return studentRepository.countStudent();
    }

    public double countStudentsAverageAge() {
        logger.info("Was invoked method for count students average age");
        IntStream stream = studentRepository.findAll().stream().mapToInt(Student::getAge);
        return stream.average().orElse(0);
    }

    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method for getting last five students");
        return studentRepository.getLastFiveStudents();
    }
}
