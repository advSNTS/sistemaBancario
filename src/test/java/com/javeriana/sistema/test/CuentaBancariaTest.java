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
        CuentaBancaria cuenta = cuentaBancarias.getFirst();
        assertEquals(1, cuenta.getId());
        assertEquals(1, cuenta.getUsuarioId());
        assertEquals("Ahorro", cuenta.getTipo());
        assertEquals(1000, cuenta.getSaldo());
    }

    @Test
    public void testCrearDosCuentas(){
        CuentaBancaria cuentaBancaria = new CuentaBancaria(0, 1, "Ahorro", 1000, null);
        CuentaBancaria cuentaBancaria1 = new CuentaBancaria(1, 1, "Corriente", 2000, null);
        cuentaBancariaService.crearCuenta(cuentaBancaria);
        cuentaBancariaService.crearCuenta(cuentaBancaria1);
        List<CuentaBancaria> cuentaBancarias = cuentaBancariaService.obtenerCuentasDeUsuario(1);

        CuentaBancaria cuenta = cuentaBancarias.getFirst();
        CuentaBancaria cuenta2 = cuentaBancarias.get(1);

        assertEquals(1, cuenta.getId());
        assertEquals(1, cuenta.getUsuarioId());
        assertEquals("Ahorro", cuenta.getTipo());
        assertEquals(1000, cuenta.getSaldo());

        assertEquals(2, cuenta2.getId());
        assertEquals(1, cuenta2.getUsuarioId());
        assertEquals("Corriente", cuenta2.getTipo());
        assertEquals(2000, cuenta2.getSaldo());

    }

    @Test
    public void testCambiarSaldo(){
        CuentaBancaria cuentaBancaria = new CuentaBancaria(0, 1, "Ahorro", 1000, null);
        cuentaBancariaService.crearCuenta(cuentaBancaria);
        List<CuentaBancaria> cuentaBancarias = cuentaBancariaService.obtenerCuentasDeUsuario(1);
        CuentaBancaria cuenta1 = cuentaBancarias.getFirst();

        cuentaBancariaService.actualizarSaldo(1, 2000);
        List<CuentaBancaria> cuentaBancarias2 = cuentaBancariaService.obtenerCuentasDeUsuario(1);
        CuentaBancaria cuenta2 = cuentaBancarias2.getFirst();

        assertEquals(1000, cuenta1.getSaldo());
        assertEquals(2000, cuenta2.getSaldo());
    }

    @Test
    public void testDepositar(){
        CuentaBancaria cuentaBancaria = new CuentaBancaria(0, 1, "Ahorro", 1000, null);
        cuentaBancariaService.crearCuenta(cuentaBancaria);
        cuentaBancariaService.depositar(1, 5000);
        CuentaBancaria cuentaDespuesDeTest = cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst();

        assertEquals(6000, cuentaDespuesDeTest.getSaldo());

        cuentaBancariaService.depositar(1, 300);
        CuentaBancaria cuentaBancaria1 = cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst();

        assertEquals(6300, cuentaBancaria1.getSaldo());
    }



}
