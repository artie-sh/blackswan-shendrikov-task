package pages;

import auxiliary.EnvParams;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Arrays;
import java.util.List;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    EnvParams envParams;

    BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        envParams = new EnvParams();
    }

    protected void waitUntilElementsVisible(List<By> elements) {
        for (By by : elements) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }
    }

    protected void waitUntilElementsNotVisible(List<By> elements) {
        for (By by : elements) {
            wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfAllElementsLocatedBy(by)));
        }
    }

    protected void waitUntilElementsPresent(List<By> elements) {
        for (By by : elements) {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        }
    }

    protected void waitUntilElementsNotPresent(List<By> elements) {
        for (By by : elements) {
            wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(by)));
        }
    }

    protected boolean isElementVisible(By locatorKey) {
        boolean result;
        try {
            result = driver.findElement(locatorKey).isDisplayed();
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    protected void clickBy(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    protected WebElement scrollToView(By element) {
        WebElement desiredElement = wait.until(ExpectedConditions.presenceOfElementLocated(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", desiredElement);
        waitUntilElementsVisible(Arrays.asList(element));
        return desiredElement;
    }
}
