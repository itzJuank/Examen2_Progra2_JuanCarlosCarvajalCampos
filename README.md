# Examen2_Progra2_JuanCarlosCarvajalCampos

## Sistema de Gestión de Parqueo Público

Aplicación de escritorio en Java Swing para registrar ingresos, salidas, cobros y consultar el historial de un parqueo público. El proyecto queda como aplicación Java standalone compilable con Apache Ant.

## Regla de validación de placas

La placa debe cumplir estas condiciones:
- Tener entre 5 y 8 caracteres
- Contener al menos una letra y al menos un número
- Usar solo letras mayúsculas, números o guion `-`
- No incluir espacios

## Instrucciones para compilar con Ant

```bash
ant compile
```

## Instrucciones para ejecutar

```bash
ant run
```

## Estructura del proyecto

```text
.
├── CHANGELOG.md
├── README.md
├── build.xml
├── manifest.mf
├── prompts.txt
└── src/
    └── com/parqueo/
        ├── datos/
            │   ├── ArchivoHelper.java
        │   ├── RegistroDAO.java
        │   └── VehiculoDAO.java
        ├── entidades/
        │   ├── Registro.java
        │   └── Vehiculo.java
        ├── negocio/
        │   ├── ParqueoService.java
        │   └── Validador.java
        └── presentacion/
            ├── EstilosUI.java
            ├── MainFrame.java
            ├── PanelActivos.java
            ├── PanelDecorativoIngreso.java
            ├── PanelHistorial.java
            ├── PanelIngreso.java
            ├── PanelTarjeta.java
            └── PanelSalida.java
```
