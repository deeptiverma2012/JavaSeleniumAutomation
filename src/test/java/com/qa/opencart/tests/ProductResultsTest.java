package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class ProductResultsTest extends BaseTest {
	
	@BeforeClass
	public void productInfoSetUp()
	{
		accPage = loginpg.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
//	@DataProvider
//	public Object[][] getSearchData()
//	{
//		return new Object[][] {
//			
//			{"MacBook","MacBook Pro",4},
//			{"MacBook","MacBook Air",4},
//			{"samsung","Samsung SyncMaster 941BW",1}
//		};
//		}
	
	@DataProvider
	public Object[][] getSearchExcelData()
	{
		Object searchData[][] = ExcelUtil.getTestData(AppConstants.PRODUCT_SEARCH_DATA_SHEET);
		return searchData;
	}
	
	
	@Test(dataProvider = "getSearchExcelData")
	public void productImagesTest(String searchKey, String productName, String imgCount)
	{
		searchResultPg = accPage.doSearch(searchKey);
		productInfoPg = searchResultPg.selectProduct(productName);
		Assert.assertEquals(String.valueOf(productInfoPg.getproductImageCount()), imgCount);
		
		
	}
	@Test
	public void getProductDetailsTest()
	{
		searchResultPg = accPage.doSearch("MacBook");
		productInfoPg = searchResultPg.selectProduct("MacBook Air");
		Map<String, String> prodDetailsMap = productInfoPg.getProductDetails();
//		Assert.assertEquals(prodDetailsMap.get("Brand"), "Apple");
//		Assert.assertEquals(prodDetailsMap.get("Availability"), "In Stock");
//		Assert.assertEquals(prodDetailsMap.get("Reward Points"), "700");
//		Assert.assertEquals(prodDetailsMap.get("Product Code"), "Product 17");
		softAssert.assertEquals(prodDetailsMap.get("Brand"), "Apple");
		softAssert.assertEquals(prodDetailsMap.get("Availability"), "In Stock");
		softAssert.assertEquals(prodDetailsMap.get("Reward Points"), "700");
		softAssert.assertEquals(prodDetailsMap.get("Product Code"), "Product 17");
		
		softAssert.assertAll();// This is mandatory 
	}

}
