package org.km.partyShaker.repository;
import org.km.partyShaker.stock.Cocktail;
import org.km.partyShaker.stock.Ingredient;

import java.util.*;

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
        Map<String, Ingredient> ingredientQuantities = new HashMap<>();
        List<Ingredient> subsetIngredients = new ArrayList<>();
        this.cocktails.forEach(cocktail -> {
            if (cocktailNames.contains(cocktail.getName())) {
                subsetIngredients.addAll(cocktail.getIngredients());
            }
        });
        for (int i = 1; i < subsetIngredients.size(); i++) {
            Ingredient currentIngredient = subsetIngredients.get(i);
            Ingredient existingIngredient = ingredientQuantities.get(currentIngredient.getName());
            if (existingIngredient == null) {
                ingredientQuantities.put(currentIngredient.getName(), currentIngredient);
            } else {
                float updatedQuantity = existingIngredient.getQuantity() + currentIngredient.getQuantity();
                existingIngredient.setQuantity(updatedQuantity);
                ingredientQuantities.put(existingIngredient.getName(), existingIngredient);
            }
        }
        return new ArrayList<Ingredient>(ingredientQuantities.values());
    }
}
