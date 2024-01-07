package utils;

import org.openqa.selenium.WebDriver;

public class WindowHandler {

    static String mainWindow;
    static WebDriver driver;

    public static void switchToChildWindow(){
        driver = Driver.getDriver();

        mainWindow = driver.getWindowHandle();

        for (String s : driver.getWindowHandles()){
            if (!s.equals(mainWindow)){
                driver.switchTo().window(s);
                break;
            }
        }

    }
    public static void switchToMainWindow(){
        driver.switchTo().window(mainWindow);
    }
}
