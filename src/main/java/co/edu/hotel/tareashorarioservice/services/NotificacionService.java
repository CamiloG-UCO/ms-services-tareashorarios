package co.edu.hotel.tareashorarioservice.services;

import co.edu.hotel.tareashorarioservice.domain.Empleado;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {
    public void notificar(Empleado empleado, String mensaje) {
        // Simulación de notificación. En el futuro, aquí se consumirá la API de empleados.
        // Ejemplo para integración:
        // empleadoApiClient.notificar(empleado.getId(), mensaje);
        System.out.println("Notificación enviada a " + empleado.getNombreCompleto() + ": " + mensaje);
    }
}
