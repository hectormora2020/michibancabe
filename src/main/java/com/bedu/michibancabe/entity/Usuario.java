package com.bedu.michibancabe.entity;

import lombok.Data;

@Data
public class Usuario {

    boolean error;
    String clave;
    String perfil;
    String cuenta;
    String saldo;
    String nombre;
    String estatus;
    String token;
}
