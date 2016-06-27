package gdp.glassdatapresentation.service;

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
import gdp.glassdatapresentation.util.DateUtil;

/**
 * Created by cadu on 24/06/2016.
 */
public class ChartDataFeeder implements Runnable {

    private ArrayList<ChartEntry> dataArray;
    private StringBuilder stringBuilder;
    private HttpClient client;
    private HttpGet httpGet;


    public ChartDataFeeder(ArrayList<ChartEntry> dataArray, String hostAddress){
        this.dataArray = dataArray;
        this.stringBuilder = new StringBuilder();
        this.client = new DefaultHttpClient();
        this.httpGet = new HttpGet(hostAddress);
    }

    @Override
    public void run() {
        try{
            while(true){
                JSONArray jsonArray = new JSONArray(this.getJSON());
                this.dataArray.clear();
                for(int i = 0; i < jsonArray.length(); i ++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    dataArray.add(new ChartEntry(jsonObject.getString("device"),
                            jsonObject.getDouble("temperature"),
                            jsonObject.getDouble("pressure"),
                            jsonObject.getDouble("humidity"),
                            DateUtil.parseDateFromString(jsonObject.getString("datetime"))));
                }
                Thread.sleep(5000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
