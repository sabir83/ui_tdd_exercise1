package scripts;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class MixFromEverything {
    WebDriver driver;
    /*
        Go to https://www.google.com/
        Validate that the user see a search input box
         */
    @BeforeMethod
    public void setDriver(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void searchBox() {
        driver.get("https://www.google.com/");
        WebElement element = driver.findElement(By.id("APjFqb"));
        Assert.assertTrue(element.isDisplayed());
        element.sendKeys("bitcoin" + Keys.SHIFT);
        element.submit();

        WebElement result = driver.findElement(By.id("result-stats"));
        String resultText = result.getText();
        System.out.println(resultText);//About 763,000,000 results (0.43 seconds)
        String numberResult = resultText.split(" ")[1].replace(",", "");//763000000
        long number = Long.parseLong(numberResult);
        System.out.println(number);

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        driver.navigate().back();
    }

    @Test(description = "TG Waits")
    public void waits(){
        driver.get("https://techglobal-training.com/frontend/waits");

        WebElement redBox = driver.findElement(By.id("red"));
        redBox.click();

        WebElement redBoxSeen = driver.findElement(By.cssSelector("[class*=Waits_red"));
        Assert.assertTrue(redBoxSeen.isDisplayed());

        WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
        webDriverWait.until(ExpectedConditions.visibilityOf(redBoxSeen));
    }

    @Test(description = ("TG dropdowns"))
    public void dropdowns() throws InterruptedException {
        driver.get("https://techglobal-training.com/frontend/dropdowns");

        WebElement productElement = driver.findElement(By.id("product_dropdown"));

        Select product = new Select(productElement);
        product.selectByVisibleText("iPhone 14 Pro Max");
        //Thread.sleep(3000);
        WebElement colorElement = driver.findElement(By.id("color_dropdown"));
        Select color = new Select(colorElement);
        color.selectByVisibleText("Black");
        //Thread.sleep(3000);
        WebElement shipmentElement = driver.findElement(By.id("shipment_dropdown"));
        shipmentElement.click();
        WebElement shipmentType = driver.findElement(By.xpath("//span[text()='Delivery']"));
        shipmentType.click();
        //Thread.sleep(3000);
        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();
    }

    @Test(description = "TG Alerts")
    public void alerts() throws InterruptedException {
        driver.get("https://techglobal-training.com/frontend/alerts");

        WebElement warning = driver.findElement(By.id("warning_alert"));
        warning.click();
        Alert warningAlert = driver.switchTo().alert();
        warningAlert.accept();

        WebElement confirmation = driver.findElement(By.id("confirmation_alert"));
        confirmation.click();
        Alert confirmationAlert = driver.switchTo().alert();
        confirmationAlert.dismiss();

        WebElement prompt = driver.findElement(By.id("prompt_alert"));
        prompt.click();
        Alert promptAlert = driver.switchTo().alert();
        promptAlert.sendKeys("I love you my little pogacam");
    }

    @Test(description = "TG Iframes")
    public void iframes(){
        driver.get("https://techglobal-training.com/frontend/iframes");

        driver.switchTo().frame("form_frame");
        WebElement firstName = driver.findElement(By.id("first_name"));
        firstName.sendKeys("nurettin");

        WebElement lastName = driver.findElement(By.id("last_name"));
        lastName.sendKeys("kek");

        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();

        driver.switchTo().defaultContent();
        WebElement result = driver.findElement(By.id("result"));
        Assert.assertTrue(result.isDisplayed());
        Assert.assertEquals(result.getText(), "You entered: nurettin kek");
        //WebDriverWait wait = new WebDriverWait(driver,10);
        //wait.until(ExpectedConditions.visibilityOf(result));
    }
    @Test(description = "Take Screenshot")
    public void screenshot() throws IOException {
        driver.get("https://www.tesla.com/");

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("/Users/sabirburcu/Desktop/tesla.png"));// do not forget to add
        //after Desktop or whatever the last destination, add tesla.png- if you don't write x.png it doesn't work.
    }
    @Test(description = "TG Multiple Windows")
    public void multipleWindows() throws Exception{
        driver.get("https://techglobal-training.com/frontend/multiple-windows");
        WebElement apple = driver.findElement(By.id("apple"));
        apple.click();
        String mainWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        for (String windowHandle : allWindows) {
            if(!mainWindow.contentEquals(windowHandle)){
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        WebElement appleLogo = driver.findElement(By.cssSelector("[class*=globalnav-link-apple]"));
        Assert.assertTrue(appleLogo.isDisplayed());
        driver.close();
        driver.switchTo().window(mainWindow);
        Thread.sleep(3000);
    }
    /*
    Go to https://techglobal-training.com/frontend/
    Verify the headers of the table
    Check that the headers of the table are "Rank", "Company", "Employees", and "Country"
    Verify the rows of the table

    Check that the first row of the table has the values "1", "Amazon", "1,523,000", and "USA"
    Verify the columns of the table

     */
    @Test(description = "TG static tables")
    public void staticTables(){
        driver.get("https://techglobal-training.com/frontend/tables");
        List<WebElement> headers = driver.findElements(By.xpath("(//table)[1]//thead//tr//th"));
        String[] expectedTexts = {"Rank","Company","Employees","Country"};

        for (int i = 0; i < expectedTexts.length; i++) {
            Assert.assertEquals(headers.get(i).getText(),expectedTexts[i]);
        }

        List<WebElement> rows = driver.findElements(By.xpath("(//table)[1]//tbody/tr[1]"));
        String[] firstRowTexts = {"1","Amazon", "1,523,000", "USA"};
        for (int i = 0; i < firstRowTexts.length; i++) {
            Assert.assertEquals(rows.get(i).getText(),firstRowTexts[i]);
        }
    }
    @Test(description = "TG File download/upload")
    public void UploadFile(){
        driver.get("https://techglobal-training.com/frontend/file-download");
        WebElement uploadFile = driver.findElement(By.id("file_upload"));
        uploadFile.sendKeys("/Users/sabirburcu/Desktop/hi.docx");

        WebElement uploadButton = driver.findElement(By.id("file_submit"));
        uploadButton.click();
    }
    @Test(description = "TG Actions)")
    public void actions() throws Exception{
        driver.get("https://techglobal-training.com/frontend/actions");

        WebElement rightClick = driver.findElement(By.id("right-click"));
        Actions actions = new Actions(driver);
        actions.moveToElement(rightClick).contextClick().perform();
        Thread.sleep(3000);
        WebElement doubleClick = driver.findElement(By.id("double-click"));
        actions.moveToElement(doubleClick).doubleClick().perform();
        Thread.sleep(3000);

        WebElement drag = driver.findElement(By.id("drag_element"));
        WebElement drop = driver.findElement(By.id("drop_element"));
        actions.dragAndDrop(drag, drop).perform();
        Thread.sleep(3000);

        WebElement inputBox = driver.findElement(By.id("input_box"));
        inputBox.clear();
        actions.keyDown(Keys.SHIFT).sendKeys(inputBox,"i")
                .keyUp(Keys.SHIFT).sendKeys("pek").perform();
        Thread.sleep(3000);
    }
    @Test(description = "Scroll Down")
    public void scrollDown() throws Exception{
        driver.get("https://www.etsy.com/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
       // js.executeScript("window.scrollBy(0, 1000)"); // Scroll Down by a Specific Amount
        // js.executeScript("window.scrollBy(0, document.body.scrollHeight)");//Scroll to the Bottom of the Page
        WebElement scrollDown = driver.findElement(By.id("email-list-signup-email-input"));
        js.executeScript("arguments[0].scrollIntoView(true);", scrollDown); // Scroll to a Specific Element

           // Actions actions = new Actions(driver);
            //actions.sendKeys(Keys.PAGE_DOWN).build().perform(); // Action class way
    }
    @Test(description = "Scroll Down More")
    public void scrollDownMore() throws Exception{
        driver.get("https://www.etsy.com/");
        //1st way- BEST WAY SO FAR

        JavascriptExecutor js = (JavascriptExecutor) driver;
        Long screenHeight = (Long) js.executeScript("return window.innerHeight");
        Long totalHeight = (Long) js.executeScript("return document.body.scrollHeight");

        while (totalHeight > screenHeight) {
            js.executeScript("window.scrollBy(0, window.innerHeight)");
            screenHeight = (Long) js.executeScript("return window.innerHeight + window.scrollY");
        }
         /*
        //2ND BEST WAY
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
          */

        Thread.sleep(5000);
    }
    @AfterMethod
    public void closeDriver(){
        driver.quit();
    }
}
