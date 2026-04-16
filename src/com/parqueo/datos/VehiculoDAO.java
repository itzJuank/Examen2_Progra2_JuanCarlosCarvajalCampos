package com.parqueo.datos;

import com.parqueo.entidades.Registro;
import com.parqueo.entidades.Vehiculo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {
    private static final String ARCHIVO_ACTIVOS = "activos.txt";

    public List<Registro> obtenerActivos() {
        List<String> lineas = ArchivoHelper.leerLineas(ARCHIVO_ACTIVOS);
        List<Registro> registros = new ArrayList<Registro>();
        for (String linea : lineas) {
            if (linea == null || linea.trim().isEmpty()) {
                continue;
            }
            String[] partes = linea.split("\\|");
            if (partes.length < 4) {
                continue;
            }
            Vehiculo vehiculo = new Vehiculo(partes[1], partes[2]);
            Registro registro = new Registro();
            registro.setId(partes[0]);
            registro.setVehiculo(vehiculo);
            registro.setHoraEntrada(LocalDateTime.parse(partes[3]));
            registro.setActivo(true);
            registro.setMonto(0.0);
            registro.setHoraSalida(null);
            registros.add(registro);
        }
        return registros;
    }

    public void guardarActivo(Registro r) {
        String linea = r.getId() + "|" + r.getVehiculo().getPlaca() + "|" + r.getVehiculo().getTipo()
                + "|" + r.getHoraEntrada();
        ArchivoHelper.agregarLinea(ARCHIVO_ACTIVOS, linea);
    }

    public void actualizarActivos(List<Registro> lista) {
        List<String> lineas = new ArrayList<String>();
        for (Registro r : lista) {
            lineas.add(r.getId() + "|" + r.getVehiculo().getPlaca() + "|" + r.getVehiculo().getTipo()
                    + "|" + r.getHoraEntrada());
        }
        ArchivoHelper.escribirLineas(ARCHIVO_ACTIVOS, lineas);
    }

    public boolean existePlacaActiva(String placa) {
        List<Registro> activos = obtenerActivos();
        for (Registro registro : activos) {
            if (registro.getVehiculo().getPlaca().equalsIgnoreCase(placa)) {
                return true;
            }
        }
        return false;
    }
}
