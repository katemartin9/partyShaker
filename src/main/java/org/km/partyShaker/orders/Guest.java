package org.km.partyShaker.orders;;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

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
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String toString() {
        return this.name;
    }
    @DynamoDbSecondaryPartitionKey(indexNames = {"party-guestName-index"})
    @DynamoDbAttribute("party")
    public String getParty() {return this.name.split("#")[1];}

    public void setParty(String partyCode){}

}
