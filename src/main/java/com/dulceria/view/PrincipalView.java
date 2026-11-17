package com.dulceria.view;
//Importaciones de librerias
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.dulceria.config.ConexionDB;
import com.dulceria.model.Usuario;
import com.dulceria.view.componets.BarraInferiorView;
import com.dulceria.view.componets.BarraSuperiorView;
import com.dulceria.view.componets.MenuPrincipalView;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class PrincipalView extends JFrame {

    private Usuario usuario;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JTextField txtCodigo;

    private JLabel valorSubtotal;
    private JLabel valorCantidad;
    //Constructor de la clase
    public PrincipalView(Usuario usuario) {

        this.usuario = usuario;

        configurarVentana();

        inicializarComponentes();

    }
    //Metodo para configurar la ventana
    private void configurarVentana() {

        setTitle("Punto de Venta");
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    //Metodo para inicializar los componentes de la ventana
    private void inicializarComponentes() {
        //Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);
        //Panel superior
        JPanel superior = new JPanel(new BorderLayout());
        //Agregar barra superior y menu al panel superior
        superior.add(new BarraSuperiorView(usuario), BorderLayout.NORTH);
        MenuPrincipalView menu = new MenuPrincipalView();
        superior.add(menu, BorderLayout.CENTER);
        menu.getBtnProductos().addActionListener( e ->{
            ProductosView productos = new ProductosView(usuario);
            productos.setVisible(true);

            dispose();
        });

        //Panel central
        JPanel centro = crearPanelCentral();

        //Panel inferior
        JPanel inferior = new BarraInferiorView();

        panelPrincipal.add(superior,BorderLayout.NORTH);
        panelPrincipal.add(centro,BorderLayout.CENTER);
        panelPrincipal.add(inferior,BorderLayout.SOUTH);

        add(panelPrincipal);

    }

    //Metodo para crear el panel central
    private JPanel crearPanelCentral() {

        JPanel panel = new JPanel(new BorderLayout(15,0));
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        panel.add(crearPanelProducto(), BorderLayout.CENTER);

        panel.add(crearPanelCobro(), BorderLayout.EAST);



        return panel;
    }

    //Metodo para crear el panel de productos
    private JPanel crearPanelProducto(){
        JPanel panel = new JPanel(new BorderLayout());

        // El modelo se crea UNA sola vez aquí, con las 7 columnas reales
        // (antes se volvía a crear en cada agregarProductoTabla(), dejando
        // la tabla mostrando un modelo distinto al que usaba la lógica).
        modeloTabla = new DefaultTableModel(
            new Object[]{
                "ID",
                "Código",
                "Producto",
                "Precio",
                "Cantidad",
                "Importe",
                "Existencia"
            }, 0
        ){
            @Override
            public boolean isCellEditable(int fila, int columna){
                return columna == 4; // Solo la columna Cantidad es editable
            }
        };

        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setRowHeight(35);
        tablaProductos.setFont(new Font("Segoe UI",Font.PLAIN,16));
        tablaProductos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD,16));
        tablaProductos.getTableHeader().setBackground(new Color(240,240,240));
        tablaProductos.setSelectionBackground(new Color(220,235,250));
        tablaProductos.setSelectionForeground(Color.BLACK);

        // Ocultar columna ID (índice 0)
        tablaProductos.getColumnModel().getColumn(0).setMinWidth(0);
        tablaProductos.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(0);

        // Ocultar columna Existencia (índice 6)
        tablaProductos.getColumnModel().getColumn(6).setMinWidth(0);
        tablaProductos.getColumnModel().getColumn(6).setMaxWidth(0);
        tablaProductos.getColumnModel().getColumn(6).setPreferredWidth(0);

        // Listener para cuando el usuario edita manualmente la Cantidad
        tablaProductos.getModel().addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int fila = e.getFirstRow();
                int columna = e.getColumn();

                if (columna == 4 && fila >= 0) {
                    Object valor = modeloTabla.getValueAt(fila, columna);
                    try {
                        int nuevaCantidad = Integer.parseInt(valor.toString());

                        modificarCantidad(fila, nuevaCantidad);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Ingrese un número válido.", "Cantidad inválida", JOptionPane.ERROR_MESSAGE);

                        modeloTabla.setValueAt(1, fila, 4);
                    }
                }
            }
        });

        tablaProductos.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JTextField()));

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));

        panel.add(scrollPane,BorderLayout.CENTER);
        panel.add(crearPanelProductoSuperior(), BorderLayout.NORTH);
        panel.add(crearPanelBotonesInferiores(),BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelProductoSuperior() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(80, 100));

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        izquierda.setOpaque(false);

        JLabel lblCodigo = new JLabel("Codigo de barras");
        lblCodigo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblCodigo.setForeground(new Color(21,101,192));

        txtCodigo = new JTextField();
        txtCodigo.addActionListener(e -> buscarYAgregarProducto());
        txtCodigo.setPreferredSize(new Dimension(450, 40));
        txtCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtCodigo.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));

        JButton btnEnter = new JButton("Enter");
        btnEnter.addActionListener(e -> buscarYAgregarProducto());
        btnEnter.setFocusPainted(false);
        btnEnter.setBackground(new Color(21,101,192));
        btnEnter.setForeground(Color.white);
        btnEnter.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btnEnter.setBorderPainted(false);
        btnEnter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEnter.setPreferredSize(new Dimension(140,40));

        izquierda.add(lblCodigo);
        izquierda.add(Box.createVerticalStrut(5)); // Espacio entre el label y el textfield
        izquierda.add(txtCodigo);
        izquierda.add(Box.createHorizontalStrut(10)); // Espacio entre el textfield y el boton
        izquierda.add(btnEnter);


        panel.add(izquierda,BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(10), BorderLayout.CENTER); // Espacio entre el panel superior y los botones
        panel.add(crearBotonesProducto(), BorderLayout.SOUTH);

        return panel;
    }

   private void buscarYAgregarProducto() {

        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
        return;
        }

        String sql = """
            SELECT id_producto, codigo, nombre, precio_venta, existencia
            FROM productos
            WHERE codigo = ?
            """;

        try (
            Connection conexion = ConexionDB.conectar();
            PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            sentencia.setString(1, codigo);

            try (ResultSet resultado = sentencia.executeQuery()) {

                if (resultado.next()) {

                    int idProducto = resultado.getInt("id_producto");
                    String codigoProducto = resultado.getString("codigo");
                    String nombre = resultado.getString("nombre");
                    double precio = resultado.getDouble("precio_venta");
                    int existencia = resultado.getInt("existencia");

                    agregarProductoTabla(
                        idProducto,
                        codigoProducto,
                        nombre,
                        precio,
                        existencia
                    );

                } else {

                    JOptionPane.showMessageDialog(
                        this,
                        "El producto no existe.",
                        "Producto no encontrado",
                        JOptionPane.WARNING_MESSAGE
                    );
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                this,
                "Error al buscar el producto:\n" + e.getMessage(),
                "Error de base de datos",
                JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();

        } finally {

            txtCodigo.setText("");
            txtCodigo.requestFocusInWindow();
        }
    }

    // Ahora este método SOLO contiene la lógica de negocio: buscar si el
    // producto ya está en la tabla (y sumar cantidad) o insertarlo como fila
    // nueva. Ya NO reconstruye el modelo ni reconfigura columnas/listeners
    // (eso vive una sola vez en crearPanelProducto()).
    private void agregarProductoTabla(int idProducto,String codigo,String nombre,double precio, int existencia){

        for(int i=0; i <modeloTabla.getRowCount(); i++){
            int idExistente =
                (int) modeloTabla.getValueAt(i, 0);

            if(idExistente == idProducto){

                int cantidad=  (int)modeloTabla.getValueAt(i,4);

                if (cantidad >= existencia){

                    JOptionPane.showMessageDialog(this,
                    "No hay más existencia disponible.",
                    "Existencia insuficiente.",
                    JOptionPane.WARNING_MESSAGE);

                    return;
                }

                cantidad++;

                modeloTabla.setValueAt(cantidad, i, 4);

                double importe = precio * cantidad;

                modeloTabla.setValueAt(String.format("$%.2f",importe), i, 5);

                actualizarTotales();

                return;
            }
        }

        int cantidad = 1;

        double importe = precio * cantidad;

        modeloTabla.addRow(
            new Object[]{
                idProducto,
                codigo,
                nombre,
                String.format("$%.2f", precio),
                cantidad,
                String.format("$%.2f", importe),
                existencia
            }
        );

        actualizarTotales();
    }

    private void modificarCantidad(int fila, int nuevaCantidad){
        int existencia = (int) modeloTabla.getValueAt(fila, 6);
        double precio= Double.parseDouble(modeloTabla.getValueAt(fila,3).toString().replace("$",""));

        if (nuevaCantidad <= 0){
            JOptionPane.showMessageDialog(this,"La cantidad debe ser mayor a 0.","Cantidad inválida", JOptionPane.WARNING_MESSAGE);

            modeloTabla.setValueAt(1, fila, 4);
            return;
        }

        // Antes no se validaba el límite de existencia al editar la celda
        // manualmente (solo al escanear con buscarYAgregarProducto).
        if (nuevaCantidad > existencia){
            JOptionPane.showMessageDialog(this,"Solo hay " + existencia + " unidades disponibles.","Existencia insuficiente", JOptionPane.WARNING_MESSAGE);

            modeloTabla.setValueAt(existencia, fila, 4);
            nuevaCantidad = existencia;
        }

        double importe = precio * nuevaCantidad;
        modeloTabla.setValueAt(String.format("$%.2f", importe),  fila,5);

        actualizarTotales();
    }

    private void actualizarTotales() {

        double subtotal = 0;
        int cantidadProductos = 0;

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {

            int cantidad =
                (int) modeloTabla.getValueAt(i, 4);

            String importeTexto =
                (String) modeloTabla.getValueAt(i, 5);

            double importe =
                Double.parseDouble(
                 importeTexto.replace("$", "")
                );

            cantidadProductos += cantidad;
            subtotal += importe;
        }

        valorSubtotal.setText(
         String.format("$%.2f", subtotal)
        );

        valorCantidad.setText(
            String.valueOf(cantidadProductos)
        );
    }

    private JPanel crearBotonesProducto(){

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Color.WHITE);

        Color colorIcon2 = new Color(21,101,192);

        JButton btnVarios = new JButton("Varios", new FlatSVGIcon("images/more.svg", 22, 22));
        ((FlatSVGIcon) btnVarios.getIcon()).setColorFilter(
        new FlatSVGIcon.ColorFilter(c -> colorIcon2));
        JButton btnBuscar = new JButton("Buscar", new FlatSVGIcon("images/search.svg", 22, 22));
        ((FlatSVGIcon) btnBuscar.getIcon()).setColorFilter(
        new FlatSVGIcon.ColorFilter(c -> colorIcon2));
        JButton btnMayoreo = new JButton("Mayoreo", new FlatSVGIcon("images/sale.svg", 22, 22));
        ((FlatSVGIcon) btnMayoreo.getIcon()).setColorFilter(
        new FlatSVGIcon.ColorFilter(c -> colorIcon2));
        JButton btnEliminar = new JButton("Eliminar", new FlatSVGIcon("images/delete.svg", 22, 22));
        ((FlatSVGIcon) btnEliminar.getIcon()).setColorFilter(
        new FlatSVGIcon.ColorFilter(c -> colorIcon2));

        JButton[] botones = {btnVarios, btnBuscar, btnMayoreo, btnEliminar};

        for(JButton boton : botones){

            boton.setAlignmentX(JButton.CENTER_ALIGNMENT);
            boton.setFocusPainted(false);
            boton.setBackground(Color.WHITE);
            boton.setForeground(colorIcon2);
            boton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            boton.setPreferredSize(new Dimension(120, 40));
            boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            boton.setBorder(BorderFactory.createLineBorder(colorIcon2));

        }

        panel.add(btnVarios);
        panel.add(btnBuscar);
        panel.add(btnMayoreo);
        panel.add(btnEliminar);

        return panel;
    }

    private JPanel crearPanelBotonesInferiores(){

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,10));
        panel.setBackground(new Color(240,240,240));
        panel.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        panel.setPreferredSize(new Dimension(60,70));

        JButton botonImprimir = new JButton("Reimprimir último ticket", new FlatSVGIcon("images/print.svg", 22,22));
        botonImprimir.setPreferredSize(new Dimension(240,50));
        botonImprimir.setForeground(Color.BLACK);
        botonImprimir.setBackground(Color.WHITE);
        botonImprimir.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        botonImprimir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton botonHistorial = new JButton("Historial de ventas", new FlatSVGIcon("images/previous.svg",22,22));
        botonHistorial.setPreferredSize(new Dimension(240,50));
        botonHistorial.setForeground(Color.BLACK);
        botonHistorial.setBackground(Color.white);
        botonHistorial.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        botonHistorial.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.add(botonImprimir);
        panel.add(botonHistorial);

        return panel;
    }

    //Metodo para crear el panel de cobro
    private JPanel crearPanelCobro() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(420, 500));
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        JPanel arriba = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        arriba.setOpaque(false);
        arriba.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));

        JLabel lblTitulo = new JLabel("Cobro");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        arriba.add(lblTitulo);

        JButton btnCobrar = new JButton("Cobrar", new FlatSVGIcon("images/signmoney.svg",40,40));
        ((FlatSVGIcon) btnCobrar.getIcon()).setColorFilter(new  FlatSVGIcon.ColorFilter(color -> Color.WHITE));

        btnCobrar.setBackground(new Color(0, 150, 70));
        btnCobrar.setForeground(Color.WHITE);
        btnCobrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnCobrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCobrar.setFocusPainted(false);

        // Tamaño
        btnCobrar.setPreferredSize(new Dimension(380, 55));
        btnCobrar.setMaximumSize(new Dimension(380, 55));

        // Centrar dentro del BoxLayout
        btnCobrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));

        panelBoton.setOpaque(false);
        panelBoton.setMaximumSize(new Dimension(Integer.MAX_VALUE,55));
        panelBoton.add(btnCobrar);

        panel.add(arriba);
        panel.add(Box.createVerticalStrut(3));
        panel.add(crearSubtotalPanel());
        panel.add(Box.createVerticalStrut(15));
        panel.add(crearInformacion());
        panel.add(Box.createVerticalStrut(15));
        panel.add(tipoPago());
        panel.add(Box.createVerticalStrut(15));
         panel.add(panelBoton);
        return panel;
    }

    private JPanel crearSubtotalPanel(){
        JPanel subtotalPanel = new JPanel(new BorderLayout());
        subtotalPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(209,209,209)),
        BorderFactory.createEmptyBorder(10,10,10,10)));
        subtotalPanel.setBackground(Color.WHITE);
        subtotalPanel.setPreferredSize(new Dimension(400,70));
        subtotalPanel.setMaximumSize(new Dimension(400,70));
         subtotalPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


        JLabel textoSubtotal = new JLabel("SUBTOTAL:");
        textoSubtotal.setFont(new Font("Segoe UI", Font.BOLD, 18));

        valorSubtotal = new JLabel("$0.00");
        valorSubtotal.setFont(new Font("Segoe UI",Font.BOLD,18));

        JPanel filaSubtotal = new JPanel(new BorderLayout());
        filaSubtotal.add(textoSubtotal, BorderLayout.WEST);
        filaSubtotal.add(valorSubtotal,BorderLayout.EAST);
        filaSubtotal.setBackground(Color.white);


        JLabel textoCantidad = new JLabel("Cantidad de productos:");
        textoCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textoCantidad.setForeground(new Color(69,69,69));

        valorCantidad = new JLabel("0");
        valorCantidad.setFont(new Font("Segoe UI", Font.PLAIN,16));

        JPanel filaCantidad = new JPanel(new BorderLayout());
        filaCantidad.add(textoCantidad, BorderLayout.WEST);
        filaCantidad.add(valorCantidad, BorderLayout.EAST);
        filaCantidad.setBackground(Color.WHITE);

        subtotalPanel.add(filaSubtotal, BorderLayout.NORTH);
        subtotalPanel.add(Box.createVerticalStrut(12));
        subtotalPanel.add(filaCantidad,BorderLayout.SOUTH);
        return subtotalPanel;
    }

    private JPanel crearInformacion(){
        JPanel informacionPanel = new JPanel();
        informacionPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(209,209,209)),
        BorderFactory.createEmptyBorder(10,10,10,10)));
        informacionPanel.setBackground(Color.WHITE);
        informacionPanel.setPreferredSize(new Dimension(400, 200));
        informacionPanel.setMaximumSize(new Dimension(400,200));
        informacionPanel.setLayout(new BoxLayout(informacionPanel, BoxLayout.Y_AXIS));
        informacionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel textoTitulo = new JLabel("Información de pago");
        textoTitulo.setFont(new Font("Segeo UI", Font.BOLD,14));

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.add(textoTitulo, BorderLayout.WEST);
        panelTitulo.setOpaque(false);

        JLabel textodePago = new JLabel("Pago con:");
        textodePago.setFont(new Font("Segoe UI", Font.PLAIN,14));
        textodePago.setForeground(new Color(69,69,69));

        JPanel panelPago = new JPanel(new BorderLayout());
        panelPago.add(textodePago, BorderLayout.WEST);
        panelPago.setOpaque(false);

        JLabel cantidadePago = new JLabel("$0.00");
        cantidadePago.setFont(new Font("Segoe UI", Font.BOLD,22));

        JPanel panelCantidadPago = new JPanel(new BorderLayout());
        panelCantidadPago.add(cantidadePago, BorderLayout.WEST);
        panelCantidadPago.setOpaque(false);

        JLabel textoCmabio = new JLabel("Cambio:");
        textoCmabio.setFont(new Font("Segoe UI",Font.BOLD, 14));

        JLabel valorCambio = new JLabel("$0.00");
        valorCambio.setFont(new Font("Segoe UI", Font.BOLD,20));
        valorCambio.setForeground(new Color(27,166,53));

        JPanel filaCambio = new JPanel(new BorderLayout());
        filaCambio.add(textoCmabio, BorderLayout.WEST );
        filaCambio.add(valorCambio, BorderLayout.EAST);
        filaCambio.setBackground(Color.white);

        informacionPanel.add(panelTitulo);
        informacionPanel.add(Box.createVerticalStrut(5));
        informacionPanel.add(panelPago);
        informacionPanel.add(Box.createVerticalStrut(2));
        informacionPanel.add(panelCantidadPago);
        informacionPanel.add(Box.createVerticalStrut(5));
        informacionPanel.add(filaCambio);

        return informacionPanel;
    }

    private JPanel tipoPago(){
        JPanel tipoPagoPanel = new JPanel();
        tipoPagoPanel.setLayout(new BoxLayout(tipoPagoPanel, BoxLayout.Y_AXIS));
        tipoPagoPanel.setBackground(Color.WHITE);
        tipoPagoPanel.setBorder(
        BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(209, 209, 209)),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        tipoPagoPanel.setPreferredSize(new Dimension(400, 80));
        tipoPagoPanel.setMaximumSize(new Dimension(400, 120));
        tipoPagoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTipoPago = new JLabel("Tipo de pago");
        lblTipoPago.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JPanel tituloTipoPago = new JPanel(new BorderLayout());
        tituloTipoPago.setOpaque(false);
        tituloTipoPago.add(lblTipoPago, BorderLayout.CENTER);

        String[] tiposPago = {"Efectivo", "Tarjeta", "Transferencia"};

        JComboBox<String> comboTiposPago = new JComboBox<>(tiposPago);
        comboTiposPago.setFont(new Font("Segoe UI",Font.BOLD ,18));
        comboTiposPago.setForeground(new Color(21,101,192));
        comboTiposPago.setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean  isSelected,
                boolean cellHasFocus){
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                    if (value !=null){
                        switch(value.toString()){

                            case "Efectivo":
                                FlatSVGIcon icono1 =  new FlatSVGIcon("images/cash.svg",24,24);
                                icono1.setColorFilter(new FlatSVGIcon.ColorFilter(color -> new Color(21,101,192)));
                                setIcon(icono1);
                                break;
                            case "Tarjeta":
                                FlatSVGIcon icono2 = new FlatSVGIcon("images/creditcard.svg",24,24);
                                icono2.setColorFilter(new FlatSVGIcon.ColorFilter(color -> new Color(21,101,192)));
                                setIcon(icono2);
                                break;
                            case "Transferencia":
                                FlatSVGIcon icono3 = new FlatSVGIcon("images/transfer.svg",24,24);
                                icono3.setColorFilter(new FlatSVGIcon.ColorFilter(color -> new Color(21,101,192)));
                                setIcon(icono3);
                                break;
                        }
                    }
                    return this;
                }
        });
        comboTiposPago.setMaximumSize(new Dimension(380,90));

        JPanel panelComboTiposPago = new JPanel(new BorderLayout());
        panelComboTiposPago.add(comboTiposPago, BorderLayout.CENTER);
        panelComboTiposPago.setOpaque(false);

        tipoPagoPanel.add(lblTipoPago);
        tipoPagoPanel.add(Box.createVerticalStrut(8));
        tipoPagoPanel.add(panelComboTiposPago);


        return tipoPagoPanel;
    }

}