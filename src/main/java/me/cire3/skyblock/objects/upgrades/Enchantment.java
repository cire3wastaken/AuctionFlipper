package me.cire3.skyblock.objects.upgrades;

public class Enchantment implements Upgrade {
    public final String friendlyName;
    public final boolean bazaar;
    public final int level;
    private final String idInternal;

    public Enchantment(String friendlyName, boolean bazaar, int level, String idInternal) {
        this.friendlyName = friendlyName;
        this.bazaar = bazaar;
        this.level = level;
        this.idInternal = idInternal;
    }

    public String getIdInternal() {
        return idInternal;
    }
}
