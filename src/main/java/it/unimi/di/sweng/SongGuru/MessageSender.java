package it.unimi.di.sweng.SongGuru;

import java.io.IOException;
import java.util.List;

import com.google.common.base.Splitter;
import com.hyurumi.fb_bot_boilerplate.models.send.Button;
import com.hyurumi.fb_bot_boilerplate.models.send.Message;

public class MessageSender {

	private static final int FB_MAX_CHAR = 320;

	public void sendMessage(final String chatId, final String msg) {
		sendMessage(chatId, Message.Text(msg));
	}

	public void sendMessage(final String chatId, final String msg, final Button[] buttons) {
		Message message = Message.Button(msg);
		for (Button button : buttons)
			message.addButton(button);
		sendMessage(chatId, message);
	}

	public void sendMessage(String chatId, Message message) {

		final String messageText = message.text;

		Message[] toSendArray = new Message[] { message };

		if (messageText != null && messageText.length() >= FB_MAX_CHAR) {
			final List<String> lyricSplitted = Splitter.fixedLength(FB_MAX_CHAR).splitToList(messageText);
			final int splitLenght = lyricSplitted.size();
			toSendArray = new Message[splitLenght];
			for (int i = 0; i < splitLenght; i++)
				toSendArray[i] = Message.Text(lyricSplitted.get(i));
		}
		try {
			for (Message toSend : toSendArray)
				toSend.sendTo(chatId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
