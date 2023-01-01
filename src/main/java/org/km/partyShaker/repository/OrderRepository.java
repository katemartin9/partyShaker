package org.km.partyShaker.repository;
import org.km.partyShaker.orders.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository {
    public void save(Order order);

    public Order latestOrderByGuestName(String guestName);

    public List<Order> allOrdersByGuestName(String guestName);

    public List<Order> allPendingOrders();

}
