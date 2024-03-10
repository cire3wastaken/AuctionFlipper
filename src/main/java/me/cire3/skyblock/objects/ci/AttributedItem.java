package me.cire3.skyblock.objects.ci;

import me.cire3.skyblock.objects.Item;
import me.cire3.skyblock.objects.upgrades.Upgrade;

import java.util.List;

public class AttributedItem extends Item {
    public final Attribute attribute1;
    public final Attribute attribute2;

    // MIGHT migrate Attributes to Upgrade interface
    public AttributedItem(String name, List<Upgrade> upgrades, Attribute attribute1, Attribute attribute2, String idInternal) {
        super(name, upgrades, idInternal);
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
    }

    public boolean containsAttribute(Attribute attribute) {
        return attribute.equals(attribute1) || attribute.equals(attribute2);
    }
}
