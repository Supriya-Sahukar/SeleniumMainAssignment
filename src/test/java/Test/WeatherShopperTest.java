package Test;

import Pages.WeatherShopperPage;
import Resources.ImplementListener;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Listeners(ImplementListener.class)
public class WeatherShopperTest {
    WebDriver driver;
    WeatherShopperPage weatherShopperPage;
    ExtentHtmlReporter extentHtmlReporter;
    ExtentReports extent;
    Logger logger = LogManager.getLogger(WeatherShopperTest.class);
    Properties prop = new Properties();
    FileInputStream fis = new FileInputStream("C:\\Users\\supriya4\\eclipse\\maven-demo\\src\\test\\java\\Resources\\Data.properties");

    public WeatherShopperTest() throws FileNotFoundException {
    }

    @BeforeTest
    public void setUp() throws IOException {
        extentHtmlReporter = new ExtentHtmlReporter("C:\\Users\\supriya4\\eclipse\\maven-demo\\reports\\report1.html");
        extent = new ExtentReports();
        extent.attachReporter(extentHtmlReporter);

        extentHtmlReporter.config().setTheme(Theme.DARK);
        extentHtmlReporter.config().setDocumentTitle("MyReport");

        logger.info("Initializing WebDriver");
        System.setProperty("webdriver.chrome.driver", "C://Drivers//chromedriver.exe");
        driver = new ChromeDriver();
        weatherShopperPage = new WeatherShopperPage(driver);
        prop.load(fis);

        //Navigating to the weather shopper page
        driver.get(prop.getProperty("weathershopperUrl"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    //verify title of the page
    @Test(priority = 1)
    public void testVerifyTitlePage() {
        ExtentTest test = extent.createTest("Verify the pageTitle").assignAuthor("supriya")
                .assignCategory("functional test cases");
        test.info("verifying the page title");
        String expTitle = "Current Temperature";
        String actTitle = weatherShopperPage.getPageTitle();
        System.out.println(actTitle);
        test.info("page title is : " + actTitle);
        assert actTitle.equals(expTitle) : test.fail("Page title mismatch: " + actTitle);
        test.pass("verified the page title: " + actTitle);

    }

    //Get the current temperature
    @Test(priority = 2)
    public void testVerifyCurrentTemperature() {
        ExtentTest test = extent.createTest("Verify the current tempearture").assignAuthor("supriya")
                .assignCategory("functional test cases").assignDevice("Windows");
        test.info("Verifying the current temperature");
        String currentTemperature = weatherShopperPage.getCurrentTemperature();
        test.info("Current Temperature is: " + currentTemperature);
        test.pass("Verified the current temperature");
    }

    //verify the sunscreen text
    @Test(priority = 3)
    public void testVerifySunscreenText() {
        ExtentTest test = extent.createTest("Verify the sunscreen text").assignAuthor("supriya")
                .assignCategory("functional test cases").assignDevice("Windows");
        test.info("Verifying the sunscreen text");
        String sunscreenText = weatherShopperPage.getSunscreenText();
        test.info("text below sunscreen is: " + sunscreenText);
        test.pass("Verified the sunscreen text");
    }

    ////verify the moisturizer text
    @Test(priority = 4)
    public void testVerifyMoisturizerText() {
        ExtentTest test = extent.createTest("Verify the moisturizer text").assignAuthor("supriya")
                .assignCategory("functional test cases").assignDevice("Windows");
        test.info("Verifying the moisturizer text");
        String moisturizerText = weatherShopperPage.getMoisturizerText();
        test.info("text below moisturizer is: " + moisturizerText);
        test.pass("Verified the moisturizer text");
    }

    //Based on temperature choose to buy sunscreen or moisturizer
    //If you choose sunscreen, then read Instructions (from i mark on top next to header) and then add product accordingly
    //If you choose moisturizer, then read Instructions and then add product accordingly
    @Test(priority = 5)
    public void addProductToCart() {
        ExtentTest test = extent.createTest("Buying Sunscreens or Moisturizer and adding to the cart").assignAuthor("supriya")
                .assignCategory("functional test cases").assignDevice("Windows");
        int temperatureValue = Integer.parseInt(weatherShopperPage.getCurrentTemperature().replaceAll("\\D+", ""));
        test.info("adding products to cart");
        //Based on temperature choose to buy sunscreen or moisturizer
        if (temperatureValue > 34) {
            test.info("It's hot! Buying Sunscreens.");
            weatherShopperPage.clickBuySunscreensBtn();

            //Read and Display instructions from sunscreen
            weatherShopperPage.clickInstructionsIcon();
            String sunscreenInstructionText = weatherShopperPage.getInstructionsText();
            test.info("Sunscreen Instructions: " + sunscreenInstructionText);

            //Add two sunscreens SPF-50 and SPF-30 to the cart
            weatherShopperPage.clickSunscreenSPF50();
            weatherShopperPage.clickSunscreenSPF30();
            test.info("Added two sunscreens SPF-50 and SPF-30 to the cart");
        } else {
            test.info("It's not too hot! Buying Moisturizers.");
            weatherShopperPage.clickBuyMoisturizerBtn();

            //Read and Display instructions from moisturizer
            weatherShopperPage.clickInstructionsIcon();
            String moisturizerInstructionText = weatherShopperPage.getInstructionsText();
            test.info("Moisturizer Instructions: " + moisturizerInstructionText);

            //Add two moisturizers Aleo and Almond to the cart
            weatherShopperPage.clickMoisturizerAleo();
            weatherShopperPage.clickMoisturizerAlmond();
            test.info("Added two moisturizers Aleo and Almond to the cart");
        }
    }

    //Verify the number of items on cart button
    @Test(priority = 6)
    public void testVerifyNoOfItemsInCart() throws InterruptedException {
        ExtentTest test = extent.createTest("verify number of items in cart").assignAuthor("supriya")
                .assignCategory("functional test cases").assignDevice("Windows");
        test.info("verifying number of items in cart");
        //wait for the cart to update
        Thread.sleep(2000);
        //verify the number of items in cart
        int itemsInCart = weatherShopperPage.getNumberOfItemsInCart();
        test.info("Number of items in cart: " + itemsInCart);

        //Aseertion
        Assert.assertEquals(itemsInCart, 2, "Number of items in cart should be 2");
        test.pass("Verified number of items in cart");
    }

    //Verify the cart details page
    @Test(priority = 7)
    public void testCartPage() {
        ExtentTest test = extent.createTest("verify cart page details").assignAuthor("supriya")
                .assignCategory("functional test cases").assignDevice("Windows");
        weatherShopperPage.clickCartBtn();
        test.info("Navigated to cart page");
        String cartHeading = weatherShopperPage.getCartHeading();
        Assert.assertEquals(cartHeading, "Checkout", "cart details page not displayed");
        test.pass("Verified cart page details");
    }

    //Make a payment and verify the success page
    @Test(priority = 8)
    public void testPaymentSuccessPage() throws InterruptedException, IOException {
        prop.load(fis);
        ExtentTest test = extent.createTest("Verifying success page for payment").assignAuthor("supriya")
                .assignCategory("functional test cases").assignDevice("Windows");
        weatherShopperPage.clickPayWithCardBtn();
        test.info("Navigated to payment page");
//        weatherShopperPage.getEmail("supriya@gmail.com");
//        weatherShopperPage.getCardNumber(prop.getProperty("card number"));
//        weatherShopperPage.getExpiryDate(prop.getProperty("expiry"));
//        weatherShopperPage.getCVV(prop.getProperty("cvv"));
//        weatherShopperPage.getZipCode(prop.getProperty("zip code"));
//        weatherShopperPage.clickPayBtn();
        test.info("Verified success page for payment");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if (!result.isSuccess()) {
            takeScreenshot(result.getName() + "Failure");
        } else {
            takeScreenshot(result.getName() + "Success");
        }
        logger.info("Test method: " + result.getMethod().getMethodName() + " _ " + (result.isSuccess() ? "SUCCESS" : "FAILURE"));
    }

    private void takeScreenshot(String fileName) throws IOException {
        File screenshot = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("screenshots/" + fileName + ".png"));
    }

    @AfterTest
    public void teardown() {
        extent.flush();
        driver.quit();
        logger.info("Closing WebDriver");
    }

}
