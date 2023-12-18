package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage  {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	//By locators
	By firstName = By.id("input-firstname");
	By lastName = By.id("input-lastname");
	By email = By.id("input-email");
	By telephone = By.id("input-telephone");
	By password = By.id("input-password");
	By confirmPassword = By.id("input-confirm");
	By subscribeYes = By.xpath("//label[@class='radio-inline']/input[@value ='1']");
	By subscribeNo = By.xpath("//label[@class='radio-inline']/input[@value ='0']");	
	By agreeCheckbox = By.name("agree");
	By continueButton = By.xpath("//input[@type='submit']");
	By confirmationMsg = By.cssSelector("div#content h1");
	By logout = By.linkText("Logout");
	By register = By.linkText("Register");
		
	
	//Const...
	public RegisterPage(WebDriver driver)
	{
		this.driver=driver;
		eleUtil= new ElementUtil(this.driver);
	}
	
	public boolean userRegistration(String firstName, String lastName, String email,String telephone, String password, String confirmPassword, String subscribe)
	{
		eleUtil.waitForVisibilityOfElement(this.firstName, AppConstants.LONG_DEFAULT_WAIT).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, confirmPassword);
	
		
		if(subscribe.equalsIgnoreCase("yes"))
		{
			eleUtil.doClick(subscribeYes);
		}
		else
		{
			eleUtil.doClick(subscribeNo);
		}
		eleUtil.doClick(agreeCheckbox);
		eleUtil.doClick(continueButton);
		
		String SuccessMsg =	eleUtil.waitForVisibilityOfElement(confirmationMsg, AppConstants.LONG_DEFAULT_WAIT).getText();
		System.out.println(SuccessMsg);
		
		if(SuccessMsg.contains(AppConstants.USER_REGISTER_SUCCESS_MSG))
		{
			eleUtil.doClick(logout);
			eleUtil.doClick(register);
			return true;
		}
		else {
			return false;
		}
		
		
		
		
	}
	}
	

