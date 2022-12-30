package org.km.partyShaker.stock;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.Objects;

@DynamoDbBean
public class Ingredient {
    String name;
    float quantity;
    boolean isAlchoholic;
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
        return "Ingredient[name=" + this.name + " ,quantity= " + this.quantity + " ,alcoholic= " + this.isAlchoholic + "]";
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
    @DynamoDbPartitionKey
    @DynamoDbAttribute("name")
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
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
}

