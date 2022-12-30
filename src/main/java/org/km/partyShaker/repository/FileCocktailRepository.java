package org.km.partyShaker.repository;
import org.km.partyShaker.stock.Cocktail;
import org.km.partyShaker.stock.Ingredient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.km.partyShaker.repository.Utilities.loadManyFromJSON;

public class FileCocktailRepository implements CocktailRepository {
    private final List<Cocktail> cocktails;
    private Set<Ingredient> allIngredients;
    public FileCocktailRepository(String fileName) {
        this.cocktails = loadManyFromJSON(fileName);
        this.allIngredients = null;
    }
    public List<Cocktail> load() {
        return this.cocktails;
    }
    public void addNewCocktail(Cocktail cocktail) {

    }
    public void removeCocktailByName(String cocktailName) {

    }
    public List<Ingredient> getAllIngredients() {
        if (allIngredients != null) {
            return new ArrayList<>(allIngredients);
        }
        this.allIngredients = new HashSet<>();
        this.cocktails.forEach(cocktail -> allIngredients.addAll(cocktail.getIngredients()));
        return new ArrayList<>(allIngredients);
    }
    public List<Ingredient> getAllIngredientsByCocktailNames(Set<String> cocktailNames) {
        Set<Ingredient> subsetIngredients = new HashSet<>();
        this.cocktails.forEach(cocktail -> {
            if (cocktailNames.contains(cocktail.getName())) {
                subsetIngredients.addAll(cocktail.getIngredients());
            }
        });
        return new ArrayList<>(subsetIngredients);
    }
}
