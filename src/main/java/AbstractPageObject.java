import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public abstract class AbstractPageObject {

    protected WebDriver driver;
    protected WebDriverWait wait;
    public static final int NUMBER_OF_STALE_ELEMENT_RETRY_ATTEMPTS = 3;

    public AbstractPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = (new WebDriverWait(driver, 60));
        waitForPageToLoad();
    }

    // Each page object must implement this method to return the identifier of a unique WebElement on that page.
    // The presence of this unique element will be used to assert that the expected page has finished loading
    protected abstract By getUniqueElement();


    protected void waitForPageToLoad() {
        // Wait until the unique element of current page is visible in the browser
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(getUniqueElement()));
        } catch (TimeoutException e) {
            TimeoutException timeoutException = new TimeoutException(
                    "Timed out loading " + this.getClass().getSimpleName() + "\n" + e.getMessage()
            );
            timeoutException.setStackTrace(e.getStackTrace());
            throw timeoutException;
        }
    }

    public String getTitle() {
        return  driver.getTitle();
    }

    /**
     *  Method will wait until element is clickable and then it will try to click  several times
     *  before throwing StaleElementReferenceException
     *
     * @param locator  used to find element;
     */
    public void clickWithWaitAndRetry(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        int attempts = 0;
        while (attempts < NUMBER_OF_STALE_ELEMENT_RETRY_ATTEMPTS) {
            try {
                driver.findElement(locator).click();
                break;
            } catch (StaleElementReferenceException e) {

            }
            attempts++;
        }
    }

    public boolean elementExists(By locator)
    {
        return !driver.findElements(locator).isEmpty();
    }

}
