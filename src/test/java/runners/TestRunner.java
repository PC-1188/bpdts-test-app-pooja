package runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/APITests",
        glue = {"stepDefinitions"},
        tags ={"@APITests"},
        plugin = {"json:target/cucumber.json","pretty", "html:target/site/cucumber-pretty"},
        monochrome = true,
        strict = true
)
	public class TestRunner {
	}

