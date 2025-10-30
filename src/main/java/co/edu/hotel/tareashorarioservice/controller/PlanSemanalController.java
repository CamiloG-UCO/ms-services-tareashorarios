package co.edu.hotel.tareashorarioservice.controller;

import co.edu.hotel.tareashorarioservice.dto.PlanSemanalRequest;
import co.edu.hotel.tareashorarioservice.dto.PlanSemanalResponse;
import co.edu.hotel.tareashorarioservice.services.NotificacionService;
import co.edu.hotel.tareashorarioservice.services.PlanSemanalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/plan-semanal")
public class PlanSemanalController {

    @Autowired
    private PlanSemanalService planSemanalService;
    @Autowired
    private NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<PlanSemanalResponse> agendarTurnos(@RequestBody PlanSemanalRequest request) {
        List<String> mensajes = new ArrayList<>();
        try {
            LocalDate inicio = LocalDate.parse(request.getFechaInicio());
            LocalDate fin = LocalDate.parse(request.getFechaFin());
            planSemanalService.asignarTurnos(request.getEmpleados(), inicio, fin);
            for (String nombre : request.getEmpleados()) {
                String mensaje = nombre + " se te agendaron turnos desde " + request.getFechaInicio() + " hasta " + request.getFechaFin();
                // Simulación de notificación
                notificacionService.notificarSimulado(nombre, mensaje);
                // Cuando tengas la API de empleados, descomenta la siguiente línea:
                // notificacionService.notificarEmpleadoApi(nombre, mensaje);
                mensajes.add(mensaje);
            }
            return ResponseEntity.ok(new PlanSemanalResponse(true, mensajes, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new PlanSemanalResponse(false, null, e.getMessage()));
        }
    }
}
