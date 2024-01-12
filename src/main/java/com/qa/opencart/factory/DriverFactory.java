package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {
	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();
	public static String highlight = null;
	
	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		
		
		//String browserName = System.getProperty("browser");// To pass environmt as a commandline argument write System.setProperty instead of prop.getProperty()
		
		System.out.println("browser name is:"+browserName);
		highlight = prop.getProperty("highlight");
		System.out.println(highlight);
	
		optionsManager = new OptionsManager(prop);
		
		switch(browserName.toLowerCase().trim())
		{
		case "chrome":
		{
			if(Boolean.parseBoolean(prop.getProperty("remote")))
			{
				//run it on remote
				initRemoteDriver(browserName);
			}
			else {
				// run on local
				//driver = new ChromeDriver(optionsManager.getChromeOptions());
				tldriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
				
			}
			
		}
			break;
		case "edge":
		{

			if(Boolean.parseBoolean(prop.getProperty("remote")))
			{
				//run it on remote
				initRemoteDriver(browserName);
			}
			else {
				//run on local
			//driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tldriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}
			break;
			
		}
		case "firefox":
		{

			if(Boolean.parseBoolean(prop.getProperty("remote")))
			{
				//run it on remote
				initRemoteDriver(browserName);
			}
			else {
				//run on local
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tldriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
			break;
		}		
		default: 
		{
			System.out.println("please enter right browser name:"+browserName);
			throw new FrameworkException("No browser found");
		}
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		
		return getDriver();
	}
	
	private void initRemoteDriver(String browserName) {
	System.out.println("Running tests on GRID with browser:"+browserName);
	try {
	switch(browserName.toLowerCase().trim())
	{
	case "chrome":
		tldriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
		break;
	case "edge":
		tldriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
		break;
	case "firefox":
		tldriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
		break;
		default:
			System.out.println("Wrong browser...Cannot execute on GRID remote machine....");
			break;
	}
	}
	catch(MalformedURLException e)
	{
		
	}
		
	}

	public static WebDriver getDriver()
	{
		return tldriver.get();
	}
	
	public Properties initProp() 
	{ 
		//mvn clean install -Denv="qa"
		FileInputStream fi = null;
	    prop= new Properties();
	    
	   String envName = System.getProperty("env");
	   System.out.println("Environment name is:"+envName);
	   
	   
	   try{
		   if(envName == null)
	   
		  {  //mvn clean install
			   System.out.println("Your env is null....running test cases on QA env");
		   fi = new FileInputStream("./src/test/resources/config/config.qa.properties");
	      }
	   else
	   {
		   switch (envName.toLowerCase().trim()) {
		case "qa":
			fi = new FileInputStream("./src/test/resources/config/config.qa.properties");
			break;
		case "dev":
			fi = new FileInputStream("./src/test/resources/config/config.dev.properties");
			break;
		case "stage":
			fi = new FileInputStream("./src/test/resources/config/config.stage.properties");
			break;
		case "uat":
			fi = new FileInputStream("./src/test/resources/config/config.uat.properties");
			break;

		default:
			System.out.println("Please enter right environment name"+ envName);
			throw new FrameworkException("INCORRECT ENVIRONMENT");
			
		}
	   }
		
	   } catch(FileNotFoundException e)	
	   {
		   e.printStackTrace();
	   }
		try {
			prop.load(fi);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		 return prop;
	}
	
	public static String getScreenshot(String methodName)
	{
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		
		String path = System.getProperty("user.dir")+"/Screenshot/"+methodName+"_"+System.currentTimeMillis()+".png";
		
		File destination = new File(path);
		
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return path;
	}

}
