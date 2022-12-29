package org.km.partyShaker.repository;

import org.km.partyShaker.orders.Guest;


public interface GuestRepository {

    public void save(Guest guest);

    public Guest loadByName(String name);
}