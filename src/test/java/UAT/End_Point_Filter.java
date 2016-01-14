package UAT;

import java.util.ArrayList;
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

public class End_Point_Filter {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int EndPoint_count = 0;
	private WebElement NextBtn;
	private boolean Is_Hide_Inactive;
	private List<String> Name = new ArrayList<String>();
	private List<String> NACS = new ArrayList<String>();
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
	  
	//Filter type Practice
	  Select select_type = new Select(driver.findElement(By.id("ctl00_cp1_cboEPType")));
	  select_type.selectByVisibleText("Practice");

	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  
	  if (driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 1){
		  NextBtn_isEnable = true;
	  } else {
		  NextBtn_isEnable = false;
		  System.out.println("No End Point on the list after filter");
	  }
	  
	  do{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			table = driver.findElement(By.id("ctl00_cp1_tbl"));
			table_row = table.findElements(By.tagName("tr"));
			
			for (int x = 1; x < table_row.size(); x++){
				table_data = table_row.get(x).findElements(By.tagName("td"));
				Name.add(table_data.get(1).getText());
				NACS.add(table_data.get(3).getText());
				Assert.assertTrue(table_data.get(0).getText().equals("Practice"));
				
				EndPoint_count ++;
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
	  
	  System.out.println("There are " + EndPoint_count + " End Points. After filter out.");
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText().contains(Integer.toString(EndPoint_count)));
	  System.out.println("Correct End Point Count Title.");
	  
	  System.out.println(Name);
	  System.out.println(NACS);

	  //Filter Name
	  Select select_name = new Select(driver.findElement(By.id("ctl00_cp1_cboEndPoint")));
	  select_name.selectByVisibleText(Name.get(0));
	  
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	
	  Assert.assertTrue(Integer.toString(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size()).equals("2"));
	  System.out.println("There is only 1 End Point on the list after filter.");
	  table = driver.findElement(By.id("ctl00_cp1_tbl"));
	  table_row = table.findElements(By.tagName("tr"));
	  table_data = table_row.get(1).findElements(By.tagName("td"));
	  Assert.assertTrue(table_data.get(3).getText().equals(NACS.get(0)));
	  System.out.println("Correct NACS code.");
	  Assert.assertTrue(table_data.get(1).getText().equals(Name.get(0)));
	  System.out.println("Correct Name.");
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText().equals("Displaying 1 - 1 of 1"));
	  System.out.println("Correct End Point Count Title. Displaying 1 - 1 of 1");
	  
	  //Show document
	  driver.findElement(By.id("ctl00_cp1_chkWaiting")).click();
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText().equals("Displaying 1 - 1 of 1"));
	  Assert.assertTrue(Integer.toString(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size()).equals("2"));
	  System.out.println("There should be only 1 End Point on the list.");
	  //Check data is number
	  table = driver.findElement(By.id("ctl00_cp1_tbl"));
	  table_row = table.findElements(By.tagName("tr"));
		
		for (int x = 1; x < table_row.size(); x++){
			table_data = table_row.get(x).findElements(By.tagName("td"));
			Assert.assertTrue(Integer.parseInt(table_data.get(6).getText()) >= 0);
			Assert.assertTrue(Integer.parseInt(table_data.get(6).getText()) >= 0);
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
