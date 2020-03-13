package blackswan.shendrikov.test;

import auxiliary.Driver;
import auxiliary.EnvParams;
import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            System.out.println("starting driver");
            driver = Driver.getDriver();
            System.out.println("driver started");
            wait = new WebDriverWait(driver, envParams.getWebdriverWaitTimeSeconds());
        } catch (Exception e) {
            LOGGER.info("Exception on startUp()");
            e.printStackTrace();
            driver.close();
        }
    }

    @AfterMethod
    public void tearDown(ITestResult iTestResult) {

        String className = iTestResult.getTestClass().getName();
        takeScreen(className.substring(className.lastIndexOf(".") + 1));

        if (envParams.doCloseBrowser()) {
            driver.close();
            driver.quit();
        }

    }

    private void takeScreen(String screenShotName) {
        try {
            screenShotName = screenShotName + "-" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File("target/screenshots/" + screenShotName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
