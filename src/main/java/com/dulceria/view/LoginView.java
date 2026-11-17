package com.dulceria.view;
//Importaciones de librerias
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dulceria.dao.UsuarioDAO;
import com.dulceria.model.Usuario;
import com.formdev.flatlaf.extras.FlatSVGIcon;

//Clase para crear la ventana de login
public class LoginView extends JFrame{
    //Componentes
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;

    //Constructor de la clase
    public LoginView(){
        //Configuracion de la ventana
        setTitle("Punto de venta - Login");
        setSize(450,360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


        inicializarComponentes();
    }
    //Clase para inicailizar los componnetes de la ventana
    private void inicializarComponentes() {

        //Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(248,249,250));

        //Icono de la ventana
        FlatSVGIcon icono = new FlatSVGIcon("images/cart.svg", 40, 40);

        //Titulo
        JLabel lblTitulo = new JLabel("Punto de Venta",icono,JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setBounds(60, 20, 300, 60);
        lblTitulo.setForeground(Color.black);

        //Subtitulo
        JLabel lblSubtitulo = new JLabel("Inicia sesión para continuar");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.GRAY);
        lblSubtitulo.setBounds(120, 75, 220, 20);

        //Usuario
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setBounds(50, 90, 100, 25);
        txtUsuario = new JTextField();
        txtUsuario.setBounds(50, 115, 330, 35);

        //Contraseña
        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setBounds(50, 165, 100, 25);
        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(50, 190, 330, 35);

        //Boton de iniciar sesión
        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setBounds(120, 255, 180, 40);
        btnIniciarSesion.setBackground(Color.black);
        btnIniciarSesion.putClientProperty("JButton.arc", 10);
        btnIniciarSesion.setForeground(Color.white);
        btnIniciarSesion.setFont(new Font("Segoe UI", Font.BOLD, 12));

        //Establecer el boton de iniciar sesión como el boton por defecto
        getRootPane().setDefaultButton(btnIniciarSesion);
        //Agregar componentes al panel
        panel.add(lblTitulo);
        panel.add(lblSubtitulo);
        panel.add(lblUsuario);
        panel.add(txtUsuario);
        panel.add(lblContrasena);
        panel.add(txtContrasena);
        panel.add(btnIniciarSesion);

        //Agregar accion al boton de inciar sesión
        btnIniciarSesion.addActionListener(e -> iniciarSesion());

        add(panel);
    }

    //Inicio de sesión
    private void iniciarSesion() {
        //Obtener los valores de usuario y contraseña
        String usuario =txtUsuario.getText();
        String contrasena = String.valueOf(txtContrasena.getPassword());

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Usuario usuarioLogueado = usuarioDAO.iniciarSesion(usuario, contrasena);

        if(usuarioLogueado != null){

            PrincipalView principal = new PrincipalView(usuarioLogueado);
            principal.setVisible(true);

            dispose();
        } else
            {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos","Error",JOptionPane.ERROR_MESSAGE);
        }

    }

    // Getters
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JPasswordField getTxtContrasena() {
        return txtContrasena;
    }

    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }
}
