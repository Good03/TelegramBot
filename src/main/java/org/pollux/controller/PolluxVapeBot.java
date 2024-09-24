package org.pollux.controller;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;


public class PolluxVapeBot extends TelegramLongPollingBot {

    public PolluxVapeBot(String botToken) throws IOException {
        super(botToken);
    }


    private final Path logFilePath = Paths.get("todo_result/log.txt");//path to log file
    private final Path abolutePath = logFilePath.toAbsolutePath(); //absolute path to log file
    private final FileWriter logFileWriter = new FileWriter(abolutePath.toString(), true);


    public void sendMessage(long id, String text) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(id);
        message.setText(text);
        execute(message);
    }

    @Override
    public void onUpdateReceived(Update update) {
        String text = update.getMessage().getText();
        String username = update.getMessage().getFrom().getUserName();
        Date date = new Date();
        String formatted_text = "Received text: \"" + text + "\" username: @" + username + " date: " + date;
        try {
            System.out.println(formatted_text);
            System.out.println(date);
            sendMessage(update.getMessage().getChatId(), text);
            logFileWriter.append(formatted_text).append('\n').flush();
        } catch (TelegramApiException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "polluxvape_bot";
    }
}
