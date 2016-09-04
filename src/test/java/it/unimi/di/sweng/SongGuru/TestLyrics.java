package it.unimi.di.sweng.SongGuru;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestLyrics {
	
	@Test
	public void getLyric()
	{
		assertTrue(LyricsFetcher.getLyric("Adele", "Hello").contains("I was wondering if after all these years"));
		assertTrue(LyricsFetcher.getLyric("Sum 41", "In too deep").contains("We're running in circles again"));
		assertTrue(LyricsFetcher.getLyric("Matthew West", "hello, my name is").contains("Just when you think you can win"));
		assertTrue(LyricsFetcher.getLyric("j-ax", "immorale").contains("con raggi gamma divento Hulk"));
		assertTrue(LyricsFetcher.getLyric("CapaRezza", "L'Età Dei Figuranti").contains("Lo mette in culo a tutti quanti"));
	}
	
	@Test
	public void getEmptyLyric()
	{
		assertEquals("Not found.", LyricsFetcher.getLyric("Adele", "Hallo"));
	}
	
	@Test
	public void wrongQuery()
	{
		assertEquals("Not found.", LyricsFetcher.getLyric("Adele", "/find/Hello"));
		assertEquals("Not found.", LyricsFetcher.getLyric("/find/Adele", "Hello"));
	}

}