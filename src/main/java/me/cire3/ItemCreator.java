package me.cire3;

import me.cire3.skyblock.objects.Item;
import me.cire3.skyblock.objects.ci.Attribute;
import me.cire3.skyblock.objects.upgrades.Enchantment;
import net.querz.nbt.io.NBTDeserializer;
import net.querz.nbt.io.NamedTag;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemCreator {
    public static Item of(JSONObject auction) {
        JSONObject bytes = auction.getJSONObject("item_bytes");
        String encoded = bytes.getString("data");

        byte[] buf = Base64.decodeBase64(encoded.getBytes(StandardCharsets.UTF_8));

        try(ByteArrayInputStream byt = new ByteArrayInputStream(buf)) {
            NamedTag tag = new NBTDeserializer(true).fromStream(byt);

            if (getExtraAttributesJson(new JSONObject(tag.getTag().toString())) != null) {
                Attribute[] attributes = getAttributes(tag);

                List<Enchantment> enchantments = getEnchantments(tag);
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    private static Attribute[] getAttributes(NamedTag tag) {
        String tagString = tag.getTag().toString();
        boolean hasAttributes = tagString.contains("\"attributes\":{");

        if (!hasAttributes)
            return null;

        JSONObject attribs = getExtraAttributesJson(new JSONObject(tagString)).getJSONObject("attributes")
                .getJSONObject("value");
        Set<String> attributes = attribs.keySet();

        if (attributes.size() != 2) {
            throw new RuntimeException("PLS UPDATE ME THANKS!");
        } else {
            Attribute attr1 = null, attr2 = null;
            for (String attrib : attributes) {
                Attribute attr = new Attribute(attrib, attribs.getJSONObject(attrib).getInt("value"), attrib);
                if (attr1 != null)
                    attr2 = attr;
                else
                    attr1 = attr;
            }

            return new Attribute[] { attr1, attr2 };
        }
    }

    private static List<Enchantment> getEnchantments(NamedTag tag) {
        String tagString = tag.getTag().toString();
        boolean hasEnchantments = tagString.contains("\"enchantments\":{");

        if (!hasEnchantments)
            return null;

        JSONObject enchantsObj = getExtraAttributesJson(new JSONObject(tagString)).getJSONObject("enchantments").getJSONObject("value");

        Set<String> enchantmentIds = enchantsObj.keySet();
        List<Enchantment> enchantments = new ArrayList<>();

        for (String enchantId : enchantmentIds) {
            boolean bazaar = false;
            int level = enchantsObj.getJSONObject(enchantId).getInt("value");
            if (enchantId.contains("ultimate"))
                bazaar = true;
            // TODO

            enchantments.add(new Enchantment(bazaar, level, enchantId));
        }
        return null;
    }

    public static JSONObject getExtraAttributesJson(JSONObject obj) {
         return obj.getJSONObject("value").getJSONObject("i").getJSONObject("value")
                .getJSONArray("list").getJSONObject(0).getJSONObject("tag").getJSONObject("value")
                .getJSONObject("ExtraAttributes").getJSONObject("value");
    }

//    private static getEnchantments
}
