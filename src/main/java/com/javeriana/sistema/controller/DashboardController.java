package com.javeriana.sistema.controller;

import com.javeriana.sistema.services.CuentaBancariaService;
import com.javeriana.sistema.util.UsuarioSesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import com.javeriana.sistema.controller.*;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Button cerrarSesionButton;

    @FXML
    private void abrirCrearCuenta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/CrearCuentaView.fxml"));
            Parent root = loader.load();

            CrearCuentaController controller = loader.getController();
            controller.setUsuarioId(UsuarioSesion.getInstancia().getUsuario().getId());

            Stage stage = new Stage();
            stage.setTitle("Crear Nueva Cuenta");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void verCuentas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/VerCuentasView.fxml"));
            Parent root = loader.load();

            VerCuentasController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Mis Cuentas Bancarias");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirSolicitarPrestamo() {
        try {
            int usuarioId = UsuarioSesion.getInstancia().getUsuario().getId();
            CuentaBancariaService cuentaService = new CuentaBancariaService();

            if (cuentaService.obtenerCuentasDeUsuario(usuarioId).isEmpty()) {
                mostrarAlerta("Error", "Debes crear una cuenta bancaria antes de solicitar un préstamo.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/SolicitarPrestamoView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Solicitar Préstamo");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/LoginView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) cerrarSesionButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sistema Bancario - Iniciar Sesión");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void verPrestamos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/VerPrestamosView.fxml"));
            Parent root = loader.load();

            VerPrestamosController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Mis Préstamos");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void verHistorialTransferencias() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/HistorialTransferenciasView.fxml"));
            Parent root = loader.load();

            HistorialTransferenciasController controller = loader.getController();
            controller.setUsuarioId(UsuarioSesion.getInstancia().getUsuario().getId());

            Stage stage = new Stage();
            stage.setTitle("Historial de Transferencias");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirTransferenciaPersonaPersona() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/TransferenciaPersonaPersonaView.fxml"));
            Parent root = loader.load();

            TransferenciaPersonaPersonaController controller = loader.getController();
            controller.setUsuarioId(UsuarioSesion.getInstancia().getUsuario().getId());

            Stage stage = new Stage();
            stage.setTitle("Transferir a Otra Persona");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de transferencia.");
        }
    }

    @FXML
    private void abrirCrearPagoProgramado() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/CrearPagoProgramadoView.fxml"));
            Parent root = loader.load();

            CrearPagoProgramadoController controller = loader.getController();
            controller.setUsuarioId(UsuarioSesion.getInstancia().getUsuario().getId());

            Stage stage = new Stage();
            stage.setTitle("Crear Pago Programado");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de pagos programados.");
        }
    }

    @FXML
    private void abrirVerPagosProgramados() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/VerPagosProgramadosView.fxml"));
            Parent root = loader.load();

            VerPagosProgramadosController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Pagos Programados");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de pagos programados.");
        }
    }

    @FXML
    private void abrirGestionTarjetas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/GestionarTarjetasView.fxml"));
            Parent root = loader.load();

            GestionarTarjetasController controller = loader.getController();
            controller.setUsuarioId(UsuarioSesion.getInstancia().getUsuario().getId());

            Stage stage = new Stage();
            stage.setTitle("Gestión de Tarjetas");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la gestión de tarjetas.");
        }
    }

    @FXML
    private void abrirInversiones() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/InversionesView.fxml"));
            Parent root = loader.load();

            InversionesController controller = loader.getController();
            controller.setUsuarioId(UsuarioSesion.getInstancia().getUsuario().getId());

            Stage stage = new Stage();
            stage.setTitle("Inversiones y Ahorros");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirSoporte() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/Soporte.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Soporte al Cliente");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirConfiguracionLimite() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/ConfigurarLimiteView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Configurar Límite de Alerta");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la configuración de límite.");
        }
    }

    @FXML
    private void abrirVerMovimientos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/view/ui/VerMovimientosView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Trazador de Movimientos");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error al abrir movimientos");
            alerta.setHeaderText("No se pudo cargar la vista del trazador de movimientos");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
