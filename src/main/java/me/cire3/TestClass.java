package me.cire3;

import net.querz.nbt.io.NBTDeserializer;
import net.querz.nbt.io.NamedTag;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * This class is used to find where certain informations about an item are stored.
 * Mainly for dumping the json data
 * */
public class TestClass {

    public static void main(String[] args) throws IOException {
        String apiKey = ApiKeyManager.getApiKey();
        String address = "https://api.hypixel.net/v2/skyblock/auction?key=" + apiKey + "&uuid=3e20c776ecd94729b5056443ebdee065";
        URL url = new URL(address);
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

        JSONObject json = new JSONObject(content.toString());

        JSONObject object = json.getJSONArray("auctions").getJSONObject(0).getJSONObject("item_bytes");
        String encoded = object.getString("data");

        byte[] buf = Base64.decodeBase64(encoded.getBytes(StandardCharsets.UTF_8));

        try(ByteArrayInputStream byt = new ByteArrayInputStream(buf)) {
            NamedTag tag = new NBTDeserializer(true).fromStream(byt);
            String tagString = tag.getTag().toString();

            JSONObject extraAttribs = ItemCreator.getExtraAttributesJson(new JSONObject(tagString));

            boolean enchantments = tagString.contains("enchantments");

            System.out.println(enchantments);
        }
    }
}
