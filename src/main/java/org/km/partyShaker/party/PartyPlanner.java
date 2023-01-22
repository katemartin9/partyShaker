package org.km.partyShaker.party;

import org.km.partyShaker.orders.Guest;
import org.km.partyShaker.repository.Constants;
import org.km.partyShaker.repository.FileCocktailRepository;
import org.km.partyShaker.repository.Utilities;
import org.km.partyShaker.stock.Ingredient;

import java.util.HashSet;
import java.util.List;

public class PartyPlanner {

    int partySize;
    String hostName;
    String partyName;
    String partyStart;
    String partyEnd;
    List<String> cocktailOptions;
    List<Ingredient> ingredients;

    public PartyPlanner() {}
    public Party createParty() {
        return new Party.PartyBuilder().withPartySize(this.partySize)
                .withHostName(this.hostName)
                .withPartyName(this.partyName)
                .withPartyStart(this.partyStart)
                .withPartyEnd(this.partyEnd)
                .withCocktailOptions(this.cocktailOptions)
                .withIngredients(generateIngredients())
                .withPartyCode(Utilities.generateRandomString(5)).build();
    }
    public List<Ingredient> generateIngredients() {
        HashSet<String > cocktailSet = new HashSet<>(this.cocktailOptions);
        return new FileCocktailRepository(Constants.COCKTAILS_FILE).getAllIngredientsByCocktailNames(cocktailSet);
    }
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
}
