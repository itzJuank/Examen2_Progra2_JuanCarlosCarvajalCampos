package parqueo.datos;

import parqueo.entidades.Registro;
import parqueo.entidades.Vehiculo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegistroDAO {
    private static final String ARCHIVO_HISTORIAL = "historial.txt";

    public List<Registro> obtenerHistorial() {
        List<String> lineas = ArchivoHelper.leerLineas(ARCHIVO_HISTORIAL);
        List<Registro> historial = new ArrayList<Registro>();
        for (String linea : lineas) {
            if (linea == null || linea.trim().isEmpty()) {
                continue;
            }
            String[] partes = linea.split("\\|");
            if (partes.length < 6) {
                continue;
            }
            Registro registro = new Registro();
            registro.setId(partes[0]);
            registro.setVehiculo(new Vehiculo(partes[1], partes[2]));
            registro.setHoraEntrada(LocalDateTime.parse(partes[3]));
            registro.setHoraSalida(LocalDateTime.parse(partes[4]));
            registro.setMonto(Double.parseDouble(partes[5]));
            registro.setActivo(false);
            historial.add(registro);
        }
        return historial;
    }

    public void guardarEnHistorial(Registro r) {
        String linea = r.getId() + "|" + r.getVehiculo().getPlaca() + "|" + r.getVehiculo().getTipo()
                + "|" + r.getHoraEntrada() + "|" + r.getHoraSalida() + "|" + r.getMonto();
        ArchivoHelper.agregarLinea(ARCHIVO_HISTORIAL, linea);
    }

    public void eliminarDelHistorial(String id) {
        List<Registro> historial = obtenerHistorial();
        List<String> lineas = new ArrayList<String>();
        for (Registro registro : historial) {
            if (!registro.getId().equals(id)) {
                lineas.add(registro.getId() + "|" + registro.getVehiculo().getPlaca() + "|"
                        + registro.getVehiculo().getTipo() + "|" + registro.getHoraEntrada() + "|"
                        + registro.getHoraSalida() + "|" + registro.getMonto());
            }
        }
        ArchivoHelper.escribirLineas(ARCHIVO_HISTORIAL, lineas);
    }
}
