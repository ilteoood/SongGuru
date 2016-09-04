package it.unimi.di.sweng.SongGuru;

import java.util.HashMap;

import org.restlet.resource.Get;

public interface LyricResultInterface {

	@Get
	public HashMap<String, String> getResult();

}