package com.careerdevs.myHalwayLocker.controllers;

import com.careerdevs.myHalwayLocker.Auth.User;
import com.careerdevs.myHalwayLocker.Security.services.UserService;
import com.careerdevs.myHalwayLocker.models.Person;
import com.careerdevs.myHalwayLocker.repositories.PersonRepository;
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
    private PersonRepository repository;

    @GetMapping
    public @ResponseBody List<Person> getAllPeople() {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        return repository.findAll();
    }

    @GetMapping("/cohort/{cohort}")
    public ResponseEntity<List<Person>> getByCohort(@PathVariable Integer cohort) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        return new ResponseEntity<>(repository.findStudentsByCohort(cohort, Sort.by("firstName")), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody Person findById(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person newPerson) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        newPerson.setUser(currentUser);

        return new ResponseEntity<>(repository.save(newPerson), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    Person updatePerson(@PathVariable Long id, @RequestBody Person updatedData) {

        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        Person person = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (updatedData.getFirstName() != null) person.setFirstName(updatedData.getFirstName());
        if (updatedData.getLastName() != null) person.setLastName(updatedData.getLastName());
        if (updatedData.getCohort() != null) // TODO: 5/14/2022 Double check implementation with lists
        {
            for (Integer cohort : person.getCohort()) {
                if (!(person.getCohort().contains(cohort))){
                    person.getCohort().add(cohort);
                }
            }
            person.setCohort(updatedData.getCohort());
        }

        return repository.save(person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> destroyPerson(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        repository.deleteById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

}


