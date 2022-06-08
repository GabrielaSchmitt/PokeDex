package Network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.*;

public class HTTPRequest {

    public JSONObject requestGetMethod(String stringUrl) {

        URL url;
        JSONObject jsonObject = null;

        try {
            url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            String response = IOUtils.toString(connection.getInputStream(), "UTF-8");

            try {
                jsonObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // System.out.println(response);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally { // got the result you wanted? yes OR no, the user will end up here

        }

        return jsonObject;
    }
}
