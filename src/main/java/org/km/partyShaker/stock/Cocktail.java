package org.km.partyShaker.stock;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class Cocktail {
    String name;
    String description;
    String recipe;
    List<Ingredient> ingredients;
    public Cocktail(String name, String description, List<Ingredient> ingredients) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }
    public Float getAlcoholContent () {
        float totalAlcohol = 0.0f;
        for (Ingredient ingredient : this.ingredients) {
            if (ingredient.isAlchoholic) {
                totalAlcohol += ingredient.quantity;
            }
        }
        return totalAlcohol;
    }
    public String getMostAlcoholicCocktail (@NotNull Cocktail cocktail)  {
        return (getAlcoholContent()>cocktail.getAlcoholContent()) ? this.name : cocktail.name;
    }
    public String toString() {
        return "Cocktail[name=" + this.name + "]";
    }
    public String getName() {
        return this.name;
    }
    public List<Ingredient> getIngredients() {return this.ingredients;}
    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
    public String getRecipe() {
        return recipe;
    }
}