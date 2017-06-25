package it.unimi.di.sweng.SongGuru;

import okhttp3.MediaType;

/**
 * Contiene le configurazioni del <em>bot</em> lette dalle variabili d'ambiente.
 *
 */
public enum Configs {
	INSTANCE;

	public final int PORT;
	public final String SERVER_TOKEN;
	public final String BOT_TOKEN;
	public final String ACCESS_TOKEN;
	public final String CLIENT_ID;
	public final String CLIENT_SECRET;

	public final String END_POINT = "https://graph.facebook.com/v2.6/me/messages";
	public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	private Configs() {
		PORT = Integer.parseInt(System.getenv("PORT"));
		SERVER_TOKEN = System.getenv("TELEGRAM_SERVER_TOKEN");
		BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");
		ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");
		CLIENT_ID = System.getenv("CLIENT_ID");
		CLIENT_SECRET = System.getenv("CLIENT_SECRET");
	}

}
