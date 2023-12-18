package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic E100:Open cart login page implementation")
@Story("Strory U1234: Login page features implementation")
@Feature("F1: Login page functionality")
public class LoginPageTest extends BaseTest {
	
	
	@Description("login page title test")
	@Severity(SeverityLevel.MINOR)
	
	@Test(priority=1)
	public void loginPageTitleTest()
	{
		String actualtitle = loginpg.getLoginPageTitle();
		Assert.assertEquals(actualtitle,AppConstants.LOGIN_PAGE_TITLE);
		
	}
	@Description("login page url test")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=2)
	public void loginPageURLTest()
	{
		String actualURL = loginpg.getLoginPageURL();
		Assert.assertTrue(actualURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION));
	}
	@Description("checking forgot password link")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=3)
	public void forgotPwdLinkTest()
	{
		Assert.assertTrue(loginpg.IsForgotPwdLinkExists());
		
	}
	@Description("checking logo on login page")
	@Severity(SeverityLevel.MINOR)
	@Test(priority=4)
	public void appLogoTest()
	{
	  Assert.assertTrue(loginpg.IsLoginPageLogoDisplayed());
		
	}
	@Description("verifying login functionality")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority=6)
	public void doLoginTest()
	{
		accPage = loginpg.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	@Description("Verifying footer links")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=5)
	public void footerLinkDisplayedTest()
	{
		Assert.assertEquals(loginpg.FooterLinkDisplayed(), 16);
	}

}
