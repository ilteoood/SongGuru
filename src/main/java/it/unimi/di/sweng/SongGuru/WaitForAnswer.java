package it.unimi.di.sweng.SongGuru;

import java.util.List;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.vdurmont.emoji.EmojiParser;

public class WaitForAnswer implements BotState {

	private final Long chatID;
	private final String songName;
	private final List<String> artistsList;
	private static final MessageSender SENDER = BotPersistence.SENDER;

	public WaitForAnswer(final Long chatID, final String songName, final List<String> artistsList) {
		this.chatID = chatID;
		this.songName = songName;
		this.artistsList = artistsList;
	}

	@Override
	public BotState nextState(final String answer) {
		if (answer.contains("Yes"))
			return new WaitForArtist(chatID, songName).nextState(artistsList.get(0));
		else {
			final String emoji_microphone = EmojiParser.parseToUnicode(":microphone:");
			SENDER.sendMessage(chatID,
					emoji_microphone + " Select an artist from the list or type it " + emoji_microphone,
					prepareArtistsKeyboard());
			return new WaitForArtist(chatID, songName);
		}
	}

	private Keyboard prepareArtistsKeyboard() {
		final int artistsSize = artistsList.size();
		final String[][] keyboardMatrix = new String[artistsSize - 1][1];
		for (int i = 1; i < artistsSize; i++)
			keyboardMatrix[i - 1][0] = artistsList.get(i);
		return new ReplyKeyboardMarkup(keyboardMatrix).oneTimeKeyboard(true).resizeKeyboard(true);
	}

}
