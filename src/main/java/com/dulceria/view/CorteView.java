
package com.dulceria.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.dulceria.model.Usuario;
import com.dulceria.view.componets.BarraInferiorView;
import com.dulceria.view.componets.BarraSuperiorView;
import com.dulceria.view.componets.MenuPrincipalView;

public class CorteView extends JFrame {

    private final Usuario usuario;

    private final Color AZUL = new Color(21, 101, 192);
    private final Color GRIS = new Color(220, 225, 230);
    private final Color FONDO = new Color(248, 249, 251);
    private final Color TEXTO = new Color(45, 55, 65);

    public CorteView(Usuario usuario) {
        this.usuario = usuario;

        configurarVentana();
        inicializarComponentes();
    }

    // =========================================================
    // CONFIGURACIÓN DE VENTANA
    // =========================================================

    private void configurarVentana() {
        setTitle("Corte de caja");
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // =========================================================
    // INICIALIZAR COMPONENTES
    // =========================================================

    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);

        // Barra superior y menú
        JPanel panelSuperior = new JPanel(new BorderLayout());

        BarraSuperiorView barraSuperior =
                new BarraSuperiorView(usuario);

        MenuPrincipalView menu =
                new MenuPrincipalView();

        menu.seleccionarCorte();

        // Navegación del menú
        menu.getBtnVentas().addActionListener(e -> {
            new PrincipalView(usuario).setVisible(true);
            dispose();
        });

        menu.getBtnProductos().addActionListener(e -> {
            new ProductosView(usuario).setVisible(true);
            dispose();
        });

        menu.getBtnInventario().addActionListener(e -> {
            new InventarioView(usuario).setVisible(true);
            dispose();
        });

        panelSuperior.add(barraSuperior, BorderLayout.NORTH);
        panelSuperior.add(menu, BorderLayout.CENTER);

        // Panel central
        JPanel panelCentral = crearPanelCentral();

