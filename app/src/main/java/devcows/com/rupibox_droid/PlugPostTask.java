package devcows.com.rupibox_droid;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by fox on 9/25/15.
 */
public class PlugPostTask extends AsyncTask<Plug, Void, String> {

    private final String TAG = "PlugPostTask";
    private Context context;

    public PlugPostTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Plug... plugs) {
        Plug plug = plugs[0];

        String url = String.format("http://10.10.10.104:3000/pins/%s.json", plug.getId());
        return POST(url, plug);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, "Plug changed!", Toast.LENGTH_SHORT).show();
        Log.v(TAG, "POST plug: " + result);
    }


    public static String POST(String url, Plug plug) {
        String result = "";
        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make PUT request to the given URL
            HttpPut httpPost = new HttpPut(url);

            // 3. build jsonObject

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("pin", plug.getJsonObject());

            // 4. convert JSONObject to JSON to String
            String json = jsonObject.toString();

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            InputStream inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            } else {
                result = "Did not work!";
            }
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        inputStream.close();
        return result;

    }
}
