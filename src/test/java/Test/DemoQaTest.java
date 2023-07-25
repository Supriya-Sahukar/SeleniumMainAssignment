package Test;

import Pages.DemoQaPage;
import Resources.ImplementListener;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Listeners(ImplementListener.class)
public class DemoQaTest {
    WebDriver driver;
    DemoQaPage demoQaPage;
    ExtentReports extent = new ExtentReports();
    ExtentSparkReporter spark = new ExtentSparkReporter("C:\\Users\\supriya4\\eclipse\\maven-demo\\reports\\report2.html");
    private static Logger logger = LogManager.getLogger(DemoQaTest.class);
    Properties prop = new Properties();
    FileInputStream fis = new FileInputStream("C:\\Users\\supriya4\\eclipse\\maven-demo\\src\\test\\java\\Resources\\Data.properties");

    public DemoQaTest() throws FileNotFoundException {
    }

    @BeforeTest
    public void setUp() throws IOException {
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("MyReport");
        extent.attachReporter(spark);

        logger.info("Initializing WebDriver");
        System.setProperty("webdriver.chrome.driver", "C://Drivers//chromedriver.exe");
        driver = new ChromeDriver();
        demoQaPage = new DemoQaPage(driver);

        prop.load(fis);
        //navigating to the browser windows page
        driver.get(prop.getProperty("browserWindowsUrl"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    //click on each button then get the text displayed on page.
    @Test(priority = 1)
    public void testBrowserWindowsBtn() {
        ExtentTest test = extent.createTest("Verify the browser window button").assignAuthor("supriya");
        logger.info("Verifying the browser window button");
        demoQaPage.clickNewTabBtn();
        String expText = "This is a sample page";
//        String tabPageText = demoQaPage.getNewTabPageText();
//        System.out.println(tabPageText);
//        assert tabPageText.equals(expText) : test.fail("Page title mismatch: " + tabPageText);
//        test.pass("verified the new tab page text: " + tabPageText);
        demoQaPage.clickNewWindowBtn();
        demoQaPage.clickNewWindowMsgBtn();
        test.info("Verifying the browser window button");
    }

    //get the text of child and parent frames
    @Test(priority = 2)
    public void testFrame() throws IOException {
        ExtentTest test = extent.createTest("Verify the frame page").assignAuthor("supriya")
                .assignCategory("functional test cases").assignDevice("Windows");
        logger.info("Navigating to the frames page");
        prop.load(fis);
        driver.get(prop.getProperty("framesUrl"));
        String childFrameText = demoQaPage.getTextFromChildFrame();
        System.out.println(childFrameText);
        test.info("Text from child frame: " + childFrameText);
        test.info("Verified the frame page");
    }

    //select date 2 days back from current date
    @Test(priority = 3)
    public void testDatePicker() throws InterruptedException, IOException {
        ExtentTest test = extent.createTest("Verify the date picker page").assignAuthor("supriya");
        logger.info("Navigating to the date picker page");
        prop.load(fis);
        driver.get(prop.getProperty("DatePickerUrl"));
        demoQaPage.selectDateTwoDaysBack();
        Thread.sleep(1000);
        String selectedDate = demoQaPage.getSelectedDate();
        test.info("2 days back date from current date is: " + selectedDate);
        String expDate = "07/23/2023";
        String actDate = selectedDate;
//        assert actDate.equals(expDate) : test.fail("Date is mismatch: " + actDate);
//        test.pass("verified the date : " + actDate);
    }

    //from ‘Main Item 2’ select ‘sub sub Item 2’
    @Test(priority = 4)
    public void testMenu() throws IOException {
        ExtentTest test = extent.createTest("Verify the menu page").assignAuthor("supriya")
                .assignCategory("functional test cases").assignDevice("Windows");
        logger.info("Navigating to the menu page");
        prop.load(fis);
        driver.get(prop.getProperty("MenuUrl"));
        demoQaPage.selectSubSubItem2FromMaiItem2();
        test.info("Verified the menu page");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (!result.isSuccess()) {
            takeScreenshot(result.getName() + "Failure");
        } else {
            takeScreenshot(result.getName() + "Success");
        }
        logger.info("Test method: " + result.getMethod().getMethodName() + " _ " + (result.isSuccess() ? "SUCCESS" : "FAILURE"));
    }

    private void takeScreenshot(String fileName) {
        File screenshot = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("screenshots/" + fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    public void teardown() {
        extent.flush();
        driver.quit();
        logger.info("Closing WebDriver");
    }

}
