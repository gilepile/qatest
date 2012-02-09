import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public abstract class AbstractPageObject {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public AbstractPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = (new WebDriverWait(driver, 30));
        isLoaded();
    }

    // Each page object must implement this method to return the identifier of a unique WebElement on that page
    // The presence of this unique element will be used to assert that the expected page has finished loading
    protected abstract By getUniqueElement();

    protected void isLoaded() throws Error {
        // Assert that the unique element is present in the DOM
        Assert.assertTrue(driver.findElements(getUniqueElement()).size() > 0, "Unique Element \'" + getUniqueElement().toString() + "\' not found for " + this.getClass().getSimpleName());
        // Wait until the unique element is present in the DOM and visible in the browser
        wait.until(ExpectedConditions.visibilityOfElementLocated(getUniqueElement()));
    }

}