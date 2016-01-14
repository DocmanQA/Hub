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

public class View_Active_Document_Sources {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int Source_count = 0;
	private WebElement NextBtn;
	private boolean Is_Hide_Inactive;
  @Test
  public void main() {
	//Click the View button next to Active Document Sources
	  driver.findElement(By.cssSelector("a[href='ViewSource.aspx']")).click();
	  NextBtn_isEnable = true;
	  String Source_List_title = driver.getTitle();
	  Assert.assertTrue(Source_List_title.contains("Source List"));
	  System.out.println(Source_List_title);
	  Is_Hide_Inactive = driver.findElement(By.name("ctl00$cp1$chkInactive")).isSelected();
	  System.out.println(Is_Hide_Inactive);
	  Assert.assertTrue(Is_Hide_Inactive);
	  String Doc_Source_Count = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	  System.out.println(Doc_Source_Count);
	  

	  do{
	  			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  			table = driver.findElement(By.id("ctl00_cp1_tbl"));
	  			table_row = table.findElements(By.tagName("tr"));
	  			
	  			for (int x = 1; x < table_row.size(); x++){
	  				table_data = table_row.get(x).findElements(By.tagName("td"));
	  				System.out.println(table_data.get(1).getText());
	  				Assert.assertTrue(table_data.get(3).getText().equals("No"));
	  				
	  				Source_count ++;
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
	  
	  System.out.println( "There are " + Source_count + " document sources");
	  Assert.assertTrue(Doc_Source_Count.contains(Integer.toString(Source_count)));
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
