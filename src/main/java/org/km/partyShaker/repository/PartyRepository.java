package org.km.partyShaker.repository;

import org.km.partyShaker.party.Party;
import org.springframework.stereotype.Repository;



@Repository
public interface PartyRepository {
    public void save(Party party);

    public Party load(String partyName);
}
