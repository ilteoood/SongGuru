package it.unimi.di.sweng.SongGuru;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestWaitForRequest {
	
	@Mock
	TrackInfo track;
	
	final String SEPARATOR = "\n";
	List<String> info = new ArrayList<String>();
	
	public String chatId;
	
	
	@Before
	public void setup()
	{
		chatId = "65193455";
		info.add("Artist: Sum 41" + SEPARATOR);
		info.add("Track title: In Too Deep" + SEPARATOR);
		info.add("Track duration: 03:27" + SEPARATOR);
		info.add("Track number: 7" + SEPARATOR);
		info.add("Track popularity: 64" + SEPARATOR);
		info.add("Album name: All Killer, No Filler"  + SEPARATOR);
		info.add("Album number: 1"  + SEPARATOR);
		when(track.getTrackInfo()).thenReturn(info);
	}
	
	@Test
	public void testInit()
	{
		WaitForRequest request = new WaitForRequest(chatId, track);
		assertEquals(request, request.nextState(CommandHelper.TRACK_INFO));
	}
	
	@Test
	public void testInvalidCommand()
	{
		WaitForRequest request = new WaitForRequest(chatId, track);
		assertEquals(request, request.nextState("/info"));
	}
	
	@Test
	public void verifyGetTrackInfo()
	{	
		new WaitForRequest(chatId, track);
		verify(track).getTrackInfo();
	}
	
	@Test
	public void verifyGetTrackPreview()
	{	
		final String previewLink = "https://p.scdn.co/mp3-preview/cf7b72458ad62c5fbcf31e75425e815d4b324c19";
		when(track.getTrackPreview()).thenReturn(previewLink);
		final WaitForRequest request = new WaitForRequest(chatId, track);
		assertEquals(request, request.nextState(CommandHelper.PREVIEW));
		InOrder order = inOrder(track);
		order.verify(track).getTrackInfo();
		order.verify(track).getTrackPreview();
	}
	
	@Test
	public void verifyGetTrackLink()
	{
		final String publicLink = "https://open.spotify.com/track/1HNE2PX70ztbEl6MLxrpN";
		when(track.getTrackLink()).thenReturn(publicLink);
		final WaitForRequest request = new WaitForRequest(chatId, track);
		assertEquals(request, request.nextState(CommandHelper.LINK));
		InOrder order = inOrder(track);
		order.verify(track).getTrackInfo();
		order.verify(track).getTrackLink();
	}
	
	@Test
	public void verifyGetAlbumArt()
	{	
		final String albumArt = "https://i.scdn.co/image/e8201efef7598e80b043f31c78e23a756173b1b6";
		when(track.getAlbumCover()).thenReturn(albumArt);
		final WaitForRequest request = new WaitForRequest(chatId, track);
		assertEquals(request, request.nextState(CommandHelper.ALBUM));
		InOrder order = inOrder(track);
		order.verify(track).getTrackInfo();
		order.verify(track).getAlbumCover();
	}
	
	@Test
	public void verifyGetLyric()
	{	
		final String lyric = "Hello, it's me\nI was wondering if after all these years\nYou'd like to meet\nTo go over everything\nThey say that time's supposed to heal ya\nBut I ain't done much healing\n\nHello, can you hear me?\nI'm in California dreaming about who\nWe used to be\nWhen we were younger and free\nI've forgotten how it felt\nBefore the world fell at our feet\n\nThere's such a difference between us\nAnd a million miles\n\nHello from the other side\nI must have called a thousand times\nTo tell you I'm sorry\nFor everything that I've done\nBut when I call\nYou never seem to be home\n\nHello from the outside\nAt least I can say that I've tried\nTo tell you I'm sorry\nFor breaking your heart\nBut it don't matter\nIt clearly doesn't tear you apart\nAnymore\n\nHello, how are you?\nIt's so typical of me to talk\nAbout myself, I'm sorry\nI hope that you're well\nDid you ever make it out of that town\nWhere nothing ever happened?\n\nIt's no secret that the both of us\nAre running out of time\n\nSo hello from the other side\n(Other side)\nI must have called a thousand times\n(Thousand times)\nTo tell you I'm sorry\nFor everything that I've done\nBut when I call\nYou never seem to be home\n\nHello from the outside\n(Outside)\nAt least I can say that I've tried\n(I've tried)\nTo tell you I'm sorry\nFor breaking your heart\nBut it don't matter\nIt clearly doesn't tear you apart\nAnymore\n\nOoh, anymore\nOoh, anymore\nOoh, anymore\nAnymore\n\nHello from the other side\n(Other side)\nI must have called a thousand times\n(Thousand times)\nTo tell you I'm sorry\nFor everything that I've done\nBut when I call\nYou never seem to be home\n\nHello from the outside\n(Outside)\nAt least I can say that I've tried\n(I've tried)\nTo tell you I'm sorry\nFor breaking your heart\nBut it don't matter\nIt clearly doesn't tear you apart\nAnymore";
		when(track.getAlbumCover()).thenReturn(lyric);
		final WaitForRequest request = new WaitForRequest(chatId, track);
		assertEquals(request, request.nextState(CommandHelper.LYRIC));
		InOrder order = inOrder(track);
		order.verify(track).getTrackInfo();
		order.verify(track).getLyric();
	}
	
	@Test
	public void globalTest()
	{
		final String albumArt = "https://i.scdn.co/image/e8201efef7598e80b043f31c78e23a756173b1b6";
		final String publicLink = "https://open.spotify.com/track/1HNE2PX70ztbEl6MLxrpN";
		final String previewLink = "https://p.scdn.co/mp3-preview/cf7b72458ad62c5fbcf31e75425e815d4b324c19";
		when(track.getAlbumCover()).thenReturn(albumArt);
		when(track.getTrackLink()).thenReturn(publicLink);
		when(track.getTrackPreview()).thenReturn(previewLink);
		when(track.getTrackInfo()).thenReturn(info);
		final WaitForRequest request = new WaitForRequest(chatId, track);
		request.nextState(CommandHelper.ALBUM);
		request.nextState(CommandHelper.PREVIEW);
		request.nextState(CommandHelper.LINK);
		request.nextState(CommandHelper.LYRIC);
		InOrder order = inOrder(track);
		order.verify(track).getTrackInfo();
		order.verify(track).getAlbumCover();
		order.verify(track).getTrackPreview();
		order.verify(track).getTrackLink();
		order.verify(track).getLyric();
	}
	

}