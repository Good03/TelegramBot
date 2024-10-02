package org.pollux.controller;



import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.pollux.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



import java.io.IOException;

import java.util.Objects;
@Slf4j
public class MessageController extends TelegramLongPollingBot {
    UserService userService; //TODO fix this.userService is null

    public MessageController(String botToken) throws IOException {
        super(botToken);
    }

    private void sendMessage(long id, String text) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(id);
        message.setText(text);
        execute(message);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            if(Objects.equals(message, "/start")) {
                try {
                    sendMessage(update.getMessage().getChatId(), "Hello " + update.getMessage().getFrom().getFirstName() + "!");
                    log.info("Command received: {}", message);
                    userService.addUserToDatabase(update.getMessage().getFrom());
                } catch (TelegramApiException e) {
                    log.error(e.getMessage());
                }
            } else {
                log.info("Message received: {}", message);
            }

        } else {
            try {
                sendMessage(update.getMessage().getChatId(), "Something went wrong...");
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public String getBotUsername() {
        return "polluxvape_bot";
    }
}
