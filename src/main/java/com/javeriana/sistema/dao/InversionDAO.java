package com.javeriana.sistema.dao;

import com.javeriana.sistema.model.Inversion;
import java.util.List;

public interface InversionDAO {
    void guardar(Inversion inversion);
    void actualizar(Inversion inversion);
    List<Inversion> listarPorUsuario(int usuarioId);
    List<Inversion> listarFinalizables();
}
