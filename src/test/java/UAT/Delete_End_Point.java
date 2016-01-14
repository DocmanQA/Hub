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

public class Delete_End_Point {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int EndPoint_count = 0;
	private WebElement NextBtn;
	private boolean Is_Hide_Inactive;
	private String[] data = {"Testing", "P78901", "Testing", "test@docman.com"};
  @Test
  public void main() {
	  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  //Click End Points under Configuration
	  driver.findElement(By.cssSelector("a[href='ViewEndPoints.aspx']")).click();
	  //Verify the Title
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  String End_Point_List_title = driver.getTitle();
	  Assert.assertTrue(End_Point_List_title.contains("End Point List"));
	  System.out.println(End_Point_List_title);
	  //Verify Hide Inactive is enabled
	  Is_Hide_Inactive = driver.findElement(By.name("ctl00$cp1$chkInactive")).isSelected();
	  System.out.println(Is_Hide_Inactive);
	  Assert.assertTrue(Is_Hide_Inactive);
	  
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 1);
	  System.out.println("There is End Point on the list.");
	  NextBtn_isEnable = true;
	  
	  do{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			table = driver.findElement(By.id("ctl00_cp1_tbl"));
			table_row = table.findElements(By.tagName("tr"));
			
			EndPoint_count = EndPoint_count + table_row.size() - 1;
			
			//driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			NextBtn = driver.findElement(By.id("ctl00_cp1_cmdNext"));
			
			if (NextBtn.isEnabled()){
				NextBtn_isEnable = true;
				//Click the next button to next page.
				NextBtn.click();
			} else {
				NextBtn_isEnable = false;
				
				table = driver.findElement(By.id("ctl00_cp1_tbl"));
				table_row = table.findElements(By.tagName("tr"));
				table_data = table_row.get(table_row.size() - 1).findElements(By.tagName("td"));
				Assert.assertTrue(table_data.get(1).getText().equals(data[0]));
				Assert.assertTrue(table_data.get(3).getText().equals(data[1]));
				Assert.assertTrue(table_data.get(4).getText().equals(data[3]));
				System.out.println("Correct End Point data");
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				//Click the edit button	
				table_data.get(10).findElement(By.cssSelector("*[id ^='ctl00_cp1_lnkPRID']")).click();
				System.out.println("Click the edit button");
				
			}

		} while (NextBtn_isEnable == true);
	  
	  
	
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	
	driver.findElement(By.id("ctl00_cp1_cmdDelete")).click();
	driver.switchTo().alert().accept();
	
	
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	
	System.out.println("Before delete, there are " + EndPoint_count + " End Points.");
	
	String End_Point_List_Count_Title = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	System.out.println(End_Point_List_Count_Title);
	String[] End_Point_List_Count_Split = End_Point_List_Count_Title.split(" ");
	String End_Point_List_Count = End_Point_List_Count_Split[End_Point_List_Count_Split.length - 1];
	System.out.println(End_Point_List_Count);
	
	Assert.assertTrue(End_Point_List_Count.equals(Integer.toString(EndPoint_count - 1)));
	System.out.println("Correct End Point number. 1 has been deleted.");
	
	  
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
