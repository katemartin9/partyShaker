package org.km.partyShaker.repository;
import org.km.partyShaker.stock.Cocktail;
import org.km.partyShaker.stock.Ingredient;

import java.util.List;
import java.util.Set;

public interface CocktailRepository {
    public List<Cocktail> load();

    public void addNewCocktail(Cocktail cocktail);

    public void removeCocktailByName(String cocktailName);

    public List<Ingredient> getAllIngredients();

    public List<Ingredient> getAllIngredientsByCocktailNames(Set<String> cocktailNames);
}
