package com.qa.opencart.tests;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {
	
	@BeforeClass
	public void registrationSetUp()
	{
		registerPg = loginpg.navigateToRegister();
	}
	
	public  String getRandomEmailId()
	{
	  return "testautomation"+System.currentTimeMillis()+"@opencart.com";
	  
	 //return "testautomation"+UUID.randomUUID()+"opencart.com";
	  
	  
	}
	
//	@DataProvider
//	public Object[][] getUserRegData()
//	{
//		return new Object[][] {
//			{"Tim","Johns","9999999999","Selenium@123","Selenium@123", "yes"},
//			{"Leena","Adams","9999999999","Selenium@123","Selenium@123", "no"},
//			{"Smith","Neils","9999999999","Selenium@123","Selenium@123", "yes"}
//		};
//	} 
	
	@DataProvider
	public Object[][] getExcelTestData()
	{
		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_TEST_DATA_SHEET);
		return regData;
	
	}
	
	@Test(dataProvider = "getExcelTestData")
	public void registerPageTest(String firstName, String lastName,String telephone, String password, String confirmPassword, String subscribe)
	{
		
		boolean isRegDone = registerPg.userRegistration(firstName,lastName,getRandomEmailId(),telephone,password,confirmPassword,subscribe);
		Assert.assertTrue(isRegDone);
	}

}
