package me.cire3;

import me.cire3.skyblock.Auction;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Hello world!
 *
 */
public class HypixelAuctionFlipper
{
    private static Queue<Auction> auctions = new ConcurrentLinkedQueue<Auction>();

    private static long lastUpdated;

    public static final String API = "https://api.hypixel.net/skyblock/auctions?page=";

    public static void main( String[] args )
    {
        try {
            while (true) {
                int pages;

                JSONObject json = fetchPage(0);

                if (json == null)
                    System.exit(-1);

                long lastUpdatedSnapshot = lastUpdated;

                if (lastUpdatedSnapshot == (lastUpdated = json.getLong("lastUpdated")))
                    continue;

                pages = json.getInt("totalPages");

                if (pages > 1) {
                    // create thread for page 0
                    new Thread(new FlipFinderWorker(json)).start();

                    for (int i = 0; i < pages - 1; i++) {
                        new Thread(new FlipFinderWorker(fetchPage(i))).start();
                    }
                }

                return;
            }
        } catch (Exception e) {

        }
    }

    public static Queue<Auction> getAuctions() {
        return auctions;
    }

    public static boolean addSnipe(Auction auction) {
        return auctions.add(auction);
    }

    public static JSONObject fetchPage(int page) {
        try {
            URL url = new URL(API + page);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            return new JSONObject(content.toString());
        } catch (Exception e) {
            return null;
        }
    }
}
