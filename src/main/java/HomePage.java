import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class HomePage extends AbstractPageObject {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By getUniqueElement() {
        return By.cssSelector("a#clientLogin");
    }

    By loginLink = By.cssSelector("a#clientLogin");

    public LoginPage clickLoginLink() {
        clickWithWaitAndRetry(loginLink);
        return new LoginPage(driver);
    }


}