        // Barra inferior
        BarraInferiorView barraInferior =
                new BarraInferiorView();

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(barraInferior, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    // =========================================================
    // PANEL CENTRAL
    // =========================================================

    private JPanel crearPanelCentral() {

        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(FONDO);

        panel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        // Encabezado
        JPanel encabezado = crearEncabezado();

        // Resumen de ventas
        JPanel resumen = crearResumen();

        // Botones inferiores
        JPanel acciones = crearPanelAcciones();

        panel.add(encabezado, BorderLayout.NORTH);
        panel.add(resumen, BorderLayout.CENTER);
        panel.add(acciones, BorderLayout.SOUTH);

        return panel;
    }

    // =========================================================
    // ENCABEZADO
    // =========================================================

    private JPanel crearEncabezado() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(GRIS),
                        BorderFactory.createEmptyBorder(15, 20, 15, 20)
                )
        );

        // Título
        JLabel titulo = new JLabel("Corte de caja");

        titulo.setFont(
                new Font("Segoe UI", Font.BOLD, 25)
        );

        titulo.setForeground(AZUL);

        // Información del corte
        JPanel informacion = new JPanel(
                new GridLayout(2, 2, 30, 8)
        );

        informacion.setBackground(Color.WHITE);

        String fecha = LocalDate.now().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );

        informacion.add(crearDato("Fecha:", fecha));
        informacion.add(crearDato("Cajero:", usuario.getNombre()));
        informacion.add(crearDato("Estado:", "Pendiente de cierre"));
        informacion.add(crearDato("Turno:", "Actual"));

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(informacion, BorderLayout.CENTER);

        return panel;
    }

    // =========================================================
    // DATO DE INFORMACIÓN
    // =========================================================

    private JLabel crearDato(String etiqueta, String valor) {

        JLabel label = new JLabel(
                "<html><b>" + etiqueta + "</b> " + valor + "</html>"
        );

        label.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );

        label.setForeground(TEXTO);

        return label;
    }

    // =========================================================
    // RESUMEN
    // =========================================================

    private JPanel crearResumen() {

        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(FONDO);

        // Título
        JLabel titulo = new JLabel("Resumen del corte");

        titulo.setFont(
                new Font("Segoe UI", Font.BOLD, 20)
        );

        titulo.setForeground(TEXTO);

        // Tarjetas superiores
        JPanel tarjetas = new JPanel(
                new GridLayout(1, 2, 20, 0)
        );

        tarjetas.setBackground(FONDO);

        tarjetas.add(
                crearTarjeta(
                        "Ventas realizadas",
                        "35",
                        "Operaciones registradas"
                )
        );

        tarjetas.add(
                crearTarjeta(
                        "Productos vendidos",
                        "87",
                        "Unidades vendidas"
                )
        );

        // Desglose de pagos
        JPanel pagos = crearPanelPagos();

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(tarjetas, BorderLayout.CENTER);
        panel.add(pagos, BorderLayout.SOUTH);

        return panel;
    }

    // =========================================================
    // TARJETA DE RESUMEN
    // =========================================================

    private JPanel crearTarjeta(
            String titulo,
            String valor,
            String descripcion
    ) {

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(
                new BoxLayout(tarjeta, BoxLayout.Y_AXIS)
        );

        tarjeta.setBackground(Color.WHITE);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(GRIS),
                        BorderFactory.createEmptyBorder(20, 25, 20, 25)
                )
        );

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(
                new Font("Segoe UI", Font.PLAIN, 16)
        );
        lblTitulo.setForeground(TEXTO);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(
                new Font("Segoe UI", Font.BOLD, 32)
        );
        lblValor.setForeground(AZUL);

        JLabel lblDescripcion = new JLabel(descripcion);
        lblDescripcion.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        lblDescripcion.setForeground(Color.GRAY);

        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(lblValor);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(lblDescripcion);

        return tarjeta;
    }

    // =========================================================
    // PANEL DE MÉTODOS DE PAGO
    // =========================================================

    private JPanel crearPanelPagos() {

        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(GRIS),
                        BorderFactory.createEmptyBorder(18, 22, 18, 22)
                )
        );

        JLabel titulo = new JLabel("Desglose de ventas por método de pago");

        titulo.setFont(
                new Font("Segoe UI", Font.BOLD, 17)
        );

        titulo.setForeground(TEXTO);

        JPanel detalle = new JPanel(
                new GridLayout(3, 2, 10, 12)
        );

        detalle.setBackground(Color.WHITE);

        detalle.add(crearEtiquetaPago("Efectivo"));
        detalle.add(crearImporte("$2,450.00"));

        detalle.add(crearEtiquetaPago("Tarjeta"));
        detalle.add(crearImporte("$1,280.00"));

        detalle.add(crearEtiquetaPago("Transferencia"));
        detalle.add(crearImporte("$650.00"));

        // Total
        JPanel panelTotal = new JPanel(new BorderLayout());

        panelTotal.setBackground(
                new Color(235, 243, 252)
        );

        panelTotal.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JLabel lblTotal = new JLabel("TOTAL DE VENTAS");

        lblTotal.setFont(
                new Font("Segoe UI", Font.BOLD, 17)
        );

        JLabel valorTotal = new JLabel("$4,380.00");

        valorTotal.setFont(
                new Font("Segoe UI", Font.BOLD, 25)
        );

        valorTotal.setForeground(AZUL);

        panelTotal.add(lblTotal, BorderLayout.WEST);
        panelTotal.add(valorTotal, BorderLayout.EAST);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(detalle, BorderLayout.CENTER);
        panel.add(panelTotal, BorderLayout.SOUTH);

        return panel;
    }

    // =========================================================
    // ETIQUETAS DE PAGOS
    // =========================================================

    private JLabel crearEtiquetaPago(String texto) {

        JLabel label = new JLabel(texto);

        label.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );

        label.setForeground(TEXTO);

        return label;
    }

    private JLabel crearImporte(String importe) {

        JLabel label = new JLabel(
                importe,
                SwingConstants.RIGHT
        );

        label.setFont(
                new Font("Segoe UI", Font.BOLD, 15)
        );

        label.setForeground(TEXTO);

        return label;
    }

    // =========================================================
    // BOTONES DE ACCIÓN
    // =========================================================

    private JPanel crearPanelAcciones() {

        JPanel panel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 15, 0)
        );

        panel.setBackground(FONDO);

        JButton btnImprimir = new JButton("Imprimir corte");
        JButton btnCerrar = new JButton("Cerrar corte");

        configurarBotonSecundario(btnImprimir);
        configurarBotonPrincipal(btnCerrar);

        // Por ahora son acciones pendientes de conectar a la BD.
        btnImprimir.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "La impresión se implementará al conectar el sistema.",
                        "Imprimir corte",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        btnCerrar.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "El cierre de caja se implementará al conectar el sistema.",
                        "Cerrar corte",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        panel.add(btnImprimir);
        panel.add(btnCerrar);

        return panel;
    }

    // =========================================================
    // ESTILO BOTONES
    // =========================================================

    private void configurarBotonPrincipal(JButton boton) {

        boton.setPreferredSize(new Dimension(190, 48));
        boton.setFont(
                new Font("Segoe UI", Font.BOLD, 15)
        );

        boton.setBackground(AZUL);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
    }

    private void configurarBotonSecundario(JButton boton) {

        boton.setPreferredSize(new Dimension(190, 48));
        boton.setFont(
                new Font("Segoe UI", Font.BOLD, 15)
        );

        boton.setBackground(Color.WHITE);
        boton.setForeground(AZUL);
        boton.setFocusPainted(false);
        boton.setBorder(
                BorderFactory.createLineBorder(AZUL)
        );

        boton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
    }
}
