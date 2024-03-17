package testcases;

import AllureReport.AllureListener;

import com.Base.com.testBase;
import com.Bing.keywords.KeywordExecutor;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

@Listeners({AllureListener.class})//add it to each class u want to run with allure Listeners 
public class KeywordDrivenTest extends testBase {
	KeywordExecutor keywordEcecutor=new KeywordExecutor();
	public KeywordDrivenTest() throws IOException {
		super();
	}

	//Extent report

	@Parameters({ "Browser" })

	//keyword driven Tests


	@Test(priority = 1,groups= {"E2E"})
	@Description("Key Word Driven Test - Validate number of results ar equal or not on page 2 and 3 ") // allure Report Notations , seen by allure Report

	public void KWDTest_Validate_number_of_results_are_equal_on_page_2_and_3() throws IOException {
		keywordEcecutor.ExecuteKeyWordFromExcel(prop.getProperty("sheetname"));

	}


}
