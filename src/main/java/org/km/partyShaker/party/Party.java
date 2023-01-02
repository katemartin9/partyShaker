package org.km.partyShaker.party;
import org.km.partyShaker.stock.Ingredient;
import java.util.List;

public class Party {
    int partySize;
    String hostName;
    String partyName;
    String partyStart;
    String partyEnd;
    List<String> cocktailOptions;
    List<Ingredient> ingredients;

    public Party() {}

    public int getPartySize() {
        return partySize;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyStart() {
        return partyStart;
    }

    public void setPartyStart(String partyStart) {
        this.partyStart = partyStart;
    }

    public String getPartyEnd() {
        return partyEnd;
    }

    public void setPartyEnd(String partyEnd) {
        this.partyEnd = partyEnd;
    }

    public List<String> getCocktailOptions() {
        return cocktailOptions;
    }

    public void setCocktailOptions(List<String> cocktailOptions) {
        this.cocktailOptions = cocktailOptions;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String toString() {
        return "Party[size=" + this.partySize + " host= " + this.hostName + " partyName=" + this.partyName
                + " start=" + this.partyStart + " end=" + this.partyEnd + "]";
    }
    public static class PartyBuilder {
        int partySize;
        String hostName;
        String partyName;
        String partyStart;
        String partyEnd;
        List<String> cocktailOptions;
        List<Ingredient> ingredients;

        public PartyBuilder() {
            this.cocktailOptions = null;
            this.ingredients = null;
        }
        public PartyBuilder withPartySize(int partySize) {
            this.partySize = partySize;
            return this;
        }
        public PartyBuilder withHostName(String hostName) {
            this.hostName = hostName;
            return this;
        }
        public PartyBuilder withPartyName(String partyName) {
            this.partyName = partyName;
            return this;
        }
        public PartyBuilder withPartyStart(String partyStart) {
            this.partyStart = partyStart;
            return this;
        }
        public PartyBuilder withPartyEnd(String partyEnd) {
            this.partyEnd = partyEnd;
            return this;
        }
        public PartyBuilder withIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }
        public PartyBuilder withCocktailOptions(List<String> cocktails) {
            this.cocktailOptions = cocktails;
            return this;
        }

        public Party build() {
            Party newParty = new Party();
            newParty.partySize = this.partySize;
            newParty.hostName = this.hostName;
            newParty.partyName = this.partyName;
            newParty.partyStart = this.partyStart;
            newParty.partyEnd = this.partyEnd;
            newParty.ingredients = this.ingredients;
            newParty.cocktailOptions = this.cocktailOptions;
            return newParty;
        }

    }
}
