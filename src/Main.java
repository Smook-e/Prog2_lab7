import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // --- 1. Create ArrayList of Person ---
        List<Person> people;
        String fromFile = Files.readString(Path.of("src\\JSON\\users.json"));
        people =  gson.fromJson(fromFile, new TypeToken<ArrayList<Person>>(){}.getType());
        for (Person p : people) {
           System.out.println(p);
       }
//        people.add(new Person("Alice", 28, "alice@example.com"));
//        people.add(new Person("Bob", 35, "bob@example.com"));
//        people.add(new Person("Charlie", 22, "charlie@example.com"));

        // --- 2. Save ArrayList → JSON file ---
        String json = gson.toJson(people);
        Files.writeString(Path.of("users.json"), json);
        System.out.println("Saved to users.json:\n" + json);


//        String jsonFromFile = Files.readString(Path.of("users.json"));


//        TypeToken<ArrayList<Person>> typeToken = new TypeToken<>() {};
//        ArrayList<Person> loaded = gson.fromJson(jsonFromFile, typeToken.getType());

//        System.out.println("\nLoaded " + loaded.size() + " people:");
//
//
//
//        System.out.println("\nClass: " + loaded.getClass().getName());
    }
}

class Person {
    public String name;
    public int age;
    public String email;

    // Required by Gson
    public Person() {}

    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", email='" + email + "'}";
    }
}