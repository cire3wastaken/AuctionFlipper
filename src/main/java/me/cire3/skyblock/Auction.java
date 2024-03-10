package me.cire3.skyblock;

public class Auction {
    public final String auctionId;
    public final String auctioneer;
    public final long price;
    public final String itemName;

    public Auction(String auctionId, String auctioneer, long price, String itemName) {
        this.auctionId = auctionId;
        this.auctioneer = auctioneer;
        this.price = price;
        this.itemName = itemName;
    }
}
