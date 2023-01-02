package org.km.partyShaker.orders;

public class GuestManager {
    String name;
    String partyID;

    public GuestManager() {}

    public Guest createGuest() {
        return new Guest(this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartyID() {
        return partyID;
    }

    public void setPartyID(String partyID) {
        this.partyID = partyID;
    }
    public String toString() {
        return "GuestManager[guestName=" + this.name + " partyID= " + this.partyID + "]";
    }
}
