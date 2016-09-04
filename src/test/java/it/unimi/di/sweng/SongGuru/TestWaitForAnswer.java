package it.unimi.di.sweng.SongGuru;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import it.unimi.di.sweng.SongGuru.Spoty;
import it.unimi.di.sweng.SongGuru.WaitForAnswer;
import it.unimi.di.sweng.SongGuru.WaitForArtist;
import it.unimi.di.sweng.SongGuru.WaitForRequest;

@RunWith(MockitoJUnitRunner.class)
public class TestWaitForAnswer {
	
	public Long chatId;

	@Before
	public void setup() {
		chatId = new Long(65193455);
	}

	@Test
	public void testYes() {
		WaitForAnswer wfa = new WaitForAnswer(chatId,"Hello",Spoty.getArtists("Hello"));
		assertEquals(WaitForRequest.class, wfa.nextState("Yes").getClass());
	}
	
	@Test
	public void testNo() {
		WaitForAnswer wfa = new WaitForAnswer(chatId,"Hello",Spoty.getArtists("Hello"));
		assertEquals(WaitForArtist.class, wfa.nextState("No").getClass());
	}
}
