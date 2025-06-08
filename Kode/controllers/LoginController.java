package controllers;

import dao.UserDAO;
import models.User;
import views.AdminDashboard;
import views.StaffDashboard;

import javax.swing.*;

public class LoginController {
    public static void login(String username, String password) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.login(username, password);
        if (user != null) {
            if (user.getRole().equals("admin")) {
                new AdminDashboard(user).setVisible(true);
            } else {
                new StaffDashboard(user).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Login gagal!");
        }
    }
}
