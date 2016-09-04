package it.unimi.di.sweng.SongGuru;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.unimi.di.sweng.SongGuru.BotPersistence;
import it.unimi.di.sweng.SongGuru.BotState;
import it.unimi.di.sweng.SongGuru.WaitForAnswer;
import it.unimi.di.sweng.SongGuru.WaitForRequest;
import it.unimi.di.sweng.SongGuru.WaitForSong;

public class TestPersistence {
	
	public BotState returnState;
	
	@Test
	public void testA()
	{
		returnState = BotPersistence.INSTANCE.route("42", CommandHelper.SEARCH_SONG);
		assertEquals(WaitForSong.class, returnState.getClass());
	}
	
	@Test
	public void testB()
	{
		returnState = BotPersistence.INSTANCE.route("42", "Hello");
		assertEquals(WaitForAnswer.class, returnState.getClass());
	}
	
	@Test
	public void testC()
	{
		returnState = BotPersistence.INSTANCE.route("42", "Yes");
		assertEquals(WaitForRequest.class, returnState.getClass());
	}
	
	@Test
	public void testD()
	{
		returnState = BotPersistence.INSTANCE.route("42", CommandHelper.ALBUM);
		assertEquals(WaitForRequest.class, returnState.getClass());
	}
	
	@Test
	public void testE()
	{
		returnState = BotPersistence.INSTANCE.route("43", CommandHelper.SEARCH_SONG);
		assertEquals(null, returnState.nextState(""));
	}
	
	@Test
	public void testF()
	{
		returnState = BotPersistence.INSTANCE.route("44", "ciao");
		assertEquals(null, returnState);
	}
	
	@Test
	public void testG()
	{
		returnState = BotPersistence.INSTANCE.route("44", "/start");
		assertEquals(null, returnState);
	}
	
	@Test
	public void testH()
	{
		returnState = BotPersistence.INSTANCE.route("44", "/help");
		assertEquals(null, returnState);
	}
	
	@Test
	public void testI()
	{
		returnState = BotPersistence.INSTANCE.route("45", "/ciccio");
		assertEquals(null, returnState);
	}
	
	@Test
	public void testL()
	{
		returnState = BotPersistence.INSTANCE.route("45", "/mooseca");
		assertEquals(WaitForRequest.class, returnState.getClass());
	}
}
