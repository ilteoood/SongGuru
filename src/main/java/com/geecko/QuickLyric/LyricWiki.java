/*
 * *
 *  * This file is part of QuickLyric
 *  * Created by geecko
 *  *
 *  * QuickLyric is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * QuickLyric is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  * You should have received a copy of the GNU General Public License
 *  * along with QuickLyric.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.geecko.QuickLyric;

import static com.geecko.QuickLyric.Lyrics.ERROR;
import static com.geecko.QuickLyric.Lyrics.NEGATIVE_RESULT;
import static com.geecko.QuickLyric.Lyrics.NO_RESULT;
import static com.geecko.QuickLyric.Lyrics.POSITIVE_RESULT;
import static com.geecko.QuickLyric.Net.getUrlAsString;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Whitelist;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class LyricWiki {

    private static final String baseUrl =
            "http://lyrics.wikia.com/api.php?action=lyrics&fmt=json&func=getSong&artist=%1s&song=%1s";
    private static final String baseAPIUrl =
            "http://lyrics.wikia.com/wikia.php?controller=LyricsApi&method=getSong&artist=%1s&song=%2s";

    public static Lyrics fromMetaData(String artist, String title) {
        if ((artist == null) || (title == null))
            return new Lyrics(ERROR);
        String originalArtist = artist;
        String originalTitle = title;
        String url = null;
        try {
            String encodedArtist = URLEncoder.encode(artist, "UTF-8");
            String encodedSong = URLEncoder.encode(title, "UTF-8");
            JsonObject json = new JsonParser().parse(getUrlAsString(new URL(
                    String.format(baseUrl, encodedArtist, encodedSong))).replace("song = ", "")).getAsJsonObject();
            url = URLDecoder.decode(json.get("url").getAsString(), "UTF-8");
            artist = json.get("artist").getAsString();
            title = json.get("song").getAsString();
            encodedArtist = URLEncoder.encode(artist, "UTF-8");
            encodedSong = URLEncoder.encode(title, "UTF-8");
            json = new JsonParser().parse(getUrlAsString
                    (new URL(String.format(baseAPIUrl, encodedArtist, encodedSong)))
            ).getAsJsonObject().get("result").getAsJsonObject();
            Lyrics lyrics = new Lyrics(POSITIVE_RESULT);
            lyrics.setText(json.get("lyrics").getAsString().replaceAll("\n", "<br />"));
            return lyrics;
        } catch (JsonParseException e) {
            return new Lyrics(NO_RESULT);
        } catch (IOException | IllegalStateException | NullPointerException e) {
            return url == null ? new Lyrics(ERROR) : fromURL(url, originalArtist, originalTitle);
        }
    }

    public static Lyrics fromURL(String url, String artist, String song) {
        if (url.endsWith("action=edit")) {
            return new Lyrics(NO_RESULT);
        }
        String text;
        try {
            //url = URLDecoder.decode(url, "utf-8");
            Document lyricsPage = Jsoup.connect(url).get();
            Element lyricbox = lyricsPage.select("div.lyricBox").get(0);
            lyricbox.getElementsByClass("references").remove();
            String lyricsHtml = lyricbox.html();
            final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
            text = Jsoup.clean(lyricsHtml, "", new Whitelist().addTags("br"), outputSettings);
            if (text.contains("&#"))
                text = Parser.unescapeEntities(text, true);
            text = text.replaceAll("\\[\\d\\]", "").trim();

            String title = lyricsPage.getElementsByTag("title").get(0).text();
            int colon = title.indexOf(':');
            if (artist == null)
                artist = title.substring(0, colon).trim();
            if (song == null) {
                int end = title.indexOf("Lyrics - LyricW");
                song = title.substring(colon+1, end).trim();
            }
        } catch (IndexOutOfBoundsException | IOException e) {
            return new Lyrics(ERROR);
        }

        try {
            artist = URLDecoder.decode(artist, "UTF-8");
            song = URLDecoder.decode(song, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (text.contains("Unfortunately, we are not licensed to display the full lyrics for this song at the moment.")
                || text.equals("Instrumental <br />")) {
            Lyrics result = new Lyrics(NEGATIVE_RESULT);
            return result;
        } else if (text.equals("") || text.length() < 3)
            return new Lyrics(NO_RESULT);
        else {
            Lyrics lyrics = new Lyrics(POSITIVE_RESULT);
            lyrics.setText(text);
            return lyrics;
        }
    }

}