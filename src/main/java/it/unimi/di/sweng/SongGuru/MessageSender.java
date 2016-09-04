package it.unimi.di.sweng.SongGuru;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardHide;
import com.pengrad.telegrambot.request.SendMessage;

public class MessageSender {

	private static final ReplyKeyboardHide EMPTY_KEYBOARD = new ReplyKeyboardHide();
	private final TelegramBot bot;

	public MessageSender(final TelegramBot bot) {
		this.bot = bot;
	}

	public void sendMessage(final long chatId, final String msg) {
		sendMessage(chatId, msg, EMPTY_KEYBOARD);
	}

	public void sendMessage(final long chatId, final String msg, final Keyboard keyboard) {
		bot.execute(new SendMessage(chatId, msg).replyMarkup(keyboard));
	}

}
