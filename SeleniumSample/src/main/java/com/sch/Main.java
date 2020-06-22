package com.sch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {
	public static void main(String args[]) {
		System.setProperty("webdriver.chrome.driver", "z:/opt/drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("auto-select-desktop-capture-source=Entire Screen");
		//options.addArguments("enable-usermedia-screen-capturing");
		options.addArguments("use-fake-ui-for-media-stream");		
		WebDriver driver = new ChromeDriver(options);
		String baseUrl = "http://localhost:8080/";
		driver.get(baseUrl);
		try {
			Thread.sleep(5000);
			driver.findElement(By.id("start")).click();
			Thread.sleep(10000);
			driver.findElement(By.id("stop")).click();
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		driver.close();
	}
}
