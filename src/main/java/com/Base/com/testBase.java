package com.Base.com;



import com.Bing.util.WebListener;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class testBase {
    public static WebDriver driver;
	//public static ChromeDriver MockGeoLocationDriver;
	public static JavascriptExecutor js;
	//public static JavascriptExecutor jscript;//For ChallengesWeb Driver
	public static Properties prop;

	//public static EventFiringWebDriver e_driver; // For logging Purpose
	public static WebListener weblistener;

	//Extent Report Variables
    public static ExtentReports extent;//
	//Thread Local Variable Gor Html Report
	public static EventFiringWebDriver e_driver; // For logging Purpose

	public static ExtentTest logger;// to log each test in the Report
	//public static ThreadLocal<ExtentTest> extent_test = new ThreadLocal<ExtentTest>();// Thread local variable for extentTest
    //public static ThreadLocal<WebDriver> tdriver=new ThreadLocal<WebDriver>();//For synchronized WebDriver - Thread To share The Driver Between Multiple Files-Allure Listener
	public static ThreadLocal<WebDriver> tdriver=new ThreadLocal<WebDriver>();//For synchronized WebDriver - Thread To share The Driver Between Multiple Files-Allure Listener

	public testBase() throws IOException {

		loadProperties();  // By Making This method  Synchronized , we make only one Thread can access this method at any given time .


	}


	public void loadProperties() throws IOException {
		prop = new Properties();
		String projectPath=System.getProperty("user.dir");
		FileInputStream fis = new FileInputStream(
				projectPath+"/Data/config.properties");
		prop.load(fis);
	}


	public WebDriver initialization(String browser) throws IOException {
		if(browser.equalsIgnoreCase("Grid-linux-firefox"))
		{
			//Standalone Selenium Grid support remote execution
			String nodeURL="http://192.168.1.4:4444"; // changes every time
			//DesiredCapabilities cap= DesiredCapabilities.firefox(); //working in eclipse not in IntelliJ
			DesiredCapabilities cap= new DesiredCapabilities();
			cap.setBrowserName("firefox");
			cap.setPlatform(Platform.LINUX);
			driver= new RemoteWebDriver(new URL(nodeURL),cap);
			
		}else if (browser.equalsIgnoreCase("chrome")) {
			 WebDriverManager.chromedriver().setup();
     		 driver = new ChromeDriver();

			//Set Thread Local Variable

			//driver.set(ThreadGuard.protect(new ChromeDriver())); //Thread Guard Used to make sure that driver is only called by the thread created it (Optional - Java Only).


		} else if (browser.equalsIgnoreCase("firefox")) {
              WebDriverManager.firefoxdriver().setup();

			//Thread Variable Driver
			//driver.set(new FirefoxDriver());
             driver = new FirefoxDriver();

		} else if (browser.equalsIgnoreCase("edge")) {

			WebDriverManager.edgedriver().setup();
			//Thread Variable Driver
			//driver.set(new EdgeDriver());
			driver = new EdgeDriver();

			//headless Browser Testing

		}else if (browser.equalsIgnoreCase("safari")) {
			/*
			 * Configure safari driver Manually first in your MacbookAir : (Open terminal :
			 * [run 'cd usr/bin , safaridriver --enble', enable remote automation on
			 * safari-Deceloper , close any safari browser before start testing as safari
			 * not permit to instantiate more than safari browser in the same time] )
			 */

			//Thread Variable Driver
			//driver.set(new SafariDriver());
			driver = new SafariDriver();
		}


		// For web Driver listener
		//e_driver = new EventFiringWebDriver(GetDriver());
		e_driver = new EventFiringWebDriver(driver);
	weblistener = new WebListener();
		e_driver.register(weblistener);
		//driver.set( e_driver);
		driver = e_driver;
		//tdriver.set(GetDriver());
		tdriver.set(driver); //-- For Allure report Listener
		 driver.get(prop.getProperty("Bing")) ; // to lnd on the main page u want
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		js = ((JavascriptExecutor)driver);

		return getDriver();

	}
	public static int countNumOfSearchResults()
	{
		int count = 0;
		List<WebElement> listofLinks = driver.findElements(By.tagName("cite"));
		for (WebElement link : listofLinks ){
			//Count All Links displayed on Page
			count ++;
		}
		System.out.println(count);
		return count;
	}
	public void closeBrowser(){
		driver.quit();
	}
	public static synchronized WebDriver getDriver()
	{
		return tdriver.get();
	}


}
