package com.luanr.agregadorinvestimentos.repository;

import com.luanr.agregadorinvestimentos.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String basic);
}
