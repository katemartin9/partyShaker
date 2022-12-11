package org.km.partyShaker.orders;

import org.km.partyShaker.stock.Ingredient;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class OrderManager {
    Deque<Order> orderQueue;

    public OrderManager() {
        this.orderQueue = new ArrayDeque<>();
    }

    public void addToQueue(Order newOrder) {orderQueue.addLast(newOrder);
    }
    public void removeFromQueue(Order completedOrder) {
        orderQueue.remove(completedOrder);
    }

    public boolean checkIfAlreadyInQueue(Guest guest) {
        return true;
    }
}
