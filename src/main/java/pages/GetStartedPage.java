package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

public class GetStartedPage extends BasePage {

    private By riskTile = By.xpath("//div[contains(@class, 'insurance-risk-tile')]");
    private By amlTile = By.xpath("//div[contains(@class, 'aml-tile')]");
    private By financialTile = By.xpath("//div[contains(@class, 'financial-tile')]");
    private By marketTile = By.xpath("//div[contains(@class, 'market-tile')]");
    private By creditCardsTile = By.xpath("//div[contains(@class, 'credit-cards-tile')]");

    //for some reason, socialListeningTile and insuranceTile have the same class social-listening-tile
    private By socialListeningTile = By.xpath("(//div[contains(@class, 'social-listening-tile')])[1]");
    private By insuranceTile = By.xpath("(//div[contains(@class, 'social-listening-tile')])[2]");

    public GetStartedPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        waitUntilElementsPresent(Arrays.asList(riskTile, amlTile, financialTile, marketTile, creditCardsTile, socialListeningTile, insuranceTile));
    }

    public RiskPage getOntoRiskPage() {
        clickBy(riskTile);
        return new RiskPage(driver, wait);
    }
}
