package org.km.partyShaker.orders;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import org.km.partyShaker.stock.Cocktail;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class Order {
    String orderId;
    String guestName;
    String cocktail;
    int orderStatus;

    public Order() {};
    public Order(Guest guest, String orderId, Cocktail cocktail) {
        this.guestName = guest.name;
        this.orderId = orderId;
        this.cocktail = cocktail.getName();
    }
    public Order(String guest, String orderId, String cocktail) {
        this.guestName = guest;
        this.orderId = orderId;
        this.cocktail = cocktail;
    }
    @DynamoDbPartitionKey
    @DynamoDbAttribute("guestName")
    public String getGuestName() {
        return this.guestName;
    }
    public void setGuestName(String guestName) {}
    @DynamoDbSortKey
    @DynamoDbAttribute("orderId")
    public String getOrderId() {
        return this.orderId;
    }
    public void setOrderId(String orderId) {}
    public Integer getStatus() {
        return orderStatus;
    }
    public void setStatus(Integer status)  {
        this.orderStatus = status;
    }
}
