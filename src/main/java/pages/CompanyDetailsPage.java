package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class CompanyDetailsPage extends BasePage {

    private String loadingIndicator = "//div[contains(@class, 'entity-result-item')]//div[contains(text(), 'Loading...')]";

    private String companyIntro = "//div[contains(@class, 'entity-company-intro')]";
    private String companyH2 = "//h2[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '%s')]";
    private String countryOfOrigin = "//span[contains(text(), 'Country Of Origin:')]/following-sibling::span[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '%s')]";
    private String webSite = "//span[contains(text(), 'Website:')]/following-sibling::a[contains(text(), '%s')]";
    private String companyStatus = "//span[contains(text(), 'Company Status:')]/following-sibling::span[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '%s')]";

    private String financialLi = "//li[contains(@ng-class, 'Financial')]";
    private String financial = "//span[contains(text(), 'Financial')]";
    private String financialEntityItem = "//div[contains(@ng-show, 'Financial Growth')]//div[contains(@class, 'entity-item') and contains(@class, 'col')]";
    private String dataNotFound = "//p[contains(text(), 'Data') and contains(text(), 'Not Found')]";


    public CompanyDetailsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        waitUntilElementsVisible(Arrays.asList(By.xpath(entityResultsWrapper)));
    }

    public long checkDataLoading() {
        long startTimestamp = System.currentTimeMillis();
        wait.withTimeout(300, TimeUnit.SECONDS).until(ExpectedConditions.not(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(entityResultsWrapper + loadingIndicator))));
        long endTimestamp = System.currentTimeMillis();
        wait = new WebDriverWait(driver, envParams.getWebdriverWaitTimeSeconds());
        return (endTimestamp-startTimestamp)/1000;
    }

    public CompanyDetailsPage checkCompanyIntro(Map<String,String> companyDetails) {
        waitUntilElementsVisible(Arrays.asList(
                By.xpath(String.format(companyIntro + companyH2, companyDetails.get("companyName").toLowerCase().replace(",", "").replace(".", ""))),
                By.xpath(String.format(companyIntro + countryOfOrigin, companyDetails.get("countryOfOrigin"))),
                By.xpath(String.format(companyIntro + companyStatus, companyDetails.get("status"))),
                By.xpath(String.format(companyIntro + webSite, companyDetails.get("website")))
        ));
        return this;
    }

    public int checkFinancial() {
        Actions builder = new Actions(driver);
        builder.moveToElement(driver.findElement(By.xpath(financialLi))).perform();
        waitUntilElementsVisible(Arrays.asList(By.xpath(financialLi + financial)));
        builder.moveToElement(driver.findElement(By.xpath(financialLi + financial))).click().perform();
        waitUntilElementsPresent(Arrays.asList(By.xpath(financialEntityItem)));
        waitUntilElementsNotPresent(Arrays.asList(By.xpath(financialEntityItem + loadingIndicator)));
        return driver.findElements(By.xpath(financialEntityItem + dataNotFound)).size();
    }
}
