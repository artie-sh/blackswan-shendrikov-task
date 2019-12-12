package blackswan.shendrikov.test;

import org.testng.annotations.Test;
import pages.LogInPage;

public class SearchByCompanyNameTest extends BaseTest {

    @Test
    public void logInAndSearchByCompanyName() {

        LogInPage logInPage = new LogInPage(driver, wait);
        logInPage.logIn(username, password, environment);
    }
}
