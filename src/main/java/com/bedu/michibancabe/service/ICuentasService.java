package com.bedu.michibancabe.service;
import com.bedu.michibancabe.entity.Referencia;
import com.bedu.michibancabe.entity.Usuario;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ICuentasService {

    public Usuario validaCredenciales( String clave, String contrasenya );

    public List<Usuario> getAll();

    public void crea( String clave, String contrasenya,  String nombre );

    public Usuario incrementaSaldo(String cuenta);

    public Referencia referencia(String cuenta);
}
