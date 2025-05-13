package com.javeriana.sistema.test;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.TransferenciaService;
import com.javeriana.sistema.services.UsuarioService;
import com.javeriana.sistema.util.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferenciaServiceTest {
    private TransferenciaService transferenciaService;

    @BeforeEach
    void setup(){
        DBConnection.activarModoPrueba();
        DBConnection.ejecutarScript();
        transferenciaService = new TransferenciaService();

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

        Usuario usuario1 = new Usuario(2, "Santiago", "santiago@mail.com", "casa123");
        usuario1.setCedula("456789");
        usuario1.setPreguntaSecreta("¿Ciudad?");
        usuario1.setRespuestaSecreta("Barranquilla");

        try{
            usuarioService.registrarUsuario(usuario1);
        }catch (Exception e){
            System.err.println("Error al registrar usuario en el BeforeEach: " + e.getMessage());
        }


    }

    @Test
    public void transferirEntreCuentasTest(){
        CuentaBancariaService cuentaBancariaService = new CuentaBancariaService();
        cuentaBancariaService.crearCuenta(new CuentaBancaria(0, 1, "Ahorro", 1000, null));
        CuentaBancaria cuenta1 = cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst();
        cuentaBancariaService.crearCuenta(new CuentaBancaria(1, 1, "Corriente", 1000, null));
        CuentaBancaria cuenta2 = cuentaBancariaService.obtenerCuentasDeUsuario(1).get(1);

        assertEquals(1000, cuenta1.getSaldo());
        assertEquals(1000, cuenta2.getSaldo());

        transferenciaService.realizarTransferencia(1, 2, 1000);
        CuentaBancaria cuenta11 = cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst();
        CuentaBancaria cuenta22 = cuentaBancariaService.obtenerCuentasDeUsuario(1).get(1);
        assertEquals(0, cuenta11.getSaldo());
        assertEquals(2000, cuenta22.getSaldo());
    }

}
