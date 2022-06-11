package com.careerdevs.myHalwayLocker.controllers;

import com.careerdevs.myHalwayLocker.Auth.ERole;
import com.careerdevs.myHalwayLocker.Auth.User;
import com.careerdevs.myHalwayLocker.Security.services.UserService;
import com.careerdevs.myHalwayLocker.models.Teacher;
import com.careerdevs.myHalwayLocker.repositories.TeacherRepository;
import com.careerdevs.myHalwayLocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/teachers")
public class TeacherController {
    @Autowired
    public TeacherRepository repository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public UserService userService;

    //get all
    @GetMapping
    public @ResponseBody List<Teacher> getAllTeachers(){
        User currentUser = userService.getCurrentUser();

        if (currentUser == null && currentUser.getRoles().equals(ERole.ROLE_STUDENT)){
            return null;
        }
        return repository.findAll();
    }

    //get one\
    @GetMapping("/{id}")
    public @ResponseBody Teacher getOneTeacher(@PathVariable Long id){
        User currentUser = userService.getCurrentUser();

        if (currentUser == null && currentUser.getRoles().equals(ERole.ROLE_STUDENT)){
            return null;
        }

        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //post teacher
    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher newTeacher){
        User currentUser = userService.getCurrentUser();

        if (currentUser == null && currentUser.getRoles().equals(ERole.ROLE_STUDENT)){
            return null;
        }
        newTeacher.setUser(currentUser);

        return new ResponseEntity<>(repository.save(newTeacher), HttpStatus.CREATED);
    }

    //patch teacher
    @PatchMapping("/{id}")
    public @ResponseBody Teacher updateTeacher(@PathVariable Long id, @RequestBody Teacher updatedData){
        User currentUser = userService.getCurrentUser();

        if (currentUser == null && currentUser.getRoles().equals(ERole.ROLE_STUDENT)){
            return null;
        }
        Teacher teacher = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (updatedData.getFirstName() != null) teacher.setFirstName(updatedData.getFirstName());
        if (updatedData.getLastName() != null) teacher.setLastName(updatedData.getLastName());
        if (updatedData.getCohort() != null) // TODO: 6/10/2022 Double check implementation with lists
        {
            for (Integer cohort : teacher.getCohort()) {
                if (!(teacher.getCohort().contains(cohort))){
                    teacher.getCohort().add(cohort);
                }
            }
            teacher.setCohort(updatedData.getCohort());
        }

        if (updatedData.isSub() != teacher.isSub()) teacher.setSub(updatedData.isSub());

        if (updatedData.getSubjects() != null){
            for (String subject : teacher.getSubjects()) {
                if (!(teacher.getSubjects().contains(subject))){
                    teacher.getSubjects().add(subject);
                }
            }
        }

        return repository.save(teacher);
    }

    //deleteTeacher
    @DeleteMapping("{id}")
    public ResponseEntity<String> destroyTeacher(@PathVariable Long id){
        User currentUser = userService.getCurrentUser();

        if (currentUser == null && currentUser.getRoles().equals(ERole.ROLE_STUDENT)){
            return null;
        }

        repository.deleteById(id);
        return new ResponseEntity<>("Deleted Teacher", HttpStatus.OK);
    }
}
