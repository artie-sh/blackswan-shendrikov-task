package blackswan.shendrikov.test;

import auxiliary.RetryAnalyzer;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import pages.CompanyDetailsPage;
import pages.LogInPage;

import java.util.HashMap;
import java.util.Map;

public class SearchByCompanyNameTest extends BaseTest {

    private Map<String,String> companyDetails = new HashMap<String, String>() {{
        put("companyName", "facebook, inc.");
        put("companyJurisdiction", "United State");
        put("website", "http://www.facebook.com");
        put("status", "active");
        put("countryOfOrigin", "us");
    }};

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "User logs in and proceeds to the Risk page")
    public void logInAndGetOntoRiskPage() {
        logInPage = new LogInPage(driver, wait);
        getStartedPage = logInPage.logIn(username, password, environment);
        riskPage = getStartedPage.getOntoRiskPage();
    }

    @Test(dependsOnMethods = {"logInAndGetOntoRiskPage"}, retryAnalyzer = RetryAnalyzer.class, description = "User specifies company, jurisdiction and runs search")
    public void getOntoAdvancedSearchPageAndSearchCompany() {
        advancedSearchPage = riskPage.getOntoAdvancedSearchPage();
        advancedSearchPage.selectCompanyEntityType();
        advancedSearchPage.specifyCompanyNameJurisdictionAndRunSearch(companyDetails);
        long searchTime = advancedSearchPage.checkSearchResults("facebook, inc.");
        LOGGER.info("search time " + searchTime);
        assertTrue(searchTime <= 120, String.format("Search time for %s took longer than 120 seconds", companyDetails.get("companyName")));
    }

    @Test(dependsOnMethods = {"getOntoAdvancedSearchPageAndSearchCompany"}, retryAnalyzer = RetryAnalyzer.class, description = "User switches to details tab of the given company")
    public void switchToCompanyDetails() {
        long resultDetailsTime = advancedSearchPage.switchToCompanyDetails(companyDetails.get("companyName"));
        LOGGER.info("result details time " + resultDetailsTime);
        assertTrue(resultDetailsTime <= 60, String.format("Search time for %s took longer than 60 seconds", companyDetails.get("companyName")));

        CompanyDetailsPage companyDetailsPage = new CompanyDetailsPage(driver, wait);
        long dataLoadingTime = companyDetailsPage.checkDataLoading();
        LOGGER.info("data loading time " + dataLoadingTime);
        assertTrue(resultDetailsTime <= 300-resultDetailsTime, String.format("Result details time for %s took longer than 300 seconds", companyDetails.get("companyName")));
    }

    @Test(dependsOnMethods = {"switchToCompanyDetails"}, retryAnalyzer = RetryAnalyzer.class, description = "User checks intro for the given company")
    public void checkCompanyIntro() {
        CompanyDetailsPage companyDetailsPage = new CompanyDetailsPage(driver, wait);
        companyDetailsPage.checkCompanyIntro(companyDetails);
    }

    @Test(dependsOnMethods = {"switchToCompanyDetails"}, retryAnalyzer = RetryAnalyzer.class, description = "User checks intro for the given company")
    public void switchToFinancialAndAssertData() {
        CompanyDetailsPage companyDetailsPage = new CompanyDetailsPage(driver, wait);
        int dataNotFound = companyDetailsPage.checkFinancial();
        LOGGER.info("data not found time " + dataNotFound);
        assertTrue(dataNotFound <= 2, String.format("%s data not found assertion error: limit 2, actual %s", companyDetails.get("companyName"), dataNotFound));
    }
}
