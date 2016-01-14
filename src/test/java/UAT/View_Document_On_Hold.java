package UAT;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

public class View_Document_On_Hold {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int document_count = 0;
	private WebElement NextBtn;
	private String Filter_status;
  @Test
  public void main() {
	//Click the View button next to Document On Hold
	  driver.findElement(By.cssSelector("a[href='ViewDocList.aspx?DCSTAT=4001']")).click();
	  NextBtn_isEnable = true;
	  String Doc_List_title = driver.getTitle();
	  Assert.assertTrue(Doc_List_title.contains("Document List"));
	  System.out.println(Doc_List_title);
	  Select selectedvalue = new Select(driver.findElement(By.name("ctl00$cp1$cboFStat")));
	  Filter_status = selectedvalue.getFirstSelectedOption().getText();
	  System.out.println("The filter status is: " + Filter_status);
	  Assert.assertTrue(Filter_status.equals("On Hold"));
	  String Doc_List_Count = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	  System.out.println(Doc_List_Count);
	  
	  do{
	  			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  			table = driver.findElement(By.id("ctl00_cp1_tbl"));
	  			table_row = table.findElements(By.tagName("tr"));
	  			
	  			for (int x = 1; x < table_row.size(); x++){
	  				table_data = table_row.get(x).findElements(By.tagName("td"));
	  				System.out.println(table_data.get(0).getText());
	  				Assert.assertTrue(table_data.get(4).getText().equals("On Hold"));
	  				
	  				document_count ++;
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
	  
	  System.out.println( document_count + " documents are on hold");
	  //Verify the document list count
	  Assert.assertTrue(Doc_List_Count.contains(Integer.toString(document_count)));
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
