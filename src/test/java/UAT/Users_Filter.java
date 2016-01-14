package UAT;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class Users_Filter {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int Users_count = 0;
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
	  Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains("User List"), "table header does not contains (User List)");
	  System.out.println("Correct Item Count header");
	  String User_List_Count = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	  System.out.println(User_List_Count);
	  
	  //Filter Role
	  driver.findElement(By.id("ctl00_cp1_txtRole")).sendKeys("Test User");
	  driver.findElement(By.id("ctl00_cp1_txtRole")).sendKeys(Keys.RETURN);
	  
	  //Verify there is User on the list
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  
	  if (driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 1){
		  NextBtn_isEnable = true;
	  } else {
		  NextBtn_isEnable = false;
		  System.out.println("No user on the list after filter");
	  }
	  
	  
	  do{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			table = driver.findElement(By.id("ctl00_cp1_tbl"));
			table_row = table.findElements(By.tagName("tr"));
			
			for (int x = 1; x < table_row.size(); x++){
				table_data = table_row.get(x).findElements(By.tagName("td"));
				Assert.assertTrue(table_data.get(4).getText().equals("Test User"));
				Users_data.add(table_data.get(2).getText());
				
				Users_count ++;
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
	  
	  System.out.println("There are " + Users_count + " users. After filter out.");
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText().contains(Integer.toString(Users_count)));
	  System.out.println("Correct users Count Title.");
	  
	  System.out.println(Users_data);
	  //Filter Description
	  driver.findElement(By.id("ctl00_cp1_txtDesc")).sendKeys(Users_data.get(1));
	  driver.findElement(By.id("ctl00_cp1_txtDesc")).sendKeys(Keys.RETURN);
	
	  //Verify there is User on the list
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  Assert.assertTrue((driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size()) == 2);
	  System.out.println("There is only 1 User after filtering description.");
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
