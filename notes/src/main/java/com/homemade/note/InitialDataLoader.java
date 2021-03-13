package com.homemade.note;

import com.homemade.note.entity.UserEntity;
import com.homemade.note.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public InitialDataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String testUserName = "test";
        String password = "YouNeverGuess";

        UserEntity userEntity;
        String testEmail;
        for (int i = 0; i < 4; i++) {
            testEmail = testUserName + "_" + i + "@test.com";
            userEntity = userRepository.findByEmail(testEmail);

            if (userEntity == null) {
                userEntity = new UserEntity();
                userEntity.setPassword(passwordEncoder.encode(password));
                userEntity.setEmail(testEmail);
                userRepository.save(userEntity);
            }
        }
    }

}
