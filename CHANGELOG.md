## CHANGELOG

### v1.3 - Dashboard visual y simplificación de paquete raíz
- El paquete raíz pasó de `com.parqueo` a `parqueo`
- La interfaz principal ahora incluye tarjetas de métricas en vivo
- Se modernizó la pestaña de ingreso con un panel visual más tipo dashboard
- Se mantuvo el formato de fecha de 12 horas y el reloj en vivo en presentación

### v1.2.2 - Eliminación de metadatos de NetBeans
- Se eliminó la carpeta `nbproject` del repositorio
- `build.xml` volvió a una configuración Ant independiente
- README y configuración del proyecto ajustados para ejecución standalone

### v1.2.1 - Ajustes de validación y sincronización con NetBeans
- Validación de placa reforzada para aceptar solo formatos alfanuméricos válidos
- Mensaje de error de placa actualizado para orientar al usuario
- Archivos `nbproject` sincronizados con la configuración generada por NetBeans

### v1.2 - Mejoras de interfaz y validaciones
- Mensajes de error y éxito con color directamente en los paneles (sin JOptionPane)
- Confirmación de eliminación mediante JDialog personalizado
- Mejoras de layout y alineación en todos los formularios
- Validaciones reforzadas en campos obligatorios

### v1.1 - Registro de salida y cálculo de cobro
- Registro de salida con hora automática
- Cálculo de monto: ₡500 por hora o fracción, mínimo una hora
- Persistencia del historial en historial.txt
- Eliminación de vehículos de activos al registrar salida

### v1.0 - Registro de ingreso operativo
- Registro de entrada con placa, tipo y hora automática
- Persistencia de vehículos activos en activos.txt
- Validación de unicidad: no se permite la misma placa activa dos veces
