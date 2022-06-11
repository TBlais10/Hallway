package com.careerdevs.myHalwayLocker.repositories;

import com.careerdevs.myHalwayLocker.models.Person;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findStudentsByCohort(Integer cohort, Sort sort);

}
