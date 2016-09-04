package it.unimi.di.sweng.SongGuru;

import java.io.IOException;
import java.util.List;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;
import com.hyurumi.fb_bot_boilerplate.models.webhook.Messaging;
import com.hyurumi.fb_bot_boilerplate.models.webhook.ReceivedMessage;

public class BotResource extends ServerResource {

	private static final Gson GSON = new Gson();

	@Post
	public Representation update(Representation data) throws IOException {

		String userMessage = "";
		final ReceivedMessage receivedMessage = GSON.fromJson(data.getText(), ReceivedMessage.class);
		final List<Messaging> messagings = receivedMessage.entry.get(0).messaging;
		for (Messaging messaging : messagings) {
			if (messaging.sender != null) {
				String chatId = messaging.sender.id;
				if (messaging.message != null)
				{
					if(messaging.message.text != null)
						userMessage = messaging.message.text;
				}
				else if (messaging.postback != null)
					userMessage = messaging.postback.payload;
				else
					return null;
				BotPersistence.INSTANCE.route(chatId, CommandHelper.sanitizeMessage(userMessage));
			}
		}
		return null;
	}

	@Get
	public String update() {
		final Form query = getQuery();
		if (query.getValues("hub.verify_token").equals(Configs.INSTANCE.BOT_TOKEN))
			return getQuery().getValues("hub.challenge");
		return "Invalid token";
	}
}
