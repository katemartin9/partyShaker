package org.km.partyShaker.repository;
import org.km.partyShaker.orders.Order;

import java.util.List;

public interface OrderRepository {
    public void save(Order order);

    public Order latestOrderByGuestName(String guestName);

    public List<Order> allOrdersByGuestName(String guestName);

    public List<Order> allPendingOrders();

}
