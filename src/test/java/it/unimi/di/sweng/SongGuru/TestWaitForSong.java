package it.unimi.di.sweng.SongGuru;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import it.unimi.di.sweng.SongGuru.WaitForAnswer;
import it.unimi.di.sweng.SongGuru.WaitForSong;

@RunWith(MockitoJUnitRunner.class)
public class TestWaitForSong {

	public String chatId;

	@Before
	public void setup() {
		chatId = "65193455";
	}

	@Test
	public void testNull() {
		WaitForSong wfs = new WaitForSong(chatId);
		assertEquals(null, wfs.nextState(""));
	}
	
	@Test
	public void testValidSong() {
		WaitForSong wfs = new WaitForSong(chatId);
		assertEquals(WaitForAnswer.class, wfs.nextState("Hello").getClass());
	}

}