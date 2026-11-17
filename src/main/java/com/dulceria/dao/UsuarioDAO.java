package com.dulceria.dao;
//Importaciones de librerias
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dulceria.config.ConexionDB;
import com.dulceria.model.Usuario;

public class UsuarioDAO {
    //Metodo para buscar un usuario por su nombre de usuario
    public Usuario buscarUsuario(String nombreUsuario) {
        String sql = "SELECT * FROM usuarios WHERE usuario =?";

        try (Connection conexion = ConexionDB.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)){
                ps.setString(1, nombreUsuario);
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    // Crear un objeto Usuario y establecer sus atributos a partir del ResultSet
                    Usuario usuario = new Usuario();

                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setUsuario(rs.getString("usuario"));
                    usuario.setContrasena(rs.getString("contrasena"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setActivo(rs.getInt("activo") == 1 );
                    return usuario;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
    }
    //Metodo para iniciar sesión
    public Usuario iniciarSesion(String nombreUsuario, String contrasena) {

    String sql = 
            "SELECT * FROM usuarios " +
            "WHERE usuario = ? " +
            "AND contrasena = ? " +
            "AND activo = 1 ";
    
    try (Connection conexion = ConexionDB.conectar();
         PreparedStatement ps = conexion.prepareStatement(sql)) {

        ps.setString(1, nombreUsuario);
        ps.setString(2, contrasena);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            // Crear un objeto Usuario y establecer sus atributos a partir del ResultSet
            Usuario usuario = new Usuario();

            usuario.setIdUsuario(rs.getInt("id_usuario"));
            usuario.setUsuario(rs.getString("usuario"));
            usuario.setContrasena(rs.getString("contrasena"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setRol(rs.getString("rol"));
            usuario.setActivo(rs.getInt("activo") == 1);

            return usuario;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}
    
    
    
}
