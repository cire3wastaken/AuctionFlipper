package me.cire3;

import org.json.JSONArray;
import org.json.JSONObject;

public class FlipFinderWorker implements Runnable {
    private final JSONObject page;

    public void run() {

    }

    public FlipFinderWorker(JSONObject page) {
        this.page = page;

        this.findFlips();
    }

    public void findFlips() {
        JSONArray auctions = page.getJSONArray("auctions");

    }
}
