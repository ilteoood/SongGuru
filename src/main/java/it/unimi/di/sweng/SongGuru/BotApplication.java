package it.unimi.di.sweng.SongGuru;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class BotApplication extends Application {
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/webhook", BotResource.class);
		return router;
	}
}
