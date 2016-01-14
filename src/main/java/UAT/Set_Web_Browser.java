package UAT;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.testng.Assert;


public class Set_Web_Browser {
	public static WebDriver driver = null;
	public static String WB = "f";
	
	public static WebDriver Set_Web_Browser() {
		if (WB == "f"){
			  driver = new FirefoxDriver();
		  }
		  else if (WB == "c"){
			  System.setProperty("webdriver.chrome.driver", "C:\\Selenium Doc\\chromedriver.exe");
			  driver = new ChromeDriver();
			  driver.manage().window().maximize();
		  } 
		  else if (WB == "i"){
			  System.setProperty("webdriver.ie.driver", "C:\\Selenium Doc\\IEDriverServer_32bit.exe");
			  driver = new InternetExplorerDriver();
		  }
		  
		  LoginPage loginPage = new LoginPage(driver);
		  loginPage.login();
		  String title = driver.getTitle();
		  //Assert.assertTrue(title.contains("EDT Hub - Console"));
		
		  return driver;
	}

}
