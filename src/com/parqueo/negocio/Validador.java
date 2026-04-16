package com.parqueo.negocio;

import com.parqueo.entidades.Registro;
import java.util.List;

public class Validador {
    private Validador() {
    }

    public static boolean placaValida(String placa) {
        return placa != null && !placa.trim().isEmpty();
    }

    public static boolean tipoValido(String tipo) {
        return "CARRO".equals(tipo) || "MOTO".equals(tipo);
    }

    public static boolean placaYaActiva(String placa, List<Registro> activos) {
        if (placa == null) {
            return false;
        }
        String placaNormalizada = placa.trim().toUpperCase();
        for (Registro registro : activos) {
            if (registro.isActivo() && registro.getHoraSalida() == null
                    && registro.getVehiculo().getPlaca().equalsIgnoreCase(placaNormalizada)) {
                return true;
            }
        }
        return false;
    }
}
