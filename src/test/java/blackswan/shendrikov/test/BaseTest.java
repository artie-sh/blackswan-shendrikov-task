package blackswan.shendrikov.test;

import auxiliary.Driver;
import auxiliary.EnvParams;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseTest extends TestWatcher {

    public final static Logger LOGGER = Logger.getLogger(BaseTest.class.getName());

    public BaseTest() { LOGGER.setLevel(Level.INFO); }

    protected static EnvParams envParams = new EnvParams();
    protected static String username = envParams.getUserName();
    protected static String password = envParams.getPassword();
    protected static String environment = envParams.getEnv();

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void startUp() {
        try {
            driver = Driver.getDriver();
            wait = new WebDriverWait(driver, envParams.getWebdriverWaitTimeSeconds());
        } catch (Exception e) {
            LOGGER.info("Exception on startUp()");
            e.printStackTrace();
            driver.close();
        }
    }

    @AfterMethod
    public void tearDown(ITestResult iTestResult) {
        if (envParams.doCloseBrowser()) {
            driver.close();
            driver.quit();
        }
    }
}