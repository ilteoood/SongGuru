package com.geecko.QuickLyric;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * This file is part of QuickLyric
 * Created by geecko
 * <p/>
 * QuickLyric is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * QuickLyric is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with QuickLyric.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Net {

    public static String USER_AGENT =
            "Mozilla/5.0 (Linux; U; Android 6.0.1; ko-kr; Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";

    public static String getUrlAsString(String paramURL) throws IOException {
        return getUrlAsString(new URL(paramURL));
    }

    public static String getUrlAsString(URL paramURL) throws IOException {
        Request request = new Request.Builder().header("User-Agent", USER_AGENT).url(paramURL).build();
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS).build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }
}