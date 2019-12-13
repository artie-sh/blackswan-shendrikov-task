package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

public class RiskPage extends BasePage {

    private By enrichTile = By.xpath("//div[contains(@class, 'enrich-tile')]");
    private By discoverTile = By.xpath("//div[contains(@class, 'discover-tile')]");
    private By predictTile = By.xpath("//div[contains(@class, 'predict-tile')]");
    private By actTile = By.xpath("//div[contains(@class, 'act-tile')]");
    private By manageTile = By.xpath("//div[contains(@class, 'manage-tile')]");

    private By advancedSearchTile = By.xpath("//p[contains(text(), 'Advanced Search')]/../../button");

    public RiskPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        waitUntilElementsPresent(Arrays.asList(enrichTile, discoverTile, predictTile, actTile, manageTile));
    }

    public AdvancedSearchPage getOntoAdvancedSearchPage() {
        clickBy(advancedSearchTile);
        return new AdvancedSearchPage(driver, wait);
    }
}
