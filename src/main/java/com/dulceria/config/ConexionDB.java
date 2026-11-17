package com.dulceria.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    //Ruta de la base de datos
    private static final String URL = "jdbc:sqlite:database/dulceria";

    /**
     * Obtener una conexion con la base de datos
     */
    public static Connection conectar() {
        Connection conexion = null;
        
        try{
            conexion = DriverManager.getConnection(URL);
            System.out.println("Connexion exitosa");
        }catch (SQLException e){
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }

        return conexion;
    }
}
