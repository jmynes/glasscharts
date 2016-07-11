package gdp.glassdatapresentation.service;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import gdp.glassdatapresentation.entity.ChartEntry;
import gdp.glassdatapresentation.util.ParserUtil;

/**
 * Created by cadu on 27/06/2016.
 */
public class ChartFeederTask extends AsyncTask<Void, Void, Void> {

    private String address;
    private ArrayList<ChartEntry> entries;
    private StringBuilder stringBuilder;
    private HttpClient client;
    private HttpGet httpGet;
    public boolean finished;

    public ChartFeederTask(String address, ArrayList<ChartEntry> entries){
        this.address = address;
        this.entries = entries;
        this.stringBuilder = new StringBuilder();
        this.client = new DefaultHttpClient();
        this.httpGet = new HttpGet(this.address);
        this.finished = false;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try{
            JSONArray jsonArray = new JSONArray(this.getJSON());
            for(int i = 0; i < jsonArray.length(); i ++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject idObject = jsonObject.getJSONObject("_id");
                this.entries.add(
                        new ChartEntry(idObject.getString("room"),
                                jsonObject.getDouble("avgTemperature"),
                                jsonObject.getDouble("avgPressure"),
                                jsonObject.getDouble("avgHumidity"),
                                idObject.getInt("time"),
                                ParserUtil.parseDataType(idObject.getString("type"))
                        )
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        this.finished = true;
        return null;
    }

    private String getJSON() throws IOException {
        HttpResponse response = this.client.execute(this.httpGet);
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                this.stringBuilder.append(line);
            }
        }
        return this.stringBuilder.toString();
    }
}
