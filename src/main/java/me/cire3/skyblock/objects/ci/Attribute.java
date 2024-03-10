package me.cire3.skyblock.objects.ci;

public class Attribute {
    public final String attributeName;
    public int level;
    private final String idInternal;

    public Attribute(String attributeName, int level, String idInternal) {
        this.attributeName = attributeName;
        this.level = level;
        this.idInternal = idInternal;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Attribute))
            return false;

        Attribute attr = (Attribute) obj;

        if (attr.attributeName == null || attr.idInternal == null || this.attributeName == null || this.idInternal == null)
            return false;

        if (attr.attributeName.equals(this.attributeName) && attr.idInternal.equals(this.idInternal))
            return true;
        return false;
    }

    public Attribute copy() {
        return new Attribute(this.attributeName, this.level, this.idInternal);
    }

    public String friendlyName() {
        return this.attributeName + " " + this.level;
    }
}
