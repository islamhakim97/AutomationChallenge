package com.Bing.pages;

import com.Base.com.testBase;
import io.cucumber.java.eo.Se;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class ResultPage extends testBase{
	int implicitwait = (int) Long.parseLong(prop.getProperty("implicitlyWait"));
	public ResultPage() throws IOException {
		PageFactory.initElements(driver, this); // creates the findElement calls behind the scene. - PageFactory.initElements(driver, this);
	}
	//Put Elements Of The HomePage with PageFactory Object Technique
	
    @FindBy(id="b_tween")
    WebElement Divodafone;
	@FindBy(xpath="//a[@aria-label='Page 2']")
	WebElement SecondPageIcon;

	
	//Put Methods Of The HomePage with PageFactory Object Technique
	public boolean ResultisDisplayed()
	{
		driver.manage().timeouts().implicitlyWait(implicitwait, TimeUnit.SECONDS);
		js.executeScript("arguments[0].style.border='3px solid purple'", Divodafone);
		return Divodafone.isDisplayed();
	}
	public SecondResultPage GOToSecondPage() throws IOException {
		driver.manage().timeouts().implicitlyWait(implicitwait, TimeUnit.SECONDS);
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		js.executeScript("arguments[0].style.border='3px solid purple'", SecondPageIcon);
		SecondPageIcon.click();

		return new SecondResultPage();
	}


}
