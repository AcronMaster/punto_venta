package com.dulceria.view.componets;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.formdev.flatlaf.extras.FlatSVGIcon;

public class MenuPrincipalView extends JPanel {

    private JButton btnVentas;
    private JButton btnProductos;
    private JButton btnInventario;
    private JButton btnCorte;

    public MenuPrincipalView() {

        crearMenu();
    }

    private void crearMenu() {

        setLayout(
            new FlowLayout(
                FlowLayout.LEFT,
                0,
                0
            )
        );

        setBackground(
            new Color(240, 240, 240)
        );

        setBorder(
            BorderFactory.createEmptyBorder(
                15,
                20,
                0,
                20
            )
        );

        Color colorIcon =
            new Color(21, 101, 192);

        // =========================
        // BOTÓN VENTAS
        // =========================

        btnVentas =
            crearBoton(
                "Ventas",
                "images/cart.svg",
                colorIcon
            );

        // =========================
        // BOTÓN PRODUCTOS
        // =========================

        btnProductos =
            crearBoton(
                "Productos",
                "images/box.svg",
                colorIcon
            );

        // =========================
        // BOTÓN INVENTARIO
        // =========================

        btnInventario =
            crearBoton(
                "Inventario",
                "images/list.svg",
                colorIcon
            );

        // =========================
        // BOTÓN CORTE
        // =========================

        btnCorte =
            crearBoton(
                "Corte",
                "images/check.svg",
                colorIcon
            );

        // =========================
        // VENTAS SELECCIONADO
        // =========================

        seleccionarBoton(btnVentas);

        // =========================
        // AGREGAR BOTONES
        // =========================

        add(btnVentas);
        add(btnProductos);
        add(btnInventario);
        add(btnCorte);
    }

    private JButton crearBoton(
        String texto,
        String rutaIcono,
        Color colorIcono
    ) {

        FlatSVGIcon icono =
            new FlatSVGIcon(
                rutaIcono,
                24,
                24
            );

        icono.setColorFilter(
            new FlatSVGIcon.ColorFilter(
                c -> colorIcono
            )
        );

        JButton boton =
            new JButton(
                texto,
                icono
            );

        boton.setPreferredSize(
            new Dimension(180, 70)
        );

        boton.setFont(
            new Font(
                "Segoe UI",
                Font.BOLD,
                18
            )
        );

        boton.setFocusPainted(false);

        boton.setBackground(Color.WHITE);

        boton.setCursor(
            new Cursor(
                Cursor.HAND_CURSOR
            )
        );

        boton.setBorder(
            BorderFactory.createLineBorder(
                new Color(220, 220, 220)
            )
        );

        return boton;
    }

    private void seleccionarBoton(
        JButton botonSeleccionado
    ) {

        Color azul =
            new Color(21, 101, 192);

        JButton[] botones = {
            btnVentas,
            btnProductos,
            btnInventario,
            btnCorte
        };

        for (JButton boton : botones) {

            if (boton == botonSeleccionado) {

                boton.setBackground(azul);
                boton.setForeground(Color.WHITE);

                FlatSVGIcon icono =
                    (FlatSVGIcon) boton.getIcon();

                icono.setColorFilter(
                    new FlatSVGIcon.ColorFilter(
                        c -> Color.WHITE
                    )
                );

            } else {

                boton.setBackground(Color.WHITE);
                boton.setForeground(Color.BLACK);

                FlatSVGIcon icono =
                    (FlatSVGIcon) boton.getIcon();

                icono.setColorFilter(
                    new FlatSVGIcon.ColorFilter(
                        c -> azul
                    )
                );
            }
        }
    }

    public JButton getBtnVentas() {
        return btnVentas;
    }

    public JButton getBtnProductos() {
        return btnProductos;
    }

    public JButton getBtnInventario() {
        return btnInventario;
    }

    public JButton getBtnCorte() {
        return btnCorte;
    }

    public void seleccionarVentas() {
        seleccionarBoton(btnVentas);
    }

    public void seleccionarProductos() {
        seleccionarBoton(btnProductos);
    }

    public void seleccionarInventario() {
        seleccionarBoton(btnInventario);
    }

    public void seleccionarCorte() {
        seleccionarBoton(btnCorte);
    }
}