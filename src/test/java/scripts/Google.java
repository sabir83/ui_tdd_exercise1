package scripts;

import com.google.gson.annotations.Until;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Google {
    WebDriver driver;
    /*
        Go to https://www.google.com/
        Validate that the user see a search input box
         */
    @BeforeMethod
    public void setDriver(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
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
    @AfterMethod
    public void closeDriver(){
        driver.quit();
    }
}
