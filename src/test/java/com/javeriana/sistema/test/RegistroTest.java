package com.javeriana.sistema.test;

import com.javeriana.sistema.model.Usuario;
import com.javeriana.sistema.services.UsuarioService;
import com.javeriana.sistema.util.DBConnection;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RegistroTest {

    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        DBConnection.activarModoPrueba();
        usuarioService = new UsuarioService();
    }

    @Test
    void testRegistrarUsuario() {
        Usuario usuario = new Usuario(1, "Ana Torres", "ana@correo.com", "clave456");
        usuario.setCedula("98765432");
        usuario.setPreguntaSecreta("¿Ciudad donde naciste?");
        usuario.setRespuestaSecreta("Bogotá");

        usuarioService.registrarUsuario(usuario);

        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();

        assertEquals(1, usuarios.size());
        assertEquals("Ana Torres", usuarios.get(0).getNombre());
        assertEquals("98765432", usuarios.get(0).getCedula());
    }
}
