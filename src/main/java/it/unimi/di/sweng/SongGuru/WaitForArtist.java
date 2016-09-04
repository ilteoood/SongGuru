package it.unimi.di.sweng.SongGuru;

import com.vdurmont.emoji.EmojiParser;

public class WaitForArtist implements BotState {

	private static final String SORRY_EMOJI = EmojiParser.parseToUnicode(":cold_sweat:");
	private final String songName;
	private final Long chatID;
	private static final MessageSender SENDER = BotPersistence.SENDER;

	public WaitForArtist(final Long chatID, final String songName) {
		this.songName = songName;
		this.chatID = chatID;
	}

	@Override
	public BotState nextState(final String artistName) {
		final TrackInfo trackInfo = Spoty.getTrackInfo(songName, artistName);

		if (trackInfo == null) {
			SENDER.sendMessage(chatID, "Sorry, something went wrong. " + SORRY_EMOJI);
			return null;
		}

		return new WaitForRequest(chatID, trackInfo);
	}

}