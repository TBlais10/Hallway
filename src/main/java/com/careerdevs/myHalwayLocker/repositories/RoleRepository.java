package com.careerdevs.myHalwayLocker.repositories;

import com.careerdevs.myHalwayLocker.Auth.ERole;
import com.careerdevs.myHalwayLocker.Auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    @Query(value = "select count(*) from role",
            nativeQuery = true)
    int checkTableSize();

    @Query(value = "Insert into role (name) name values(:roleName) ",
            nativeQuery = true)
    Set<ERole> insertRoles(@Param("roleName")ERole role);
}
