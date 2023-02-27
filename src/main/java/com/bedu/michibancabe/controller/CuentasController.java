package com.bedu.michibancabe.controller;
import com.bedu.michibancabe.entity.Referencia;
import com.bedu.michibancabe.entity.Usuario;
import com.bedu.michibancabe.service.ICuentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CuentasController {

    private ICuentasService servicio;

    @Autowired
    public CuentasController( ICuentasService servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/api/v1/autentica")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Usuario autentica( @RequestParam("clave") String clave, @RequestParam("contrasenya") String contrasenya ) {
        return this.servicio.validaCredenciales(clave, contrasenya);
    } // ./autentica()

    @GetMapping("/api/v1/getAll")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Usuario> getAll() {
        return this.servicio.getAll();
    } // ./getAll()

    @GetMapping("/api/v1/nueva")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Usuario> nueva( @RequestParam("clave") String clave, @RequestParam("contrasenya") String contrasenya, @RequestParam("nombre") String nombre  ) {
        this.servicio.crea( clave, contrasenya, nombre );
        return this.servicio.getAll();
    } // ./nueva()

    @GetMapping("/api/v1/saldo")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Usuario incrementaSaldo( @RequestParam("cuenta") String cuenta) {
        return this.servicio.incrementaSaldo(cuenta);
    }

    @GetMapping("/api/v1/referencia")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Referencia referencia(@RequestParam("cuenta") String cuenta) {
        return this.servicio.referencia(cuenta);
    }

}
