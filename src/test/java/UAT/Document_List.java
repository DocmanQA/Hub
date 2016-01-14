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

public class Document_List {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int Documents_count = 0;
	private WebElement NextBtn;
	
  @Test
  public void main() {
	  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  //Click Document List under Documents
	  driver.findElement(By.cssSelector("a[href='ViewDocList.aspx']")).click();
	  String Doc_List_title = driver.getTitle();
	  Assert.assertTrue(Doc_List_title.contains("Document List"));
	  
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText().contains("Displaying"));
	  //Verify the document list count header
	  String Doc_List_header = driver.findElement(By.tagName("h1")).getText();
	  System.out.println(Doc_List_header);
	  Assert.assertTrue(Doc_List_header.contains("Document List Displaying"));
	  //Verify the document list table titles
	  System.out.println(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(0).getText());
	  //Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(0).getText().equals("Doc ID Date End Point Contributor Status Document Source Source Ref"));
	  System.out.println(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size());
	  Assert.assertTrue((driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 0) && (driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() < 32));
	  System.out.println((driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 0) && (driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() < 32));
	  //Verify the Prev button is disabled
	  Assert.assertFalse(driver.findElement(By.name("ctl00$cp1$cmdPrev")).isEnabled());
	  System.out.println("The prev button is enabled on the 1st page = " + driver.findElement(By.name("ctl00$cp1$cmdPrev")).isEnabled());
	  
	  if (driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 1){
		  NextBtn_isEnable = true;
	  } else {
		  NextBtn_isEnable = false;
	  }
	  
	  //Verify every document details
	  do{
			driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
			table = driver.findElement(By.id("ctl00_cp1_tbl"));
			table_row = table.findElements(By.tagName("tr"));
			
			for (int x = 1; x < table_row.size(); x++){
				table_data = table_row.get(x).findElements(By.tagName("td"));
				String Doc_Status = table_data.get(4).getText();
				System.out.println("Doc ID: " + table_data.get(0).getText() + ", Doc Status: " + Doc_Status);
				
				boolean Doc_Status_Test = false;
				//Verify the every documents status
				if (Doc_Status.contains("Relayed") || Doc_Status.contains("Rejected") || Doc_Status.contains("Deleted By Sender") || Doc_Status.contains("Delivered") || Doc_Status.contains("Waiting") || Doc_Status.contains("On Hold") || Doc_Status.contains("Conversion Pending")) {
						Doc_Status_Test = true;
				}
				Assert.assertTrue(Doc_Status_Test);
				Assert.assertTrue(table_data.get(8).isEnabled());
				
				Documents_count ++;
			}

			//driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			NextBtn = driver.findElement(By.id("ctl00_cp1_cmdNext"));
			
			if (NextBtn.isEnabled()){
				NextBtn_isEnable = true;
				//Click the next button to next page.
				NextBtn.click();
			} else {
				NextBtn_isEnable = false;
			}

		} while (NextBtn_isEnable == true);
	  
	  System.out.println( "There are " + Documents_count + " documents in the list");
	  Assert.assertTrue(Doc_List_header.contains(Integer.toString(Documents_count)));
	  
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
