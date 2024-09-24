package org.pollux;

import io.github.cdimascio.dotenv.Dotenv;
import org.pollux.controller.PolluxVapeBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

@SpringBootApplication
public class TelegramBotApplication {


    public static void main(String[] args) throws TelegramApiException, IOException {
        Dotenv dotenv = Dotenv.load();
        String botToken = dotenv.get("API_KEY");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        PolluxVapeBot polluxBot = new PolluxVapeBot(botToken);
        telegramBotsApi.registerBot(polluxBot);
        SpringApplication.run(TelegramBotApplication.class, args);
    }
}
