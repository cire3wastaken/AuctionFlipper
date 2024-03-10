package me.cire3.skyblock.objects;

import me.cire3.skyblock.objects.upgrades.Upgrade;

import java.util.List;

public class Item {
    public final String name;
    public final List<Upgrade> upgrades;

    private final String idInternal;

    public Item(String name, List<Upgrade> upgrades, String idInternal) {
        this.name = name;
        this.upgrades = upgrades;
        this.idInternal = idInternal;
    }


    public String getIdInternal() {
        return idInternal;
    }
}
