package co.edu.hotel.tareashorarioservice.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/control_asistencia_turno.feature",
        glue = "co.edu.hotel.tareashorarioservice.stepdefinitions",
        plugin = {"pretty", "html:target/cucumber-reports"},
        monochrome = true,
        objectFactory = io.cucumber.spring.SpringFactory.class
)
public class ControlAsistenciaTurnoRunner {
}