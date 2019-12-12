package auxiliary;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    int retryAttemptsCounter = 0;
    int maxRetryLimit = new EnvParams().RETRY_ATTEMPTS_NUMBER;

    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if(retryAttemptsCounter < maxRetryLimit-1){
                retryAttemptsCounter++;
                return true;
            }
        }
        return false;
    }
}
