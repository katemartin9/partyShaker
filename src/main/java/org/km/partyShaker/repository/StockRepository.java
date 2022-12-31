package org.km.partyShaker.repository;
import org.km.partyShaker.stock.Ingredient;
import java.util.List;

public interface StockRepository {
    public void saveMany(List<Ingredient> ingredients);
    public void save(Ingredient ingredient);
}
