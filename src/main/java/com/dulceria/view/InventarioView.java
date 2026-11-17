package com.dulceria.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.dulceria.model.Usuario;
import  com.dulceria.view.componets.BarraInferiorView;
import  com.dulceria.view.componets.BarraSuperiorView;
import  com.dulceria.view.componets.MenuPrincipalView;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class InventarioView extends JFrame {

    private Usuario usuario;

    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    private JTextField txtBuscar;
    private JComboBox<String> comboCategoria;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public InventarioView(Usuario usuario) {

        this.usuario = usuario;

        configurarVentana();
        inicializarComponentes();
    }

    // =========================================================
    // CONFIGURAR VENTANA
    // =========================================================

    private void configurarVentana() {

        setTitle("Inventario");

        setSize(
            1400,
            800
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
            JFrame.DISPOSE_ON_CLOSE
        );
    }

    // =========================================================
    // INICIALIZAR COMPONENTES
    // =========================================================

    private void inicializarComponentes() {

        JPanel panelPrincipal =
            new JPanel(
                new BorderLayout()
            );

        panelPrincipal.setBackground(
            Color.WHITE
        );

        // =====================================================
        // PARTE SUPERIOR
        // =====================================================

        JPanel panelSuperior =
            new JPanel(
                new BorderLayout()
            );

        BarraSuperiorView barraSuperior =
            new BarraSuperiorView(usuario);

        MenuPrincipalView menu =
            new MenuPrincipalView();

        menu.seleccionarInventario();

        // =====================================================
        // NAVEGACIÓN
        // =====================================================

        menu.getBtnVentas()
            .addActionListener(e -> {

                PrincipalView ventas =
                    new PrincipalView(usuario);

                ventas.setVisible(true);

                dispose();
            });

        menu.getBtnProductos()
            .addActionListener(e -> {

                ProductosView productos =
                    new ProductosView(usuario);

                productos.setVisible(true);

                dispose();
            });

        panelSuperior.add(
            barraSuperior,
            BorderLayout.NORTH
        );

        panelSuperior.add(
            menu,
            BorderLayout.CENTER
        );

        // =====================================================
        // PARTE CENTRAL
        // =====================================================

        JPanel panelCentral =
            crearPanelCentral();

        // =====================================================
        // PARTE INFERIOR
        // =====================================================

        BarraInferiorView barraInferior =
            new BarraInferiorView();

        // =====================================================
        // ARMAR VENTANA
        // =====================================================

        panelPrincipal.add(
            panelSuperior,
            BorderLayout.NORTH
        );

        panelPrincipal.add(
            panelCentral,
            BorderLayout.CENTER
        );

        panelPrincipal.add(
            barraInferior,
            BorderLayout.SOUTH
        );

        add(
            panelPrincipal
        );
    }

    // =========================================================
    // PANEL CENTRAL
    // =========================================================

    private JPanel crearPanelCentral() {

        JPanel panel =
            new JPanel(
                new BorderLayout(
                    0,
                    0
                )
            );

        panel.setBackground(
            Color.WHITE
        );

        panel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(
                        220,
                        220,
                        220
                    )
                ),
                BorderFactory.createEmptyBorder(
                    20,
                    20,
                    20,
                    20
                )
            )
        );

        // =====================================================
        // PARTE 1 - BUSCAR PRODUCTO
        // =====================================================

        JPanel panelBusqueda =
            crearPanelBusqueda();

        panel.add(
            panelBusqueda,
            BorderLayout.NORTH
        );

        // =====================================================
        // PARTE 2 - LISTA DE PRODUCTOS
        // =====================================================

        JPanel panelLista =
            crearPanelLista();

        panel.add(
            panelLista,
            BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // PANEL BUSQUEDA
    // =========================================================

    private JPanel crearPanelBusqueda() {

        JPanel panel =
            new JPanel(
                new GridBagLayout()
            );

        panel.setBackground(
            Color.WHITE
        );

        panel.setBorder(
            BorderFactory.createMatteBorder(
                0,
                0,
                1,
                0,
                new Color(
                    225,
                    225,
                    225
                )
            )
        );

        GridBagConstraints gbc =
            new GridBagConstraints();

        gbc.insets =
            new Insets(
                5,
                5,
                15,
                10
            );

        gbc.fill =
            GridBagConstraints.HORIZONTAL;

        // =====================================================
        // TITULO
        // =====================================================

        JLabel numero =
            crearNumeroSeccion(
                "1"
            );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        panel.add(
            numero,
            gbc
        );

        JLabel titulo =
            new JLabel(
                "Buscar producto"
            );

        titulo.setFont(
            new Font(
                "Segoe UI",
                Font.BOLD,
                18
            )
        );

        gbc.gridx = 1;
        gbc.gridy = 0;

        panel.add(
            titulo,
            gbc
        );

        // =====================================================
        // CAMPO BUSQUEDA
        // =====================================================

        txtBuscar =
            new JTextField();

        txtBuscar.setPreferredSize(
            new Dimension(
                0,
                42
            )
        );

        txtBuscar.setFont(
            new Font(
                "Segoe UI",
                Font.PLAIN,
                15
            )
        );

        txtBuscar.setBorder(
            BorderFactory.createLineBorder(
                new Color(
                    210,
                    215,
                    220
                )
            )
        );

        txtBuscar.setToolTipText(
            "Buscar producto por nombre o código"
        );

        gbc.gridx = 1;
        gbc.gridy = 1;

        gbc.weightx = 1;

        panel.add(
            txtBuscar,
            gbc
        );

        // =====================================================
        // BOTÓN BUSCAR
        // =====================================================

        JButton btnBuscar =
            crearBotonBuscar();

        gbc.gridx = 2;
        gbc.gridy = 1;

        gbc.weightx = 0;

        gbc.insets =
            new Insets(
                5,
                0,
                15,
                25
            );

        panel.add(
            btnBuscar,
            gbc
        );

        // =====================================================
        // CATEGORÍA
        // =====================================================

        JLabel lblCategoria =
            new JLabel(
                "Categoría:"
            );

        lblCategoria.setFont(
            new Font(
                "Segoe UI",
                Font.PLAIN,
                15
            )
        );

        gbc.gridx = 3;
        gbc.gridy = 1;

        gbc.insets =
            new Insets(
                5,
                10,
                15,
                10
            );

        panel.add(
            lblCategoria,
            gbc
        );

        comboCategoria =
            new JComboBox<>(
                new String[]{
                    "Todas las categorías",
                    "Bebidas",
                    "Botanas",
                    "Lácteos",
                    "Panadería",
                    "Abarrotes",
                    "Limpieza",
                    "Otros"
                }
            );

        comboCategoria.setPreferredSize(
            new Dimension(
                280,
                42
            )
        );

        comboCategoria.setFont(
            new Font(
                "Segoe UI",
                Font.PLAIN,
                15
            )
        );

        gbc.gridx = 4;
        gbc.gridy = 1;

        gbc.weightx = 0;

        gbc.insets =
            new Insets(
                5,
                0,
                15,
                5
            );

        panel.add(
            comboCategoria,
            gbc
        );

        // =====================================================
        // EVENTOS
        // =====================================================

        btnBuscar.addActionListener(
            e -> buscarProductos()
        );

        comboCategoria.addActionListener(
            e -> filtrarPorCategoria()
        );

        return panel;
    }

    // =========================================================
    // BOTON BUSCAR
    // =========================================================

    private JButton crearBotonBuscar() {

        FlatSVGIcon icono =
            new FlatSVGIcon(
                "images/search.svg",
                21,
                21
            );

        icono.setColorFilter(
            new FlatSVGIcon.ColorFilter(
                c -> Color.WHITE
            )
        );

        JButton boton =
            new JButton(
                "Buscar",
                icono
            );

        boton.setPreferredSize(
            new Dimension(
                135,
                42
            )
        );

        boton.setFont(
            new Font(
                "Segoe UI",
                Font.BOLD,
                15
            )
        );

        boton.setBackground(
            new Color(
                21,
                101,
                192
            )
        );

        boton.setForeground(
            Color.WHITE
        );

        boton.setFocusPainted(
            false
        );

        boton.setBorderPainted(
            false
        );

        boton.setCursor(
            new Cursor(
                Cursor.HAND_CURSOR
            )
        );

        return boton;
    }

    // =========================================================
    // PANEL LISTA
    // =========================================================

    private JPanel crearPanelLista() {

        JPanel panel =
            new JPanel(
                new BorderLayout(
                    0,
                    10
                )
            );

        panel.setBackground(
            Color.WHITE
        );

        panel.setBorder(
            BorderFactory.createEmptyBorder(
                15,
                0,
                0,
                0
            )
        );

        // =====================================================
        // TITULO
        // =====================================================

        JPanel panelTitulo =
            new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT,
                    0,
                    0
                )
            );

        panelTitulo.setBackground(
            Color.WHITE
        );

        JLabel numero =
            crearNumeroSeccion(
                "2"
            );

        JLabel titulo =
            new JLabel(
                "Lista de productos"
            );

        titulo.setFont(
            new Font(
                "Segoe UI",
                Font.BOLD,
                18
            )
        );

        panelTitulo.add(
            numero
        );

        panelTitulo.add(
            BoxFiller.horizontal(
                10
            )
        );

        panelTitulo.add(
            titulo
        );

        panel.add(
            panelTitulo,
            BorderLayout.NORTH
        );

        // =====================================================
        // TABLA
        // =====================================================

        crearTabla();

        JScrollPane scroll =
            new JScrollPane(
                tablaProductos
            );

        scroll.setBorder(
            BorderFactory.createLineBorder(
                new Color(
                    215,
                    220,
                    225
                )
            )
        );

        panel.add(
            scroll,
            BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // CREAR TABLA
    // =========================================================

    private void crearTabla() {

        modeloTabla =
            new DefaultTableModel(
                new Object[]{
                    "Código de producto",
                    "Nombre de producto",
                    "Cantidad de producto existente",
                    "Precio de costo",
                    "Precio de venta",
                    "Acciones"
                },
                0
            ) {

                @Override
                public boolean isCellEditable(
                    int fila,
                    int columna
                ) {
                    return false;
                }
            };

        tablaProductos =
            new JTable(
                modeloTabla
            );

        tablaProductos.setRowHeight(
            50
        );

        tablaProductos.setFont(
            new Font(
                "Segoe UI",
                Font.PLAIN,
                15
            )
        );

        tablaProductos.setSelectionBackground(
            new Color(
                230,
                240,
                252
            )
        );

        tablaProductos.setSelectionForeground(
            Color.BLACK
        );

        tablaProductos.setGridColor(
            new Color(
                225,
                225,
                225
            )
        );

        tablaProductos.setShowVerticalLines(
            true
        );

        tablaProductos.setShowHorizontalLines(
            true
        );

        // =====================================================
        // ENCABEZADO
        // =====================================================

        tablaProductos
            .getTableHeader()
            .setFont(
                new Font(
                    "Segoe UI",
                    Font.BOLD,
                    14
                )
            );

        tablaProductos
            .getTableHeader()
            .setPreferredSize(
                new Dimension(
                    0,
                    48
                )
            );

        // =====================================================
        // ALINEAR COLUMNAS
        // =====================================================

        DefaultTableCellRenderer centro =
            new DefaultTableCellRenderer();

        centro.setHorizontalAlignment(
            JLabel.CENTER
        );

        tablaProductos
            .getColumnModel()
            .getColumn(2)
            .setCellRenderer(
                centro
            );

        tablaProductos
            .getColumnModel()
            .getColumn(3)
            .setCellRenderer(
                centro
            );

        tablaProductos
            .getColumnModel()
            .getColumn(4)
            .setCellRenderer(
                centro
            );

        // =====================================================
        // ANCHOS
        // =====================================================

        tablaProductos
            .getColumnModel()
            .getColumn(0)
            .setPreferredWidth(
                170
            );

        tablaProductos
            .getColumnModel()
            .getColumn(1)
            .setPreferredWidth(
                330
            );

        tablaProductos
            .getColumnModel()
            .getColumn(2)
            .setPreferredWidth(
                250
            );

        tablaProductos
            .getColumnModel()
            .getColumn(3)
            .setPreferredWidth(
                150
            );

        tablaProductos
            .getColumnModel()
            .getColumn(4)
            .setPreferredWidth(
                150
            );

        tablaProductos
            .getColumnModel()
            .getColumn(5)
            .setPreferredWidth(
                150
            );

        // =====================================================
        // DATOS DE PRUEBA
        // =====================================================

        agregarProducto(
            "7501234567890",
            "Refresco Coca Cola 600 ml",
            120,
            "$10.00",
            "$18.00"
        );

        agregarProducto(
            "7501000654321",
            "Agua Ciel 600 ml",
            85,
            "$6.00",
            "$12.00"
        );

        agregarProducto(
            "7501025401237",
            "Sabritas Original 170 g",
            60,
            "$16.00",
            "$28.00"
        );

        agregarProducto(
            "7501055300880",
            "Galletas Marías 170 g",
            100,
            "$8.50",
            "$15.00"
        );

        agregarProducto(
            "7501011121314",
            "Jugo del Valle 500 ml",
            75,
            "$9.00",
            "$16.00"
        );

        agregarProducto(
            "7501031314151",
            "Pan Bimbo Blanco 680 g",
            50,
            "$20.00",
            "$32.00"
        );

        agregarProducto(
            "7501041617185",
            "Leche Lala Entera 1 L",
            40,
            "$14.00",
            "$22.00"
        );

        agregarProducto(
            "7501071920218",
            "Aceite Nutrioli 900 ml",
            35,
            "$28.00",
            "$45.00"
        );

        agregarProducto(
            "7501082223242",
            "Arroz Verde Valle 1 kg",
            80,
            "$18.00",
            "$29.00"
        );

        agregarProducto(
            "7501092526276",
            "Azúcar Zulka 1 kg",
            70,
            "$16.50",
            "$26.00"
        );
    }

    // =========================================================
    // AGREGAR PRODUCTO A TABLA
    // =========================================================

    private void agregarProducto(
        String codigo,
        String nombre,
        int existencia,
        String precioCosto,
        String precioVenta
    ) {

        modeloTabla.addRow(
            new Object[]{
                codigo,
                nombre,
                existencia,
                precioCosto,
                precioVenta,
                "Modificar"
            }
        );
    }

    // =========================================================
    // NUMERO DE SECCION
    // =========================================================

    private JLabel crearNumeroSeccion(
        String numero
    ) {

        JLabel label =
            new JLabel(
                numero,
                JLabel.CENTER
            );

        label.setPreferredSize(
            new Dimension(
                30,
                30
            )
        );

        label.setOpaque(
            true
        );

        label.setBackground(
            new Color(
                21,
                101,
                192
            )
        );

        label.setForeground(
            Color.WHITE
        );

        label.setFont(
            new Font(
                "Segoe UI",
                Font.BOLD,
                15
            )
        );

        return label;
    }

    // =========================================================
    // BUSCAR PRODUCTOS
    // =========================================================

    private void buscarProductos() {

        String texto =
            txtBuscar
                .getText()
                .trim()
                .toLowerCase();

        if (texto.isEmpty()) {

            cargarProductosEjemplo();

            return;
        }

        for (
            int i =
                modeloTabla.getRowCount() - 1;
            i >= 0;
            i--
        ) {

            String codigo =
                modeloTabla
                    .getValueAt(i, 0)
                    .toString()
                    .toLowerCase();

            String nombre =
                modeloTabla
                    .getValueAt(i, 1)
                    .toString()
                    .toLowerCase();

            if (
                !codigo.contains(texto)
                &&
                !nombre.contains(texto)
            ) {

                modeloTabla.removeRow(i);
            }
        }
    }

    // =========================================================
    // FILTRAR POR CATEGORIA
    // =========================================================

    private void filtrarPorCategoria() {

        /*
         * Por ahora las categorías son solamente
         * parte visual.
         *
         * Cuando conectemos SQLite,
         * aquí realizaremos la consulta
         * correspondiente.
         */
    }

    // =========================================================
    // RECARGAR DATOS DE EJEMPLO
    // =========================================================

    private void cargarProductosEjemplo() {

        modeloTabla.setRowCount(
            0
        );

        agregarProducto(
            "7501234567890",
            "Refresco Coca Cola 600 ml",
            120,
            "$10.00",
            "$18.00"
        );

        agregarProducto(
            "7501000654321",
            "Agua Ciel 600 ml",
            85,
            "$6.00",
            "$12.00"
        );

        agregarProducto(
            "7501025401237",
            "Sabritas Original 170 g",
            60,
            "$16.00",
            "$28.00"
        );

        agregarProducto(
            "7501055300880",
            "Galletas Marías 170 g",
            100,
            "$8.50",
            "$15.00"
        );

        agregarProducto(
            "7501011121314",
            "Jugo del Valle 500 ml",
            75,
            "$9.00",
            "$16.00"
        );

        agregarProducto(
            "7501031314151",
            "Pan Bimbo Blanco 680 g",
            50,
            "$20.00",
            "$32.00"
        );

        agregarProducto(
            "7501041617185",
            "Leche Lala Entera 1 L",
            40,
            "$14.00",
            "$22.00"
        );

        agregarProducto(
            "7501071920218",
            "Aceite Nutrioli 900 ml",
            35,
            "$28.00",
            "$45.00"
        );

        agregarProducto(
            "7501082223242",
            "Arroz Verde Valle 1 kg",
            80,
            "$18.00",
            "$29.00"
        );

        agregarProducto(
            "7501092526276",
            "Azúcar Zulka 1 kg",
            70,
            "$16.50",
            "$26.00"
        );
    }

    // =========================================================
    // CLASE AUXILIAR PARA ESPACIO HORIZONTAL
    // =========================================================

    private static class BoxFiller {

        public static Component horizontal(
            int ancho
        ) {

            return javax.swing.Box.createRigidArea(
                new Dimension(
                    ancho,
                    0
                )
            );
        }
    }
}