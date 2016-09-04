package it.unimi.di.sweng.SongGuru;

import java.io.IOException;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

public class BotResource extends ServerResource {

	@Post
	public Representation update(Representation data) throws IOException {

		final String token = getAttribute("token");
		if (!Configs.INSTANCE.SERVER_TOKEN.equals(token)) {
			setStatus(Status.CLIENT_ERROR_FORBIDDEN, "Wrong server token");
			return null;
		}

		final Update update = BotUtils.parseUpdate(data.getText());
		if (update.updateId() == null) {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Can't parse the update");
			return null;
		}

		final Message message = update.message();
		if (message == null)
			return null;

		final String userMessage = message.text();
		final Long chatId = message.chat().id();

		if (userMessage != null && chatId != null)
			BotPersistence.INSTANCE.route(chatId, CommandHelper.sanitizeMessage(userMessage));

		return null;
	}

}
