package com.example.ejercicio_listadelacompra_login.modelos;

import com.example.ejercicio_listadelacompra_login.R;

import java.text.NumberFormat;

public class Producto {

    private String nombre;
    private int cantidad;
    private float precio;

    private static final NumberFormat nf;
    private static final NumberFormat nfNumeros;



    static {
        nf = NumberFormat.getCurrencyInstance();
        nfNumeros = NumberFormat.getNumberInstance();

    }

    public String getPrecioMoneda(){ //el numberFormat siempre devuelve un String
        return nf.format(this.precio);
    }


    public String getCantidadTexto(){
        return nfNumeros.format(this.cantidad);
    }

    public Producto() {
    }

    public Producto(String nombre, int cantidad, float precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                '}';
    }
}
