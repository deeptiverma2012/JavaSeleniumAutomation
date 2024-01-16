package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultPage;

public class BaseTest {

	protected WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	protected LoginPage loginpg;
	protected AccountsPage accPage;
	protected SearchResultPage searchResultPg; 
	protected ProductInfoPage productInfoPg;
	protected SoftAssert softAssert;
	protected RegisterPage registerPg;
	
	@Parameters({"browser","browserversion","testname"})
	@BeforeTest
	public void setUp(String browserName, String browserVersion, String testName )
	{
	  df = new DriverFactory();
	  prop = df.initProp();
	  
	  if(browserName!= null)
	  {
		  prop.setProperty("browser", browserName);
		  prop.setProperty("browserversion", browserVersion);
		  prop.setProperty("testname", testName);
		  
		 
	  }
	  driver = df.initDriver(prop);
	  loginpg = new LoginPage(driver);
	  softAssert = new SoftAssert();
	}
	@AfterTest
	public void tearDown()
	{
		driver.quit();
	}
}
