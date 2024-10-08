package org.pollux;


import org.pollux.controller.MessageController;
import org.pollux.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

@SpringBootApplication
public class TelegramBotApplication {


    public static void main(String[] args) throws TelegramApiException, IOException {
        SpringApplication.run(TelegramBotApplication.class, args);
    }
}
