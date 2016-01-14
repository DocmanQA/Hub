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

public class Edit_End_Point {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int EndPoint_count = 0;
	private WebElement NextBtn;
	private boolean Is_Hide_Inactive;
	private String[] data = {"Pioneers Way 2", "Pcti Qa 2", "Indigo4 - Development"};
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
	  String End_Point_List_Count = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	  System.out.println(End_Point_List_Count);
	  
	  //Verify there is End Point on the list
	  int End_Point_No_1st_Page = driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size();
	  int Edit_No = 0;
	  if (End_Point_No_1st_Page > 1){
		  
		  if (End_Point_No_1st_Page > 4){
			  Edit_No = 4;
		  } else {
			  Edit_No = End_Point_No_1st_Page;
		  }
		  for (int x = 1; x < Edit_No; x++){
			  driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(x).findElements(By.tagName("td")).get(10).findElement(By.cssSelector("*[id ^='ctl00_cp1_lnkPRID']")).click();
			  
			  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			  //Key in new data
			  driver.findElement(By.id("ctl00_cp1_txtPRNAME")).clear();
			  driver.findElement(By.id("ctl00_cp1_txtPRNAME")).sendKeys("Testing" + x);
			  driver.findElement(By.id("ctl00_cp1_txtPRCEML")).clear();
			  driver.findElement(By.id("ctl00_cp1_txtPRCEML")).sendKeys("Test@docman.com");
			  //Click save button
			  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
			  
			  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			  //Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_txtPRNAME")).getText().contains("Testing"));
			  
			  table_data = driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(x).findElements(By.tagName("td"));
			  Assert.assertTrue(table_data.get(1).getText().equals("Testing" + x));
			  System.out.println("End Point Name" + x + " has been edited.");
			  Assert.assertTrue(table_data.get(4).getText().equalsIgnoreCase("Test@docman.com"));
			  System.out.println("Correct Email Address.");
			  
			  //Change the data back to origin
			  driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(x).findElements(By.tagName("td")).get(10).findElement(By.cssSelector("*[id ^='ctl00_cp1_lnkPRID']")).click();
			  
			  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			  //Key in new data
			  driver.findElement(By.id("ctl00_cp1_txtPRNAME")).clear();
			  driver.findElement(By.id("ctl00_cp1_txtPRNAME")).sendKeys(data[x - 1]);
			  //Save
			  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
			  
			  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			  System.out.println(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(x).findElements(By.tagName("td")).get(1).getText());

		  }
	  } else {
		  
		  System.out.println("No End Point on the list");
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
