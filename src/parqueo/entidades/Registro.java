package parqueo.entidades;

import java.time.LocalDateTime;
import java.util.UUID;

public class Registro {
    private String id;
    private Vehiculo vehiculo;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    private double monto;
    private boolean activo;

    public Registro() {
        this.id = UUID.randomUUID().toString();
        this.monto = 0.0;
        this.activo = true;
    }

    public Registro(Vehiculo vehiculo, LocalDateTime horaEntrada) {
        this();
        this.vehiculo = vehiculo;
        this.horaEntrada = horaEntrada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null || id.trim().isEmpty() ? UUID.randomUUID().toString() : id;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Registro{" + "id=" + id + ", vehiculo=" + vehiculo + ", horaEntrada=" + horaEntrada
                + ", horaSalida=" + horaSalida + ", monto=" + monto + ", activo=" + activo + '}';
    }
}
