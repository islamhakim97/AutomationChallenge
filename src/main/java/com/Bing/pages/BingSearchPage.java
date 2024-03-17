package com.Bing.pages;

import com.Base.com.testBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BingSearchPage extends testBase {
	int implicitwait = (int) Long.parseLong(prop.getProperty("implicitlyWait"));
	public BingSearchPage() throws IOException {
		super();
		PageFactory.initElements(driver,this);
	}
	//WebElements
    @FindBy(id="sb_form_q")
    WebElement SearchBox;
    @FindBy(id="search_icon")
    WebElement SearchIcon;


	//Methods
	public void getMainPage()
	{
		driver.get(prop.getProperty("Bing")) ;
		
		driver.manage().timeouts().implicitlyWait(implicitwait, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(implicitwait, TimeUnit.SECONDS);
	}

	public Boolean OpenBingWebsite()
	{

		driver.manage().timeouts().implicitlyWait(implicitwait, TimeUnit.SECONDS);

		js.executeScript("arguments[0].style.border='3px solid purple'", SearchBox);
		return SearchBox.isDisplayed();

	}

    public ResultPage performSearch(String txt) throws IOException
    {
	//	<!-- Edge has problems with JavaScript Executer-->
    	
       // getMainPage();
        driver.manage().timeouts().implicitlyWait(implicitwait, TimeUnit.SECONDS);

		js.executeScript("arguments[0].style.border='3px solid purple'", SearchBox); //--Java Script Executer fails when working with Edge so don't use it there because js loaded the element one more time after i have referred.
		SearchBox.sendKeys(txt);
		js.executeScript("arguments[0].style.border='3px solid purple'", SearchIcon);
		SearchIcon.click();
		return new ResultPage();
    }


}

    

	


