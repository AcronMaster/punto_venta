package com.dulceria.model;

public class Usuario {

    //Atributos
    private int idUsuario;
    private String usuario;
    private String contrasena;
    private String nombre;
    private String rol;
    private boolean activo;
    
    //Constructor
    public Usuario(){}

    //Constructor con todos los atributos
    public Usuario(int idUsuario, String usuario, String contraseña, String nombre, String rol, boolean activo) {
    this.idUsuario = idUsuario;
    this.usuario = usuario;
    this.contrasena = contrasena;
    this.nombre = nombre;
    this.rol = rol;
    this.activo = activo;
    }

    //Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}

