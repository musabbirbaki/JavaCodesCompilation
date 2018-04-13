package dbexam;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.*;

public class HousingPricesLoader {

    public static String URL = "http://csundergrad.science.uoit.ca/csci2020u/data/housing_prices.json";

    public static String getJSONDataUsingURL(String url) throws IOException {
        Map<String,List<Integer>> data = new TreeMap<>();
        
        URL netURL = new URL(url);
        URLConnection conn = netURL.openConnection();
        conn.setDoOutput(false);
        conn.setDoInput(true);

        InputStream inStream = conn.getInputStream();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(inStream)
        );

        System.out.println(url);

        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        String jsonData = buffer.toString();

        JSONArray items = new JSONArray(jsonData);
        for (int i = 0; i < items.length(); i++) {
            int year = items.getJSONObject(i).getInt("Year");

            System.out.println(year);
        }

        return jsonData;
    }

}
