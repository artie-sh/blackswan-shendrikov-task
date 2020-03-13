package blackswan.shendrikov.test;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import pages.GetStartedPage;
import pages.LogInPage;

public class GoogleTest extends BaseTest {

    @Test
    public void googleTest() {
        System.out.println(">>> starting logInAndSearchByCompanyNameTest");
        driver.get("https://www.google.pl/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@title='Search']")));
    }
}
