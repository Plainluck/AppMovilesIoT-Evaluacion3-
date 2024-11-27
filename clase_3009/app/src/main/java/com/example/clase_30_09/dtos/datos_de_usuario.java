package com.example.clase_30_09.dtos;

import java.io.Serializable;

public class datos_de_usuario implements Serializable {
    private String nombre;
    private String apellido;
    private String cargo;
    private Integer sueldo;
    private String id;

    public datos_de_usuario() {
    }


    public datos_de_usuario(String nombre, String apellido, String cargo, Integer sueldo, String id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cargo = cargo;
        this.sueldo = sueldo;
        this.id = id;
    }

    //getters
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCargo() {
        return cargo;
    }

    public Integer getSueldo() {
        return sueldo;
    }

    public String getId() { return id; }

    //setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setSueldo(Integer sueldo) {
        this.sueldo = sueldo;
    }



}

