package co.edu.hotel.tareashorarioservice.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class ControlAsistenciaTurnoStepDefinitions {

    @Given("el empleado {string} tiene turno asignado de {string} a {string} el dia {string}")
    public void asignarTurno(String empleado, String horaInicio, String horaFin, String fecha) {
        System.out.println("Empleado: " + empleado + " - Turno: " + horaInicio + " a " + horaFin + " Fecha: " + fecha);
    }

    @When("el usuario registra su llegada con codigo o tarjeta a las {string}")
    public void registrarLlegada(String horaEntrada) {
        System.out.println("Llegada registrada a las: " + horaEntrada);
    }

    @Then("el sistema debe marcar {string}")
    public void validarEntrada(String mensaje) {
        System.out.println("Resultado: " + mensaje);
    }

    @When("registra salida a las {string}")
    public void registrarSalida(String horaSalida) {
        System.out.println("Salida registrada a las: " + horaSalida);
    }

    @Then("el sistema debe calcular el total trabajado {string}")
    public void calcularHoras(String total) {
        System.out.println("Total trabajado: " + total);
    }

    @Then("guardar el registro en el historial de asistencia del empleado")
    public void guardarHistorial() {
        System.out.println("Historial actualizado correctamente.");
    }
}