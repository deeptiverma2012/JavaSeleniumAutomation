package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;

	//By locators OR Object Repository  OR Page locators
	private By userName = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@type='submit']");
	
	private By forgotLink = By.xpath("(//a[text() ='Forgotten Password'])[1]");
	private By logo = By.xpath("//img[@title='naveenopencart']");
	private By footerLinks= By.xpath("//div[@class='row']//div//ul/li");
	private By registerLink = By.linkText("Register");
	
	//page const....
	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}
	
	//actions/ Methods
	@Step("getting login page title")
	public String getLoginPageTitle()
	{
		
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Login Page Title :"+ title);
		return title;
	}
	@Step("getting login page url")
	public String getLoginPageURL()
	{
		String url = eleUtil.waitForUrl(AppConstants.LOGIN_PAGE_URL_FRACTION, AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Login Page URL :"+ url);
		return url;
	}
	@Step("checking forgot password link exists")
	public boolean IsForgotPwdLinkExists()
	{
		return eleUtil.waitForVisibilityOfElement(forgotLink, AppConstants.MEDIUM_DEFAULT_WAIT).isDisplayed();
	}
	@Step("checking if logo on login page")
	public boolean IsLoginPageLogoDisplayed()
	{
		return eleUtil.waitForVisibilityOfElement(logo, AppConstants.SHORT_DEFAULT_WAIT).isDisplayed();
	}
	@Step("verifying login functionality with user:{0} and password{1}")
	public AccountsPage doLogin(String username, String pwd )
	{	
		System.out.println("Credentials:"+ username+":"+"pwd");
		eleUtil.waitForVisibilityOfElement(userName, AppConstants.MEDIUM_DEFAULT_WAIT).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		System.out.println("User is logged In");
		return new AccountsPage(driver);
	}
	@Step("Verifying footer links")
	public int FooterLinkDisplayed()
	{
		List<WebElement> eleList = eleUtil.waitForPresenceOfAllElement(footerLinks, AppConstants.MEDIUM_DEFAULT_WAIT);
		return eleList.size();
	}
	@Step("Veryfying navigation to register page")
	public RegisterPage navigateToRegister()
	{
		eleUtil.waitForPresenceOfElement(registerLink, AppConstants.LONG_DEFAULT_WAIT).click();
		return new RegisterPage(driver);
	}
	
	
}
