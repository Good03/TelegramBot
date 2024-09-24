package org.pollux.controller;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;




public class PolluxVapeBot extends TelegramLongPollingBot {

    public PolluxVapeBot(String botToken) {
        super(botToken);
    }

    public void sendMessage(long id, String text) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(id);
        message.setText(text);
        execute(message);
    }

    @Override
    public void onUpdateReceived(Update update) {
        String text = update.getMessage().getText();
        try {
            System.out.println("Received text: " + text);
            sendMessage(update.getMessage().getChatId(), text);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "polluxvape_bot";
    }
}
