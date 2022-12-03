package org.km.partyShaker.stock;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.km.partyShaker.PartyShakerApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StockManager {
    List<Ingredient> stock;
    public StockManager(List<Ingredient> stock) {
        this.stock = stock;
    }
    public StockManager(String fileName) throws FileNotFoundException, URISyntaxException {
        URL resource = PartyShakerApplication.class.getClassLoader().getResource(fileName);
        File file = Paths.get(resource.toURI()).toFile();
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(file));
        Ingredient[] stockIngredients = gson.fromJson(reader, Ingredient[].class);
        this.stock = Arrays.asList(stockIngredients);
    }
    public boolean checkIfCanMakeCocktail(Cocktail cocktail) {
        List<Ingredient> cocktailIngredients = cocktail.getIngredients();
        for (Ingredient cocktailIngredient: cocktailIngredients) {
            if (!stock.contains(cocktailIngredient))
                return false;
            int stockIndex = stock.indexOf(cocktailIngredient);
            Ingredient stockIngredient = stock.get(stockIndex);
            if (stockIngredient.getQuantity() < cocktailIngredient.getQuantity())
                return false;
        }
        return true;
    }
    public void addStock(Ingredient ingredient) {
        int stockIndex = stock.indexOf(ingredient);
        if (stockIndex == -1) {
            stock.add(ingredient);
            return;
        }
        Ingredient stockIngredient = stock.get(stockIndex);
        stockIngredient.quantity += ingredient.quantity;
    }
    public boolean removeStock(Cocktail cocktail) {
        if (!this.checkIfCanMakeCocktail(cocktail))
            return false;
        cocktail.getIngredients().forEach(this::removeStock);
        return true;
    }
    public boolean removeStock(Ingredient ingredient) {
        int stockIndex = stock.indexOf(ingredient);
        Ingredient stockIngredient = stock.get(stockIndex);
        if (stockIngredient.quantity >= ingredient.quantity) {
            stockIngredient.quantity -= ingredient.quantity;
            return true;
        }
        return false;
    }
    public List<Ingredient> getStock() {
        return stock;
    }
    public List<Cocktail> listAvailableCocktails() throws FileNotFoundException, URISyntaxException {
        /* load db of cocktails, maybe later search should be done based on alcohol*/
        URL resource = PartyShakerApplication.class.getClassLoader().getResource("cocktails.json");
        File file = Paths.get(resource.toURI()).toFile();
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(file));
        Cocktail[] cocktails = gson.fromJson(reader, Cocktail[].class);
        List<Cocktail> availableCocktails = new ArrayList<>();
        for (Cocktail cocktail : cocktails) {
            if (checkIfCanMakeCocktail(cocktail)) {
                availableCocktails.add(cocktail);
            }
        }
        return availableCocktails;
    }
}