package com.example.chaneltelegrambot;

import com.example.chaneltelegrambot.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private BotConfig config;
    public TelegramBot(BotConfig botConfig) {
        this.config = botConfig;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start" -> {
                    sendMessage(chatId, "I`m working!");
                }
            }
        }
    }

    public void sendMessage(long chatId, String massageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(massageText);
        sendMessage.setChatId(String.valueOf(chatId));
        try {
            execute(sendMessage);
        } catch (TelegramApiException ignored) {}
    }
}
