package org.km.partyShaker.stock;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StockManagerTest {
    @Test
    public void testCheckIfCanMakeCocktail() {
        Ingredient ingredient = new Ingredient("vodka", 50);
        Cocktail cocktail = new Cocktail("cocktail1", "", Arrays.asList(new Ingredient("vodka", 49)));
        List<Ingredient> ingredientArrayList = new ArrayList<>();
        ingredientArrayList.add(ingredient);
        StockManager stockManager = new StockManager(ingredientArrayList);
        boolean result = stockManager.checkIfCanMakeCocktail(cocktail);
        assertTrue(result);
    }
    @Test
    public void testAddStock() {
        Ingredient ingredient = new Ingredient("vodka", 50);
        StockManager stockManager = new StockManager(new ArrayList<>());
        stockManager.addStock(ingredient);
        assertTrue(stockManager.getStock().contains(ingredient));
    }
    @Test
    public void testRemoveStock() {
        Ingredient ingredient = new Ingredient("vodka", 100);
        Cocktail cocktail = new Cocktail("cocktail1", "", Arrays.asList(new Ingredient("vodka", 50)));
        List<Ingredient> ingredientArrayList = new ArrayList<>();
        ingredientArrayList.add(ingredient);
        StockManager stockManager = new StockManager(ingredientArrayList);
        assertTrue(stockManager.removeStock(cocktail));
    }
}
