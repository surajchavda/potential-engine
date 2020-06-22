package com.sch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeleniumDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeleniumDemoApplication.class, args);
		String useChrome = System.getProperty("use-chrome");
		if (useChrome == null || useChrome.isEmpty()) {
			useChrome = "false";
		}
		boolean isChrome = Boolean.parseBoolean(useChrome); // false assume using Firefox
		WebDriver driver = getWebDriver(isChrome);
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

	private static WebDriver getWebDriver(boolean isChrome) {
		WebDriver webDriver = null;
		if (isChrome) {
			System.setProperty("webdriver.chrome.driver", "z:/opt/drivers/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			// Chrome Options
			// options.addArguments("auto-select-desktop-capture-source=Entire Screen");
			// options.addArguments("enable-usermedia-screen-capturing");
			options.addArguments("use-fake-ui-for-media-stream");
			webDriver = new ChromeDriver(options);
		} else {
			System.setProperty("webdriver.gecko.driver", "z:/opt/drivers/geckodriver.exe");
			// System.setProperty("webdriver.firefox.marionette",
			// "z:/opt/drivers/geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions();
			// ProfilesIni profiles = new ProfilesIni();
			// FirefoxProfile profile = profiles.getProfile("SeleniumTest");
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download.dir", "z:/tmp/");
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"application/zip,application/x-compress,application/octet-stream,video/webm");
			options.setProfile(profile);
			// Firefox Options
			options.addPreference("media.navigator.permission.disabled", true);
			options.addPreference("media.navigator.streams.fake", true);
			webDriver = new FirefoxDriver(options);
		}
		return webDriver;
	}
}
