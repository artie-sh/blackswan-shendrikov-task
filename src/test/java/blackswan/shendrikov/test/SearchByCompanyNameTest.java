package blackswan.shendrikov.test;

import org.testng.annotations.Test;
import pages.GetStartedPage;
import pages.LogInPage;

public class SearchByCompanyNameTest extends BaseTest {

    @Test
    public void logInAndSearchByCompanyNameTest() {
        LogInPage logInPage = new LogInPage(driver, wait);
        GetStartedPage getStartedPage = logInPage.logIn(username, password, environment);
    }
}
