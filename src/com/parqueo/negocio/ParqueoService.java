package com.parqueo.negocio;

import com.parqueo.datos.RegistroDAO;
import com.parqueo.datos.VehiculoDAO;
import com.parqueo.entidades.Registro;
import com.parqueo.entidades.Vehiculo;
import java.time.LocalDateTime;
import java.util.List;

public class ParqueoService {
    public static final double TARIFA_POR_HORA = 500.0;
    private final VehiculoDAO vehiculoDAO;
    private final RegistroDAO registroDAO;

    public ParqueoService() {
        this.vehiculoDAO = new VehiculoDAO();
        this.registroDAO = new RegistroDAO();
    }

    public Registro registrarIngreso(String placa, String tipo) {
        if (!Validador.placaValida(placa)) {
            throw new IllegalArgumentException("La placa es obligatoria");
        }
        if (!Validador.tipoValido(tipo)) {
            throw new IllegalArgumentException("El tipo de vehículo es inválido");
        }

        List<Registro> activos = vehiculoDAO.obtenerActivos();
        if (Validador.placaYaActiva(placa, activos)) {
            throw new IllegalStateException("El vehículo ya se encuentra registrado en el parqueo");
        }

        Vehiculo vehiculo = new Vehiculo(placa, tipo);
        Registro registro = new Registro(vehiculo, LocalDateTime.now());
        registro.setActivo(true);
        registro.setHoraSalida(null);
        registro.setMonto(0.0);
        vehiculoDAO.guardarActivo(registro);
        return registro;
    }

    public Registro registrarSalida(String registroId) {
        throw new UnsupportedOperationException("Disponible a partir de la versión 1.1");
    }

    public List<Registro> obtenerActivos() {
        return vehiculoDAO.obtenerActivos();
    }

    public List<Registro> obtenerHistorial() {
        return registroDAO.obtenerHistorial();
    }

    public void eliminarHistorial(String id) {
        registroDAO.eliminarDelHistorial(id);
    }
}
