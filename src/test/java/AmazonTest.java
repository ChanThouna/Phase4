import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AmazonTest {

    AndroidDriver<MobileElement> driver;
    WebElement skipSignUp;
    SoftAssert softAssert;
    WebElement searchTextField;

    @BeforeClass
    public void beforeClass(){
        softAssert = new SoftAssert();

    }

    @Test(description = "Launch amazon App")
    public void launchApp() throws MalformedURLException {

        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName","Android");
        cap.setCapability("platformVersion","9.0");
        cap.setCapability("appPackage","com.amazon.mShop.android.shopping");
        cap.setCapability("appActivity","com.amazon.windowshop.home.HomeLauncherActivity");

        driver = new  AndroidDriver<MobileElement>(url,cap);
        driver.manage().timeouts().implicitlyWait(3000,TimeUnit.MILLISECONDS);

        System.out.println(driver.getSessionId());
    }

    @Test(description = "Verify the Landing page",dependsOnMethods ="launchApp" )
    public void landingPageVerification() throws InterruptedException {

        Thread.sleep(15000);
     //  driver.manage().timeouts().implicitlyWait(10000,TimeUnit.MILLISECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//android.widget.ImageView[@resource-id = 'com.amazon.mShop.android.shopping:id/sso_splash_logo']"))));
     //   WebElement logo = driver.findElement(By.xpath("//android.widget.ImageView[@resource-id = 'com.amazon.mShop.android.shopping:id/sso_splash_logo']"));
        WebElement signIn = driver.findElement(By.xpath("//android.widget.TextView[@resource-id = 'com.amazon.mShop.android.shopping:id/signin_to_yourAccount']"));
        WebElement wishList = driver.findElement(By.xpath("//android.widget.TextView[@resource-id = 'com.amazon.mShop.android.shopping:id/view_your_wish_list']"));
        WebElement trackPackages = driver.findElement(By.xpath("//android.widget.TextView[@resource-id = 'com.amazon.mShop.android.shopping:id/track_your_packages']"));
        WebElement existingCustomer = driver.findElement(By.xpath("//android.widget.Button[@resource-id = 'com.amazon.mShop.android.shopping:id/sign_in_button']"));
        WebElement createAccount = driver.findElement(By.xpath("//android.widget.Button[@resource-id = 'com.amazon.mShop.android.shopping:id/new_user']"));
        skipSignUp = driver.findElement(By.xpath("//android.widget.Button[@resource-id = 'com.amazon.mShop.android.shopping:id/skip_sign_in_button']"));


        softAssert.assertEquals(skipSignUp.getAttribute("text"),"Skip sign in", "Difference in expected Skip Sign Up text");
        softAssert.assertEquals(signIn.getAttribute("text"),"Sign in to your account", "Difference in expected Sign In text");
        softAssert.assertEquals(wishList.getAttribute("text"),"View your wish list", "Difference in expected wish List text");
        softAssert.assertEquals(trackPackages.getAttribute("text"), "Track your purchases", "Difference in expected Skip Sign Up text");
        softAssert.assertEquals(existingCustomer.getAttribute("text"),"Already a customer? Sign in", "Difference in expected Skip Sign Up text");
        softAssert.assertEquals(createAccount.getAttribute("text"),"New to Amazon.com? Create an account", "Difference in expected Skip Sign Up text");
        softAssert.assertAll();

        Thread.sleep(2000);
    }

    @Test(description = "Verify Skip Sign up functionality",dependsOnMethods ="landingPageVerification")
    public void skipSignUp(){
       skipSignUp.click();
       WebElement logo = driver.findElement(By.xpath("//android.widget.ImageView[@resource-id='com.amazon.mShop.android.shopping:id/chrome_action_bar_home_logo']"));
       WebElement hamburger = driver.findElement(By.xpath("//android.widget.EditText[@resource-id='com.amazon.mShop.android.shopping:id/rs_search_src_text']"));
       searchTextField = driver.findElement(By.xpath("//android.widget.EditText[@resource-id='com.amazon.mShop.android.shopping:id/rs_search_src_text']"));
       softAssert.assertTrue(logo.isDisplayed(),"Amazon logo is not visible");
       softAssert.assertTrue(hamburger.isDisplayed(),"hamburger is not visible");
       softAssert.assertTrue(searchTextField.isDisplayed(),"searchTextField is not visible");
       softAssert.assertAll();
    }

    @Test(description = "Search One Plus Mobile",dependsOnMethods = "skipSignUp")
    public void searchPhone() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(3000,TimeUnit.MILLISECONDS);
        Actions actions = new Actions(driver);
        searchTextField = driver.findElement(By.xpath("//android.widget.EditText[@resource-id='com.amazon.mShop.android.shopping:id/rs_search_src_text']"));
        searchTextField.sendKeys("Oneplus 9 Pro Mobile phone");
        actions.sendKeys(Keys.ENTER).perform();

        Thread.sleep(2000);

    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
      driver.quit();
    }
}



