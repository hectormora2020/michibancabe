package com.bedu.michibancabe.service.impl;

import com.bedu.michibancabe.entity.Referencia;
import com.bedu.michibancabe.entity.Usuario;
import com.bedu.michibancabe.service.ICuentasService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class CuentasServiceImpl implements ICuentasService {

    @Override
    public Usuario validaCredenciales( String clave, String contrasenya) {

        String resultadoAPI = this.llamaServicio(
                "http://localhost/beduapp/michiapis/autenticacion/?clave=" + clave +
                        "&contrasenya=" + contrasenya
        );

        Usuario usuario = new Usuario();
        try {
            JSONObject json = new JSONObject( resultadoAPI );
            usuario.setError( json.getBoolean("error") );
            usuario.setClave( json.getString( "clave") );
            usuario.setNombre(  json.getString("nombre") );
            usuario.setToken( json.getString("token") );
            usuario.setSaldo( json.getString("saldo") );
            usuario.setPerfil( json.getString("perfil") );
            usuario.setCuenta( json.getString("cuenta") );

        } catch( Exception e ) {
            System.out.println("Error al parsear el json" + resultadoAPI );
        }
        return usuario;
    }

    @Override
    public List<Usuario> getAll() {
        List<Usuario> lista = new ArrayList<Usuario>();
        String resultadoAPI = this.llamaServicio("http://localhost/beduapp/michiapis/cuentas" );

        try {
            JSONArray array = new JSONArray(resultadoAPI);
            for(int i = 0; i < array.length(); i ++ ) {
                JSONObject object = array.getJSONObject(i);
                Usuario usuario = new Usuario();
                usuario.setCuenta( object.getString("cuenta") );
                usuario.setClave( object.getString("clave"));
                usuario.setSaldo( object.getString("saldo"));
                usuario.setPerfil( object.getString("perfil"));
                usuario.setNombre( object.getString("nombre"));
                usuario.setClave( object.getString("clave") );
                lista.add(usuario);
            }


        } catch( Exception e ) {
            System.out.println("Error" + e.toString() );
        }

        return lista;
    }

    @Override
    public void crea( String clave, String contrasenya, String nombre ) {
        this.llamaServicio("http://localhost/beduapp/michiapis/nuevacuenta/?clave=" + clave + "&contrasenya=" + contrasenya + "&nombre=" + nombre );
    }

    @Override
    public Usuario incrementaSaldo(String cuenta) {

        String resultadoAPI = this.llamaServicio("http://localhost/beduapp/michiapis/incrementasaldo/?cuenta=" + cuenta );
        Usuario objUsuario = new Usuario();

        try {
            JSONObject json = new JSONObject(resultadoAPI);
            objUsuario.setError( json.getBoolean("error" ) );
            objUsuario.setSaldo( json.getString( "saldo") );
        } catch( Exception e ) {
            System.out.println(e);
        }

        return objUsuario;
    } // ./incrementaSaldo

    @Override
    public Referencia referencia( String cuenta) {

        Referencia referencia = new Referencia();
        String resultadoAPI = this.llamaServicio("http://localhost/beduapp/michiapis/referencia/?cuenta=" + cuenta  );

        try {
            JSONObject json = new JSONObject(resultadoAPI);
            referencia.setClave( json.getString( "referencia") );
        } catch( Exception e ) {
            System.out.println(e);
        }

        return referencia;
    } // .

    private String llamaServicio( String endpoint ) {

        String salida = "";

        try {
            URL url = new URL( endpoint );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Error : HTTP código : " + conn.getResponseCode());
            }

            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            salida = br.readLine();

            conn.disconnect();
        }catch (Exception e ) {
            System.out.println( e) ;
        }

        return salida;
    }
}
