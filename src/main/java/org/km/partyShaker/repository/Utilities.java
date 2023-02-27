package org.km.partyShaker.repository;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.km.partyShaker.stock.Cocktail;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Utilities {

    public static List<Cocktail> loadManyFromJSON() {
        try {
            Resource resource = new ClassPathResource("cocktails.json");
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new InputStreamReader(resource.getInputStream()));
            return Arrays.asList(gson.fromJson(reader, Cocktail[].class));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static DynamoDbEnhancedClient createDynamoDBClient() {
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        Region region = Region.US_EAST_1;
        DynamoDbClient ddb = DynamoDbClient.builder()
                .credentialsProvider(credentialsProvider)
                .region(region)
                .build();

        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
        return enhancedClient;
    }

    public static String generateRandomString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
}
        return sb.toString();

    }
}


