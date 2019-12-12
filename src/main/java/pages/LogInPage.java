package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

public class LogInPage extends BasePage {

    private By logInPageMottoText = By.xpath("//p[contains(text(), 'Intelligence') and contains(text(), 'of Everything')]");
    private By logInUsernameInput = By.xpath("//span[contains(text(), 'Username or Email address')]/preceding-sibling::input[@id='username']");
    private By logInPasswordInput = By.xpath("//span[contains(text(), 'Password')]/preceding-sibling::input[@id='password']");
    private By signInButton = By.xpath("//button[@id='signIn']");

    public LogInPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public LogInPage logIn(String username, String password, String environment) {
        driver.get(environment);
        waitUntilElementsVisible(Arrays.asList(logInPageMottoText, logInUsernameInput, logInPasswordInput, signInButton));
        driver.findElement(logInUsernameInput).sendKeys(username);
        driver.findElement(logInPasswordInput).sendKeys(password);
        return this;
    }
}
