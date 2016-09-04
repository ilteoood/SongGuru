package it.unimi.di.sweng.SongGuru;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SpotyTest {

	@Test
	public void testGetArtists() {
		List<String> artistsList = Spoty.getArtists("In too deep");
		assertTrue(artistsList.contains("Sum 41"));
		assertFalse(artistsList.contains("Gigi D'Alessio"));
		artistsList = Spoty.getArtists("In the end");
		assertTrue(artistsList.contains("Linkin Park"));
		artistsList = Spoty.getArtists("Panda");
		assertTrue(artistsList.contains("Desiigner"));
	}

	@Test
	public void testTrackInfo() {
		TrackInfo trackInfo = Spoty.getTrackInfo("In too deep", "Sum 41");
		final String SEPARATOR = "\n";
		List<String> info = new ArrayList<String>();
		info.add("Artist: Sum 41" + SEPARATOR);
		info.add("Track title: In Too Deep" + SEPARATOR);
		info.add("Track duration: 03:27" + SEPARATOR);
		info.add("Track number: 7" + SEPARATOR);
		info.add("Album name: All Killer, No Filler"  + SEPARATOR);
		info.add("Album number: 1"  + SEPARATOR);
		
		List<String> realInfo = trackInfo.getTrackInfo();
		realInfo.remove(4);
		
		assertEquals(info, realInfo);
		
		info = new ArrayList<String>();

		trackInfo = Spoty.getTrackInfo("In the end", "Linkin Park");
		info.add("Artist: Linkin Park" + SEPARATOR);
		info.add("Track title: In The End" + SEPARATOR);
		info.add("Track duration: 03:39" + SEPARATOR);
		info.add("Track number: 8" + SEPARATOR);
		info.add("Album name: Hybrid Theory (Bonus Track Version)"  + SEPARATOR);
		info.add("Album number: 1"  + SEPARATOR);
		
		realInfo = trackInfo.getTrackInfo();
		realInfo.remove(4);

		assertEquals(info, realInfo);
	}
	
	@Test
	public void testSameTrackNameAndAlbum()
	{
		final TrackInfo trackInfo = Spoty.getTrackInfo("Le cose che non ho", "Marco Mengoni");
		final String SEPARATOR = "\n";
		List<String> info = new ArrayList<String>();
		info.add("Artist: Marco Mengoni" + SEPARATOR);
		info.add("Track title: Le cose che non ho" + SEPARATOR);
		info.add("Track duration: 04:04" + SEPARATOR);
		info.add("Track number: 9" + SEPARATOR);
		info.add("Album name: Le cose che non ho"  + SEPARATOR);
		info.add("Album number: 1"  + SEPARATOR);
		
		final List<String> realInfo = trackInfo.getTrackInfo();
		realInfo.remove(4);
		
		assertEquals(info, realInfo);
	}
	
	@Test
	public void testSuggestions()
	{
		final TrackInfo trackInfo = Spoty.getTrackInfo("rose", "The Chainsmokers");
		final String SEPARATOR = "\n";
		List<String> info = new ArrayList<String>();
		info.add("Artist: The Chainsmokers ROZES" + SEPARATOR);
		info.add("Track title: Roses" + SEPARATOR);
		info.add("Track duration: 03:46" + SEPARATOR);
		info.add("Track number: 1" + SEPARATOR);
		info.add("Album name: Roses"  + SEPARATOR);
		info.add("Album number: 1"  + SEPARATOR);
		
		final List<String> realInfo = trackInfo.getTrackInfo();
		realInfo.remove(4);
		
		assertEquals(info, realInfo);
	}

	@Test
	public void testEmptyTrackInfo() {
		TrackInfo trackInfo = Spoty.getTrackInfo("abcdefghijklm", "abcdefghijklm");
		assertEquals(null, trackInfo);
		
		trackInfo = Spoty.getTrackInfo("In too deep", "Gigi D'Alessio");
		assertEquals(null, trackInfo);
	}
	
	@Test
	public void testEmptySongName() {
		List<String> artistsList = Spoty.getArtists("");
		assertEquals(0, artistsList.size());
	}
	
	@Test
	public void testInvalidSongArtistName() {
		TrackInfo trackInfo = Spoty.getTrackInfo("ciao*","&offset=*[]||");
		assertEquals(null, trackInfo);
	}
	
	@Test
	public void testGetAlbum()
	{
		TrackInfo trackInfo = Spoty.getTrackInfo("In too deep", "Sum 41");
		assertEquals("https://i.scdn.co/image/143d706e32d36794d1e0bd253d7b0fa3bb79c98e", trackInfo.getAlbumCover());
		trackInfo = Spoty.getTrackInfo("In the end", "Linkin Park");
		assertEquals("https://i.scdn.co/image/66ff51342a9b250bf5b998fd0ec8e977671468bc", trackInfo.getAlbumCover());
	}
	
	@Test
	public void testGetPreview()
	{
		TrackInfo trackInfo = Spoty.getTrackInfo("In too deep", "Sum 41");
		assertEquals("https://p.scdn.co/mp3-preview/cf7b72458ad62c5fbcf31e75425e815d4b324c19", trackInfo.getTrackPreview());
		trackInfo = Spoty.getTrackInfo("In the end", "Linkin Park");
		assertEquals("https://p.scdn.co/mp3-preview/23552502ef8477c882d6f2ae07e7579f38b8a92d", trackInfo.getTrackPreview());
	}
	
	@Test
	public void testDirectLink()
	{
		TrackInfo trackInfo = Spoty.getTrackInfo("In too deep", "Sum 41");
		assertEquals("https://open.spotify.com/track/1HNE2PX70ztbEl6MLxrpNL", trackInfo.getTrackLink());
	}
	
	@Test
	public void testLyrics()
	{
		TrackInfo trackInfo = Spoty.getTrackInfo("Hello", "Adele");
		assertTrue(trackInfo.getLyric().contains("if after all these years"));
	}

}
