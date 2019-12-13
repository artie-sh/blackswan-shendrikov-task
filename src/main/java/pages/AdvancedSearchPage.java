package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

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
    private String searhContentCompanyName = "//span[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '%s')]";
    private By searchButton = By.xpath("//span[text()='SEARCH']/../../button");


    public AdvancedSearchPage(WebDriver driver, WebDriverWait wait) {
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

    public AdvancedSearchPage specifyCompanyNameAndJurisdiction(String companyName, String companyJurisdiction) {
        By companyNameBy = By.xpath(companyDetailsDialog + companyNameInput);
        By companyJurisdictionBy = By.xpath(companyDetailsDialog + companyJurisdictionInput);
        By companyJurisdictionOptionBy = By.xpath(String.format(companyJurisdictionOption, companyJurisdiction));

        waitUntilElementsVisible(Arrays.asList(companyNameBy, companyJurisdictionBy));
        driver.findElement(companyNameBy).sendKeys(companyName);
        driver.findElement(companyJurisdictionBy).sendKeys(companyJurisdiction);
        clickBy(companyJurisdictionOptionBy);
        clickBy(By.xpath(companyDetailsDialog + updateButton));
        waitUntilElementsNotVisible(Arrays.asList(By.xpath(companyDetailsDialog)));
        waitUntilElementsVisible(Arrays.asList(By.xpath(nodeSearchContent + searchContentCompany), By.xpath(String.format(nodeSearchContent + searhContentCompanyName, companyName.toLowerCase()))));
        return this;
    }
}
