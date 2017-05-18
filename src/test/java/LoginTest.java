import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.Reporter;
import org.testng.annotations.Test;


public class LoginTest extends BaseTest {

    public LoginTest() {
        super();
    }

    @DataProvider(name = "invalidCredentialsData")
    public Object[][] getInvalidCredentialsData() {
        return  new Object[][]{
                {"qacodetest", "whatever"}, //valid user name
                {"whatever", "wQ8VVPH&hVXfR8%*"}, //valid password
                {"qa code test", "wQ8VVPH&hVXfR8%*"}, //valid password. spaces in valid user name
                {"qacodetest", "wQ8V VPH&hV XfR8%*"}, //valid user name, spaces in valid password
                {"qacodetest", "wq8vvph&hvxfr8%*"}, //password in lower case, this should fail
                {"whatever", "whatever"}
        };
    }

    @DataProvider(name = "validCredentialsData")
    public Object[][] getIalidCredentialsData() {
        return  new Object[][] {
                { "qacodetest", "wQ8VVPH&hVXfR8%*" }
        };
    }


    @Test(dataProvider = "validCredentialsData", groups = {"allLoginTests"})
    public void successful_LoginTest(String userName, String password) {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickLoginLink();

        //this assertion is not necessary, AbstractPageObject waitForPageToLoad() will ensure that page is loaded
        //but it does not hurt to check before start entering data
        Assert.assertEquals(loginPage.getTitle(), "Some Company - Client Login", "Title on login page is not correct");
        loginPage.typeUserName(userName);
        loginPage.typePassword(password);

        Reporter.log("Checking if captcha is present on the page...");
        if (loginPage.isCaptchaPresent()) {
            Assert.fail("Captcha element present on page, aborting login attempt");
            Reporter.log("Captcha is present on the page, aborting login attempt... ");
        }  else {

            Reporter.log("Submitting form with credentials: userName = " + userName + " password = " + password);
            ClientPortalPage clientPortalPage = loginPage.clickSubmitForValidCredentials();
            Assert.assertEquals(clientPortalPage.isClientPortalTextPresent(), true, "\"Client Portal\" text is not present on the page after successful login");
            Reporter.log("Assertion for Client Portal text successful ");
        }

    }

    @Test(dataProvider = "invalidCredentialsData", groups = {"allLoginTests"})
    public void invalidLoginTest(String userName, String password) {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickLoginLink();

        // this assertion is not necessary, AbstractPageObject waitForPageToLoad() will ensure that page is loaded
        // but it does not hurt to check before start entering data
        Assert.assertEquals(loginPage.getTitle(), "Some Company - Client Login", "Title on login page is not correct");

        loginPage.typeUserName(userName);
        loginPage.typePassword(password);
        Reporter.log("Submitting form with credentials: userName = " + userName + " password = " + password);
        loginPage.clickSubmit();


        if (loginPage.isCaptchaPresent()) {
            Reporter.log("Captcha is present on the page... ");
        }
        //for failed login attempts we don't care if captcha is present or not
        Assert.assertEquals(loginPage.getInvalidCredentialsErrorMessage(), "Invalid Username or Password",
                "Invalid Credentials error message is incorrect");
        Reporter.log("Assertion for Invalid Credentials error message successful ");
        }


}