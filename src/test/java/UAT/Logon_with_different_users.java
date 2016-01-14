package UAT;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.chrome.*;
import org.testng.Assert;

import UAT.LoginPage;

public class Logon_with_different_users {
	private WebDriver driver;
	private String WB = "f";
	List<WebElement> table_row;
	List<WebElement> table_data;
	
  @Test
  public void main() {
	  
	  for (int x = 0; x< 5; x++){
		  if (WB == "f"){
			  driver = new FirefoxDriver();
		  }
		  else if (WB == "c"){
			  System.setProperty("webdriver.chrome.driver", "C:\\Selenium Doc\\chromedriver.exe");
			  driver = new ChromeDriver();
			  driver.manage().window().maximize();
		  } 
		  else if (WB == "i"){
			  System.setProperty("webdriver.ie.driver", "C:\\Selenium Doc\\IEDriverServer.exe");
			  driver = new InternetExplorerDriver();
		  }
	  
	  
		  driver.get("http://hub08to2/EDTConsole/");
		  driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	  
		  driver.findElement(By.name("txtUser")).sendKeys("User" + (x + 1));
		  driver.findElement(By.name("txtPWD")).sendKeys("123");
		  driver.findElement(By.name("cmdLogin")).click();
	  
		  String title = driver.getTitle();
		  Assert.assertTrue(title.contains("EDT Hub - Console"));
		  driver.quit();
		  try {
				Thread.sleep(1000);
			  } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  }
	  }
  }
  @BeforeTest
  public void beforeTest() {
	  driver = Set_Web_Browser.Set_Web_Browser();
	  
	  driver.findElement(By.cssSelector("a[href='Users.aspx']")).click(); 
	  String title = driver.getTitle();
	  Assert.assertTrue(title.contains("User List"));
	  
	  for (int x = 0; x < 5; x++){
		  driver.findElement(By.name("ctl00$cp1$cmdNew")).click();
		  driver.findElement(By.name("ctl00$cp1$txtEXUSER")).sendKeys("User" + (x + 1));
		  
		  Select select = new Select(driver.findElement(By.name("ctl00$cp1$cboRole")));
		  select.selectByIndex(x);
		  
		  driver.findElement(By.name("ctl00$cp1$txtEXDESC")).sendKeys("Testing");
		  driver.findElement(By.name("ctl00$cp1$txtEXEMAIL")).sendKeys("TonyYip@docman.com");
		  driver.findElement(By.name("ctl00$cp1$txtEXPWD")).sendKeys("123");
		  driver.findElement(By.name("ctl00$cp1$txtConf")).sendKeys("123");
		  driver.findElement(By.name("ctl00$cp1$cmdSave")).click();
	  }
	  driver.quit();
  }

  @AfterTest
  public void afterTest() {
	  
	  driver = Set_Web_Browser.Set_Web_Browser();
	  
	  
	  driver.findElement(By.cssSelector("a[href='Users.aspx']")).click();
	  
	  for (int x = 0; x <5; x++){
		  driver.findElement(By.name("ctl00$cp1$txtName")).clear();
		  driver.findElement(By.name("ctl00$cp1$txtName")).sendKeys("User" + (x + 1) + Keys.RETURN);
		  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  
		  table_row = driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr"));
		  table_data = table_row.get(1).findElements(By.tagName("td"));
		  String UserID = table_data.get(0).getText();
		  System.out.println(UserID);
		  String UserName = table_data.get(1).getText();
		  Assert.assertTrue(UserName.equals("USER" + (x + 1)));
		  //Click the edit button
		  table_data.get(6).findElement(By.cssSelector("*[id ^= 'ctl00_cp1_lnkEXID']")).click();
		  
		  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		  //Click delete
		  driver.findElement(By.name("ctl00$cp1$cmdDelete")).click();
	  
		  driver.switchTo().alert().accept();
	  }
	  
	  driver.quit();
	  //((JavascriptExecutor)m_driver)
	  //((JavascriptExecutor) driver).executeScript("window.confirm = function(msg){return true;};");
	  //JavascriptExecutor js = (JavascriptExecutor)driver;
	  //js.executeScript("window.alert = function(msg) {document.lastAlert=msg;}");
	  
  }

}
