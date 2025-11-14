package FrontEnd;

import JSON.UserService;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SignInApp {
    UserService users;

    public SignInApp() throws IOException {
        users = new UserService("src\\JSON\\users.json");
    }

    public static void main(String[] args) {
        createSampleUsersIfNeeded();
        SwingUtilities.invokeLater(SignInApp::showSignInWindow);
    }

    // ===================== SIGN IN WINDOW =====================
    private static void showSignInWindow() {
        JFrame frame = new JFrame("Sign In");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 220);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton signInBtn = new JButton("Sign In");
        JButton registerBtn = new JButton("Register");

        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(signInBtn);
        panel.add(registerBtn);

        signInBtn.addActionListener(e -> handleSignIn(userField.getText().trim(), new String(passField.getPassword()), frame));
        registerBtn.addActionListener(e -> showRegisterWindow(frame));

        frame.add(panel);
        frame.setVisible(true);
    }

    // ===================== REGISTER WINDOW =====================
    private static void showRegisterWindow(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Register New User", true);
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JPasswordField confirmField = new JPasswordField();
        JButton createBtn = new JButton("Create Account");

        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(new JLabel("Confirm:"));
        panel.add(confirmField);
        panel.add(new JLabel());
        panel.add(createBtn);

        createBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String pass1 = new String(passField.getPassword());
            String pass2 = new String(confirmField.getPassword());

            if (username.isEmpty() || pass1.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!pass1.equals(pass2)) {
                JOptionPane.showMessageDialog(dialog, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (usernameExists(username)) {
                JOptionPane.showMessageDialog(dialog, "Username already taken!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Save new user

            users.add(new User(username, pass1));
            saveUsers(users);

            JOptionPane.showMessageDialog(dialog, "Account created! You can now sign in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // ===================== SIGN IN LOGIC =====================
    private static void handleSignIn(String username, String password, JFrame frame) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (validateLogin(username, password)) {
            JOptionPane.showMessageDialog(frame, "Welcome, " + username + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            // Open main app here
            openMainApp();
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void openMainApp() {
        JFrame main = new JFrame("Main App");
        main.setSize(400, 300);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setLocationRelativeTo(null);
        main.add(new JLabel("Welcome! This is your app.", SwingConstants.CENTER));
        main.setVisible(true);
    }

    // ===================== USER STORAGE =====================
    private static ArrayList<User> loadUsers() {
        try {
            if (!Files.exists(FILE)) return new ArrayList<>();
            String json = Files.readString(FILE);
            TypeToken<ArrayList<User>> type = new TypeToken<>() {};
            return gson.fromJson(json, type.getType());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private static void saveUsers(ArrayList<User> users) {
        try {
            String json = gson.toJson(users);
            Files.writeString(FILE, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean validateLogin(String username, String password) {
        return loadUsers().stream()
                .anyMatch(u -> u.username.equals(username) && u.password.equals(password));
    }

    private static boolean usernameExists(String username) {
        return loadUsers().stream().anyMatch(u -> u.username.equals(username));
    }

    private static void createSampleUsersIfNeeded() {
        if (Files.exists(FILE)) return;

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("admin", "1234"));
        saveUsers(users);
        System.out.println("Created users.json with default admin/1234");
    }
}

