package org.km.partyShaker.orders;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
import org.km.partyShaker.stock.Cocktail;

import java.text.SimpleDateFormat;

@DynamoDbBean
public class Order {
    int orderId;
    String guestName;
    String cocktail;
    int orderStatus;
    String timestamp;

    public Order() {}
    public Order(String guest, int orderId, Cocktail cocktail) {
        this.guestName = guest;
        this.orderId = orderId;
        this.cocktail = cocktail.getName();
        this.timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    }
    public Order(String guest, int orderId, String cocktail) {
        this.guestName = guest;
        this.orderId = orderId;
        this.cocktail = cocktail;
        this.timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    }
    @DynamoDbPartitionKey
    @DynamoDbAttribute("guestName")
    public String getGuestName() {
        return this.guestName;
    }
    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
    @DynamoDbSortKey
    @DynamoDbAttribute("orderId")
    public int getOrderId() {
        return this.orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    @DynamoDbSecondaryPartitionKey(indexNames = {"statusParty-timestamp-index"})
    @DynamoDbAttribute("statusParty")
    public String getStatusParty() {return this.orderStatus + "#" + this.guestName.split("#")[1];}
    public void setStatusParty(String val){}
    @DynamoDbSecondarySortKey(indexNames = {"statusParty-timestamp-index"})
    @DynamoDbAttribute("timestamp")
    public String getTimestamp(){return this.timestamp;}
    public void setTimestamp(String val){
        this.timestamp = val;
    }
    public String getCocktail() {
        return this.cocktail;
    }
    public void setCocktail(String cocktail) {
        this.cocktail = cocktail;
    }
    public int getStatus() {
        return orderStatus;
    }
    public void setStatus(int status)  {
        this.orderStatus = status;
    }
    public String toString() {
        return "Order[name=" + this.guestName + " orderId=" + this.orderId + " cocktailName= " + this.cocktail + "]";
    }
}
