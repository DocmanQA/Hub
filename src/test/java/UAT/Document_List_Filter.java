package UAT;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class Document_List_Filter {
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
	 
	  //Select Source from filter
	  Select selected_Source = new Select(driver.findElement(By.id("ctl00_cp1_cboFSrc")));
	  selected_Source.selectByVisibleText("Biochemistry");
	  System.out.println("Selected Biochemistry");
	  
	  //Select Status from filer
	  Select selected_Status = new Select (driver.findElement(By.id("ctl00_cp1_cboFStat")));
	  selected_Status.selectByVisibleText("Waiting");
	  System.out.println("Selected Waiting");
	 
	  //Click the search button
	  driver.findElement(By.id("ctl00_cp1_btnResults")).click();
	  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  
	//Verify the document list count header
	  String Doc_List_header = driver.findElement(By.tagName("h1")).getText();
	  System.out.println(Doc_List_header);
	  Assert.assertTrue(Doc_List_header.contains("Document List Displaying"));
	  
	  //Verify there is document on the list
	  if (driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 1){
		  NextBtn_isEnable = true;
	  } else {
		  NextBtn_isEnable = false;
		  System.out.println("No document on the list");
	  }
	  
	//Verify every document details
	  do{
			driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
			table = driver.findElement(By.id("ctl00_cp1_tbl"));
			table_row = table.findElements(By.tagName("tr"));
			
			for (int x = 1; x < table_row.size(); x++){
				table_data = table_row.get(x).findElements(By.tagName("td"));
				String Doc_Status = table_data.get(4).getText();
				String Doc_Source = table_data.get(5).getText();
				System.out.println("Doc ID: " + table_data.get(0).getText() + ", Doc Status: " + Doc_Status + ", Doc Source: " + Doc_Source);
				Assert.assertTrue(Doc_Status.equals("Waiting"));
				Assert.assertTrue(Doc_Source.equals("Biochemistry"));
				Assert.assertTrue(table_data.get(8).isEnabled());
				
				Documents_count ++;
			}

			//driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			NextBtn = driver.findElement(By.id("ctl00_cp1_cmdNext"));
			
			if (NextBtn.isEnabled()){
				NextBtn_isEnable = true;
				//Click the next button to next page.
				NextBtn.click();
				System.out.println("Next page");
			} else {
				NextBtn_isEnable = false;
			}

		} while (NextBtn_isEnable == true);
	  
	  System.out.println( "There are " + Documents_count + " documents in the list after filter");
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
