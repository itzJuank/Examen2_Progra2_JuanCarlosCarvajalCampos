package parqueo.entidades;

public class Vehiculo {
    private String placa;
    private String tipo;

    public Vehiculo() {
    }

    public Vehiculo(String placa, String tipo) {
        setPlaca(placa);
        setTipo(tipo);
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa == null ? "" : placa.trim().toUpperCase();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo == null ? "" : tipo.trim().toUpperCase();
    }

    @Override
    public String toString() {
        return "Vehiculo{" + "placa=" + placa + ", tipo=" + tipo + '}';
    }
}
