package com.parqueo.negocio;

import com.parqueo.datos.RegistroDAO;
import com.parqueo.datos.VehiculoDAO;
import com.parqueo.entidades.Registro;
import com.parqueo.entidades.Vehiculo;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
        List<Registro> activos = vehiculoDAO.obtenerActivos();
        Registro encontrado = null;
        List<Registro> restantes = new ArrayList<Registro>();
        for (Registro registro : activos) {
            if (registro.getId().equals(registroId)) {
                encontrado = registro;
            } else {
                restantes.add(registro);
            }
        }

        if (encontrado == null) {
            throw new IllegalArgumentException("No se encontró un registro activo con el id indicado");
        }

        encontrado.setHoraSalida(LocalDateTime.now());
        long minutos = ChronoUnit.MINUTES.between(encontrado.getHoraEntrada(), encontrado.getHoraSalida());
        double horas = Math.ceil(minutos / 60.0);
        encontrado.setMonto(Math.max(1, horas) * TARIFA_POR_HORA);
        encontrado.setActivo(false);

        vehiculoDAO.actualizarActivos(restantes);
        registroDAO.guardarEnHistorial(encontrado);
        return encontrado;
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
