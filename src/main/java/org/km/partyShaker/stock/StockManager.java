package org.km.partyShaker.stock;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.km.partyShaker.PartyShakerApplication;
import org.km.partyShaker.repository.Constants;
import org.km.partyShaker.repository.DynamoStockRepository;
import org.km.partyShaker.repository.FileCocktailRepository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.km.partyShaker.repository.Utilities.createDynamoDBClient;

public class StockManager {
    DynamoStockRepository repository;
    public StockManager() {
        DynamoDbEnhancedClient client = createDynamoDBClient();
        this.repository = new DynamoStockRepository(client);
    }
    public List<Ingredient> getStock() {
        return repository.getStock();
    }
    public boolean checkIfCanMakeCocktail(Cocktail cocktail) {
        List<Ingredient> stock = getStock();
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
    public void addStock(List<Ingredient> ingredients) {
        repository.saveMany(ingredients);
        }
    public void addStock(Ingredient ingredient) {
        repository.save(ingredient);
    }
    public boolean removeStock(Cocktail cocktail) {
        if (!this.checkIfCanMakeCocktail(cocktail))
            return false;
        cocktail.getIngredients().forEach(this::addStock);
        return true;
    }
    public List<Cocktail> listAvailableCocktails() {
        FileCocktailRepository cocktail_repo = new FileCocktailRepository(Constants.COCKTAILS_FILE);
        List<Cocktail> cocktails = cocktail_repo.load();
        List<Cocktail> availableCocktails = new ArrayList<>();
        for (Cocktail cocktail : cocktails) {
            if (checkIfCanMakeCocktail(cocktail)) {
                availableCocktails.add(cocktail);
            }
        }
        return availableCocktails;
    }
    public Optional<Cocktail> getCocktailByName(String name) {
        List<Cocktail> availableCocktails;
        try {
            availableCocktails = this.listAvailableCocktails();
        } catch (Exception e) {return Optional.empty();};
    return availableCocktails.stream().filter(cocktail -> cocktail.name.equals(name)).findFirst();
    }
}