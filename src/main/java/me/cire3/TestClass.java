package me.cire3;

import me.cire3.skyblock.objects.ci.Attribute;
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
import java.util.Set;
//

public class TestClass {
    public static void main(String[] args) throws IOException {
        String apiKey = ApiKeyManager.getApiKey();
        String address = "https://api.hypixel.net/v2/skyblock/auction?key=" + apiKey + "&uuid=d17a1a08d4c5461f811497b797e0a4b9";
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
            boolean hasAttributes = tag.getTag().toString().contains("ExtraAttributes");

            if (hasAttributes) {
                JSONObject testObj = new JSONObject(tag.getTag().toString());
                JSONObject attributesObject = testObj.getJSONObject("value").getJSONObject("i").getJSONObject("value")
                        .getJSONArray("list").getJSONObject(0).getJSONObject("tag").getJSONObject("value")
                        .getJSONObject("ExtraAttributes").getJSONObject("value").getJSONObject("attributes")
                        .getJSONObject("value");
                Set<String> attributes = attributesObject.keySet();

                if (attributes.size() != 2) {
                    // wtf
                } else {
                    Attribute attr1 = null, attr2 = null;
                    for (String attrib : attributes) {
                        Attribute attr = new Attribute(attrib, attributesObject.getJSONObject(attrib).getInt("value"), attrib);
                        if (attr1 != null)
                            attr2 = attr;
                        else
                            attr1 = attr;
                    }

                    if (attr2 != null) {
                        System.out.println(attr1.friendlyName() + "   " + attr2.friendlyName());
                    }
                }
            }
        }

        // TODO, serialize this into an item
    }
}
