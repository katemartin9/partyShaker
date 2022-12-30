package org.km.partyShaker.repository;
import org.km.partyShaker.stock.Cocktail;

import java.util.List;

import static org.km.partyShaker.repository.Utilities.loadManyFromJSON;

public class FileCocktailRepository implements CocktailRepository {

    public List<Cocktail> load(String fileName) {
        return loadManyFromJSON(fileName);
    }
    public void addNewCocktail(Cocktail cocktail) {

    }
    public void removeCocktailByName(String cocktailName) {

    }
}
