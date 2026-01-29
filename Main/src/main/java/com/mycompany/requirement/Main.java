package com.mycompany.requirement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static class User {
        private String userName;
        private String passWord;

        public User(String userName, String passWord) {
            this.userName = userName;
            this.passWord = passWord;
        }

        public boolean verUser(String inputUser, String inputPass) {
            return this.passWord.equals(inputPass) && this.userName.equals(inputUser);
        }
    }

    public static class Car {
        private int id;
        private String carName;
        private double pricePerDay;
        private boolean isAvailable;
        private boolean isRented;

        public Car(int id, String carName, double pricePerday, boolean isAvailable) {
            this.id = id;
            this.carName = carName;
            this.pricePerDay = pricePerday;
            this.isAvailable = isAvailable;
            this.isRented = false;
        }

        public int getID() {
            return id;
        }

        public String getCarName() {
            return carName;
        }

        public double getPricePerDay() {
            return pricePerDay;
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public boolean isRented() {
            return isRented;
        }

        public void rentCar() {
            if (isAvailable && !isRented) {
                isAvailable = false;
                isRented = true;
            }
        }

        public void returnCar() {
            if (isRented) {
                isRented = false;
                isAvailable = true;
            }
        }
    }

    public static void main(String[] args) {
        // Create user and cars
        User user = new User("Lionell", "Rhys");
        Car car1 = new Car(1613, "Honda yung may Tambutso", 50.0, true);
        Car car2 = new Car(2172, "Genggeng", 100.0, true);
        Car car3 = new Car(3889, "Oner", 10.0, true);
        Car car4 = new Car(4258, "Rusi Racal", 2.0, true);
        Car car5 = new Car(5058, "Lamborghini", 10000.0, true);

        // JFrame setup
        JFrame frame = new JFrame("Car Rental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Create login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginPanel.add(userLabel);
        loginPanel.add(userField);
        loginPanel.add(passLabel);
        loginPanel.add(passField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        // Create main menu panel
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridLayout(4, 1));
        JButton rentCarButton = new JButton("Rent a Car");
        JButton returnCarButton = new JButton("Return a Car");
        JButton profileButton = new JButton("Profile");
        JButton exitButton = new JButton("Exit");

        mainMenuPanel.add(rentCarButton);
        mainMenuPanel.add(returnCarButton);
        mainMenuPanel.add(profileButton);
        mainMenuPanel.add(exitButton);

        // Create car rental panel
        JPanel rentPanel = new JPanel();
        rentPanel.setLayout(new GridLayout(2, 1));
        JComboBox<String> carChoiceCombo = new JComboBox<>(new String[]{
            "Honda yung may Tambutso",
            "Genggeng",
            "Oner",
            "Rusi Racal",
            "Lamborghini"
        });
        JButton rentCarConfirmButton = new JButton("Confirm Rent");
        rentPanel.add(carChoiceCombo);
        rentPanel.add(rentCarConfirmButton);
        rentPanel.setVisible(false); // Initially hidden

        // Create car return panel
        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new GridLayout(2, 1));
        JComboBox<String> returnCarChoiceCombo = new JComboBox<>(new String[]{
            "Honda yung may Tambutso",
            "Genggeng",
            "Oner",
            "Rusi Racal",
            "Lamborghini"
        });
        JButton returnCarConfirmButton = new JButton("Confirm Return");
        returnPanel.add(returnCarChoiceCombo);
        returnPanel.add(returnCarConfirmButton);
        returnPanel.setVisible(false); // Initially hidden

        // Add all panels to the frame
        frame.add(loginPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Handle login logic
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputUser = userField.getText();
                String inputPass = new String(passField.getPassword());

                if (user.verUser(inputUser, inputPass)) {
                    // Hide login panel and show main menu panel
                    frame.remove(loginPanel);
                    frame.add(mainMenuPanel, BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.");
                }
            }
        });

        // Handle rent car logic
        rentCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuPanel.setVisible(false);
                rentPanel.setVisible(true);
                frame.add(rentPanel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            }
        });

        // Handle car return logic
        returnCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuPanel.setVisible(false);
                returnPanel.setVisible(true);
                frame.add(returnPanel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            }
        });

        // Handle confirm rent action
        rentCarConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCar = (String) carChoiceCombo.getSelectedItem();
                Car carToRent = getCarByName(selectedCar);
                if (carToRent != null && !carToRent.isRented()) {
                    carToRent.rentCar();
                    JOptionPane.showMessageDialog(frame, "You have rented the " + selectedCar + "!");
                    rentPanel.setVisible(false);
                    mainMenuPanel.setVisible(true);
                    frame.revalidate();
                    frame.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "The car is not available for rent.");
                }
            }
        });

        // Handle confirm return action
        returnCarConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCar = (String) returnCarChoiceCombo.getSelectedItem();
                Car carToReturn = getCarByName(selectedCar);
                if (carToReturn != null && carToReturn.isRented()) {
                    carToReturn.returnCar();
                    JOptionPane.showMessageDialog(frame, "You have returned the " + selectedCar + "!");
                    returnPanel.setVisible(false);
                    mainMenuPanel.setVisible(true);
                    frame.revalidate();
                    frame.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "You haven't rented this car.");
                }
            }
        });

        // Handle profile and exit logic
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "User Profile:\nUsername: " + user.getUser());
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    // Helper method to get car by name
    private static Car getCarByName(String carName) {
        switch (carName) {
            case "Honda yung may Tambutso": return car1;
            case "Genggeng": return car2;
            case "Oner": return car3;
            case "Rusi Racal": return car4;
            case "Lamborghini": return car5;
            default: return null;
        }
    }
}
