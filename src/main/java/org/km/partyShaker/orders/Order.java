package org.km.partyShaker.orders;

import org.km.partyShaker.stock.Cocktail;

public class Order {
    int orderId;
    Guest guest;
    Cocktail cocktail;

    public Order(Guest guest, int orderId, Cocktail cocktail) {
        this.guest = guest;
        this.orderId = orderId;
        this.cocktail = cocktail;
    }
}
