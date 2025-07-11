package com.benchmgmt.config;

import com.benchmgmt.entity.Admin;
import com.benchmgmt.entity.Trainer;
import com.benchmgmt.repository.AdminRepository;
import com.benchmgmt.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final TrainerRepository trainerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email).orElse(null);
        if (admin != null) {
            return new User(
                    admin.getEmail(),
                    admin.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        Trainer trainer = trainerRepository.findByEmail(email).orElse(null);
        if (trainer != null) {
            return new User(
                    trainer.getEmail(),
                    trainer.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_TRAINER"))
            );
        }

        throw new UsernameNotFoundException("User not found: " + email);
    }
}
