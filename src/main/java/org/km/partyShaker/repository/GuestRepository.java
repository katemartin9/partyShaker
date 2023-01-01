package org.km.partyShaker.repository;

import org.km.partyShaker.orders.Guest;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository {

    public void save(Guest guest);

    public boolean load(Guest guest);
}
