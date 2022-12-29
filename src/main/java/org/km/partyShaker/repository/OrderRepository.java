package org.km.partyShaker.repository;
import org.km.partyShaker.orders.Order;

public interface OrderRepository {
    public void save(Order order);

    public void loadByGuestName(String guestName);

}
