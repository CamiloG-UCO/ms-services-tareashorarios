package co.edu.hotel.tareashorarioservice.services;

import co.edu.hotel.tareashorarioservice.repository.NotificacionTurnoRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {
    private NotificacionTurnoRepository notificacionTurnoRepository;

    public void notificarSimulado(String nombreEmpleado, String mensaje) {
        // Simulación de notificación. Aquí se podría guardar en la BD o simplemente imprimir.
        System.out.println("Notificación simulada para " + nombreEmpleado + ": " + mensaje);
    }

    // Cuando tengas la API de empleados, descomenta y usa este método:
    // public void notificarEmpleadoApi(String nombreEmpleado, String mensaje) {
    //     // Aquí iría el consumo real de la API de empleados para notificar
    // }
}
