package dbexam;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.*;

public class HousingPricesLoader {

    public final String HOUSING_PRICES_URL = "http://csundergrad.science.uoit.ca/csci2020u/data/housing_prices.json";

//    public Map<String,List<Integer>> loadPrices() {
    public List<Year> loadPrices() {

        Map<String,List<Integer>> data = new TreeMap<>();
        List<Year> yrs = new ArrayList<>();

        try {
            URL url = new URL(HOUSING_PRICES_URL);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(false);
            conn.setDoInput(true);
            InputStream inStream = conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inStream));

            String line = null;
            String jsonData = "";
            while ((line = in.readLine()) != null) {
                jsonData += line + "\n";
            }

            List<Integer> flats1BedroomList = new ArrayList<>();
            List<Integer> flats2BedroomList = new ArrayList<>();
            List<Integer> houses2BedroomList = new ArrayList<>();
            List<Integer> houses3BedroomList = new ArrayList<>();
            List<Integer> houses4BedroomList = new ArrayList<>();


            JSONArray years = new JSONArray(jsonData);
            for (int i = 0; i < years.length(); i++) {
                int year = years.getJSONObject(i).getInt("Year");
                int flats1Bedroom = years.getJSONObject(i).getInt("1 bed flats");
                int flats2Bedroom = years.getJSONObject(i).getInt("2 bed flats");
                int houses2Bedroom = years.getJSONObject(i).getInt("2 bed houses");
                int houses3Bedroom = years.getJSONObject(i).getInt("3 bed houses");
                int houses4Bedroom = years.getJSONObject(i).getInt("4 bed houses");

                Year y = new Year(year, flats1Bedroom, flats2Bedroom, houses2Bedroom, houses3Bedroom, houses4Bedroom);
                yrs.add(y);


                flats1BedroomList.add(flats1Bedroom);
                flats2BedroomList.add(flats2Bedroom);
                houses2BedroomList.add(houses2Bedroom);
                houses3BedroomList.add(houses3Bedroom);
                houses4BedroomList.add(houses4Bedroom);
            }

            data.put("1 bed flats", flats1BedroomList);
            data.put("2 bed flats", flats2BedroomList);
            data.put("2 bed houses", houses2BedroomList);
            data.put("3 bed houses", houses3BedroomList);
            data.put("4 bed houses", houses4BedroomList);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        return data;
        return yrs;
    }

}
