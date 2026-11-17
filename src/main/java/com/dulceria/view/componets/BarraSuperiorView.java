package com.dulceria.view.componets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dulceria.model.Usuario;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class BarraSuperiorView extends JPanel {

    private Usuario usuario;

    public BarraSuperiorView(Usuario usuario) {

        this.usuario = usuario;

        crearBarra();
    }

    private void crearBarra() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1400, 55));

        setBorder(
            BorderFactory.createMatteBorder(
                0,
                0,
                1,
                0,
                new Color(220, 220, 220)
            )
        );

        // =========================
        // PARTE IZQUIERDA
        // =========================

        JPanel izquierda =
            new JPanel(new FlowLayout(FlowLayout.LEFT));

        izquierda.setOpaque(false);

        JButton btnSalir =
            new JButton("Salir");

        btnSalir.setFocusPainted(false);
        btnSalir.setFont(
            new Font("Segoe UI", Font.PLAIN, 16)
        );
        btnSalir.setBackground(Color.WHITE);
        btnSalir.setBorderPainted(false);
        btnSalir.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );
        btnSalir.setForeground(Color.RED);

        FlatSVGIcon iconoSalir =
            new FlatSVGIcon(
                "images/out.svg",
                32,
                32
            );

        iconoSalir.setColorFilter(
            new FlatSVGIcon.ColorFilter(
                c -> Color.RED
            )
        );

        btnSalir.setIcon(iconoSalir);

        izquierda.add(btnSalir);

        // =========================
        // PARTE DERECHA
        // =========================

        JPanel derecha =
            new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
            );

        derecha.setOpaque(false);

        FlatSVGIcon iconoUsuario =
            new FlatSVGIcon(
                "images/user.svg",
                32,
                32
            );

        iconoUsuario.setColorFilter(
            new FlatSVGIcon.ColorFilter(
                c -> Color.BLUE
            )
        );

        JLabel lblUsuario =
            new JLabel(
                "Usuario: " + usuario.getNombre(),
                iconoUsuario,
                JLabel.LEFT
            );

        lblUsuario.setFont(
            new Font(
                "Segoe UI",
                Font.PLAIN,
                16
            )
        );

        derecha.add(lblUsuario);

        // =========================
        // AGREGAR A LA BARRA
        // =========================

        add(
            izquierda,
            BorderLayout.WEST
        );

        add(
            derecha,
            BorderLayout.EAST
        );
    }
}