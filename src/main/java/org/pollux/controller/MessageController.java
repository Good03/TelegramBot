package org.pollux.controller;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class MessageController extends TelegramLongPollingBot {

    public MessageController(String botToken) throws IOException {
        super(botToken);
    }

    private void sendMessage(long id, String text) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(id);
        message.setText(text);
        execute(message);
    }
    private void logUserMessage(String username, String message) throws TelegramApiException {
        String filename = "data/logs/user_logs/" + username + "_logs.txt";
        try (FileWriter writer = new FileWriter(filename, true)){
            writer.append(message).append('\n').flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            String username = update.getMessage().getFrom().getUserName();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String formattedDate = sdf.format(date);
            if(username.isEmpty()) {
                username = "user_" + update.getMessage().getFrom().getId();
            }
            if(Objects.equals(message, "/start")) {
                try {
                    sendMessage(update.getMessage().getChatId(), "Hello " + update.getMessage().getFrom().getFirstName() + "!");
                    logUserMessage(username, message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else {
                String formatted_text = "Received message: \"" + message + "\" username: @" + username + " date: " + formattedDate;
                try {
                    logUserMessage(username, formatted_text);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
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
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public String getBotUsername() {
        return "polluxvape_bot";
    }
}
