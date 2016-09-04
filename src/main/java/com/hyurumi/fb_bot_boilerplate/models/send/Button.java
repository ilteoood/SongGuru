package com.hyurumi.fb_bot_boilerplate.models.send;

import com.google.gson.annotations.SerializedName;

/**
 * Created by genki.furumi on 4/15/16.
 */
public class Button {
	private enum Type {
		@SerializedName("postback")
		Postback, @SerializedName("web_url")
		WebUrl
	}

	private final Type type;
	private final String title;
	private final String payload;
	private final String url;

	private Button(Type type, String title, String url, String action) {
		this.type = type;
		this.title = title;
		this.url = url;
		this.payload = action;
	}

	public static Button Url(String title, String url) {
		return new Button(Type.WebUrl, title, url, null);
	}

	public static Button Postback(String title, String action) {
		return new Button(Type.Postback, title, null, action);
	}
}
