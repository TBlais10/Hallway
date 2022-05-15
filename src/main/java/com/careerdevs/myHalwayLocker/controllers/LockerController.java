package com.careerdevs.myHalwayLocker.controllers;

import com.careerdevs.myHalwayLocker.Auth.User;
import com.careerdevs.myHalwayLocker.Security.services.UserService;
import com.careerdevs.myHalwayLocker.models.Locker;
import com.careerdevs.myHalwayLocker.repositories.ContentRepository;
import com.careerdevs.myHalwayLocker.repositories.LockerRepository;
import com.careerdevs.myHalwayLocker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/locker")
public class LockerController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LockerRepository repository;

    @GetMapping
    public @ResponseBody List<Locker> getAll() {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }
        return repository.findAll();
    }


    @GetMapping("/{locker_id}")
    public @ResponseBody Locker getById(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Locker> createLocker(@RequestBody Locker newLocker) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }
        return new ResponseEntity<>(repository.save(newLocker), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteLocker/{id}")
    public ResponseEntity<String> deleteLocker (@PathVariable Long id){
        User currentUser = userService.getCurrentUser();

        if (currentUser == null){
            return null;
        }

        repository.deleteById(id);
        return new ResponseEntity<>("Deleted Locker", HttpStatus.OK);
    }

}
