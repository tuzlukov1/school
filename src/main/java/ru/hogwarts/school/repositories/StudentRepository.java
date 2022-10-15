package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAge(int age);
    Student findStudentById(Long id);
    List<Student> findByAgeBetween(int min, int max);
    List<Student> findByFacultyId(Long facultyId);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    int countStudent();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    int countStudentsAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}
