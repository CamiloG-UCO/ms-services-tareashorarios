package co.edu.hotel.tareashorarioservice.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/monitoreo_tareas.feature",
        glue = "co.edu.hotel.tareashorarioservice.stepdefinitions",
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        plugin = {"pretty"}
)
public class MonitoreoTareasRunner {
}
