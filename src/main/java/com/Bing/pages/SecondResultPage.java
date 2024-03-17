package com.Bing.pages;

import com.Base.com.testBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SecondResultPage extends testBase{

	int implicitwait = (int) Long.parseLong(prop.getProperty("implicitlyWait"));
	public SecondResultPage() throws IOException {
		PageFactory.initElements(driver, this); // creates the findElement calls behind the scene. - PageFactory.initElements(driver, this);
	}
	//Put Elements Of The HomePage with PageFactory Object Technique

    @FindBy(id="b_tween")
    WebElement Divodafone;
	@FindBy(xpath="//a[@aria-label='Page 3']")
	WebElement ThirdPageIcon;

	//Put Methods Of The HomePage with PageFactory Object Technique
	public boolean secondResultisDisplayed()
	{
		driver.manage().timeouts().implicitlyWait(implicitwait, TimeUnit.SECONDS);
		js.executeScript("arguments[0].style.border='3px solid purple'", Divodafone);
		return Divodafone.isDisplayed();
	}

	public ThirdPageResultPage GOToThirdPage() throws IOException {
		driver.manage().timeouts().implicitlyWait(implicitwait, TimeUnit.SECONDS);
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		js.executeScript("arguments[0].style.border='3px solid purple'", ThirdPageIcon);
		ThirdPageIcon.click();

		return new ThirdPageResultPage();
	}

}
