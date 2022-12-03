package org.km.partyShaker.orders;

import org.km.partyShaker.stock.Ingredient;

import java.util.ArrayDeque;
import java.util.Queue;

public class OrderManager {
    Queue<Order> orderQueue;

    public OrderManager() {
        this.orderQueue = new ArrayDeque<>();
    }

    public void addToQueue(Order newOrder) {
        orderQueue.add(newOrder);
    }
    public void removeFromQueue(Order completedOrder) {
        orderQueue.remove(completedOrder);
    }
}
