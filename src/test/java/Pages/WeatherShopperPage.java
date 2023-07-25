package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WeatherShopperPage {
    private final WebDriver driver;

    public WeatherShopperPage(WebDriver driver){
        this.driver = driver;
    }

    By temperature = By.xpath("//span[@id='temperature']");
    By sunscreenText = By.xpath("(//p[@class='text-justify'])[1]");
    By moisturizerText = By.xpath("(//p[@class='text-justify'])[2]");
    By buySunscreensBtn = By.xpath("//button[text()='Buy sunscreens']");
    By buyMoisturizerBtn = By.xpath("//button[text()='Buy moisturizers']");
    By instructionsIcon = By.xpath("//span[@class='octicon octicon-info']");
    By instructionsText = By.xpath("//div[@class='popover-body']");
    By sunscreenSPF50List = By.xpath("//button[contains(text(),'Add') and contains(@onclick,'SPF-50')]");
    By sunscreenSPF30List = By.xpath("//button[contains(text(),'Add') and contains(@onclick,'SPF-30')]");
    By moisturizerAloeList = By.xpath("//button[contains(text(),'Add') and contains(@onclick,'Aloe')]");
    By moisturizerAlmondList = By.xpath("//button[contains(text(),'Add') and contains(@onclick,'Almond')]");
    By cartBtn = By.xpath("//button/span[contains(text(), '2')]");
    By cartHeading = By.xpath("//h2[text()='Checkout']");
    By payWithCardBtn = By.xpath("//span[text()='Pay with Card']");
    By emailInput = By.xpath("//*[@id=\"email\"]");
    By cardNumberInput = By.xpath("//input[@placeholder='Card number']");
    By expiryDateInput = By.xpath("//input[@placeholder='MM / YY']");
    By cvvInput = By.xpath("//input[@placeholder='CVC']");
    By zipCode = By.xpath("//input[@placeholder='ZIP Code']");
    By payBtn = By.xpath("//span[@class='iconTick']");
    public String getPageTitle(){
        String pageTitle = driver.getTitle();
        return pageTitle;
    }

    public String getCurrentTemperature(){
        return driver.findElement(temperature).getText();
    }

    public String getSunscreenText(){
        return driver.findElement(sunscreenText).getText();
    }

    public String getMoisturizerText(){
        return driver.findElement(moisturizerText).getText();
    }

    public void clickBuySunscreensBtn(){
        driver.findElement(buySunscreensBtn).click();
    }

    public void clickBuyMoisturizerBtn(){
        driver.findElement(buyMoisturizerBtn).click();
    }

    public void clickInstructionsIcon(){
        driver.findElement(instructionsIcon).click();
    }

    public String getInstructionsText(){
        return driver.findElement(instructionsText).getText();
    }

    public void clickSunscreenSPF50(){
        driver.findElements(sunscreenSPF50List).get(0).click();
    }

    public void clickSunscreenSPF30(){
       driver.findElements(sunscreenSPF30List).get(0).click();
    }

    public void clickMoisturizerAleo(){
        driver.findElements(moisturizerAloeList).get(0).click();
    }

    public void clickMoisturizerAlmond(){
        driver.findElements(moisturizerAlmondList).get(0).click();
    }

    public int getNumberOfItemsInCart(){
        String cartBtnText = driver.findElement(cartBtn).getText();
        StringBuffer numbers = new StringBuffer();
        for (int i=0; i<cartBtnText.length(); i++)
        {
            if (Character.isDigit(cartBtnText.charAt(i)))
                numbers.append(cartBtnText.charAt(i));
        }
        System.out.println(numbers);
        return Integer.parseInt(String.valueOf(numbers));
    }

    public void clickCartBtn(){
        driver.findElement(cartBtn).click();
    }

    public String getCartHeading(){
        return driver.findElement(cartHeading).getText();
    }

    public void clickPayWithCardBtn(){
        driver.findElement(payWithCardBtn).click();
    }

    public void getEmail(String email){
        driver.findElement(emailInput).sendKeys(email);
    }

    public void getCardNumber(String cardNumber){
        driver.findElement(cardNumberInput).sendKeys(cardNumber);
    }

    public void getExpiryDate(String expiry){
        driver.findElement(expiryDateInput).sendKeys(expiry);
    }

    public void getCVV(String cvv){
        driver.findElement(cvvInput).sendKeys(cvv);
    }

    public void getZipCode(String zipcode){
        driver.findElement(zipCode).sendKeys(zipcode);
    }

    public void clickPayBtn(){
        driver.findElement(payBtn).click();
    }
}
