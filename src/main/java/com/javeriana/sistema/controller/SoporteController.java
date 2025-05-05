package com.javeriana.sistema.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.util.HashMap;
import java.util.Map;

public class SoporteController {

    @FXML private ComboBox<String> comboPreguntas;
    @FXML private TextArea areaRespuesta;

    private final Map<String, String> faqMap = new HashMap<>();

    @FXML
    public void initialize() {
        // Preguntas y respuestas predefinidas
        faqMap.put("\u00bfC\u00f3mo creo una cuenta bancaria?", "Para crear una cuenta bancaria, dir\u00edgete al men\u00fa principal y haz clic en 'Crear Nueva Cuenta Bancaria'. Luego selecciona el tipo de cuenta y confirma.");
        faqMap.put("\u00bfC\u00f3mo solicito un pr\u00e9stamo?", "Desde el men\u00fa principal, selecciona 'Solicitar Pr\u00e9stamo', llena los datos requeridos y confirma la solicitud.");
        faqMap.put("\u00bfC\u00f3mo transfiero dinero a otro usuario?", "Haz clic en 'Transferir a Otro Usuario', elige tu cuenta, el destinatario y el monto, y confirma la transacci\u00f3n.");
        faqMap.put("\u00bfC\u00f3mo ver mi historial de transacciones?", "En el men\u00fa, selecciona 'Historial de Transferencias' para ver todas tus transacciones realizadas.");
        faqMap.put("\u00bfC\u00f3mo reporto una tarjeta robada?", "Ingresa a 'Gestionar Tarjetas', selecciona la tarjeta y elige la opci\u00f3n para bloquearla.");

        comboPreguntas.getItems().addAll(faqMap.keySet());
        comboPreguntas.setOnAction(e -> mostrarRespuesta());
        areaRespuesta.setEditable(false);
    }

    private void mostrarRespuesta() {
        String pregunta = comboPreguntas.getValue();
        if (pregunta != null && faqMap.containsKey(pregunta)) {
            areaRespuesta.setText(faqMap.get(pregunta));
        } else {
            areaRespuesta.setText("Selecciona una pregunta para ver la respuesta.");
        }
    }
}
