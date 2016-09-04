package it.unimi.di.sweng.SongGuru;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import com.geecko.QuickLyric.*;

public class LyricsFetcher {
	
	private final static Class[] PARAM_STRING = new Class[]{String.class, String.class};

	public static String getLyric(final String artistName, final String songName) {
		Lyrics lyrics;
		
		ArrayList<Class> lyricsProviders = new ArrayList<>();
		lyricsProviders.add(AZLyrics.class);
		lyricsProviders.add(Genius.class);
		lyricsProviders.add(JLyric.class);
		lyricsProviders.add(Lololyrics.class);
		lyricsProviders.add(LyricsMania.class);
        lyricsProviders.add(LyricWiki.class);
        lyricsProviders.add(MetalArchives.class);
        lyricsProviders.add(PLyrics.class);
        lyricsProviders.add(UrbanLyrics.class);
        
        for(Class provider:lyricsProviders)
        {
        	try 
        	{
        		lyrics = (Lyrics) provider.getDeclaredMethod("fromMetaData", PARAM_STRING).invoke(null,  new Object[]{artistName, songName});
        		if(lyrics.getFlag() == Lyrics.POSITIVE_RESULT)
        			return Jsoup.clean(lyrics.getText(), "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false)).replaceAll("[ ]{2,}", "");
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {System.out.println("Unable to do reflection");}
        }
		return "Not found.";
	}

}