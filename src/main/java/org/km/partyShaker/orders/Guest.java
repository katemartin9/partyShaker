package org.km.partyShaker.orders;

import org.km.partyShaker.repository.Constants;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Guest {
    String name;
    public Guest() {};
    public Guest(String name) {
        this.name = name;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("guestName")
    public String getName() {
        return name + "#" + Constants.PARTY_ID;
    }
    public void setName(String name)  {}

    public String toString() {
        return this.name;
    }
}
