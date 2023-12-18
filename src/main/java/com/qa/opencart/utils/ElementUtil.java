package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exception.FrameworkException;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {
	private WebDriver driver;
	JavaScriptUtil util;
	public ElementUtil(WebDriver driver)
	{
		this.driver=driver;
		util = new JavaScriptUtil(this.driver);
	}
	
	public By getBy(String locatorType, String locatorValue)
	{
		By by = null;
		switch(locatorType.toLowerCase().trim())
		{
		case"id":
			by = By.id(locatorValue);
			break;
		case "name" :
			by = by.name(locatorValue);
			break;
		case "classname":;
			by = By.className(locatorValue);
			break;
		
		case "xPath":
			by = By.xpath(locatorValue);
			break;
		case "cssselector":
			by = By.cssSelector(locatorValue);
			break;
			
		case "linktext":
			by = By.linkText(locatorValue);
			break;
		case "partiallinktext":
			by = By.partialLinkText(locatorValue);
			break;
		case "tagname":
			by = By.tagName(locatorValue);
			break;
			
			default :
			System.out.println("Wrong input");
			throw new FrameworkException("INVALID LOCATOR TYPE");
				
			}
		return by;
		}

	
	private void isHighlight(WebElement element)
	{
		if(Boolean.parseBoolean(DriverFactory.highlight))
		{
			util.flash(element);
		}
		
	}
	@Step("getting element:{0}")
	public WebElement getElement(By locator)
	{
		WebElement element = driver.findElement(locator);
		isHighlight(element);
		return element;
	}
	
	@Step("getting element with locatorType:{0} and value:{1}")
	public WebElement getElement( String locatorType,String locatorValue)
	{
		WebElement element = driver.findElement(getBy(locatorType, locatorValue));
		isHighlight(element);
		return element;
	}
	@Step("verifying a single element:{0}")
	public  boolean checkSingleElementPresent(By locator)
	{
		return driver.findElements(locator).size() ==1 ? true : false;
	}
	@Step("verifying multiple elements:{0}")
	public  boolean checkElementPresent(By locator)
	{
		return driver.findElements(locator).size()>=1 ? true : false;
	}
	@Step("verifying presence of element:{0}")
	public  boolean checkElementPresent(By locator, int TotalElements)
	{
		return driver.findElements(locator).size() == TotalElements? true : false;
	}
	
	@Step("clicking on locator:{0}")
	public  void doClick(By locator)
	{
		getElement( locator).click();
	}
	@Step("clicking on an element with locatorType:{0} and value:{1}")
	public  void doClick(String locatorType, String locatorValue)
	{
		getElement(locatorType, locatorValue).click();
	}
	@Step("getting text of an element with locator:{0}")
	public String doElementGetText(By locator)
	{
		return getElement(locator).getText();
		
	}
	@Step("sending values to element:{1} and with locatorType:{0} value:{2}")
	public void doSendKeys(String locatorType, String locator, String value)
	{
		getElement(getBy( locatorType, locator)).sendKeys(value);
		
	}
	@Step("sending jeys to element:{0} and value:{1}")
	public void doSendKeys(By locator, String value)
	{
		getElement(locator).sendKeys(value);
	}

	@Step("getting text of element:{0}")
	public String getText(By locator)
	{
		return getElement(locator).getText();
	}
	
	public String getAttribute(By locator, String attributeName)
	{
		return getElement(locator).getAttribute(attributeName);
	}
	
	public List<String> getElementsTextList(By locator)
	{
		List<WebElement> li = getElements(locator);
		List<String> strList = new ArrayList<String>();
		for(WebElement e : li)
		{
			String text = e.getText();
			if(text.length()!=0)
			{
				 strList.add(text);
			}
		}
		return strList;
			
	}
	public List<String> getElementsAttributeList(By locator, String attributeName)
	{
		List<WebElement>list = getElements(locator);
		List<String> li = new ArrayList<String>();
		for(WebElement e: list)
		{
			String text = e.getAttribute(attributeName);
			
			if(text.length()!=0)
			{
				li.add(text);
			}
			
		}
		System.out.println(li);
		
		return li;
	}
	
	public int getElementCount(By locator)
	{
		List <WebElement>li = getElements(locator);
		return li.size();
		
	}
	public List<WebElement> getElements(By locator)
	{
		List <WebElement> list = driver.findElements(locator);
		return list;
				
	}
	public  void Search(By searchField, By searchText, String sendString, String comparisionString) throws InterruptedException
	{
		doSendKeys(searchField,sendString);
		Thread.sleep(3000);
		List <WebElement>list = getElements(searchText);
	
		for(WebElement e: list)
		{
			String text = e.getText();
			
			System.out.println(text);
					
			if(text.equals(comparisionString))
			{
				System.out.println("Search result found--------Pass");
				e.click();
				break;
			}
		}
	}
	
	//**********************************Select Dropdown Utils************************//
	
	private Select createSelect(By locator)
	{
		Select select = new Select(getElement(locator));
		return select;
	}
	
	public void doSelectDropdownByValue(By locator, String value)
	 {
	    createSelect(locator).selectByValue(value);
		//select.selectByValue(value);
	 }

	 public void doSelectDropdownByIndex(By locator, int indexNo)
	 {
		createSelect(locator).selectByIndex(indexNo);
	 }
	 public void doSelectDropdownByVisibleText(By locator, String value)
	 {
		 createSelect(locator).selectByVisibleText(value);
	 }
	 public int getDropdownOptionsCount(By locator)
		{
		    
		    List<WebElement>optionsList = createSelect(locator).getOptions();
			return optionsList.size();
		}
	 public List<String> getDropdownOptions(By locator)
		{
		
			List<WebElement>optionsList =  createSelect(locator).getOptions();
			System.out.println(optionsList.size());
			List<String> optionsTextList = new ArrayList<String>();
			for(WebElement e:optionsList )
			{
				String text = e.getText();
			    optionsTextList.add(text);
			}
			return optionsTextList;
		}
	 
	 public void selectDropdownOptions(By locator, String dropdownText)
		{
			
			 
			 List<WebElement> optionsList =  createSelect(locator).getOptions();
			 System.out.println(optionsList.size());
			 
			 for(WebElement e:optionsList ) {
				String text = e.getText();
				System.out.println(text);
				// Now, select a specific value without using selectByValue, SelectByIndex & SelectByVisibleText
				if(text.equals(dropdownText))
				{
					e.click();
					break;
				}
			 }
		}
	 

		public void selectDropdwnValueWithOutSelect(By locator, String value)
		{
			 List<WebElement> optionsList =	 getElements(locator); 
		     System.out.println(optionsList.size());
		     
		     for(WebElement e: optionsList)
		     {
		    	String text = e.getText();
		    	System.out.println(text);
		    	
		    	if(text.equals(value))
		    	{
		    		e.click();
		    		break;
		    	}
		    }
		}
		

		public boolean isDropdownMultiple(By locator)
		{
			Select select = new Select(getElement(locator));
			return select.isMultiple() ? true: false;
			
		}
		
		/*This method is used to select values from dropdown. It can select
		 * 1. Multiple dropdown values
		 * 2. Single dropdown value
		 * 3. All selection : To select all the dropdown options available pass all 
		 * @param locator
		 * @param values
		 * */
		public void selectDropdownMultipleValues(By locator,By optionsLocator, String... values)
		{
			Select select = new Select(getElement(locator));
			
			
			 if(isDropdownMultiple(locator))
			 {
				 if(values[0].equals("all"))
				 {
					List <WebElement> optionsList = getElements(optionsLocator);
					for(WebElement e: optionsList)
					{
						e.click();
					}
					
				 }
				 else {
				 for(String eval:values)
				 {
					 select.selectByVisibleText(eval);
				 }
				 }
				 
			 }
		}
		
		/* This method is going to select dropdown values from a non select dropdown
		 * It can select multiple dropdown values
		 * Singlr dropdown value
		 *  All selection : To select all the dropdown options available pass all 
		 * @param locator
		 * @param values */
		public  void selectMultipleValNonSelectDropdown(By locator,By optionsLocator, String... optionsValue) throws InterruptedException
		{
			 Thread.sleep(1000);
	         getElement(locator).click();
			 
			 Thread.sleep(2000);		
			
			
				if(optionsValue[0].equals("all"))
				{
					List<WebElement> optionsList =  getElements(optionsLocator);
					for(WebElement e : optionsList)
					{ 
						try {
				   		       e.click();
				   		 }
						catch(ElementNotInteractableException ex)
						{
						   System.out.println("ElementNotInteractableException occurred");
						   break;
							
						}
									
					}
				}
				else
				{
					List<WebElement> optionsList =  getElements(optionsLocator);
					for(WebElement e : optionsList)
					{
						String text = e.getText();
						System.out.println(text);
						for(String val:optionsValue)
						{
							if(val.equals(text))
							{
								e.click();
								break;
							}
							
						}
						
					}
					
				
				}
				
		}
		
		//*****************************Actions Util**********************************//
		public void parentChildMenuHandling(By parentMenuLocator, By childMenuLocator) throws InterruptedException
		{
			 Actions act = new Actions(driver);
			 Thread.sleep(2000);
			 act.moveToElement(getElement(parentMenuLocator)).build().perform();
			 Thread.sleep(2000);
			 doClick(childMenuLocator);
		}
			
		public void doActionsClick(By locator)
		{
			Actions act = new Actions(driver);
			act.click(getElement(locator)).perform();
		}
		public void doActionsSendKeys(By locator, String value)
		{
			Actions act = new Actions(driver);
			act.sendKeys(getElement(locator),value).perform();
		}
		
		public void doActionsSendKeysWithPause(By locator, String val)
		{
			WebElement attribute = getElement(locator);
			 String value = val;
			 char[] v = value.toCharArray();
			 
			 for(char c: v )
			 {
				 Actions act = new Actions(driver);
				 act.sendKeys(attribute, String.valueOf(c)).pause(500).perform();
			 }
		
		}
	//***************************************Wait Utils**********************************************************
		/*An expectation for checking that an element is present on the DOM of a page. 
		 * This does not necessarily mean that the element is visible.	 *
		 * @param locator
		 * @param timeout
		 * @return
		 * */
		public WebElement waitForPresenceOfElement(By locator, int timeout)
		{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			WebElement element =  wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			isHighlight(element);
			return element;
			
			
		}
		/*An expectation for checking that an element is present on the DOM of a page. 
		 * This does not necessarily mean that the element is visible.	 *
		 * @param locator
		 * @param timeout
		 * @param intervalTime
		 * @return
		 * */
		public WebElement waitForPresenceOfElement(By locator, int timeout, int intervalTime)
		{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout),Duration.ofSeconds(intervalTime) );
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			isHighlight(element);
			return element;
		}
		
		/*An expectation for checking that an element is present on the DOM of a page and visible.
		 * Visibility means that the element is not only displayed but 
		 * also has a height and width that is greater than 0.
		 * @param locator
		 * @param timeout
		 * @return*/
		@Step("waiting for the visibility of {0} and timeout:{1}")
		public WebElement waitForVisibilityOfElement(By locator, int timeout)
		{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			isHighlight(element);
			return element;
		}
		/*An expectation for checking that an element is present on the DOM of a page and visible.
		 * Visibility means that the element is not only displayed but 
		 * also has a height and width that is greater than 0.
		 * @param locator
		 * @param timeout
		 * @param intervalTime
		 * @return*/
		public WebElement waitForVisibilityOfElement(By locator, int timeout, int intervalTime)
		{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout),Duration.ofSeconds(intervalTime));
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			isHighlight(element);
			return element;
			
		}
		
		public void doClickWithWait(By locator, int timeout)
		{
			 waitForVisibilityOfElement(locator, timeout).click();
		}
		
		public void doSendKeys(By locator, int timeout, String value)
		{
			waitForVisibilityOfElement(locator,timeout).sendKeys(value);
		}
		
		
		
		/*An expectation for checking that all elements present on the web page that match the locatorare visible. 
		 * Visibility means that the elements are not only displayed but also have a heightand width that is greater than 0.
         * @param locator
		 * @param timeout
		 * @return*/
     	public List <WebElement> waitForVisibilityOfElements(By locator, int timeout)
		{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			return  wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
     	
     	/*An expectation for checking that there is at least one element present on a web page.
     	 * @param locator
		 * @param timeout
		 * @return*/
     	public List <WebElement> waitForPresenceOfAllElement(By locator, int timeout)
		{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			return  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			
		}

     	public String waitForTitleContains(String titleFraction, int timeout)
    	{
    		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    		try{if(wait.until(ExpectedConditions.titleContains(titleFraction)))
    		{
    			return driver.getTitle();
    		}
    		}
    		catch(TimeoutException e)
    		{
    			System.out.println(titleFraction+"Title not present");
    			e.printStackTrace();
    		}
    		
    		return null;
    	}
     	@Step("waiting for the page title:{0} and timeout:{1}")
    	public String waitForTitleIs(String title, int timeout)
    	{
    		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    		try{if(wait.until(ExpectedConditions.titleIs(title)))
    		{
    			return driver.getTitle();
    		}
    		}
    		catch(TimeoutException e)
    		{
    			System.out.println(title+"Title not present");
    			e.printStackTrace();
    		}
    		
    		return null;
    	}
    	@Step("waiting for url:{0} and timeout:{1}")
    	public String waitForUrl(String urlFraction, int timeout)
    	{
    		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
    		try {
    		if(wait.until(ExpectedConditions.urlContains(urlFraction)))
    		{
    			return driver.getCurrentUrl();
    		}
    		}
    		catch(TimeoutException e)
    		{
    			System.out.println(urlFraction+"Url not present");
    			e.printStackTrace();
    			
    		}
    		return null;
    	}
    	public String waitForUrlIs(String url, int timeout)
    	{
    		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
    		try {
    		if(wait.until(ExpectedConditions.urlToBe(url)))
    		{
    			return driver.getCurrentUrl();
    		}
    		}
    		catch(TimeoutException e)
    		{
    			System.out.println(url+"Url not present");
    			e.printStackTrace();
    		}
    		return null;
    	}
    	
    	//*******************************************Alert Utils / Non WebElement**************************************
    	
    	
    	public Alert waitForJSAlert(int timeout)
    	{
    		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    		return  wait.until(ExpectedConditions.alertIsPresent());
    		
    	}
    	
    	public void acceptJsAlert(int timeout)
    	{
    		waitForJSAlert(timeout).accept();
    	}
    	
    	public void dismissJsAlert(int timeout)
    	{
    		waitForJSAlert(timeout).dismiss();
    	}
    	
    	public String getAlertText(int timeout)
    	{
    		return waitForJSAlert(timeout).getText();
    	}
    	
    	public void enterValueOnJSAlert(int timeout, String value)
    	{
    		waitForJSAlert(timeout).sendKeys(value);
    	}
    	public void waitForFrameByLocator(By framelocator, int timeout)
    	{
    		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
    		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(framelocator));
    	}
    	public void waitForFrameByIndex(int frameIndex, int timeout)
    	{
    		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
    		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
    	}
    	public void waitForFrameByIdOrName(String idOrName, int timeout)
    	{
    		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
    		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
    	}
    	public void waitForFrameByWebElement(WebElement frameElement, int timeout)
    	{
    		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
    		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
    	}
    	public boolean checkNewWindowExist(int timeout, int ExpectedNoOfWindows)
    	{
    		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    		 try { 
    		 if( wait.until(ExpectedConditions.numberOfWindowsToBe(2)))
    		   { return true;
    		   }
    		 }
    		 catch(TimeoutException e) {
    			System.out.println("No. of windows expected are not same");
    		 }
    		   
    		   return false;
    		  }
    	
    	/*An expectation for checking an element is visible and enabled such that you can click it.
    	 * @Param locator
    	 * @param timeout*/
    	public  void clickElementWhenReady(By locator, int timeout)
    	{
    		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    		try{
    			 wait.until(ExpectedConditions.elementToBeClickable(locator));
    		}
    		catch(TimeoutException e)
    		{
    			System.out.println("Element is not clickable or enabled");
    		}
    	}
    	
    	public WebElement waitForElementWithFluentWait(By locator, int timeout, int interval) {
    		
    		Wait<WebDriver> wait =  new FluentWait<WebDriver>(driver)
     				.withTimeout(Duration.ofSeconds(timeout))
     				.pollingEvery(Duration.ofSeconds(interval))
     				.withMessage("Timeout occurred... Element not found")
     				.ignoring(NoSuchElementException.class);

    		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    		isHighlight(element);
    		return element;
    	
    	}

    	public WebElement retryingElement(By locator, int timeout)
    	{
    		WebElement element = null;
    		int totalAttempt = 0;
    		while(totalAttempt<timeout)
    		{
    			try 
    		
    			{
    		
    			 element = getElement(locator);
    			 System.out.println("Element is found"+ locator+ "No. of attempt/s :  "+totalAttempt);
    			 break;
    			}
    		catch(NoSuchElementException e)
    		{
    			System.out.println("Element is not found"+locator+"No. of attempt/s : "+totalAttempt);
    			try {
    				Thread.sleep(500);//polling time
    			} catch (InterruptedException e1) {
    				e1.printStackTrace();
    			}
    			
    		}
    			totalAttempt++;
    			
    		}
    	
    			if(element == null) {
    				System.out.println("element is not found.... tried for "+timeout+"with the interval.."+"500 miliseconds");
    			    throw new FrameworkException("No Such Element");
    			}
    			isHighlight(element);
    			return element;
    		}
    	 public Boolean isPageLoaded(int timeout)
    	 {
    		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    		 String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState =='complete'")).toString();
    	     return Boolean.parseBoolean(flag);
    	 }
    	
    	

		     

}
