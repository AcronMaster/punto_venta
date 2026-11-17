package com.dulceria.view.componets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.formdev.flatlaf.extras.FlatSVGIcon;

public class BarraInferiorView extends JPanel {

    private JLabel lblHora;
    private JLabel lblFecha;

    public BarraInferiorView() {

        crearBarra();
    }

    private void crearBarra() {

        setLayout(new BorderLayout());

        setBackground(Color.WHITE);

        setPreferredSize(
            new Dimension(100, 45)
        );

        setBorder(
            BorderFactory.createMatteBorder(
                1,
                0,
                0,
                0,
                new Color(220, 220, 220)
            )
        );

        // =========================
        // PARTE IZQUIERDA
        // =========================

        JPanel izquierda =
            new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT,
                    15,
                    8
                )
            );

        izquierda.setOpaque(false);

        JLabel iconoHora =
            new JLabel(
                new FlatSVGIcon(
                    "images/clock.svg",
                    18,
                    18
                )
            );

        lblHora = new JLabel();

        lblHora.setFont(
            new Font(
                "Segoe UI",
                Font.PLAIN,
                14
            )
        );

        JLabel separador =
            new JLabel("|");

        separador.setForeground(
            new Color(180, 180, 180)
        );

        JLabel iconoCalendario =
            new JLabel(
                new FlatSVGIcon(
                    "images/calendar.svg",
                    18,
                    18
                )
            );

        lblFecha = new JLabel();

        lblFecha.setFont(
            new Font(
                "Segoe UI",
                Font.PLAIN,
                14
            )
        );

        izquierda.add(iconoHora);
        izquierda.add(lblHora);
        izquierda.add(separador);
        izquierda.add(iconoCalendario);
        izquierda.add(lblFecha);

        // =========================
        // PARTE DERECHA
        // =========================

        JPanel derecha =
            new JPanel(
                new FlowLayout(
                    FlowLayout.RIGHT,
                    15,
                    8
                )
            );

        derecha.setOpaque(false);

        JLabel iconoFolio =
            new JLabel(
                new FlatSVGIcon(
                    "images/folio.svg",
                    18,
                    18
                )
            );

        JLabel lblFolio =
            new JLabel(
                "Folio actual: TKT-000123"
            );

        lblFolio.setFont(
            new Font(
                "Segoe UI",
                Font.PLAIN,
                14
            )
        );

        derecha.add(iconoFolio);
        derecha.add(lblFolio);

        // =========================
        // AGREGAR
        // =========================

        add(
            izquierda,
            BorderLayout.WEST
        );

        add(
            derecha,
            BorderLayout.EAST
        );

        // =========================
        // FECHA Y HORA
        // =========================

        actualizarFechaHora();

        Timer timer =
            new Timer(
                1000,
                e -> actualizarFechaHora()
            );

        timer.start();
    }

    private void actualizarFechaHora() {

        LocalDateTime ahora =
            LocalDateTime.now();

        DateTimeFormatter formatoHora =
            DateTimeFormatter.ofPattern(
                "hh:mm a"
            );

        DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern(
                "dd/MM/yyyy"
            );

        lblHora.setText(
            ahora.format(formatoHora)
        );

        lblFecha.setText(
            ahora.format(formatoFecha)
        );
    }
}