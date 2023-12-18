package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	By productHeader = By.cssSelector("div#content h1");
	By imagesLocator = By.xpath("//ul[@class='thumbnails']//img");
	By productMetaData = By.xpath("(//div[@id='content']//ul)[3]/li");
	By productPriceData = By.xpath("(//div[@id='content']//ul)[4]/li");
	
	
	private Map<String, String> productMap = new HashMap<String,String>();
	
	public ProductInfoPage(WebDriver driver)
	{
	   this.driver = driver;
	   eleUtil = new ElementUtil(this.driver);
	}
	
	public String getProductHeaderName()
	{
		String productHeaderValue = eleUtil.doElementGetText(productHeader);
		System.out.println("product header is:"+ productHeaderValue);
		return productHeaderValue;
	}

	public int getproductImageCount() {
		int imagesCount = eleUtil.waitForVisibilityOfElements(imagesLocator,AppConstants.LONG_DEFAULT_WAIT ).size();		
		System.out.println("The product name:"+ getProductHeaderName()+ "Image Count:"+ imagesCount);
		return imagesCount;
	}
	
	/*Brand: Apple
	Product Code: Product 17
	Reward Points: 700
	Availability: In Stock*/
	private void getProductMetaData()
	{
		List <WebElement>metaDataList = eleUtil.waitForVisibilityOfElements(productMetaData, AppConstants.MEDIUM_DEFAULT_WAIT);
		for(WebElement e: metaDataList)
		{
			String metaData = e.getText();
			
		String metaKey = metaData.split(":")[0].trim();
		String metaVal = metaData.split(":")[1].trim();
		productMap.put(metaKey, metaVal);
			
		}
	}
	
	private void getPriceMetaData()
	{
		List<WebElement> priceMetaDataList = eleUtil.waitForVisibilityOfElements(productPriceData, AppConstants.MEDIUM_DEFAULT_WAIT);
		String productPrice = priceMetaDataList.get(0).getText();
		String productExTax = priceMetaDataList.get(1).getText();
		productMap.put("Price",productPrice );
		//String exTaxPriceKey = productExTax.split(":")[0].trim();// I am not using this as key because I want to remove the first part & give custom key
		String exTaxPriceVal = productExTax.split(":")[1].trim();
		
		productMap.put("exTaxPrice",exTaxPriceVal);
	}
	
	public Map<String, String> getProductDetails()
	{
		productMap.put("productname", getProductHeaderName());
		getProductMetaData();
		getPriceMetaData();
		
		return productMap;
	}
	
	
	
	
	

}
