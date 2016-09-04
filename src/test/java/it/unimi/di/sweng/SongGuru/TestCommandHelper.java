package it.unimi.di.sweng.SongGuru;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCommandHelper {
	
	
	@Test
	public void testSanitize()
	{
		String toSanitize = CommandHelper.SEARCH_SONG + CommandHelper.BOT_NAME;
		assertEquals(CommandHelper.SEARCH_SONG, CommandHelper.sanitizeMessage(toSanitize));
	}
	
	@Test
	public void testUnsanitize()
	{
		String toSanitize = CommandHelper.SEARCH_SONG + "@SongGara";
		assertEquals(toSanitize, CommandHelper.sanitizeMessage(toSanitize));
	}
	
	@Test
	public void testUnnecessarySanitize()
	{
		String toSanitize = CommandHelper.SEARCH_SONG;
		assertEquals(toSanitize, CommandHelper.sanitizeMessage(toSanitize));
		toSanitize = "Sum 41";
		assertEquals(toSanitize, CommandHelper.sanitizeMessage(toSanitize));
		
	}

}
