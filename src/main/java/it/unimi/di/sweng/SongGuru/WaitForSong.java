package it.unimi.di.sweng.SongGuru;

import java.util.List;

import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.vdurmont.emoji.EmojiParser;

public class WaitForSong implements BotState {

	private final Long chatID;
	private static final MessageSender SENDER = BotPersistence.SENDER;
	private static final String HELLO = EmojiParser.parseToUnicode(":wave:");
	private static final String SORRY = EmojiParser.parseToUnicode(":flushed:");
	private static final String MUSIC_NOTE = EmojiParser.parseToUnicode(":musical_note:");
	private static final String DISAPPOINT = EmojiParser.parseToUnicode(":disappointed_relieved:");
	private static final String ARTIST_SUGGESTION = EmojiParser.parseToUnicode(":grin:");
	private static final String AFFIRMATIVE = EmojiParser.parseToUnicode(":white_check_mark:");
	private static final String NEGATIVE = EmojiParser.parseToUnicode(":x:");

	public WaitForSong(final Long chatID) {
		this.chatID = chatID;
		SENDER.sendMessage(chatID, "Hi! " + HELLO + "\nTell me the title of the track " + MUSIC_NOTE);
	}

	@Override
	public BotState nextState(final String songName) {
		final List<String> artists = Spoty.getArtists(songName);
		if (artists.size() == 0) {
			SENDER.sendMessage(chatID,
					"Sorry " + SORRY + " something went wrong." + "\nI was unable to find your song " + DISAPPOINT);
			return null;
		}
		SENDER.sendMessage(chatID,
				"I've found this: " + artists.get(0) + ". Is it what you're looking for? " + ARTIST_SUGGESTION,
				prepareYesNoKeyboard());
		return new WaitForAnswer(chatID, songName, artists);
	}

	private Keyboard prepareYesNoKeyboard() {
		final String[][] keyboardMatrix = { { "Yes " + AFFIRMATIVE, "No " + NEGATIVE }, {} };
		return new ReplyKeyboardMarkup(keyboardMatrix).oneTimeKeyboard(true).resizeKeyboard(true);
	}

}
