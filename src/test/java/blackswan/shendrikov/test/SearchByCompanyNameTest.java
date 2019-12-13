package blackswan.shendrikov.test;

import org.testng.annotations.Test;
import pages.AdvancedSearchPage;
import pages.GetStartedPage;
import pages.LogInPage;
import pages.RiskPage;

public class SearchByCompanyNameTest extends BaseTest {

    @Test
    public void logInAndSearchByCompanyNameTest() {
        LogInPage logInPage = new LogInPage(driver, wait);
        GetStartedPage getStartedPage = logInPage.logIn(username, password, environment);
        RiskPage riskPage = getStartedPage.getOntoRiskPage();
        AdvancedSearchPage advancedSearchPage = riskPage.getOntoAdvancedSearchPage();
        advancedSearchPage.selectCompanyEntityType();
        advancedSearchPage.specifyCompanyNameAndJurisdiction("BlackSwan", "United State");
    }
}
