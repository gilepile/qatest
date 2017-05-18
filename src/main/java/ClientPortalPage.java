import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ClientPortalPage extends AbstractPageObject {

    public ClientPortalPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By getUniqueElement() {
        return  By.cssSelector("div#middle div.fancycontent");
    }

    By logoutLink =By.cssSelector("div#membermenu  a[href=\"client/logout\"]");
    By clientPortalLocator = By.cssSelector("div#middle div.fancycontent");

    public HomePage clickLogoutLink() {
        clickWithWaitAndRetry(logoutLink);
        return new HomePage(driver);
    }

    public boolean isClientPortalTextPresent(){
        return  elementExists(clientPortalLocator);
    }

    public  ClientPortalPage getClientPortalPage(){
        return  new ClientPortalPage(driver);
    }

}
