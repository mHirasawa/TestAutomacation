package com.regression;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class HelloWorldTest {

	@Test
	public void testHelloWorld() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/webdrivers/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.co.jp/");
        driver.quit();
		AssertJUnit.assertEquals(true, true);
	}
}