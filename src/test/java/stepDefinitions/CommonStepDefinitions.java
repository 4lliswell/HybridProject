package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.CartPage;
import utilities.ConfigReader;
import utilities.Driver;

public class CommonStepDefinitions {

    private static final Logger LOG = LogManager.getLogger(CommonStepDefinitions.class);

    @Then("kullanici tarayiciyi kapatir")
    public void kullaniciTarayiciyiKapatir() {
        Driver.closeDriver();
        LOG.info("Kullanici tarayiciyi kapatir");
    }

    @Given("kullanici URL'e gider {string}")
    public void kullaniciUrleGider(String url) {
        Driver.getDriver().get(ConfigReader.getProperty(url));
        LOG.info("Kullanici " + ConfigReader.getProperty(url)+" 'e gider ");
    }

}
