package UAT;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Date;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

public class Edit_button_Access_Log {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private WebElement table_detail;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private List<WebElement> table_detail_row;
	private int Source_count = 0;
	private WebElement NextBtn;
	private boolean Is_Hide_Inactive;
	private SimpleDateFormat ft;
	private String str;
	private String ID;
	private String Signed_In;
	private String User;
	private String Machine;
	
  @Test
  public void main() {
	//Click the Access Log button under Summary
	  driver.findElement(By.cssSelector("a[href='ViewAccessLog.aspx']")).click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  String Access_Log_Title = driver.getTitle();
	  Assert.assertTrue(Access_Log_Title.contains("Access Log"));
	  table = driver.findElement(By.id("ctl00_cp1_tbl"));
	  table_row = table.findElements(By.tagName("tr"));
	  table_data = table_row.get(1).findElements(By.tagName("td"));
	  ID = table_data.get(0).getText();
	  System.out.println(ID);
	  Assert.assertTrue(table_data.get(3).getText().equals("ADMIN*"));
	  User = table_data.get(3).getText();
	  System.out.println(User);
	  Assert.assertTrue(table_data.get(4).getText().equals("HUB08TO"));
	  Machine = table_data.get(4).getText();
	  System.out.println(Machine);
	  Assert.assertTrue(table_data.get(1).getText().contains(str));
	  Signed_In = table_data.get(1).getText();
	  System.out.println(Signed_In);
	  
	  //Click view/edit button
	  table_data.get(6).findElement(By.cssSelector("*[id ^='ctl00_cp1_lnkEAID']")).click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  
	  table_detail = driver.findElement(By.id("tblEdit"));
	  table_detail_row = table_detail.findElements(By.tagName("tr"));
	  System.out.println(table_detail_row.get(0).findElement(By.tagName("th")).getText());
	  //Verify the table header
	  Assert.assertTrue(table_detail_row.get(0).findElement(By.tagName("th")).getText().equals("View Access Log Details"));
	  //Verify the Access Log Details
	  System.out.println(driver.findElement(By.id("ctl00_cp1_lblEAID")).getText());
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblEAID")).getText().equals(ID));
	  
	  System.out.println(driver.findElement(By.id("ctl00_cp1_lblEAIN")).getText());
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblEAIN")).getText().equals(Signed_In));
	  
	  System.out.println(driver.findElement(By.id("ctl00_cp1_lblEAUSER")).getText());
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblEAUSER")).getText().equals(User));
	  
	  System.out.println(driver.findElement(By.id("ctl00_cp1_lblEAMACH")).getText());
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblEAMACH")).getText().equals(Machine));
	  
	  Assert.assertTrue(driver.findElement(By.name("ctl00$cp1$cmdCancel")).isEnabled());
	  
	  //Click Back
	  driver.findElement(By.name("ctl00$cp1$cmdCancel")).click();
	  
	  //Verify it went back to the previous Access Log List
	  Assert.assertTrue(driver.getTitle().contains("Access Log"));
	  System.out.println(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(0).getText());
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(0).getText().equals("ID Signed In Signed Out User Machine IP"));
	  
  }
  @BeforeTest
  public void beforeTest() {
	  driver = Set_Web_Browser.Set_Web_Browser();
	  Date date = new Date();
	  ft = new SimpleDateFormat ("dd/MM/yyyy HH:mm:");
	  str = ft.format(date);
	  System.out.println(str);
  }

  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

}
