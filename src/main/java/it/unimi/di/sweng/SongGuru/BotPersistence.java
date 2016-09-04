package it.unimi.di.sweng.SongGuru;

import java.util.HashMap;

import com.pengrad.telegrambot.TelegramBotAdapter;

public enum BotPersistence {

	INSTANCE;

	private static final HashMap<Long, BotState> PERSISTENCE = new HashMap<>();
	private static final String HELP_MSG = "/searchsong - Search song by title\n"
			+ "/album - Get the albumart of the found song\n" + "/preview - Get a 30 secs preview of the found song\n"
			+ "/link - Get the Spotify link of the found song\n" + "/lyric - Get lyrics of the found song\n"
			+ "/mooseca - Get a random song\n" + "/help - Print this message";
	private static final String WRONG_MSG = "/searchsong - Search song by title\n" + "/help - Print this message\n"
			+ "/mooseca - Get a random song";
	public static final MessageSender SENDER = new MessageSender(TelegramBotAdapter.build(Configs.INSTANCE.BOT_TOKEN));

	public synchronized BotState route(final Long chatId, final String msg) {
		BotState tempState = null;
		switch (msg) {
		case CommandHelper.SEARCH_SONG:
			tempState = new WaitForSong(chatId);
			break;
		case CommandHelper.START:
		case CommandHelper.HELP:
			tempState = PERSISTENCE.get(chatId);
			SENDER.sendMessage(chatId, HELP_MSG);
			break;
		case CommandHelper.MOOSECA:
			tempState = new WaitForRequest(chatId, Spoty.getRandomSong());
			break;
		default:
			if (PERSISTENCE.containsKey(chatId))
				tempState = PERSISTENCE.get(chatId).nextState(msg);
			else
				SENDER.sendMessage(chatId, "Invalid command. Commands available:\n" + WRONG_MSG);
		}
		if (tempState != null)
			PERSISTENCE.put(chatId, tempState);
		else
			PERSISTENCE.remove(chatId);
		return tempState;
	}
}
