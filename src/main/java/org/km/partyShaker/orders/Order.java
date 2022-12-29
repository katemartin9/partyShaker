package org.km.partyShaker.orders;

import org.km.partyShaker.repository.Constants;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import org.km.partyShaker.stock.Cocktail;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class Order {
    int orderId;
    String guestName;
    String cocktail;
    int orderStatus;

    public Order() {}
    public Order(Guest guest, int orderId, Cocktail cocktail) {
        this.guestName = guest.name;
        this.orderId = orderId;
        this.cocktail = cocktail.getName();
    }
    public Order(String guest, int orderId, String cocktail) {
        this.guestName = guest;
        this.orderId = orderId;
        this.cocktail = cocktail;
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
