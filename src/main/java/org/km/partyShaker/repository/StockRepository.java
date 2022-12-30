package org.km.partyShaker.repository;
import org.km.partyShaker.stock.Ingredient;
import java.util.List;

public interface StockRepository {
    public void save(List<Ingredient> ingredients);
}
