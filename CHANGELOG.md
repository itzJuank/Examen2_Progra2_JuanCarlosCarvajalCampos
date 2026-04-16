## CHANGELOG

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
