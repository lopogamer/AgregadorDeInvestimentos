package com.luanr.agregadorinvestimentos.config;


import com.luanr.agregadorinvestimentos.entity.Role;
import com.luanr.agregadorinvestimentos.entity.User;
import com.luanr.agregadorinvestimentos.repository.RoleRepository;
import com.luanr.agregadorinvestimentos.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Configuration

public class AdminUserConfig implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    
    @Value("#{environment.ADMIN_USER}")
    string ADMIN_USER;

    @Value("#{environment.ADMIN_PASSWORD}")
    string ADMIN_PASSWORD;

    @Value("#{environment.ADMIN_EMAIL}")
    string ADMIN_EMAIL;

    public AdminUserConfig(RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var adminRole = roleRepository.findByRoleName(Role.Values.ADMIN.name()).orElseThrow();

        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                (user) -> {
                    System.out.println("User admin already exists");
                },
                () -> {
                    var user = new User();
                    user.setUsername(ADMIN_USER);
                    user.setPassword(bCryptPasswordEncoder.encode(ADMIN_PASSWORD));
                    user.setRoles(Set.of(adminRole));
                    user.setUser_id(UUID.randomUUID());
                    user.setEmail(ADMIN_EMAIL);
                    user.setCreated_at(Instant.now());
                    userRepository.save(user);
                }
        );

    }
}
