package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> findFaculty(long id) {
        logger.info("Was invoked method for find faculty with id = " + id);
        return facultyRepository.findById(id);
    }

    public List<Faculty> findFacultyByNameOrColor(String nameOrColor) {
        logger.info("Was invoked method for find faculty with name or color" + nameOrColor);
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(nameOrColor, nameOrColor);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for edit faculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("Was invoked method for delete faculty with id " + id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for get all facutlies");
        return facultyRepository.findAll();
    }

    public String getLongestFacultyName() {
        logger.info("Was invoked method for get longest faculty name");
        return facultyRepository.findAll().stream().map(Faculty::getName).max(Comparator.comparingInt(String::length)).toString();
    }
}
