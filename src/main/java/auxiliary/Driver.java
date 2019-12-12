package auxiliary;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Driver {

    protected static WebDriver driver;

    public static WebDriver getDriver() {
        EnvParams envParams = new EnvParams();
        ChromeOptions options = new ChromeOptions();
        if (!envParams.getChromeBinary().equals("")) { options.setBinary(envParams.getChromeBinary()); }
        String chromeDriverOptions = envParams.getChromeDriverOptions();
        options.addArguments(chromeDriverOptions.split(","));
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
        dc.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(dc);
        driver.manage().window().maximize();
        return driver;
    }
}
