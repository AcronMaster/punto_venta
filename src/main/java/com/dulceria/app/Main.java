package com.dulceria.app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.dulceria.view.LoginView;
import com.formdev.flatlaf.FlatLightLaf;

public class Main {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {

            LoginView login = new LoginView();
            login.setVisible(true);
        });

    }

}