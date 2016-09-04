package it.unimi.di.sweng.SongGuru;

import java.util.HashMap;
import java.util.List;

import com.hyurumi.fb_bot_boilerplate.models.send.Message;
import com.vdurmont.emoji.EmojiParser;

public class WaitForRequest implements BotState {

	private static final String WARNING = EmojiParser.parseToUnicode(":warning:");
	private static final String ALBUM_NUMBER = EmojiParser.parseToUnicode(":minidisc:");
	private static final String DISC_NAME = EmojiParser.parseToUnicode(":dvd:");
	private static final String POPULARITY = EmojiParser.parseToUnicode(":star:");
	private static final String TRACK_NUMBER = EmojiParser.parseToUnicode(":repeat:");
	private static final String TRACK_DURATION = EmojiParser.parseToUnicode(":clock8:");
	private static final String TRACK_TITLE = EmojiParser.parseToUnicode(":musical_note:");
	private static final String ARTIST_NAME = EmojiParser.parseToUnicode(":microphone:");

	private final String chatId;
	private final HashMap<String, Message> methodInvocation = new HashMap<String, Message>();

	private static final MessageSender SENDER = BotPersistence.SENDER;

	public WaitForRequest(final String chatId, final TrackInfo track) {
		final String trackInfo = insert(track);
		this.chatId = chatId;
		SENDER.sendMessage(chatId, trackInfo);
		methodInvocation.put(CommandHelper.TRACK_INFO, Message.Text(trackInfo));
		methodInvocation.put(CommandHelper.ALBUM, Message.Image(track.getAlbumCover()));
		methodInvocation.put(CommandHelper.PREVIEW, Message.Text(track.getTrackPreview()));
		methodInvocation.put(CommandHelper.LINK, Message.Text(track.getTrackLink()));
		methodInvocation.put(CommandHelper.LYRIC, Message.Text(track.getLyric()));
	}

	@Override
	public BotState nextState(final String msg) {
		final Message toSend = methodInvocation.getOrDefault(msg,
				Message.Text(WARNING + " Invalid command: " + msg + " " + WARNING));
		SENDER.sendMessage(chatId, toSend);
		return this;
	}

	private String insert(final TrackInfo track) {
		final StringBuilder builder = new StringBuilder();
		final List<String> trackInfo = track.getTrackInfo();
		builder.append(ARTIST_NAME + " " + trackInfo.get(0));
		builder.append(TRACK_TITLE + " " + trackInfo.get(1));
		builder.append(TRACK_DURATION + " " + trackInfo.get(2));
		builder.append(TRACK_NUMBER + " " + trackInfo.get(3));
		builder.append(POPULARITY + " " + trackInfo.get(4));
		builder.append(DISC_NAME + " " + trackInfo.get(5));
		builder.append(ALBUM_NUMBER + " " + trackInfo.get(6));
		return builder.toString();
	}
}