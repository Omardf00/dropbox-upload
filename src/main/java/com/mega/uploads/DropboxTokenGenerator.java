package com.mega.uploads;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DropboxTokenGenerator {
	
	public static String getDBTOken(String url, String properties, String driverRoute) {
        System.setProperty("webdriver.chrome.driver", driverRoute);
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless"); // Para no mostrar el navegador
        options.addArguments("user-data-dir=" + properties);
        options.addArguments("profile-directory=Default"); // Usa el perfil "Default" u otro si tienes varios

        WebDriver driver = new ChromeDriver(options);
        String accessToken = null;

        try {
            driver.get(url);

            WebElement generateButton = driver.findElement(By.id("generate-token-button"));
            generateButton.click();

            Thread.sleep(5000);

            //WebElement tokenElement = driver.findElement(By.id("access-token"));
            WebElement tokenElement = driver.findElement(By.id("generated-token"));
            accessToken = tokenElement.getAttribute("value");

            System.out.println("Access Token generado: " + accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return accessToken;
    }

}
