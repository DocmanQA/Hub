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

public class Edit_Users {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int User_count = 0;
	private WebElement NextBtn;
	private boolean Is_Hide_Inactive;
	private List<String> Users_data = new ArrayList<String>();
  @Test
  public void main() {
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  //Click Users under Configuration
	  driver.findElement(By.xpath("//*[contains(text(), 'Users')]")).click();
	  //Verify the Title
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  String User_List_title = driver.getTitle();
	  Assert.assertTrue(User_List_title.contains("User List"), "Wrong title name");
	  System.out.println(User_List_title);

	  //Verify Hide Inactive is enabled
	  Is_Hide_Inactive = driver.findElement(By.id("ctl00_cp1_chkInactive")).isSelected();
	  System.out.println(Is_Hide_Inactive);
	  Assert.assertTrue(Is_Hide_Inactive, "Is_hide_Inactive is false");
	  //Verify the table header
	  Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains("User List"), "table header does not contains (User List)");
	  System.out.println("Correct Item Count header");
	  String User_List_Count = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	  System.out.println(User_List_Count);
	  
	  //Verify there is User on the list
	  int User_No_1st_Page = driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size();
	  int Edit_No = 0;
	  if (User_No_1st_Page > 1){
		  
		  if (User_No_1st_Page > 4){
			  //Edit the first 3 Users
			  Edit_No = 4;
			  
		  } else {
			  Edit_No = User_No_1st_Page;
		  }
		  for (int x = 1; x < Edit_No; x++){
			  driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(x).findElements(By.tagName("td")).get(6).findElement(By.cssSelector("*[id ^='ctl00_cp1_lnkEXID']")).click();
			  
			  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
			  //Get data from the Users details
			  Users_data.clear();
			  Users_data.add(driver.findElement(By.id("ctl00_cp1_lblEXID")).getText());
			  Users_data.add(driver.findElement(By.id("ctl00_cp1_txtEXUSER")).getAttribute("value"));
			  Select select_Users = new Select(driver.findElement(By.id("ctl00_cp1_cboRole")));
			  Users_data.add(select_Users.getFirstSelectedOption().getText());
			  Users_data.add(driver.findElement(By.id("ctl00_cp1_txtEXDESC")).getAttribute("value"));
			  
			  System.out.println(Users_data);
			  
			  //Key in new data
			  driver.findElement(By.id("ctl00_cp1_txtEXDESC")).clear();
			  driver.findElement(By.id("ctl00_cp1_txtEXDESC")).sendKeys("Testing");
			
			  
			  //Click save button
			  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
			  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			  
			  //Verify the description has been edited
			  table_data = driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(x).findElements(By.tagName("td"));
			  Assert.assertTrue((table_data.get(2).getText()).equals("Testing"));
			  System.out.println("User Description " + x + " has been edited.");  
			  
			  //Change the data back to origin
			  driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(x).findElements(By.tagName("td")).get(6).findElement(By.cssSelector("*[id ^='ctl00_cp1_lnkEXID']")).click();
			  
			  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			  //Key in new data
			  driver.findElement(By.id("ctl00_cp1_txtEXDESC")).clear();
			  driver.findElement(By.id("ctl00_cp1_txtEXDESC")).sendKeys(Users_data.get(3));
			  //Save
			  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
			  
			  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			  System.out.println(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(x).findElements(By.tagName("td")).get(1).getText());

		  }
	  } else {
		  
		  System.out.println("No Users on the list");
	  }
	  
	  System.out.println("Pass Edit Users List.");
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
