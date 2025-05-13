package com.javeriana.sistema.test;

import com.javeriana.sistema.model.CuentaBancaria;
import com.javeriana.sistema.model.Transferencia;
import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.services.TransferenciaService;
import com.javeriana.sistema.services.UsuarioService;
import com.javeriana.sistema.util.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

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

        Usuario usuario11 = usuarioService.obtenerTodosLosUsuarios().getFirst();
        Usuario usuario22 = usuarioService.obtenerTodosLosUsuarios().get(1);

        System.out.println(usuario11.toString());
        System.out.println(usuario22.toString());


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


    @Test
    public void testTransferenciaEntreCuentasDiferenteUsuario(){

        CuentaBancariaService cuentaBancariaService = new CuentaBancariaService();
        cuentaBancariaService.crearCuenta(new CuentaBancaria(0, 1, "Ahorro", 1000, null));
        CuentaBancaria cuenta1 = cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst();
        cuentaBancariaService.crearCuenta(new CuentaBancaria(0, 2, "Corriente", 1000, null));
        CuentaBancaria cuenta2 = cuentaBancariaService.obtenerCuentasDeUsuario(2).getFirst();

        assertEquals(1000, cuenta1.getSaldo());
        assertEquals(1000, cuenta2.getSaldo());

        transferenciaService.realizarTransferencia(1, 2, 1000);


        CuentaBancaria cuenta11 = cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst();
        CuentaBancaria cuenta22 = cuentaBancariaService.obtenerCuentasDeUsuario(2).getFirst();

        assertEquals(0, cuenta11.getSaldo());
        assertEquals(2000, cuenta22.getSaldo());
    }

    @Test
    public void testMultiplesTransferenciasEntreDosUsuarios(){
        CuentaBancariaService cuentaBancariaService = new CuentaBancariaService();
        cuentaBancariaService.crearCuenta(new CuentaBancaria(0, 1, "Ahorro", 1000, null));
        cuentaBancariaService.crearCuenta(new CuentaBancaria(0, 2, "Corriente", 1000, null));

        assertEquals(1000, cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst().getSaldo());
        assertEquals(1000, cuentaBancariaService.obtenerCuentasDeUsuario(2).getFirst().getSaldo());

        transferenciaService.realizarTransferencia(1, 2, 500);
        assertEquals(500, cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst().getSaldo());
        assertEquals(1500, cuentaBancariaService.obtenerCuentasDeUsuario(2).getFirst().getSaldo());
        transferenciaService.realizarTransferencia(1, 2, 500);
        assertEquals(0, cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst().getSaldo());
        assertEquals(2000, cuentaBancariaService.obtenerCuentasDeUsuario(2).getFirst().getSaldo());
        transferenciaService.realizarTransferencia(2, 1, 1000);
        assertEquals(1000, cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst().getSaldo());
        assertEquals(1000, cuentaBancariaService.obtenerCuentasDeUsuario(2).getFirst().getSaldo());
    }

    @Test
    public void testObtenerTransferenciasBancarias(){
        CuentaBancariaService cuentaBancariaService = new CuentaBancariaService();
        cuentaBancariaService.crearCuenta(new CuentaBancaria(0, 1, "Ahorro", 1000, null));
        cuentaBancariaService.crearCuenta(new CuentaBancaria(0, 2, "Corriente", 1000, null));

        assertEquals(1000, cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst().getSaldo());
        assertEquals(1000, cuentaBancariaService.obtenerCuentasDeUsuario(2).getFirst().getSaldo());

        transferenciaService.realizarTransferencia(1, 2, 500);
        assertEquals(500, cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst().getSaldo());
        assertEquals(1500, cuentaBancariaService.obtenerCuentasDeUsuario(2).getFirst().getSaldo());
        transferenciaService.realizarTransferencia(1, 2, 500);
        assertEquals(0, cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst().getSaldo());
        assertEquals(2000, cuentaBancariaService.obtenerCuentasDeUsuario(2).getFirst().getSaldo());
        transferenciaService.realizarTransferencia(2, 1, 1000);
        assertEquals(1000, cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst().getSaldo());
        assertEquals(1000, cuentaBancariaService.obtenerCuentasDeUsuario(2).getFirst().getSaldo());

        List<Transferencia> transferenciaLIst = transferenciaService.obtenerTransferenciasDeCuenta(1);
        assertEquals(500, Objects.requireNonNull(findById(1, transferenciaLIst)).getMonto());
        assertEquals(500, Objects.requireNonNull(findById(2, transferenciaLIst)).getMonto());
        assertEquals(1000, Objects.requireNonNull(findById(3, transferenciaLIst)).getMonto());

        assertEquals(500, Objects.requireNonNull(findById(1, transferenciaService.obtenerTransferenciasDeCuenta(2))).getMonto());
        assertEquals(500, Objects.requireNonNull(findById(2, transferenciaService.obtenerTransferenciasDeCuenta(2))).getMonto());
        assertEquals(1000, Objects.requireNonNull(findById(3, transferenciaService.obtenerTransferenciasDeCuenta(2))).getMonto());

    }

    @Test
    public void testObtenerTransferenciasBancariasPorIdDeUsuario(){
        CuentaBancariaService cuentaBancariaService = new CuentaBancariaService();
        cuentaBancariaService.crearCuenta(new CuentaBancaria(0, 1, "Ahorro", 8000, null));
        cuentaBancariaService.crearCuenta(new CuentaBancaria(0, 2, "Corriente", 10000, null));
        cuentaBancariaService.crearCuenta(new CuentaBancaria(1, 1, "Ahorro", 1000, null));

        //cuenta origen id = 2 cuando usuario es 2
        //cuenta origen id = 1, 3 cuando usuario es 1
        assertEquals(8000, cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst().getSaldo());
        assertEquals(10000, cuentaBancariaService.obtenerCuentasDeUsuario(2).getFirst().getSaldo());

        transferenciaService.realizarTransferencia(2, 1, 8000);
        assertEquals(16000, cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst().getSaldo());
        assertEquals(2000, cuentaBancariaService.obtenerCuentasDeUsuario(2).getFirst().getSaldo());
        transferenciaService.realizarTransferencia(1, 2, 16000);
        assertEquals(0, cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst().getSaldo());
        assertEquals(18000, cuentaBancariaService.obtenerCuentasDeUsuario(2).getFirst().getSaldo());
        transferenciaService.realizarTransferencia(2, 1, 11000);
        assertEquals(11000, cuentaBancariaService.obtenerCuentasDeUsuario(1).getFirst().getSaldo());
        assertEquals(7000, cuentaBancariaService.obtenerCuentasDeUsuario(2).getFirst().getSaldo());
        transferenciaService.realizarTransferencia(1, 3, 1000);
        
        System.out.println(transferenciaService.obtenerTransferenciasPorUsuario(1).toString());
        System.out.println(transferenciaService.obtenerTransferenciasPorUsuario(2).toString());


    }

    private Transferencia findById(int id, List<Transferencia> transferencias){
        for(Transferencia t : transferencias){
            if(t.getId()==id){
                return t;
            }
        }
        return null;
    }

}
