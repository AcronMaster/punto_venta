package com.dulceria.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.dulceria.model.Usuario;
import com.dulceria.view.componets.BarraInferiorView;
import com.dulceria.view.componets.BarraSuperiorView;
import com.dulceria.view.componets.MenuPrincipalView;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class ProductosView extends JFrame {

    private Usuario usuario;

    private CardLayout cardLayout;
    private JPanel panelContenido;

    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnCategorias;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ProductosView(Usuario usuario) {

        this.usuario = usuario;

        configurarVentana();
        inicializarComponentes();
    }

    // =========================================================
    // CONFIGURAR VENTANA
    // =========================================================

    private void configurarVentana() {

        setTitle("Productos");

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

        JPanel superior =
            new JPanel(
                new BorderLayout()
            );

        BarraSuperiorView barraSuperior =
            new BarraSuperiorView(usuario);

        MenuPrincipalView menu =
            new MenuPrincipalView();

        menu.seleccionarProductos();

        superior.add(
            barraSuperior,
            BorderLayout.NORTH
        );

        superior.add(
            menu,
            BorderLayout.CENTER
        );

        // =====================================================
        // NAVEGACIÓN DEL MENÚ
        // =====================================================

        /*
         * Por ahora solamente dejamos preparado
         * el botón de Ventas.
         *
         * Cuando tengamos todas las Views,
         * conectaremos aquí:
         *
         * Inventario
         * Corte
         */

        menu.getBtnVentas()
            .addActionListener(e -> {

                PrincipalView ventas =
                    new PrincipalView(usuario);

                ventas.setVisible(true);

                dispose();
            });

        // =====================================================
        // PARTE CENTRAL
        // =====================================================

        JPanel centro =
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
            superior,
            BorderLayout.NORTH
        );

        panelPrincipal.add(
            centro,
            BorderLayout.CENTER
        );

        panelPrincipal.add(
            barraInferior,
            BorderLayout.SOUTH
        );

        add(panelPrincipal);
    }

    // =========================================================
    // PANEL CENTRAL
    // =========================================================

    private JPanel crearPanelCentral() {

        JPanel panel =
            new JPanel(
                new BorderLayout(
                    0,
                    15
                )
            );

        panel.setBackground(
            Color.WHITE
        );

        panel.setBorder(
            BorderFactory.createEmptyBorder(
                15,
                20,
                15,
                20
            )
        );

        // =====================================================
        // BOTONES DE SECCIÓN
        // =====================================================

        JPanel panelBotones =
            new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT,
                    10,
                    0
                )
            );

        panelBotones.setBackground(
            Color.WHITE
        );

        btnAgregar =
            crearBotonSeccion(
                "Agregar producto",
                "images/more.svg"
            );

        btnModificar =
            crearBotonSeccion(
                "Modificar producto",
                "images/box.svg"
            );

        btnEliminar =
            crearBotonSeccion(
                "Eliminar producto",
                "images/delete.svg"
            );

        btnCategorias =
            crearBotonSeccion(
                "Categorías",
                "images/list.svg"
            );

        panelBotones.add(
            btnAgregar
        );

        panelBotones.add(
            btnModificar
        );

        panelBotones.add(
            btnEliminar
        );

        panelBotones.add(
            btnCategorias
        );

        // =====================================================
        // CARDLAYOUT
        // =====================================================

        cardLayout =
            new CardLayout();

        panelContenido =
            new JPanel(
                cardLayout
            );

        panelContenido.setBackground(
            Color.WHITE
        );

        // =====================================================
        // AGREGAR LAS TARJETAS
        // =====================================================

        panelContenido.add(
            crearPanelAgregar(),
            "AGREGAR"
        );

        panelContenido.add(
            crearPanelModificar(),
            "MODIFICAR"
        );

        panelContenido.add(
            crearPanelEliminar(),
            "ELIMINAR"
        );

        panelContenido.add(
            crearPanelCategorias(),
            "CATEGORIAS"
        );

        // =====================================================
        // EVENTOS
        // =====================================================

        btnAgregar.addActionListener(e ->
            mostrarSeccion("AGREGAR")
        );

        btnModificar.addActionListener(e ->
            mostrarSeccion("MODIFICAR")
        );

        btnEliminar.addActionListener(e ->
            mostrarSeccion("ELIMINAR")
        );

        btnCategorias.addActionListener(e ->
            mostrarSeccion("CATEGORIAS")
        );

        // =====================================================
        // AGREGAR AL PANEL
        // =====================================================

        panel.add(
            panelBotones,
            BorderLayout.NORTH
        );

        panel.add(
            panelContenido,
            BorderLayout.CENTER
        );

        // Iniciar mostrando Agregar
        mostrarSeccion("AGREGAR");

        return panel;
    }

    // =========================================================
    // BOTÓN DE SECCIÓN
    // =========================================================

    private JButton crearBotonSeccion(
        String texto,
        String rutaIcono
    ) {

        FlatSVGIcon icono =
            new FlatSVGIcon(
                rutaIcono,
                22,
                22
            );

        icono.setColorFilter(
            new FlatSVGIcon.ColorFilter(
                c -> new Color(21, 101, 192)
            )
        );

        JButton boton =
            new JButton(
                texto,
                icono
            );

        boton.setPreferredSize(
            new Dimension(
                210,
                48
            )
        );

        boton.setFont(
            new Font(
                "Segoe UI",
                Font.BOLD,
                15
            )
        );

        boton.setFocusPainted(
            false
        );

        boton.setBackground(
            Color.WHITE
        );

        boton.setForeground(
            new Color(21, 101, 192)
        );

        boton.setBorder(
            BorderFactory.createLineBorder(
                new Color(21, 101, 192)
            )
        );

        boton.setCursor(
            new Cursor(
                Cursor.HAND_CURSOR
            )
        );

        return boton;
    }

    // =========================================================
    // CAMBIAR SECCIÓN
    // =========================================================

    private void mostrarSeccion(
        String nombre
    ) {

        cardLayout.show(
            panelContenido,
            nombre
        );
    }

    // =========================================================
    // PANEL AGREGAR PRODUCTO
    // =========================================================

    private JPanel crearPanelAgregar() {

        JPanel panel =
            crearPanelBase();

        JLabel titulo =
            crearTitulo(
                "Agregar producto"
            );

        panel.add(
            titulo,
            BorderLayout.NORTH
        );

        JPanel formulario =
            new JPanel(
                new GridBagLayout()
            );

        formulario.setBackground(
            Color.WHITE
        );

        GridBagConstraints gbc =
            crearConstraints();

        // -----------------------------------------------------
        // CÓDIGO
        // -----------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 0;

        formulario.add(
            crearCampoFormulario(
                "Código de barras",
                crearTextField(
                    "Ej. 7501234567890"
                )
            ),
            gbc
        );

        // -----------------------------------------------------
        // CATEGORÍA
        // -----------------------------------------------------

        JComboBox<String> comboCategoria =
            crearComboCategorias();

        gbc.gridx = 1;
        gbc.gridy = 0;

        formulario.add(
            crearCampoFormulario(
                "Categoría",
                comboCategoria
            ),
            gbc
        );

        // -----------------------------------------------------
        // NOMBRE
        // -----------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 1;

        formulario.add(
            crearCampoFormulario(
                "Nombre del producto",
                crearTextField(
                    "Ej. Refresco Coca Cola 600 ml"
                )
            ),
            gbc
        );

        // -----------------------------------------------------
        // PRECIO COSTO
        // -----------------------------------------------------

        gbc.gridx = 1;
        gbc.gridy = 1;

        formulario.add(
            crearCampoFormulario(
                "Precio de costo",
                crearTextField(
                    "$ 0.00"
                )
            ),
            gbc
        );

        // -----------------------------------------------------
        // PORCENTAJE
        // -----------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 2;

        formulario.add(
            crearCampoFormulario(
                "Porcentaje de venta (%)",
                crearTextField(
                    "0 %"
                )
            ),
            gbc
        );

        // -----------------------------------------------------
        // PRECIO VENTA
        // -----------------------------------------------------

        gbc.gridx = 1;
        gbc.gridy = 2;

        formulario.add(
            crearCampoFormulario(
                "Precio de venta",
                crearTextField(
                    "$ 0.00"
                )
            ),
            gbc
        );

        // -----------------------------------------------------
        // PRECIO MAYOREO
        // -----------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 3;

        formulario.add(
            crearCampoFormulario(
                "Precio mayoreo",
                crearTextField(
                    "$ 0.00"
                )
            ),
            gbc
        );

        // -----------------------------------------------------
        // CANTIDAD
        // -----------------------------------------------------

        gbc.gridx = 1;
        gbc.gridy = 3;

        formulario.add(
            crearCampoFormulario(
                "Cantidad de producto",
                crearTextField(
                    "0"
                )
            ),
            gbc
        );

        panel.add(
            formulario,
            BorderLayout.CENTER
        );

        // =====================================================
        // BOTÓN GUARDAR
        // =====================================================

        JPanel panelBoton =
            new JPanel(
                new FlowLayout(
                    FlowLayout.RIGHT
                )
            );

        panelBoton.setBackground(
            Color.WHITE
        );

        JButton btnGuardar =
            crearBotonPrincipal(
                "Guardar",
                "images/check.svg"
            );

        panelBoton.add(
            btnGuardar
        );

        panel.add(
            panelBoton,
            BorderLayout.SOUTH
        );

        return panel;
    }

    // =========================================================
    // PANEL MODIFICAR PRODUCTO
    // =========================================================

    private JPanel crearPanelModificar() {

        JPanel panel =
            crearPanelBase();

        JLabel titulo =
            crearTitulo(
                "Modificar producto"
            );

        panel.add(
            titulo,
            BorderLayout.NORTH
        );

        JPanel formulario =
            new JPanel(
                new GridBagLayout()
            );

        formulario.setBackground(
            Color.WHITE
        );

        GridBagConstraints gbc =
            crearConstraints();

        // =====================================================
        // BUSCAR PRODUCTO
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        formulario.add(
            crearCampoFormulario(
                "Buscar producto",
                crearTextField(
                    "Buscar por nombre o código..."
                )
            ),
            gbc
        );

        // =====================================================
        // CÓDIGO
        // =====================================================

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;

        formulario.add(
            crearCampoFormulario(
                "Código de barras",
                crearTextField(
                    "Ej. 7501234567890"
                )
            ),
            gbc
        );

        // =====================================================
        // CATEGORÍA
        // =====================================================

        gbc.gridx = 1;
        gbc.gridy = 1;

        formulario.add(
            crearCampoFormulario(
                "Categoría",
                crearComboCategorias()
            ),
            gbc
        );

        // =====================================================
        // NOMBRE
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 2;

        formulario.add(
            crearCampoFormulario(
                "Nombre del producto",
                crearTextField(
                    "Ej. Refresco Coca Cola 600 ml"
                )
            ),
            gbc
        );

        // =====================================================
        // PRECIO COSTO
        // =====================================================

        gbc.gridx = 1;
        gbc.gridy = 2;

        formulario.add(
            crearCampoFormulario(
                "Precio de costo",
                crearTextField(
                    "$ 0.00"
                )
            ),
            gbc
        );

        // =====================================================
        // PORCENTAJE
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 3;

        formulario.add(
            crearCampoFormulario(
                "Porcentaje de venta (%)",
                crearTextField(
                    "0 %"
                )
            ),
            gbc
        );

        // =====================================================
        // PRECIO VENTA
        // =====================================================

        gbc.gridx = 1;
        gbc.gridy = 3;

        formulario.add(
            crearCampoFormulario(
                "Precio de venta",
                crearTextField(
                    "$ 0.00"
                )
            ),
            gbc
        );

        // =====================================================
        // PRECIO MAYOREO
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 4;

        formulario.add(
            crearCampoFormulario(
                "Precio mayoreo",
                crearTextField(
                    "$ 0.00"
                )
            ),
            gbc
        );

        // =====================================================
        // CANTIDAD
        // =====================================================

        gbc.gridx = 1;
        gbc.gridy = 4;

        formulario.add(
            crearCampoFormulario(
                "Cantidad de producto",
                crearTextField(
                    "0"
                )
            ),
            gbc
        );

        panel.add(
            formulario,
            BorderLayout.CENTER
        );

        // =====================================================
        // BOTÓN GUARDAR CAMBIOS
        // =====================================================

        JPanel panelBoton =
            new JPanel(
                new FlowLayout(
                    FlowLayout.RIGHT
                )
            );

        panelBoton.setBackground(
            Color.WHITE
        );

        JButton btnGuardar =
            crearBotonPrincipal(
                "Guardar cambios",
                "images/check.svg"
            );

        panelBoton.add(
            btnGuardar
        );

        panel.add(
            panelBoton,
            BorderLayout.SOUTH
        );

        return panel;
    }

    // =========================================================
    // PANEL ELIMINAR PRODUCTO
    // =========================================================

    private JPanel crearPanelEliminar() {

        JPanel panel =
            crearPanelBase();

        JLabel titulo =
            crearTitulo(
                "Eliminar producto"
            );

        panel.add(
            titulo,
            BorderLayout.NORTH
        );

        JPanel contenido =
            new JPanel(
                new BorderLayout(
                    10,
                    10
                )
            );

        contenido.setBackground(
            Color.WHITE
        );

        // =====================================================
        // BUSCADOR
        // =====================================================

        JPanel panelBusqueda =
            new JPanel(
                new BorderLayout(
                    10,
                    0
                )
            );

        panelBusqueda.setBackground(
            Color.WHITE
        );

        JLabel lblBuscar =
            new JLabel(
                "Buscar producto"
            );

        lblBuscar.setFont(
            new Font(
                "Segoe UI",
                Font.BOLD,
                15
            )
        );

        JTextField txtBuscar =
            crearTextField(
                "Buscar por nombre o código..."
            );

        panelBusqueda.add(
            lblBuscar,
            BorderLayout.WEST
        );

        panelBusqueda.add(
            txtBuscar,
            BorderLayout.CENTER
        );

        contenido.add(
            panelBusqueda,
            BorderLayout.NORTH
        );

        // =====================================================
        // TABLA
        // =====================================================

        DefaultTableModel modelo =
            new DefaultTableModel(
                new Object[]{
                    "Código",
                    "Nombre de producto",
                    "Categoría",
                    "Precio",
                    "Existencia"
                },
                0
            );

        JTable tabla =
            new JTable(modelo);

        tabla.setRowHeight(
            40
        );

        tabla.setFont(
            new Font(
                "Segoe UI",
                Font.PLAIN,
                15
            )
        );

        tabla.getTableHeader().setFont(
            new Font(
                "Segoe UI",
                Font.BOLD,
                15
            )
        );

        tabla.setSelectionBackground(
            new Color(
                220,
                235,
                250
            )
        );

        tabla.setSelectionForeground(
            Color.BLACK
        );

        // =====================================================
        // DATOS DE EJEMPLO
        // =====================================================

        modelo.addRow(
            new Object[]{
                "7501234567890",
                "Refresco Coca Cola 600 ml",
                "Bebidas",
                "$18.00",
                25
            }
        );

        modelo.addRow(
            new Object[]{
                "7501000654321",
                "Agua Ciel 600 ml",
                "Bebidas",
                "$12.00",
                18
            }
        );

        modelo.addRow(
            new Object[]{
                "7501025401237",
                "Sabritas Original 170 g",
                "Botanas",
                "$25.00",
                12
            }
        );

        modelo.addRow(
            new Object[]{
                "7501055300880",
                "Galletas Marías 170 g",
                "Abarrotes",
                "$20.00",
                8
            }
        );

        JScrollPane scroll =
            new JScrollPane(
                tabla
            );

        scroll.setBorder(
            BorderFactory.createLineBorder(
                new Color(
                    220,
                    220,
                    220
                )
            )
        );

        contenido.add(
            scroll,
            BorderLayout.CENTER
        );

        // =====================================================
        // BOTÓN ELIMINAR
        // =====================================================

        JPanel panelBoton =
            new JPanel(
                new FlowLayout(
                    FlowLayout.RIGHT
                )
            );

        panelBoton.setBackground(
            Color.WHITE
        );

        JButton btnEliminarProducto =
            new JButton(
                "Eliminar producto seleccionado",
                new FlatSVGIcon(
                    "images/delete.svg",
                    22,
                    22
                )
            );

        btnEliminarProducto.setPreferredSize(
            new Dimension(
                320,
                48
            )
        );

        btnEliminarProducto.setFont(
            new Font(
                "Segoe UI",
                Font.BOLD,
                15
            )
        );

        btnEliminarProducto.setForeground(
            new Color(
                200,
                40,
                40
            )
        );

        btnEliminarProducto.setBackground(
            Color.WHITE
        );

        btnEliminarProducto.setFocusPainted(
            false
        );

        btnEliminarProducto.setBorder(
            BorderFactory.createLineBorder(
                new Color(
                    200,
                    40,
                    40
                )
            )
        );

        panelBoton.add(
            btnEliminarProducto
        );

        contenido.add(
            panelBoton,
            BorderLayout.SOUTH
        );

        panel.add(
            contenido,
            BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // PANEL CATEGORÍAS
    // =========================================================

    private JPanel crearPanelCategorias() {

        JPanel panel =
            crearPanelBase();

        JLabel titulo =
            crearTitulo(
                "Categorías"
            );

        panel.add(
            titulo,
            BorderLayout.NORTH
        );

        JPanel contenido =
            new JPanel(
                new BorderLayout(
                    15,
                    15
                )
            );

        contenido.setBackground(
            Color.WHITE
        );

        // =====================================================
        // BUSCAR CATEGORÍA
        // =====================================================

        JPanel panelBusqueda =
            new JPanel(
                new BorderLayout(
                    10,
                    0
                )
            );

        panelBusqueda.setBackground(
            Color.WHITE
        );

        JLabel lblBuscar =
            new JLabel(
                "Buscar categoría"
            );

        lblBuscar.setFont(
            new Font(
                "Segoe UI",
                Font.BOLD,
                15
            )
        );

        JTextField txtBuscar =
            crearTextField(
                "Buscar categoría..."
            );

        panelBusqueda.add(
            lblBuscar,
            BorderLayout.WEST
        );

        panelBusqueda.add(
            txtBuscar,
            BorderLayout.CENTER
        );

        contenido.add(
            panelBusqueda,
            BorderLayout.NORTH
        );

        // =====================================================
        // LISTA DE CATEGORÍAS
        // =====================================================

        JPanel lista =
            new JPanel();

        lista.setLayout(
            new BoxLayout(
                lista,
                BoxLayout.Y_AXIS
            )
        );

        lista.setBackground(
            Color.WHITE
        );

        String[] categorias = {
            "Bebidas",
            "Botanas",
            "Lácteos",
            "Panadería",
            "Abarrotes",
            "Limpieza",
            "Otros"
        };

        for (
            String categoria :
            categorias
        ) {

            JPanel fila =
                new JPanel(
                    new BorderLayout()
                );

            fila.setBackground(
                Color.WHITE
            );

            fila.setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                        new Color(
                            220,
                            220,
                            220
                        )
                    ),
                    BorderFactory.createEmptyBorder(
                        10,
                        15,
                        10,
                        15
                    )
                )
            );

            fila.setMaximumSize(
                new Dimension(
                    Integer.MAX_VALUE,
                    55
                )
            );

            // -------------------------------------------------
            // ICONO
            // -------------------------------------------------

            FlatSVGIcon icono =
                new FlatSVGIcon(
                    "images/box.svg",
                    22,
                    22
                );

            icono.setColorFilter(
                new FlatSVGIcon.ColorFilter(
                    c -> new Color(
                        21,
                        101,
                        192
                    )
                )
            );

            JLabel lblCategoria =
                new JLabel(
                    categoria,
                    icono,
                    JLabel.LEFT
                );

            lblCategoria.setFont(
                new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    16
                )
            );

            fila.add(
                lblCategoria,
                BorderLayout.WEST
            );

            // -------------------------------------------------
            // BOTONES
            // -------------------------------------------------

            JPanel botones =
                new JPanel(
                    new FlowLayout(
                        FlowLayout.RIGHT,
                        5,
                        0
                    )
                );

            botones.setOpaque(
                false
            );

            JButton btnModificar =
                new JButton(
                    "Modificar"
                );

            JButton btnEliminar =
                new JButton(
                    "Eliminar"
                );

            btnModificar.setFocusPainted(
                false
            );

            btnEliminar.setFocusPainted(
                false
            );

            btnModificar.setForeground(
                new Color(
                    21,
                    101,
                    192
                )
            );

            btnEliminar.setForeground(
                new Color(
                    200,
                    40,
                    40
                )
            );

            botones.add(
                btnModificar
            );

            botones.add(
                btnEliminar
            );

            fila.add(
                botones,
                BorderLayout.EAST
            );

            lista.add(
                fila
            );

            lista.add(
                Box.createVerticalStrut(
                    5
                )
            );
        }

        JScrollPane scroll =
            new JScrollPane(
                lista
            );

        scroll.setBorder(
            BorderFactory.createLineBorder(
                new Color(
                    220,
                    220,
                    220
                )
            )
        );

        contenido.add(
            scroll,
            BorderLayout.CENTER
        );

        // =====================================================
        // AGREGAR CATEGORÍA
        // =====================================================

        JPanel panelBoton =
            new JPanel(
                new FlowLayout(
                    FlowLayout.RIGHT
                )
            );

        panelBoton.setBackground(
            Color.WHITE
        );

        JButton btnAgregarCategoria =
            crearBotonPrincipal(
                "Agregar categoría",
                "images/more.svg"
            );

        panelBoton.add(
            btnAgregarCategoria
        );

        contenido.add(
            panelBoton,
            BorderLayout.SOUTH
        );

        panel.add(
            contenido,
            BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // PANEL BASE
    // =========================================================

    private JPanel crearPanelBase() {

        JPanel panel =
            new JPanel(
                new BorderLayout(
                    15,
                    15
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
                    25,
                    20,
                    25
                )
            )
        );

        return panel;
    }

    // =========================================================
    // TÍTULO
    // =========================================================

    private JLabel crearTitulo(
        String texto
    ) {

        JLabel titulo =
            new JLabel(
                texto
            );

        titulo.setFont(
            new Font(
                "Segoe UI",
                Font.BOLD,
                24
            )
        );

        titulo.setForeground(
            new Color(
                21,
                101,
                192
            )
        );

        return titulo;
    }

    // =========================================================
    // TEXTFIELD
    // =========================================================

    private JTextField crearTextField(
        String texto
    ) {

        JTextField campo =
            new JTextField();

        campo.setPreferredSize(
            new Dimension(
                0,
                42
            )
        );

        campo.setFont(
            new Font(
                "Segoe UI",
                Font.PLAIN,
                16
            )
        );

        campo.setBorder(
            BorderFactory.createLineBorder(
                new Color(
                    210,
                    210,
                    210
                )
            )
        );

        campo.setToolTipText(
            texto
        );

        return campo;
    }

    // =========================================================
    // COMBOBOX DE CATEGORÍAS
    // =========================================================

    private JComboBox<String> crearComboCategorias() {

        JComboBox<String> combo =
            new JComboBox<>(
                new String[]{
                    "Seleccionar categoría",
                    "Bebidas",
                    "Botanas",
                    "Lácteos",
                    "Panadería",
                    "Abarrotes",
                    "Limpieza",
                    "Otros"
                }
            );

        combo.setPreferredSize(
            new Dimension(
                0,
                42
            )
        );

        combo.setFont(
            new Font(
                "Segoe UI",
                Font.PLAIN,
                16
            )
        );

        return combo;
    }

    // =========================================================
    // CONSTRAINTS DEL FORMULARIO
    // =========================================================

    private GridBagConstraints crearConstraints() {

        GridBagConstraints gbc =
            new GridBagConstraints();

        gbc.weightx = 1.0;

        gbc.fill =
            GridBagConstraints.HORIZONTAL;

        gbc.insets =
            new Insets(
                6,
                8,
                6,
                8
            );

        return gbc;
    }

    // =========================================================
    // CAMPO DEL FORMULARIO
    // =========================================================

    private JPanel crearCampoFormulario(
        String texto,
        Component componente
    ) {

        JPanel panel =
            new JPanel(
                new BorderLayout(
                    0,
                    5
                )
            );

        panel.setBackground(
            Color.WHITE
        );

        JLabel etiqueta =
            new JLabel(
                texto
            );

        etiqueta.setFont(
            new Font(
                "Segoe UI",
                Font.BOLD,
                15
            )
        );

        panel.add(
            etiqueta,
            BorderLayout.NORTH
        );

        panel.add(
            componente,
            BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // BOTÓN PRINCIPAL
    // =========================================================

    private JButton crearBotonPrincipal(
        String texto,
        String rutaIcono
    ) {

        FlatSVGIcon icono =
            new FlatSVGIcon(
                rutaIcono,
                22,
                22
            );

        icono.setColorFilter(
            new FlatSVGIcon.ColorFilter(
                c -> Color.WHITE
            )
        );

        JButton boton =
            new JButton(
                texto,
                icono
            );

        boton.setPreferredSize(
            new Dimension(
                280,
                50
            )
        );

        boton.setFont(
            new Font(
                "Segoe UI",
                Font.BOLD,
                16
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
}