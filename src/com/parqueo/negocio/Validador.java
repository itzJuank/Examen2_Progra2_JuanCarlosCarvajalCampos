package com.parqueo.negocio;

import com.parqueo.entidades.Registro;
import java.util.List;
import java.util.regex.Pattern;

public class Validador {
    private static final Pattern PATRON_PLACA = Pattern.compile("^(?=.{5,8}$)(?=.*[A-Z])(?=.*\\d)[A-Z0-9-]+$");

    private Validador() {
    }

    public static boolean placaValida(String placa) {
        if (placa == null) {
            return false;
        }
        String placaNormalizada = placa.trim().toUpperCase();
        return !placaNormalizada.isEmpty()
                && !placaNormalizada.contains(" ")
                && PATRON_PLACA.matcher(placaNormalizada).matches();
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
