package org.pollux.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pollux.model.entities.UserEntity;
import org.pollux.model.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import java.sql.Date;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public void addUserToDatabase(User user) {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(user.getUserName());
            userEntity.setRegisteredAt(new Date(System.currentTimeMillis()));
            userEntity.setHasDiscount(false);
            userEntity.setQuantityOfByes(0);
            userRepository.save(userEntity);
            log.info("User {} added to database", user.getUserName());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
