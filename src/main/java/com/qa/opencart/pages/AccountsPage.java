package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class AccountsPage {
	private WebDriver driver;
	private ElementUtil eleUtil;	
	
	
	//Page locators
	By logoutLink = By.linkText("Logout");
	By search = By.name("search");
	By searchIcon = By.xpath("//button[@type='button']/i[@class='fa fa-search']");
	By accountHeaders = By.xpath("//div[@id='content']/h2");
	
	
	
	//Const..
	public AccountsPage(WebDriver driver)
	{
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}
	//actions / Methods
	
	public String getAccountsPageTitle()
	{
		
		String title = eleUtil.waitForTitleIs("My Account", 5);
		System.out.println("Account Page Title :"+ title);
		return title;
	}
	public String getAccountPageURL()
	{
		String url = eleUtil.waitForUrl("account/account", 5);
		System.out.println("Account Page URL :"+ url);
		return url;
	}
	@Step("Verifying if logout link exists")
	public boolean isLogoutLinkExist()
	{
		return eleUtil.waitForPresenceOfElement(logoutLink, 5).isDisplayed();
	}
	
	public void logout()
	{
		if(isLogoutLinkExist())
		{
			eleUtil.doClick(logoutLink);
		}
	}
	
	public boolean IsSearchOptionDisplayed()
	{
		return eleUtil.waitForVisibilityOfElement(search, 5).isDisplayed();
	}
	
	public List<String> isAccountHeadersDisplayed() {
		List<WebElement>headersList = eleUtil.waitForVisibilityOfElements(accountHeaders, 5);
		List<String> headersVal = new ArrayList<String>();
		for(WebElement e: headersList)
		{
			String text = e.getText();
			headersVal.add(text);
		}
		return headersVal;
		
	}
	
	public SearchResultPage doSearch(String searchKey)
	{
		eleUtil.waitForVisibilityOfElement(search, AppConstants.SHORT_DEFAULT_WAIT).clear();
		eleUtil.waitForVisibilityOfElement(search, AppConstants.SHORT_DEFAULT_WAIT).sendKeys(searchKey);
		eleUtil.doActionsClick(searchIcon);
		return new SearchResultPage(driver);
	}
	
	
}
