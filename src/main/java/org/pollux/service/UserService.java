package org.pollux.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.pollux.model.entities.UserEntity;
import org.pollux.model.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;

    public void addUserToDatabase(User user) {
        try {
            UserEntity userEntity = new UserEntity();
            if(userRepository.findByUsername(user.getUserName()) != null) {
                log.error("User already exists");
            } else {
                userEntity.setUsername(user.getUserName());
                userEntity.setRegisteredAt(new Date(System.currentTimeMillis()).toLocalDate());
                userEntity.setHasDiscount(false);
                userEntity.setQuantityOfByes(0);
                userRepository.save(userEntity);
                log.info("User {} added to database", user.getUserName());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
