package co.edu.hotel.tareashorarioservice.controller;

import co.edu.hotel.tareashorarioservice.domain.Empleado;
import co.edu.hotel.tareashorarioservice.domain.Turno;
import co.edu.hotel.tareashorarioservice.dto.PlanSemanalRequest;
import co.edu.hotel.tareashorarioservice.services.NotificacionService;
import co.edu.hotel.tareashorarioservice.services.PlanSemanalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plan-semanal")
public class PlanSemanalController {

    @Autowired
    private PlanSemanalService planSemanalService;
    @Autowired
    private NotificacionService notificacionService;

    @PostMapping
    public List<String> agendarTurnos(@RequestBody PlanSemanalRequest request) {
        LocalDate inicio = LocalDate.parse(request.getFechaInicio());
        LocalDate fin = LocalDate.parse(request.getFechaFin());
        planSemanalService.asignarTurnos(request.getEmpleados(), inicio, fin);
        return request.getEmpleados().stream()
                .map(nombre -> nombre + " se te agendaron turnos desde " + request.getFechaInicio() + " hasta " + request.getFechaFin())
                .collect(Collectors.toList());
    }

}

