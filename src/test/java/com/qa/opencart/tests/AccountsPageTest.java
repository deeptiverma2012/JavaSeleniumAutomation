package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.pages.AccountsPage;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accSetUp()
	{
		accPage = loginpg.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void accPageTitleTest()
	{
		Assert.assertEquals(accPage.getAccountsPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
	}
	@Test
	public void accPageURLTest()
	{
		Assert.assertTrue(accPage.getAccountPageURL().contains(AppConstants.ACCOUNT_PAGE_URL_FRACTION));
	}
	@Test
	public void isLogoutLinkExistTest()
	{
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test
	public void isSearchOptionDisplayedTest()
	{
		Assert.assertTrue(accPage.IsSearchOptionDisplayed());
	}
	@Test
	public void headersCountTest()
	{
		List<String> actuaHeadersList = accPage.isAccountHeadersDisplayed();
		System.out.println(actuaHeadersList.size());
		Assert.assertEquals(actuaHeadersList.size(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
		
	}
	@Test
	public void headersTest()
	{
		List<String> actuaHeadersList = accPage.isAccountHeadersDisplayed();
		System.out.println(actuaHeadersList);
		Assert.assertEquals(actuaHeadersList, AppConstants.ACCOUNTS_PAGE_HEADERS_LIST);
	}
	
	@Test
	public void searchTest()
	{
		searchResultPg = accPage.doSearch("MacBook");
		productInfoPg= searchResultPg.selectProduct("MacBook Air");
		String actProductName = productInfoPg.getProductHeaderName();
		Assert.assertEquals(actProductName, "MacBook Air");
		
		
	}
}
