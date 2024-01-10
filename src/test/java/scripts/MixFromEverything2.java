package scripts;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class MixFromEverything2 {
    WebDriver driver;

    @BeforeMethod
    public void setDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(description = "mix")
    public void mixExercise() {
        driver.get("https://www.google.com/");
        WebElement searchBarGoogle = driver.findElement(By.id("APjFqb"));
        searchBarGoogle.sendKeys("stitchedbypicky etsy" + Keys.ENTER);


        WebElement resultNumber = driver.findElement(By.id("result-stats"));
        String resultText = resultNumber.getText();
        String numberX = resultText.split(" ")[1].replace(",", "");

        Assert.assertEquals(numberX, "5330");//5330 can change during t time.if run in the future it might fail due to this number.

    }
    @Test(description = "mix2")
    public void mixExercise2() throws IOException {
        driver.get("https://www.google.com/");
        WebElement searchBarGoogle = driver.findElement(By.id("APjFqb"));
        searchBarGoogle.sendKeys("stitchedbypicky etsy" + Keys.ENTER);

        WebElement link = driver.findElement(By.cssSelector("h3[class^='LC20lb']"));
        link.click();

        File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenShot, new File("/Users/sabirburcu/Desktop/stitchedbypicky.png"));
    }
    @Test(description = "Actions")
    public void actions(){
        driver.get("https://www.google.com/");
        WebElement searchBarGoogle = driver.findElement(By.id("APjFqb"));
        searchBarGoogle.sendKeys("stitchedbypicky etsy" + Keys.ENTER);
    }
    @AfterMethod
    public void quitDriver(){
        driver.quit();
    }
}