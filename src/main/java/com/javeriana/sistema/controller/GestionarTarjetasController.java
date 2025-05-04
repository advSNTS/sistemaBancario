package com.javeriana.sistema.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;


public class GestionarTarjetasController {

    @FXML private TabPane tabPane;
    @FXML private Tab tabSolicitar;
    @FXML private Tab tabVer;
    @FXML private Tab tabBloquear;
    @FXML private Tab tabUsar;
    @FXML private Tab tabPagar;
    @FXML private Tab tabDesbloquear;

    private int usuarioId;

    public void setUsuarioId(int id) {
        this.usuarioId = id;
        cargarSubVistas();
    }

    private void cargarSubVistas() {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/SolicitarTarjetaView.fxml"));
            Parent solicitarPane = loader1.load();
            SolicitarTarjetaController controller1 = loader1.getController();
            controller1.setUsuarioId(usuarioId);
            tabSolicitar.setContent(solicitarPane);

            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/VerTarjetasView.fxml"));
            Parent verPane = loader2.load();
            VerTarjetasController controller2 = loader2.getController();
            controller2.setUsuarioId(usuarioId);
            tabVer.setContent(verPane);

            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/BloquearTarjetaView.fxml"));
            Parent bloquearPane = loader3.load();
            BloquearTarjetaController controller3 = loader3.getController();
            controller3.setUsuarioId(usuarioId);
            tabBloquear.setContent(bloquearPane);

            FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/UsarTarjetaView.fxml"));
            Parent usarPane = loader4.load();
            UsarTarjetaController controller4 = loader4.getController();
            controller4.setUsuarioId(usuarioId);
            tabUsar.setContent(usarPane);

            FXMLLoader loader5 = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/PagarDeudaTarjetaView.fxml"));
            Parent pagarPane = loader5.load();
            PagarDeudaController controller5 = loader5.getController();
            controller5.setUsuarioId(usuarioId);
            tabPagar.setContent(pagarPane);

            FXMLLoader desbloquearLoader = new FXMLLoader(getClass().getResource("/com/javeriana/sistema/ui/DesbloquearTarjetaView.fxml"));
            tabDesbloquear.setContent(desbloquearLoader.load());
            DesbloquearTarjetaController desbloquearController = desbloquearLoader.getController();
            desbloquearController.setUsuarioId(usuarioId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
