package co.edu.hotel.tareashorarioservice.controller;

import co.edu.hotel.tareashorarioservice.domain.AsistenciaTurno;
import co.edu.hotel.tareashorarioservice.services.AsistenciaTurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/asistencia")
public class AsistenciaTurnoController {

    @Autowired
    private AsistenciaTurnoService asistenciaTurnoService;

    @PostMapping("/entrada/{empleadoId}")
    public AsistenciaTurno registrarEntrada(@PathVariable Long empleadoId) {
        return asistenciaTurnoService.registrarEntrada(empleadoId);
    }

    @PostMapping("/salida/{empleadoId}")
    public AsistenciaTurno registrarSalida(@PathVariable Long empleadoId) {
        return asistenciaTurnoService.registrarSalida(empleadoId);
    }
}
