package org.km.partyShaker.repository;
import org.km.partyShaker.stock.Cocktail;

import java.util.List;

public interface CocktailRepository {
    public List<Cocktail> load(String fileName);

    public void addNewCocktail(Cocktail cocktail);

    public void removeCocktailByName(String cocktailName);
}
