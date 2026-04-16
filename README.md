# Examen2_Progra2_JuanCarlosCarvajalCampos

## Sistema de Gestión de Parqueo Público

Aplicación de escritorio en Java Swing para registrar ingresos, salidas, cobros y consultar el historial de un parqueo público. El proyecto está preparado para abrirse en NetBeans y compilarse con Apache Ant.

## Instrucciones para abrir en NetBeans

1. Abrir NetBeans.
2. Seleccionar **File > Open Project**.
3. Elegir la carpeta del repositorio.

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
├── nbproject/
│   ├── project.properties
│   └── project.xml
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
            ├── MainFrame.java
            ├── PanelActivos.java
            ├── PanelHistorial.java
            ├── PanelIngreso.java
            └── PanelSalida.java
```
