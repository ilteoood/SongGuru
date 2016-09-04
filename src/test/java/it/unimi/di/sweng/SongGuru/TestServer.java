package it.unimi.di.sweng.SongGuru;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

public class TestServer {
	
	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
	
	@Test
	public void testConfigs()
	{
		assertEquals(1, Configs.values().length);
		assertEquals(Configs.INSTANCE, Configs.values()[0]);
		assertEquals(Configs.INSTANCE,Configs.valueOf("INSTANCE"));
	}
	
	@Test
	public void testServer()
	{
		Server server = new Server();
		try {
			server.start();
		} catch (Exception e) {
			fail();
		}
		try {
			server.stop();
		} catch (Exception e) {
			fail();
		}
	}
	
}
