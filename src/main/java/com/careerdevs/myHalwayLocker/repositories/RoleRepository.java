package com.careerdevs.myHalwayLocker.repositories;

import com.careerdevs.myHalwayLocker.Auth.ERole;
import com.careerdevs.myHalwayLocker.Auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
