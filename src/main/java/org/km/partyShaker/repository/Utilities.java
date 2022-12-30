package org.km.partyShaker.repository;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.km.partyShaker.PartyShakerApplication;
import org.km.partyShaker.stock.Cocktail;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Utilities {
    public static List<Cocktail> loadManyFromJSON(String filename) {
        try {
            URL resource = PartyShakerApplication.class.getClassLoader().getResource(filename);
            File file = Paths.get(resource.toURI()).toFile();
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(file));
            return Arrays.asList(gson.fromJson(reader, Cocktail[].class));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
