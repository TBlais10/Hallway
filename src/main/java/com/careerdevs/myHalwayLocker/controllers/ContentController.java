package com.careerdevs.myHalwayLocker.controllers;


import com.careerdevs.myHalwayLocker.Auth.User;
import com.careerdevs.myHalwayLocker.Security.services.UserService;
import com.careerdevs.myHalwayLocker.models.Content;
import com.careerdevs.myHalwayLocker.models.Locker;
import com.careerdevs.myHalwayLocker.models.Student;
import com.careerdevs.myHalwayLocker.repositories.ContentRepository;
import com.careerdevs.myHalwayLocker.repositories.LockerRepository;
import com.careerdevs.myHalwayLocker.repositories.StudentRepository;
import com.careerdevs.myHalwayLocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/content")
public class ContentController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ContentRepository repository;

    @Autowired
    private LockerRepository lockerRepository;

    @GetMapping
    public ResponseEntity<Iterable<Content>> getAll() {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Content findById(@PathVariable Long id){
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        return repository.findById(currentUser.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/locker/{id}")
    public List<Content> seeContentByLocker(@PathVariable long id){
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        Locker locker = lockerRepository.getById(id);

        return locker.getMyContent();
    }

    @PostMapping("/newContent")
    public ResponseEntity<Content> createContent(@RequestBody Content newContent) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        Student currentStudent = studentRepository.findById(currentUser.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        currentStudent.getLocker().getMyContent().add(newContent);

        return new ResponseEntity<>(repository.save(newContent), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public @ResponseBody Content updateContent(@PathVariable Long id, @RequestBody Content updates){
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        Content edits = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (updates.getTitle() != null) edits.setTitle(updates.getTitle());
        if (updates.getContent() != null) edits.setContent(updates.getContent());

        return repository.save(edits);
    }

    @DeleteMapping("/deleteContent/{id}")
    public ResponseEntity<String> deleteContent(@PathVariable Long id){
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        String content = repository.getById(id).getTitle();
        repository.deleteById(id);

        return new ResponseEntity<>("Deleted content " + content, HttpStatus.OK);
    }
}
