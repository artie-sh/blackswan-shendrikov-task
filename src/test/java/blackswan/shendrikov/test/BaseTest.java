package blackswan.shendrikov.test;

import auxiliary.Driver;
import auxiliary.EnvParams;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.AdvancedSearchPage;
import pages.GetStartedPage;
import pages.LogInPage;
import pages.RiskPage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseTest extends TestWatcher {

    protected LogInPage logInPage;
    protected GetStartedPage getStartedPage;
    protected RiskPage riskPage;
    AdvancedSearchPage advancedSearchPage;

    public final static Logger LOGGER = Logger.getLogger(BaseTest.class.getName());

    public BaseTest() { LOGGER.setLevel(Level.INFO); }

    protected static EnvParams envParams = new EnvParams();
    protected static String username = envParams.getUserName();
    protected static String password = envParams.getPassword();
    protected static String environment = envParams.getEnv();

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeClass
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

    @AfterClass
    public void tearDown() {
        if (envParams.doCloseBrowser()) {
            driver.close();
            driver.quit();
        }
    }
}
