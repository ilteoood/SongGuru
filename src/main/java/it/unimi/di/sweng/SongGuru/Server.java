package it.unimi.di.sweng.SongGuru;

import org.restlet.Component;
import org.restlet.data.Protocol;

public class Server {

	private final Component component;

	public Server() {
		component = new Component();
		component.getServers().add(Protocol.HTTP, Configs.INSTANCE.PORT);
		component.getClients().add(Protocol.HTTPS);
		component.getDefaultHost().attachDefault(new BotApplication());
	}

	public void start() throws Exception {
		component.start();
	}

	public void stop() throws Exception {
		component.stop();
	}

	public static void main(String[] args) throws Exception {
		System.err.format("Server configured at http://localhost:%d/bot/%s\n", Configs.INSTANCE.PORT,
				Configs.INSTANCE.SERVER_TOKEN);
		final Server server = new Server();
		server.start();
	}
}
