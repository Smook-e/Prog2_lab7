package JSON;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class JsonDatabaseManager <T>{
    protected ArrayList<T> db;
    protected Path file;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public JsonDatabaseManager() {};
    public JsonDatabaseManager(String filePath) throws IOException {
        file = Path.of(filePath);
        db = load();
    }
    public ArrayList<T> load() throws IOException {

        String json = Files.readString(file); // make JSON string from JSON file
        return gson.fromJson(json, new TypeToken<ArrayList<T>>(){}.getType()); // change string into arraylist
    }

    public void  save() {
        try {
            String json = gson.toJson(db);
            Files.writeString(file, json);
        } catch (IOException e) {
            System.out.println("Error while saving database.");
        }
    }


    public ArrayList<T> getDb() {
        return db;
    }

    public void print(){
        for(T t: db){
            System.out.println(t);
        }
    }

    public abstract void searchBYName(String name);
}
