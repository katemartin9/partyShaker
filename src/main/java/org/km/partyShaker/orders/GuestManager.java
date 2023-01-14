package org.km.partyShaker.orders;

public class GuestManager {
    String name;
    String activationCode;

    public GuestManager() {}

    public Guest createGuest() {
        return new Guest(this.name + "#" + this.activationCode);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String toString() {
        return "GuestManager[guestName=" + this.name + " partyCode= " + this.activationCode + "]";
    }
}
