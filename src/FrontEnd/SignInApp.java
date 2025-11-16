package FrontEnd;
import JSON.StudentService;
import JSON.CourseService;
import Users.Student;
import JSON.UserService;
import Users.User;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class SignInApp {
    static UserService users;
    static CourseService courseService;
    static StudentService studentService;

    static {
        try {
            users = new UserService("src\\JSON\\users.json");
            courseService = new CourseService("src\\JSON\\courses.json");
            studentService = new StudentService(users, courseService);
        } catch (IOException e) {
            System.out.println("Error loading users!");

        }
    }

    public SignInApp() throws IOException {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignInApp::showSignInWindow);
    }

    // ===================== SIGN IN WINDOW =====================
    private static void showSignInWindow() {
        JFrame frame = new JFrame("Sign In");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 250);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 25));
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

    // ===================== REGISTER WINDOW (ALL FIELDS) =====================
    private static void showRegisterWindow(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Register New User", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField userNameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JPasswordField confirmField = new JPasswordField();
        JTextField roleField = new JTextField("Student"); // Default role
        JButton createBtn = new JButton("Create Account");
        JRadioButton option1 = new JRadioButton("Instructor");
        JRadioButton option2 = new JRadioButton("Student", true);
        ButtonGroup group = new ButtonGroup();
        ;
        group.add(option1);
        group.add(option2);



        panel.add(new JLabel("Username:"));
        panel.add(userNameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(confirmField);

        panel.add(option1);
        panel.add(option2);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(createBtn);

        createBtn.addActionListener(e -> {
            String username = userNameField.getText().trim();
            String email = emailField.getText().trim();
            String pass1 = new String(passField.getPassword());
            String pass2 = new String(confirmField.getPassword());
            String role = option2.isSelected() ? "Student" : "Instructor";

            // === VALIDATION ===
            if (username.isEmpty() || email.isEmpty() || pass1.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!pass1.equals(pass2)) {
                JOptionPane.showMessageDialog(dialog, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(dialog, "Enter a valid email!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (usernameExists(username)) {
                JOptionPane.showMessageDialog(dialog, "Username already taken!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            //create ID
            Random rand = new Random();
            int randomNum = rand.nextInt(1000) +10000;
            String userID = Integer.toString(randomNum);
            String hashedPassword = hashPassword(pass1);
            // === ADD NEW USER ===
            users.addUser(new User(userID, hashedPassword, username, role, email));
            users.save();

            JOptionPane.showMessageDialog(dialog, "Account created successfully!\nID: " + userID, "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }
    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    // ===================== SIGN IN LOGIC =====================
   private static void handleSignIn(String username, String password, JFrame frame) {
    if (username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String hashedPassword = hashPassword(password);

    if (validateLogin(username, hashedPassword)) {
        JOptionPane.showMessageDialog(frame, "Welcome, " + username + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();

        User u = users.getUserByUsername(username);

        if (u.getRole().equalsIgnoreCase("Student")) {
            // Option 2: safely create Student object if needed
            Student s;
            if (u instanceof Student) {
                s = (Student) u;
            } else {
                s = new Student(u.getUserID(), u.getUserName(), u.getEmail(), u.getPassword());
                s.setStudentService(studentService);
            }

            // Open CourseManagementStudent frame
            CourseManagementStudent cms = new CourseManagementStudent(s, studentService, courseService);
            cms.setVisible(true);
            cms.setLocationRelativeTo(null);

        } else {
            instructorDashboard();
        }

    } else {
        JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
    }
}




    private static void instructorDashboard() {
        JFrame main = new JFrame("instructorDashboard");
        main.setSize(500, 400);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setLocationRelativeTo(null);
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JButton signOutBtn = new JButton("Sign Out");
        signOutBtn.addActionListener(e -> {
            main.dispose();
            showSignInWindow();
        });
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(signOutBtn);
        main.add(panel);
        main.setVisible(true);
    }

    // ===================== VALIDATION =====================
    private static boolean validateLogin(String username, String password) {
        ArrayList<User> userList = users.getDb();

        for (User u : userList) {
            if (u.getUserName().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private static boolean usernameExists(String username) {
        return users.getDb().stream().anyMatch(u -> u.getUserName().equals(username));
    }
}
