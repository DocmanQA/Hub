package UAT;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class Edit_button_Document_List {
	private WebDriver driver;
	private boolean Doc_isAvailable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private String Doc_ID;
	private String Doc_Date;
	private String End_Point;
	private String Doc_Status;
	private String Doc_Source;
	private String Source_Ref;
	
  @Test
  public void main() {
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  //Click Document List under Documents
	  driver.findElement(By.cssSelector("a[href='ViewDocList.aspx']")).click();
	  String Doc_List_title = driver.getTitle();
	  Assert.assertTrue(Doc_List_title.contains("Document List"));
	  
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText().contains("Displaying"));
	  //Verify the document list count title
	  System.out.println(driver.findElement(By.tagName("h1")).getText());
	  Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Document List Displaying"));
	  //Verify the document list table titles
	  System.out.println(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(0).getText());
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(0).getText().equals("Doc ID Date End Point Contributor Status Document Source Source Ref"));
	  System.out.println(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size());
	  Assert.assertTrue((driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 0) && (driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() < 32));
	  System.out.println((driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 0) && (driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() < 32));
	  //Verify the Prev button is disabled
	  Assert.assertFalse(driver.findElement(By.name("ctl00$cp1$cmdPrev")).isEnabled());
	  System.out.println("The prev button is enabled on the 1st page = " + driver.findElement(By.name("ctl00$cp1$cmdPrev")).isEnabled());
	  
	  if (driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 1){
		  Doc_isAvailable = true;
	  } else {
		  Doc_isAvailable = false;
	  }
	  
	  //Verify every document details
	  if (Doc_isAvailable){
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			table = driver.findElement(By.id("ctl00_cp1_tbl"));
			table_row = table.findElements(By.tagName("tr"));
			
			table_data = table_row.get(1).findElements(By.tagName("td"));
			Doc_ID = table_data.get(0).getText();
			Doc_Date = table_data.get(1).getText();
			End_Point = table_data.get(2).getText();
			Doc_Status = table_data.get(4).getText();
			Doc_Source = table_data.get(5).getText();
			Source_Ref = table_data.get(7).getText();
			
			System.out.println("Doc ID: " + Doc_ID + ", Doc Date: " + Doc_Date + ", End Point: " + End_Point +  ", Doc Status: " + Doc_Status + ", Doc Source: " + Doc_Source + ", Source Ref: " + Source_Ref);
				
			boolean Doc_Status_Test = false;
			//Verify the every documents status
			if (Doc_Status.contains("Relayed") || Doc_Status.contains("Rejected") || Doc_Status.contains("Deleted By Sender") || Doc_Status.contains("Delivered") || Doc_Status.contains("Waiting") || Doc_Status.contains("On Hold") || Doc_Status.contains("Conversion Pending")) {
					Doc_Status_Test = true;
			}
			Assert.assertTrue(Doc_Status_Test);
			Assert.assertTrue(table_data.get(8).isEnabled());
			//Click the edit button next to the first document
			table_data.get(8).findElement(By.cssSelector("*[id ^='ctl00_cp1_lnkDCID']")).click();
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			System.out.println(driver.findElement(By.tagName("th")).getText());
			Assert.assertTrue(driver.findElement(By.tagName("th")).getText().equals("Document Details"));
			//Verify the Doc ID
			Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblDCID")).getText().equals(Doc_ID));
			System.out.println("Correct Doc ID.");
			
			Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblDCDATE")).getText().equals(Doc_Date));
			System.out.println("Correct Doc Added Date Time");
			
			Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblDCSTAT")).getText().equals(Doc_Status));
			System.out.println("Correct Doc Status");
			
			Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblDCSREF")).getText().contains(Source_Ref));
			System.out.println("Correct Source Ref");
			
			Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblDCSOID")).getText().equals(Doc_Source));
			System.out.println("Correct Document Source");
			
			//Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblDCPRID")).getText().equals(End_Point));
			//System.out.println("Correct End Point");
			
			Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_cmdView")).isEnabled());
			System.out.println("Open button is enabled");
			
			Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_cmdCancel")).isEnabled());
			System.out.println("Cancel button is enabled");
			

		} else {
			System.out.println("There is no document to test.");
			Assert.assertTrue(Doc_isAvailable);
		}
	  
  }
  @BeforeTest
  public void beforeTest() {
	  driver = Set_Web_Browser.Set_Web_Browser();
  }

  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

}
