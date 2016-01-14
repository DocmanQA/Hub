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

public class Add_End_Point {
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
	  String End_Point_List_Count_Title = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	  System.out.println(End_Point_List_Count_Title);
	  String[] End_Point_List_Count_Split = End_Point_List_Count_Title.split(" ");
	  String End_Point_List_Count = End_Point_List_Count_Split[End_Point_List_Count_Split.length - 1];
	  System.out.println(End_Point_List_Count);
	  
	  //Click Add button
	  driver.findElement(By.id("ctl00_cp1_cmdNew")).click();
	  
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  
	  Assert.assertTrue(driver.findElement(By.tagName("th")).getText().equals("End Point Details"));
	  System.out.println("Correct Header. " + driver.findElement(By.tagName("th")).getText());
	  
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblPRID")).getText().equals("NEW"));
	  System.out.println("Correct ID. " + driver.findElement(By.id("ctl00_cp1_lblPRID")).getText());
	  
	  //Add Name
	  driver.findElement(By.id("ctl00_cp1_txtPRNAME")).sendKeys(data[0]);
	  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Verify the error 
	  System.out.println(driver.findElement(By.id("ctl00_cp1_lblErr")).getText());
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblErr")).getText().equals("NACS code cannot be blank"));
	  
	  //Add NACS Code
	  driver.findElement(By.id("ctl00_cp1_txtPRNACS")).sendKeys(data[1]);
	  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  
	  //Verify the error 
	  System.out.println(driver.findElement(By.id("ctl00_cp1_lblErr")).getText());
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblErr")).getText().equals("Contact Name cannot not be blank"));
	  
	//Add Contact name
	  driver.findElement(By.id("ctl00_cp1_txtPRCONT")).sendKeys(data[2]);
	  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Verify the error 
	  System.out.println(driver.findElement(By.id("ctl00_cp1_lblErr")).getText());
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblErr")).getText().equals("Contact Email address must not be blank"));
	  
	//Add Email
	  driver.findElement(By.id("ctl00_cp1_txtPRCEML")).sendKeys(data[3]);
	  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText().contains(Integer.toString((Integer.parseInt(End_Point_List_Count)) + 1))); 
	  System.out.println("End Point List increased. " +  driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText());
	  
	  //Verify the new End Point is added to the last
	  
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
				table_row = driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr"));
				table_data = table_row.get(table_row.size() - 1).findElements(By.tagName("td"));
				Assert.assertTrue(table_data.get(1).getText().equals(data[0]));
				Assert.assertTrue(table_data.get(3).getText().equals(data[1]));
				Assert.assertTrue(table_data.get(4).getText().equals(data[3]));
				
			}

		} while (NextBtn_isEnable == true);
	  
	  Assert.assertTrue(Integer.toString(EndPoint_count).equals(Integer.toString(Integer.parseInt(End_Point_List_Count) + 1)));
	  System.out.println("Correct End Point List count");
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
