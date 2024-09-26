package org.pollux.controller;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;


public class PolluxVapeBot extends TelegramLongPollingBot {

    public PolluxVapeBot(String botToken) throws IOException {
        super(botToken);
    }

    private void sendMessage(long id, String text) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(id);
        message.setText(text);
        execute(message);
    }
    private void logMessage(String username, String message) throws TelegramApiException {
        String filename = "data/logs/" + username + "_logs.txt";
        try (FileWriter writer = new FileWriter(filename, true)){
            writer.append(message).append('\n').flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        String username = update.getMessage().getFrom().getUserName();
        Date date = new Date();
        String formatted_text = "Received message: \"" + message + "\" username: @" + username + " date: " + date;
        try {
            sendMessage(update.getMessage().getChatId(), message);
            logMessage(username, formatted_text);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "polluxvape_bot";
    }
}
