package it.unimi.di.sweng.SongGuru;

/**
 * Contiene le configurazioni del <em>bot</em> lette dalle variabili d'ambiente.
 *
 */
public enum Configs {
	INSTANCE;

	public final int PORT;
	public final String SERVER_TOKEN;
	public final String BOT_TOKEN;
	public final String CLIENT_ID;
	public final String CLIENT_SECRET;

	private Configs() {
		PORT = Integer.parseInt(System.getenv("PORT"));
		SERVER_TOKEN = System.getenv("TELEGRAM_SERVER_TOKEN");
		BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");
		CLIENT_ID = System.getenv("CLIENT_ID");
		CLIENT_SECRET = System.getenv("CLIENT_SECRET");
	}

}
