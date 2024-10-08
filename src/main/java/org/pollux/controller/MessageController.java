package org.pollux.controller;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import org.pollux.model.entities.UserEntity;
import org.pollux.model.entities.VapeEntity;
import org.pollux.service.UserService;
import org.pollux.service.VapeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;




import java.util.List;
import java.util.Objects;

@Component
@Log4j2
public class MessageController extends TelegramLongPollingBot {
    private final UserService userService;
    private final VapeService vapeService;

    public MessageController(UserService userService, @Value("${BOT_TOKEN}") String botToken, VapeService vapeService) {
        super(botToken);
        this.userService = userService;
        this.vapeService = vapeService;
    }

    private void sendMessage(long id, String text) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(id);
        message.setText(text);
        execute(message);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (message) {
                case "/start":
                    try {
                        sendMessage(chatId, "Hello " + update.getMessage().getFrom().getFirstName() + "!");
                        log.info("Command received: {}", message);
                        userService.addUserToDatabase(update.getMessage().getFrom());
                    } catch (TelegramApiException e) {
                        log.error(e.getMessage());
                    }
                    break;

                case "/displayAllUsers":
                    try {
                        log.info("Command received: {}", message);
                        List<UserEntity> users = userService.getAllUsers();
                        StringBuilder allUsersMessage = new StringBuilder("List of all users:\n");

                        for (int count = 0; count < users.size(); count++) {
                            allUsersMessage.append(count + 1).append(") ").append(users.get(count).getUsername()).append("\n");
                        }

                        sendMessage(chatId, allUsersMessage.toString());
                    } catch (Exception e) {
                        log.error("Error fetching users: {}", e.getMessage());
                    }
                    break;
                case "/displayAllVapes":
                    try {
                        log.info("Command received: {}", message);
                        List<VapeEntity> vapes = vapeService.getAllVapes();
                        StringBuilder allVapesMessage = new StringBuilder("List of all vapes:\n");

                        for (int count = 0; count < vapes.size(); count++) {
                            allVapesMessage.append(count + 1)
                                    .append(") ")
                                    .append(vapes.get(count)
                                            .getBrand())
                                    .append(" ")
                                    .append(vapes.get(count).getModel())
                                    .append(" ")
                                    .append(vapes.get(count).getFlavor())
                                    .append(" ")
                                    .append(vapes.get(count).getPuffs())
                                    .append(" ")
                                    .append(vapes.get(count).getPercentsOfNicotine())
                                    .append("%")
                                    .append("\n");
                        }

                        sendMessage(chatId, allVapesMessage.toString());
                    } catch (Exception e) {
                        log.error("Error fetching users: {}", e.getMessage());
                    }
                    break;

                case String ignored when update.getMessage().getText().startsWith("/addVape "):
                     String input = update.getMessage().getText().substring("/addVape ".length()).trim();
                    if (input.isEmpty()) {
                        try {
                            sendMessage(chatId, "Please provide the vape information in the format: brand, model, flavor, puffs, nicotine percentage (e.g., BrandName, ModelName, FlavorName, 100, 3.5)");
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        vapeService.addVapeToDatabase(chatId, input);
                        try {
                            sendMessage(chatId, "Vape information has been successfully added.");
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }

                     break;

                default:
                    try {
                        sendMessage(chatId, "Unknown command...");
                    } catch (TelegramApiException e) {
                        log.error("Error sending message: {}", e.getMessage());
                    }
                    break;
            }
        } else {
            try {
                sendMessage(update.getMessage().getChatId(), "Something went wrong...");
            } catch (TelegramApiException e) {
                log.error("Error sending message: {}", e.getMessage());
            }
        }
    }


    @Override
    public String getBotUsername() {
        return "polluxvape_bot";
    }
}
