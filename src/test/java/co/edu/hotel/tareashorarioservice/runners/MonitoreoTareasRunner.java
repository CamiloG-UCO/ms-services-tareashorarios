package co.edu.hotel.tareashorarioservice.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.SpringFactory;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/monitoreo_tareas.feature",
        glue = "co.edu.hotel.tareashorarioservice.stepdefinitions",
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true,
        objectFactory = SpringFactory.class
)
public class MonitoreoTareasRunner {
}
