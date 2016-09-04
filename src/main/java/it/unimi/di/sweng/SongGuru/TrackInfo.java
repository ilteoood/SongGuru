package it.unimi.di.sweng.SongGuru;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.wrapper.spotify.models.SimpleArtist;
import com.wrapper.spotify.models.Track;

public class TrackInfo {

	private final String track_title;
	private final String artist_name;
	private final String track_duration;
	private final int track_number;
	private final String album_name;
	private final int popularity;
	private final int album_number;
	private final Track track;

	private static final int FIRST_AVAILABLE = 0;
	private static final String SEPARATOR = "\n";
	private static final String EXTERNAL_URL_INDEX = "spotify";

	public TrackInfo(final Track track) {
		this.track = track;
		final StringBuilder artistBuilder = new StringBuilder();
		track_title = track.getName();
		for (SimpleArtist artist : track.getArtists())
			artistBuilder.append(artist.getName() + " ");
		artist_name = artistBuilder.toString().trim();
		track_duration = formatTime(track.getDuration());
		track_number = track.getTrackNumber();
		album_name = track.getAlbum().getName();
		album_number = track.getDiscNumber();
		popularity = track.getPopularity();
	}

	public List<String> getTrackInfo() {
		List<String> info = new ArrayList<String>();
		info.add("Artist: " + artist_name + SEPARATOR);
		info.add("Track title: " + track_title + SEPARATOR);
		info.add("Track duration: " + track_duration + SEPARATOR);
		info.add("Track number: " + track_number + SEPARATOR);
		info.add("Track popularity: " + popularity + SEPARATOR);
		info.add("Album name: " + album_name + SEPARATOR);
		info.add("Album number: " + album_number + SEPARATOR);
		return info;
	}

	public String getTrackPreview() {
		return track.getPreviewUrl();
	}

	public String getAlbumCover() {
		return track.getAlbum().getImages().get(FIRST_AVAILABLE).getUrl();
	}

	public String getTrackLink() {
		return track.getExternalUrls().get(EXTERNAL_URL_INDEX);
	}

	public String getLyric() {
		return LyricsFetcher.getLyric(artist_name, track_title);
	}

	private String formatTime(final int duration) {
		final long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		return String.format("%02d:%02d", minutes,
				TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(minutes));
	}

}
