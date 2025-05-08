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
        DBConnection.ejecutarScript();

        usuarioService = new UsuarioService();

    }

    @Test
    @DisplayName("Debe registrar un nuevo usuario y verificar su existencia")
    void testRegistrarUsuario() {
        Usuario usuario = new Usuario(1, "Ana Torres", "ana@correo.com", "clave456");
        usuario.setCedula("98765432");
        usuario.setPreguntaSecreta("¿Ciudad donde naciste?");
        usuario.setRespuestaSecreta("Bogotá");

        try {
            usuarioService.registrarUsuario(usuario);
        } catch (Exception e) {
            fail("Error al registrar el usuario: " + e.getMessage()); // Falla el test si hay un error de DB
        }

        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();

        assertNotNull(usuarios, "La lista de usuarios no debería ser nula");
        assertEquals(1, usuarios.size(), "Debería haber exactamente un usuario en la BD de prueba");

        Usuario usuarioRegistrado = usuarios.get(0);
        assertEquals("Ana Torres", usuarioRegistrado.getNombre());
        assertEquals("ana@correo.com", usuarioRegistrado.getCorreo());
        assertEquals("98765432", usuarioRegistrado.getCedula());
        assertEquals("¿Ciudad donde naciste?", usuarioRegistrado.getPreguntaSecreta());
        assertEquals("Bogotá", usuarioRegistrado.getRespuestaSecreta());

    }

}