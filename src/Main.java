import JSON.JsonDatabaseManager;
import JSON.UserService;
import Users.User;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        String path = "src\\JSON\\users.json";
        JsonDatabaseManager<User> db = new JsonDatabaseManager(path);
        db.print();
        UserService a = new UserService("src\\JSON\\users.json");
        for(User u : a.getDb()) {
            if(u.getPassword().equals("abc") && u.getUserName().equals("alice123")){
                System.out.println(true);
            }
        }

//

    }
}

