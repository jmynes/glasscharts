package gdp.glassdatapresentation.service;

import java.util.ArrayList;

import gdp.glassdatapresentation.entity.ChartEntry;

/**
 * Created by cadu on 08/07/2016.
 */
public class EntriesMonitor extends Thread implements Runnable {

    private ArrayList<ChartEntry> entries;

    public EntriesMonitor(ArrayList<ChartEntry> entries){
        this.entries = entries;
    }


    @Override
    public void run() {
        try {
            while(this.entries.isEmpty()) {
                Thread.sleep(1000);
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
