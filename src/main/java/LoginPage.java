import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends AbstractPageObject {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By getUniqueElement() {
        return By.cssSelector("input#username");
    }

    By userNameLocator = By.cssSelector("input#username");
    By passwordLocator = By.cssSelector("input#password");
    By submitLocator = By.cssSelector("#btnSubmit");
    By invalidUserNameOrPasswordLocator = By.cssSelector("form#login  label[for=\"password\"] aside");
    By captchaLocator = By.cssSelector("div#my-nucaptcha-wrapper");

    public void typeUserName(String text) {
        clickWithWaitAndRetry(userNameLocator);
        driver.findElement(userNameLocator).clear();

        if (text != null) {
            driver.findElement(userNameLocator).sendKeys(text);
        }
    }

    public void typePassword(String text) {
        clickWithWaitAndRetry(passwordLocator);
        driver.findElement(passwordLocator).clear();

        if (text != null) {
            driver.findElement(passwordLocator).sendKeys(text);
        }
    }

    public void clickSubmit() {
        clickWithWaitAndRetry(submitLocator);
    }

    public ClientPortalPage clickSubmitForValidCredentials() {
        clickWithWaitAndRetry(submitLocator);
        return  new ClientPortalPage(driver);
    }

    public boolean isCaptchaPresent (){
        return  elementExists(captchaLocator);
    }

    public String  getInvalidCredentialsErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(invalidUserNameOrPasswordLocator));
        return driver.findElement(invalidUserNameOrPasswordLocator).getText();
    }

}
