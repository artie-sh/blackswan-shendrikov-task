package auxiliary;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class EnvParams {

    private String environment;
    private String chromeBinary;

    private String userName;
    private String password;

    int WEBDRIVER_WAIT_TIME_SECONDS;
    int RETRY_ATTEMPTS_NUMBER;
    boolean CLOSE_BROWSER = true;

    private String chromeDriverOptions;

    public EnvParams() {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("config.properties"));

            environment = props.getProperty("environment");
            userName = props.getProperty("username");
            password = props.getProperty("password");

            WEBDRIVER_WAIT_TIME_SECONDS = Integer.parseInt(props.getProperty("webdriver.wait.seconds", "60"));
            RETRY_ATTEMPTS_NUMBER = Integer.parseInt(props.getProperty("retry.attempts.number", "3"));
            CLOSE_BROWSER = Boolean.parseBoolean(props.getProperty("close.browser", "true"));

            System.setProperty("webdriver.chrome.driver", props.getProperty("webdriver.location"));
        } catch (IOException e) {
            environment = System.getProperty("environment");
            userName = System.getProperty("username");
            password = System.getProperty("password");

            WEBDRIVER_WAIT_TIME_SECONDS = Integer.parseInt(System.getProperty("webdriver.wait.seconds", "60"));
            RETRY_ATTEMPTS_NUMBER = Integer.parseInt(System.getProperty("retry.attempts.number", "3"));

            System.setProperty("webdriver.chrome.driver", System.getProperty("webdriver.location", "./chromedriver"));

        } finally {
            chromeDriverOptions = System.getProperty("chromeDriverOptions", "start-maximized");
            chromeBinary = System.getProperty("chrome.binary", "");
        }
    }

    public String getEnv() {
        return environment;
    }

    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }

    String getChromeDriverOptions() { return chromeDriverOptions; }
    String getChromeBinary() { return chromeBinary; }
}
