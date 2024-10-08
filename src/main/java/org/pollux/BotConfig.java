package org.pollux;


import org.pollux.controller.MessageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {


    @Bean
    public TelegramBotsApi telegramBotsApi(MessageController messageController) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(messageController);
            return telegramBotsApi;
        } catch (TelegramApiException e) {
            throw new RuntimeException("Failed to initialize Telegram Bots API", e);
        }
    }
}
