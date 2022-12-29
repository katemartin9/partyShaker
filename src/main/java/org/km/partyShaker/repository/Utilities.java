package org.km.partyShaker.repository;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.km.partyShaker.PartyShakerApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Utilities {
    public static <T> T[] loadManyFromJSON(String filename, Class<T> tClass) throws FileNotFoundException, URISyntaxException {
        URL resource = PartyShakerApplication.class.getClassLoader().getResource(filename);
        File file = Paths.get(resource.toURI()).toFile();
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(file));
        return gson.fromJson(reader, tClass);
    }
}
