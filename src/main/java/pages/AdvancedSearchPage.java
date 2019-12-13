package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AdvancedSearchPage extends BasePage {

    private By advancedSearchSpan = By.xpath("//span[contains(text(), 'ADVANCED SEARCH')]");
    private String entityTypeDropdown = "//div[contains(text(), 'Entity Types')]/following-sibling::div//button[@id='single-button' and contains(@aria-expanded, '%s')]";
    private String entityTypeOption = "/following-sibling::ul//a[contains(text(), '%s')]";

    private String companyDetailsDialog = "//h4[contains(text(), 'Company')]/../../../div[contains(@class, 'modal-content')]";
    private String companyNameInput = "//form//input[@id='companyname']";
    private String companyJurisdictionInput = "//form//input[@id='input']";
    private String companyJurisdictionOption = "//div[@id='mCSB_17']//span[contains(., '%s')]";
    private String updateButton = "//button[contains(text(), 'Update')]";

    private String nodeSearchContent = "//div[@id='nodeSearchContent']";
    private String searchContentCompany = "//span[contains(text(), 'company')]";
    private String searchContentCompanyName = "//span[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '%s')]";
    private By searchButton = By.xpath("//span[text()='SEARCH']/../../button");

    private By searchSpinner = By.xpath("//div[contains(@class, 'full-page-spinner')]//i[contains(@class, 'fa-spinner')]");
    private String searchResult = "//div[@id='widgetFilter']//div[contains(@class, 'search-results-wrapper')]//h4[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '%s')]";

    AdvancedSearchPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        waitUntilElementsVisible(Arrays.asList(advancedSearchSpan, By.xpath(String.format(entityTypeDropdown, false))));
    }

    public AdvancedSearchPage selectCompanyEntityType() {
        clickBy(By.xpath(String.format(entityTypeDropdown, false)));
        By companyOption = By.xpath(String.format(entityTypeDropdown + entityTypeOption, true, "Company"));
        waitUntilElementsVisible(Arrays.asList(companyOption));
        clickBy(companyOption);
        return this;
    }

    public AdvancedSearchPage specifyCompanyNameJurisdictionAndRunSearch(Map<String,String> companyDetails) {
        String companyName = companyDetails.get("companyName");
        String companyJurisdiction = companyDetails.get("companyJurisdiction");

        By companyNameBy = By.xpath(companyDetailsDialog + companyNameInput);
        By companyJurisdictionBy = By.xpath(companyDetailsDialog + companyJurisdictionInput);
        By companyJurisdictionOptionBy = By.xpath(String.format(companyJurisdictionOption, companyJurisdiction));

        waitUntilElementsVisible(Arrays.asList(companyNameBy, companyJurisdictionBy));
        driver.findElement(companyNameBy).sendKeys(companyName);
        driver.findElement(companyJurisdictionBy).sendKeys(companyJurisdiction);
        clickBy(companyJurisdictionOptionBy);
        clickBy(By.xpath(companyDetailsDialog + updateButton));
        waitUntilElementsNotVisible(Arrays.asList(By.xpath(companyDetailsDialog)));
        waitUntilElementsVisible(Arrays.asList(By.xpath(nodeSearchContent + searchContentCompany), By.xpath(String.format(nodeSearchContent + searchContentCompanyName, companyName.toLowerCase())), searchButton));
        clickBy(searchButton);
        return this;
    }

    public long checkSearchResults(String companyName) {
        waitUntilElementsVisible(Arrays.asList(searchSpinner));
        long startTimestamp = System.currentTimeMillis();
        wait.withTimeout(120, TimeUnit.SECONDS).until(ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(searchSpinner)));
        waitUntilElementsPresent(Arrays.asList(By.xpath(String.format(searchResult, ""))));
        waitUntilElementsVisible(Arrays.asList(By.xpath(String.format(searchResult, companyName))));
        long endTimestamp = System.currentTimeMillis();
        return (endTimestamp-startTimestamp)/1000;
    }

    public long switchToCompanyDetails(String companyName) {
        scrollAndClick(By.xpath(String.format(searchResult, companyName)));
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        waitForPageLoad(wait);
        waitUntilElementsVisible(Arrays.asList(searchSpinner));
        long startTimestamp = System.currentTimeMillis();
        try {
            wait.withTimeout(60, TimeUnit.SECONDS).until(ExpectedConditions.not(ExpectedConditions.visibilityOfAllElementsLocatedBy(searchSpinner)));
        } catch (StaleElementReferenceException e) {
            System.out.println("StaleElementReferenceException");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {}
            long exceptionTimestamp = System.currentTimeMillis();
            wait.withTimeout(60 - (exceptionTimestamp-startTimestamp)/1000, TimeUnit.SECONDS).until(ExpectedConditions.not(ExpectedConditions.visibilityOfAllElementsLocatedBy(searchSpinner)));
        }
        waitUntilElementsVisible(Arrays.asList(By.xpath(entityResultsWrapper)));
        long endTimestamp = System.currentTimeMillis();
        return (endTimestamp-startTimestamp)/1000;
    }
}
