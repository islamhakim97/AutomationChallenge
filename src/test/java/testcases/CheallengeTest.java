package testcases;

import AllureReport.AllureListener;
import com.Base.com.testBase;
import com.Bing.pages.*;
import com.Bing.util.TestUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.xpath.operations.Bool;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;

@Listeners({AllureListener.class})//add it to each class u want to run with allure Listeners 
public class CheallengeTest extends testBase {

	public CheallengeTest() throws IOException {
		super();
	}

	BingSearchPage searchPage;
	ResultPage resultPage;
	SecondResultPage secondResultPage;
	ThirdPageResultPage thirdResultPage;
	String date,name;
	int numpage2,numpage3;
	@Parameters({ "Browser" })
	@BeforeTest()
	public void setup(Method method, String browser) throws IOException{
		initialization(browser);
		searchPage = new BingSearchPage();// after you initialize your driver
		searchPage.getMainPage();
	}
	@Parameters({ "Browser" })
	@BeforeMethod(groups={"E2E","Regression"})
	public void Reportsetup(Method method, String browser) throws IOException
	{
		/*initialization(browser);
		searchPage = new BingSearchPage();// after you initialize your driver
		searchPage.getMainPage();*/
		date=TestUtils.TCstime();
		name =method.getName()+date;//Assign Date and Time To picName
		TestUtils.LogTCsNamesToREport(name);
	}

	@AfterMethod(groups= {"E2E","Regression"})
	public void ReportearDown(Method method, ITestResult result) throws IOException // ITestResult is TestNG listener to log test status[pass|fail|skipped]
	{ 
		name =method.getName()+date;//Assign Date and Time To picName
		//  Take SnapShot:
		TestUtils.TakePicture(name);
		// ** Log Test Status to the Report:
		TestUtils.LogTestStatusToExtentReport(result,name);
		//closeBrowser();

	}
	@AfterTest()
	public void tearDown() throws IOException
	{

		closeBrowser();

	}

	@Test(priority = 1,groups= {"E2E"})
	@Description("Open Bing Website") // allure Report Notations , seen by allure Report

	public void Open_Bing_Website() throws IOException {
		Boolean AR=searchPage.OpenBingWebsite();
		Assert.assertTrue(AR);
	}

	@Test(priority = 2,groups= {"E2E"})
	@Description("Type and Search For Vodafone") // allure Report Notations , seen by allure Report

	public void TypeandSearchForVodafone() throws IOException {
		resultPage =searchPage.performSearch(prop.getProperty("srchtxt"));
		Boolean AR=resultPage.ResultisDisplayed();
		Assert.assertTrue(AR);
	}

	@Test(priority = 3,groups= {"E2E"})
	@Description("Scroll Down And Go To The Second Page") // allure Report Notations , seen by allure Report
	public void ScrollDownAndGoToTheSecondPage() throws IOException {
		secondResultPage=resultPage.GOToSecondPage();
		Boolean AR=secondResultPage.secondResultisDisplayed();
		Assert.assertTrue(AR);
	}
	@Test(priority = 4,groups= {"E2E"})
	@Description("Count The Number Of Results Displayed On The Second Page") // allure Report Notations , seen by allure Report
	public void  CountTheNumberOfResultsDisplayedOnTheSecondPage() throws IOException {
		Boolean AR=secondResultPage.secondResultisDisplayed();
		Assert.assertTrue(AR);
		// Count search results on second page
		numpage2=countNumOfSearchResults();

	}
	@Test(priority = 5,groups= {"E2E"})
	@Description("Scroll Down And Go To The Third Page") // allure Report Notations , seen by allure Report
	public void  ScrollDownAndGoToTheThirdPage() throws IOException {
		//Go To Third result Page
		thirdResultPage=secondResultPage.GOToThirdPage();
		Boolean AR=thirdResultPage.ThirdResultisDisplayed();
		Assert.assertTrue(AR);
	}
	@Test(priority = 5,groups= {"E2E"})
	@Description("Validate If Ihe Number Of Results On Page 2 is equal to page 3 or not") // allure Report Notations , seen by allure Report
	public void   ValidateIfIheNumberOfResultsOnPage2isequaltopage3ornot() throws IOException {
		//Count search results on third page
		numpage3=countNumOfSearchResults();
		//Validate if the number of results on page 2 is equal to page 3 or not
		Assert.assertEquals(numpage2,numpage3," number of results on page 2 and page 3 are not equal");
	}


	/*// Search Functionality
	@Test(priority = 1,groups= {"E2E","Regression"}) // [1-Fail]  // make it fail to see it failed in allure
	@Description("Verify Search Results For Vodafone") // allure Report Notations , seen by allure Report

	public void checkSearchFunctionality() throws IOException {
		resultPage = searchPage.performSearch(prop.getProperty("srchtxt"));

		resultPage.ResultisDisplayed();
		//Assert.assertTrue(AR);
		secondResultPage= resultPage.GOToSecondPage();

		secondResultPage.secondResultisDisplayed();
		//Assert.assertTrue(AR);
        // Count search results on second page
		numpage2=countNumOfSearchResults();
		//Go To Third result Page
		thirdResultPage=secondResultPage.GOToThirdPage();
		thirdResultPage.ThirdResultisDisplayed();
		//Count search results on third page
		numpage3=countNumOfSearchResults();
		//Validate if the number of results on page 2 is equal to page 3 or not
		Assert.assertEquals(numpage2,numpage3," number of results on page 2 and page 3 are not equal");



	}*/



}
