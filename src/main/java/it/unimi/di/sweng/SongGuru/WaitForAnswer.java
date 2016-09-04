package it.unimi.di.sweng.SongGuru;

import java.util.List;

import com.hyurumi.fb_bot_boilerplate.models.send.Button;
import com.vdurmont.emoji.EmojiParser;

public class WaitForAnswer implements BotState {

	private final String chatID;
	private final String songName;
	private final List<String> artistsList;
	private static final int BUTTON_LIMIT = 3;
	private static final MessageSender SENDER = BotPersistence.SENDER;

	public WaitForAnswer(final String chatID, final String songName, final List<String> artistsList) {
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

	private Button[] prepareArtistsKeyboard() {
		final int artistsSize = artistsList.size();
		final Button[] artistsButtons = new Button[BUTTON_LIMIT];
		String artist;
		for (int i = 1; i < artistsSize && i <= BUTTON_LIMIT; i++) {
			artist = artistsList.get(i);
			artistsButtons[i - 1] = Button.Postback(artist, artist);
		}
		return artistsButtons;
	}

}
