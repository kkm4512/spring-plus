package org.example.expert;

import jakarta.persistence.EntityManager;
import org.example.expert.config.PasswordEncoder;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestInfo {
    private static final int USERS_COUNT = 1_000_000;
    private static final int BATCH_SIZE = 1_000;


    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    @Rollback(false)
    public void 테스트_유저_생성() {
        long start = System.currentTimeMillis();
        List<User> users = new ArrayList<>(BATCH_SIZE);
        for ( int i=0; i<USERS_COUNT; i++ ) {
            String password = "!@Skdud340" + i;
            String convertPassword = passwordEncoder.encode(password);
            User user = new User(
                    "test@naver.com" + i,
                    "testNickname" + i,
                    convertPassword,
                    UserRole.USER
            );
            users.add(user);
            if (users.size() % BATCH_SIZE == 0) {
                userRepository.saveAll(users);
                em.flush();
                em.clear();
                users.clear();
            }
        }
        if (!users.isEmpty()) {
            userRepository.saveAll(users);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
