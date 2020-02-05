/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import org.json.*;


/**
 *
 * @author alvin
 */
// TODO need 3rd APIs to do mapping here!
public class AirportZoneMap {
    /**
     * Get ZoneId by specifying airport
     * @param airport
     * @return 
     */
    public static ZoneId GetTimeZoneByAiport(Airport airport) {
        double lat = airport.latitude();
        double lon = airport.longitude();

        String htmlstr = "https://maps.googleapis.com/maps/api/timezone/"
                + "json?location="
                + lat + "," + lon + "&timestamp=1458000000&key="
                + "AIzaSyAG80PBlcPx5O2xnSyQuxIO4mey1Rcgrcw";

        try {
//            System.out.println(GetTimeZone.getHTML(htmlstr));
            JSONObject timezoneobj = new JSONObject(getHTML(htmlstr));

            String outID = timezoneobj.getString("timeZoneId");
            ZoneId ret = ZoneId.of(outID);
            return ret;
        } catch (InterruptedException | MalformedURLException | JSONException e) {
            return ZoneId.systemDefault();
        }

    }

    private static String getHTML(String urlToRead) throws MalformedURLException, InterruptedException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        for (int i = 0; i < 10; i++) {
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");

                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();
                break;
            } catch (IOException e) {
                TimeUnit.SECONDS.sleep(1);

            }
        }

        return result.toString();
    }

}
