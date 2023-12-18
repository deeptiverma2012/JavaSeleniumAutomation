package com.qa.opencart.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
	private WebDriver driver;
	private JavascriptExecutor js;
	
	public JavaScriptUtil(WebDriver driver)
	{
		this.driver = driver;
		js = (JavascriptExecutor) this.driver;
				
	}
	
	public String getTitleByJS()
	{
	  return js.executeScript("return document.title").toString();
	    
	}
	
	public String getUrlByJS()
	{
	   return js.executeScript("return document.URL").toString();
		
	}
	
	public void generateJSAlert(String message)
	{
		js.executeScript("alert('"+message+"')");
		
		try{
			Thread.sleep(500);
		}
		catch(InterruptedException e)
		{
		  e.printStackTrace();	
		}
		driver.switchTo().alert().accept();
	}
	
	public void generateJSConfirmationAlert(String message)
	{
		js.executeScript("confirm('"+message+"')");
		
		try{
			Thread.sleep(500);
		}
		catch(InterruptedException e)
		{
		  e.printStackTrace();	
		}
		driver.switchTo().alert().accept();
	}
	public void generateJSPromptAlert(String message, String value)
	{
		js.executeScript("prompt('"+message+"')");
		try {
		      Thread.sleep(500);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		Alert alert = driver.switchTo().alert();
				alert.sendKeys(value);
				alert.accept();
		
	}
	
	public void goBackWithJS()
	{
		js.executeScript("history.go(-1)");
	}
	public void goForwardWithJS()
	{
		js.executeScript("history.go(1)");
	}
	public void refreshPageWithJS()
	{
		js.executeScript("history.go(0)");
	}
	
	public String getInnerText()
	{
		return js.executeScript("document.documentElement.innerText").toString();
	}
	public void scrollPageDownWithJS()
	{
		js.executeScript("window.scrollTo(0,document.body.scrollHeight);");
	}
	public void scrollToMidOfPageDownWithJS()
	{
		js.executeScript("window.scrollTo(0,document.body.scrollHeight/2);");
	}
	public void scrollPageDownWithJS(String height)
	{
		js.executeScript("window.scrollTo(0,'"+height+"')");
	}
	public void scrollPageUpWithJS()
	{
		js.executeScript("window.scrollTo(document.bodyscrollHeight,0);");
	}
	
	public void scrollInToView(WebElement element)
	{
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	public void zoomChromeSafariEdge(String zoomPercentage)
	{
		js.executeScript("document.body.style.zoom ='"+zoomPercentage+"';");
	}
	public void zoomFirefox(String zoomPercentage)
	{
	  String zoom =	"document.body.style.MozTransform = 'scale('"+zoomPercentage+"')'";
	  js.executeScript(zoom);
	}
	
	public void drawBorder(WebElement element)
	{
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	public void changeColor(String color, WebElement element)
	{
		js.executeScript("arguments[0].style.backgroundColor ='"+color+"'", element);
		try {
		Thread.sleep(20);
		}
		catch(InterruptedException e)
		{
			
		}
	}
	
	public void flash(WebElement element)
	{
		String bgcolor = element.getCssValue("backgroundColor");
		for(int i=0; i<10;i++)
		{
			changeColor("rgb(0,200,0)", element);//Green
			changeColor(bgcolor, element);//Purple
		}
	}
	
	public void clickByJS(WebElement element)
	{
		js.executeScript("arguments[0].click()", element);
	}
	
	public void sendKeysUsingIdWithJS(String Id, String value)
	{
		js.executeScript("document.getElementById('"+ Id +"').value ='"+value+"'");
	}
}



