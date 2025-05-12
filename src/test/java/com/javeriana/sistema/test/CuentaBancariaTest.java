package com.javeriana.sistema.test;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.UsuarioService;
import com.javeriana.sistema.util.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CuentaBancariaTest {

    private CuentaBancariaService cuentaBancariaService;

    @BeforeEach
    void setup(){
        DBConnection.activarModoPrueba();
        DBConnection.ejecutarScript();
        cuentaBancariaService = new CuentaBancariaService();

        UsuarioService usuarioService = new UsuarioService();

        Usuario usuario = new Usuario(1, "Juan", "juan@mail.com", "fundamentos123");
        usuario.setCedula("12345678");
        usuario.setPreguntaSecreta("¿Ciudad?");
        usuario.setRespuestaSecreta("Bogotá");

        try {
            usuarioService.registrarUsuario(usuario);
        } catch (Exception e) {
            System.err.println("Error al registrar usuario en el BeforeEach: " + e.getMessage());
        }

    }


    @Test
    public void testCrearCuenta(){

        CuentaBancaria cuentaBancaria = new CuentaBancaria(0, 1, "Ahorro", 1000, null);
        cuentaBancariaService.crearCuenta(cuentaBancaria);
        List<CuentaBancaria> cuentaBancarias = cuentaBancariaService.obtenerCuentasDeUsuario(1);
        System.out.println(cuentaBancarias.toString());
    }
}
