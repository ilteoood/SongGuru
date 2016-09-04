package it.unimi.di.sweng.SongGuru;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import it.unimi.di.sweng.SongGuru.BotState;
import it.unimi.di.sweng.SongGuru.WaitForArtist;
import it.unimi.di.sweng.SongGuru.WaitForRequest;

public class TestWaitForArtist {

	public Long chatId;
	
	@Before
	public void setup()
	{
		chatId = new Long(65193455);
	}
	
	@Test
	public void testNull() {
		final WaitForArtist wfa = new WaitForArtist(chatId, "ciao*");
		assertEquals(null, wfa.nextState("[]||"));
	}
	
	@Test
	public void testValidArtist() {
		BotState wfa = new WaitForArtist(chatId, "Hello");
		wfa = wfa.nextState("Adele");
		assertEquals(WaitForRequest.class, wfa.getClass());
	}
	
}