package com.homemade.note;

import com.homemade.note.entity.NoteEntity;
import com.homemade.note.entity.UserEntity;
import com.homemade.note.repository.NoteRepository;
import com.homemade.note.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Slf4j
@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${settings.test.usersQuantity:#{10}}")
    private int testUsersQuantity;

    @Value("${settings.test.notesQuantity:#{5000}}")
    private int testNotesQuantity;

    private final UserRepository userRepository;
    private final NoteRepository noteRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public InitialDataLoader(UserRepository userRepository, NoteRepository noteRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String testUserName = "test";
        String password = "YouNeverGuess";

        UserEntity userEntity;
        String testEmail;
        Set<Long> userIds = new HashSet<>();
        for (int i = 0; i < testUsersQuantity; i++) {
            testEmail = testUserName + "_" + i + "@test.com";
            userEntity = userRepository.findByEmail(testEmail);

            if (userEntity == null) {
                userEntity = new UserEntity();
                userEntity.setPassword(passwordEncoder.encode(password));
                userEntity.setEmail(testEmail);
                userEntity = userRepository.save(userEntity);
            }

            userIds.add(userEntity.getId());
        }

        String testTitle = "Lorem ipsum dolor sit amet, consectetuer adipiscin";
        String testNote = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. N";

        NoteEntity noteEntity;
        for (int i = 0; i < testNotesQuantity; i++) {
            noteEntity = new NoteEntity();

            userEntity = new UserEntity();
            userEntity.setId(userIds.stream().findAny().orElse(2L));

            noteEntity.setUserEntity(userEntity);
            noteEntity.setNote(testNote);
            noteEntity.setTitle(testTitle);

            noteRepository.save(noteEntity);
        }
    }

}
