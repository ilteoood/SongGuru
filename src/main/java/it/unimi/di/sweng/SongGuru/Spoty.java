package it.unimi.di.sweng.SongGuru;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
import com.wrapper.spotify.methods.authentication.ClientCredentialsGrantRequest;
import com.wrapper.spotify.models.ClientCredentials;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.wrapper.spotify.Api;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.models.Page;
import com.wrapper.spotify.models.SimpleArtist;
import com.wrapper.spotify.models.Track;

public class Spoty {

	private static final Api API = Api.builder().clientId(Configs.INSTANCE.CLIENT_ID).clientSecret(Configs.INSTANCE.CLIENT_SECRET).build();
	private static final int MAX_LIMIT = 50;
	private static final int MAX_OFFSET = 100000;
	private static final String ARTIST_QUERY = " artist:";
	private static final String WILDCARD = "*";
	private static final Logger LOGGER = Logger.getLogger(Spoty.class.getName());

	static{
		final ClientCredentialsGrantRequest request = API.clientCredentialsGrant().build();
		final SettableFuture<ClientCredentials> responseFuture = request.getAsync();
		Futures.addCallback(responseFuture, new FutureCallback<ClientCredentials>() {
			@Override
			public void onSuccess(ClientCredentials clientCredentials) {
				API.setAccessToken(clientCredentials.getAccessToken());
			}

			@Override
			public void onFailure(Throwable throwable) {}
		});
	}

	public static List<String> getArtists(final String songName) {

		final List<String> artistsList = new ArrayList<String>();
		final Page<Track> trackList;
		try {
			trackList = API.searchTracks(songName).limit(MAX_LIMIT).build().get();
		} catch (IOException | WebApiException e) {
			LOGGER.warning("No results for song " + songName + ": " + e.getMessage());
			return new ArrayList<String>();
		}
		for (Track track : trackList.getItems())
			for (SimpleArtist artist : track.getArtists())
				artistsList.add(artist.getName());

		return new ArrayList<String>(new LinkedHashSet<String>(artistsList));
	}

	public static TrackInfo getTrackInfo(final String songName, final String artistName) {
		final String query = WILDCARD + songName + WILDCARD + ARTIST_QUERY + artistName;
		final Page<Track> trackList;
		try {
			trackList = API.searchTracks(query).limit(MAX_LIMIT).build().get();
		} catch (IOException | WebApiException e) {
			LOGGER.warning("No results for song " + songName + " and artist " + artistName + ": " + e.getMessage());
			return null;
		}
		for (Track track : trackList.getItems())
			if (track.getName().toLowerCase().contains(songName.toLowerCase()))
				return new TrackInfo(track);
		return null;
	}

	public static TrackInfo getRandomSong() {
		Page<Track> trackList;
		while (true) {
			try {
				String randomTitle = RandomStringUtils.randomAlphabetic(1);
				int randomOffset = RandomUtils.nextInt(MAX_OFFSET);
				int randomLimit = RandomUtils.nextInt(MAX_LIMIT);
				trackList = API.searchTracks(randomTitle).limit(randomLimit).offset(randomOffset).build().get();
				return new TrackInfo(trackList.getItems().get(0));
			} catch (IOException | WebApiException | IndexOutOfBoundsException e) {
				LOGGER.warning("Invalid random parameter: " + e.getMessage());
			}
		}
	}

}
