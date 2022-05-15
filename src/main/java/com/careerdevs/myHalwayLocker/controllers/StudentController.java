package com.careerdevs.myHalwayLocker.controllers;

import com.careerdevs.myHalwayLocker.Auth.User;
import com.careerdevs.myHalwayLocker.Security.services.UserService;
import com.careerdevs.myHalwayLocker.models.Student;
import com.careerdevs.myHalwayLocker.repositories.StudentRepository;
import com.careerdevs.myHalwayLocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentRepository repository;

    @GetMapping
    public @ResponseBody List<Student> getAllStudents() {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        return repository.findAll();
    }

    @GetMapping("/cohort/{cohort}")
    public ResponseEntity<List<Student>> getByCohort(@PathVariable Integer cohort) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        return new ResponseEntity<>(repository.findStudentsByCohort(cohort, Sort.by("firstName")), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody Student findById(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student newStudent) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        newStudent.setUser(currentUser);

        return new ResponseEntity<>(repository.save(newStudent), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody Student updateStudent(@PathVariable Long id, @RequestBody Student updatedData) {

        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        Student student = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (updatedData.getFirstName() != null) student.setFirstName(updatedData.getFirstName());
        if (updatedData.getLastName() != null) student.setLastName(updatedData.getLastName());
        if (updatedData.getCohort() != null) // TODO: 5/14/2022 Double check implementation with lists
        {
            for (Integer cohort : student.getCohort()) {
                if (!(student.getCohort().contains(cohort))){
                    student.getCohort().add(cohort);
                }
            }
            student.setCohort(updatedData.getCohort());
        }

        return repository.save(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> destroyStudent(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        repository.deleteById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

}


