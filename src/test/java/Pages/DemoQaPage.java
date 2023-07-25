package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DemoQaPage {
    WebDriver driver;

    public DemoQaPage(WebDriver driver) {
        this.driver = driver;
    }

    By newTabBtn = By.xpath("//button[text()='New Tab']");
    By newTabPageText = By.id("sampleHeading");
    By newWindowBtn = By.xpath("//button[text()='New Window']");
    By newWindowMsgBtn = By.xpath("//button[text()='New Window Message']");
    By nestedFramesBtn = By.xpath("//span[text()='Nested Frames']");
    By selectDate = By.xpath("//input[@id='datePickerMonthYearInput']");
    By mainItem2 = By.xpath("//a[text()='Main Item 2']");
    By subSubList = By.xpath("//a[text()='SUB SUB LIST »']");
    By childFrameText = By.tagName("iframe");

    public void clickNewTabBtn(){
        driver.findElement(newTabBtn).click();
    }

    public String getNewTabPageText(){
        //wait.until(ExpectedConditions.visibilityOfElementLocated(newTabPageText));
        return driver.findElement(newTabPageText).getText();
    }

    public void clickNewWindowBtn(){
        driver.findElement(newWindowBtn).click();
    }

    public void clickNewWindowMsgBtn(){
        driver.findElement(newWindowMsgBtn).click();
    }


    // Method to switch to the child frame and get the text
    public String getTextFromChildFrame() {
        //driver.switchTo().frame("frame1");
        return driver.findElement(childFrameText).getText();
        //driver.switchTo().defaultContent(); // Switch back to the default content
        //return childFrameText;
    }


    public void selectDateTwoDaysBack(){
        LocalDate currentDate = LocalDate.now();
        LocalDate twoDaysBack = currentDate.minusDays(2);
        String formattedDate = twoDaysBack.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        driver.findElement(selectDate).clear();
        driver.findElement(selectDate).sendKeys(formattedDate);
    }

    public String getSelectedDate(){
        return driver.findElement(selectDate).getAttribute("value");
    }

    public void selectSubSubItem2FromMaiItem2(){
         driver.findElement(mainItem2).click();
        driver.findElement(subSubList).click();
    }
}
