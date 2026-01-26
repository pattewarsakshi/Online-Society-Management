package com.society.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.society.entity.User;
import com.society.entity.Society;
import com.society.entityenum.Role;
import com.society.repository.UserRepository;
import com.society.repository.SocietyRepository;

@Configuration
public class AdminBootstrap {

    @Bean
    CommandLineRunner createAdmin(
            UserRepository userRepository,
            SocietyRepository societyRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // ✅ If admin already exists → do nothing
            if (userRepository.existsByEmail("admin@mail.com")) {
                return;
            }

            // ✅ Society must exist (usually societyId = 1)
            Society society = societyRepository.findById(1)
                    .orElseThrow(() -> new RuntimeException("Society not found"));

            User admin = new User();
            admin.setFirstName("Super");
            admin.setLastName("Admin");
            admin.setEmail("admin@mail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setSociety(society);

            userRepository.save(admin);

            System.out.println("✅ ADMIN CREATED: admin@mail.com / admin123");
        };
    }
}
