package com.Bing.keywords;


import com.Base.com.testBase;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bouncycastle.oer.Switch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.Bing.util.TestUtils.getDataFromExcel;

public class KeywordExecutor extends testBase {
    List<Integer>numOfCites=null;//for adding the count for page2 and 3 to be compared later
    public static Workbook book;
    public static Sheet sheet;
    String projectPath=System.getProperty("user.dir");//projectPath
     String SCENARIO_SHEET_PATH = projectPath+prop.getProperty("keywordsxlsx");
    public KeywordExecutor() throws IOException {
        super();
        PageFactory.initElements(driver,this);
    }


    public void ExecuteKeyWordFromExcel(String sheetName) throws IOException {
        FileInputStream file = null;
        try {
            file = new FileInputStream(SCENARIO_SHEET_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            book = WorkbookFactory.create(file);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sheet = book.getSheet(sheetName);
        int k = 0;

        for (int i = 1; i < sheet.getLastRowNum(); i++) // i=1 to satrt from row 1 not 0 as row 0 is for headers
        {
            try {
                String Function = sheet.getRow(i).getCell(k + 3).toString().trim();
                String LocatorBy = sheet.getRow(i).getCell(k + 4).toString().trim();
                String LocatorElement = sheet.getRow(i).getCell(k + 5).toString().trim();
                String Param = sheet.getRow(i).getCell(k + 6).toString().trim();
                //print the row
                System.out.println(Function + "\t" + LocatorBy + "\t" + LocatorElement + "\t" + Param);
                //Action
                Excute(Function,LocatorBy,LocatorElement,Param);


            }catch (Exception e) {

            }
        }
    }

public void Excute(String Function,String LocatorBy, String LocatorElement, String Param) throws IOException {

    switch (Function) {
        case "launchbing":
           launch();
          //  initialization(System.getProperty("browser"));
            break;
        case "filltxt":
            fillTxt(LocatorBy, LocatorElement, Param);
            break;
        case "click":
            click(LocatorBy, LocatorElement);
            break;
        case "scrolldown":
            scrollDown();
            break;
        case "count":
            count(LocatorBy, LocatorElement);
            break;
        case "assert":
            compare();
            break;
        case "close":
            quite();
            break;

    }
}

    public void fillTxt(String locatorBy,String locatorValue,String text)
    {
        switch (locatorBy)
        {
            case "xpath":
                driver.findElement(By.xpath(locatorValue)).sendKeys(text);
                break;
            case "id":
                driver.findElement(By.id(locatorValue)).sendKeys(text);
                break;
            case "name":
                driver.findElement(By.name(locatorValue)).sendKeys(text);
                break;
            case "tagName":
                driver.findElement(By.tagName(locatorValue)).sendKeys(text);
                break;
        }
    }
    public void click(String locatorBy,String locatorValue)
    {
        switch (locatorBy)
        {
            case "xpath":
                driver.findElement(By.xpath(locatorValue)).click();
                driver.manage().timeouts().implicitlyWait(Long.parseLong(prop.getProperty("implicitwait")), TimeUnit.SECONDS);//wait 10 sec
                break;
            case "id":
                driver.findElement(By.id(locatorValue)).click();
                break;
            case "name":
                driver.findElement(By.name(locatorValue)).click();
                break;
            case "tagName":
                driver.findElement(By.tagName(locatorValue)).click();
                break;
        }
    }
    public void count(String locatorBy,String locatorValue)
    {
        int count=0;
        switch (locatorBy)
        {

            case "tagName":
                List <WebElement>listofLinks=driver.findElements(By.tagName(locatorValue));
                for (WebElement cite : listofLinks ){

                    count ++;
                }
                System.out.println(count);
                numOfCites.add(count);//add num of results for page 2 and 3

                break;
        }
    }
    public void scrollDown()
    {
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }
    public void compare()
    {
        int AR = numOfCites.get(0);
        int ER = numOfCites.get(0);
        for(int i=0;i<numOfCites.size();i++)
        {
            System.out.println(numOfCites.get(i));
        }

        Assert.assertEquals(AR,ER,"Num of results on page 2 and 3 are not Equal");
    }
    public void launch()
    {
        driver=new ChromeDriver();
        driver.get(prop.getProperty("Bing"));
    }
    public void quite()
    {
        driver.quit();
    }


}
