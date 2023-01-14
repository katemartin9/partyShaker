package org.km.partyShaker.stock;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.util.Objects;

@DynamoDbBean
public class Ingredient {
    String name;
    float quantity;
    boolean isAlchoholic;
    public static final int MEASURE = 25;
    String partyCode;
    public Ingredient() {}
    public Ingredient(String name, float quantity, boolean isAlchoholic) {
        this.name = name;
        this.quantity = quantity;
        this.isAlchoholic = isAlchoholic;
    }

    public Ingredient(String name, float quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String toString() {
        return "Ingredient[name=" + this.name + " ,quantity= " + this.quantity + " ,alcoholic= " + this.isAlchoholic + ", partyCode" + this.partyCode +"]";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ingredient ingredient = (Ingredient) obj;

        return Objects.equals(this.name, ingredient.name);
    }

    public float getQuantity() {
        return quantity;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("name")
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @DynamoDbPartitionKey
    @DynamoDbAttribute("nameParty")
    public String getNameParty() {
        return this.name + "#" + this.partyCode;
    }
    public void setNameParty(String val) {
    }

    @DynamoDbSecondaryPartitionKey(indexNames = {"party-name-index"})
    @DynamoDbAttribute("party")
    public String getParty() {
        return this.partyCode;
    }
    public void setParty(String partyCode){
        this.partyCode = partyCode;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public boolean isAlchoholic() {
        return isAlchoholic;
    }

    public void setAlchoholic(boolean alchoholic) {
        isAlchoholic = alchoholic;
    }

    public void withPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }
}

