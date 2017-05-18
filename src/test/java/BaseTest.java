import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static final String USERNAME = "dkrtolica";
    public static final String ACCESS_KEY = "870c89b8-b5b1-49cd-a71b-43a4c7fcee40";
    public static final String HUB_URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";

    protected static final String WEB_SERVER = "https://referencesite.nudatasecurity.com/";


    public static RemoteWebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void setupWebDriver() throws MalformedURLException {

        DesiredCapabilities caps = DesiredCapabilities.chrome();
        caps.setCapability("platform", "Mac");
        caps.setCapability("version", "43.0");
        driver = new RemoteWebDriver(new URL(HUB_URL), caps);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }



    @BeforeMethod(alwaysRun = true)
    public void loadWebApplication() {
        driver.get(WEB_SERVER);
    }

    @AfterMethod(alwaysRun = true)
    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void suiteTearDown() {
        driver.quit();
    }

}