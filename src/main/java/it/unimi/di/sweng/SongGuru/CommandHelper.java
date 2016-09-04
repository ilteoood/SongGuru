package it.unimi.di.sweng.SongGuru;

public class CommandHelper {

	private CommandHelper() {
	}

	private static final String COMMAND_CHAR = "/";
	public final static String SEARCH_SONG = "/searchsong";
	public final static String ALBUM = "/album";
	public final static String PREVIEW = "/preview";
	public final static String LINK = "/link";
	public final static String LYRIC = "/lyric";
	public final static String START = "/start";
	public final static String HELP = "/help";
	public final static String TRACK_INFO = "/trackinfo";
	public final static String MOOSECA = "/mooseca";
	public final static String BOT_NAME = "@SongGuruBot";

	public static String sanitizeMessage(final String message) {
		if (message.startsWith(COMMAND_CHAR) && message.contains(BOT_NAME))
			return message.replaceAll(BOT_NAME, "");
		return message;
	}

}
