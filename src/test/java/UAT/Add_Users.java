package UAT;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class Add_Users {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int Users_count = 0;
	private WebElement NextBtn;
	private boolean Is_Hide_Inactive;
	private String[] data = {"TESTING", "Testing", "123456", "test@docman.com"};
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
	  String User_List_Count_Title = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	  System.out.println(User_List_Count_Title);
	  String[] User_List_Count_Split = User_List_Count_Title.split(" ");
	  String User_List_Count = User_List_Count_Split[User_List_Count_Split.length - 1];
	  System.out.println(User_List_Count);
	  
	  //Click Add button
	  driver.findElement(By.id("ctl00_cp1_cmdNew")).click();
	  
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  
	  Assert.assertTrue(driver.findElement(By.tagName("th")).getText().equals("User Details"), "Incorrect table header.");
	  System.out.println("Correct Header. " + driver.findElement(By.tagName("th")).getText());
	  
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblEXID")).getText().equals("NEW"), "ID is not New.");
	  System.out.println("Correct ID. " + driver.findElement(By.id("ctl00_cp1_lblEXID")).getText());
	  
	  //Add User ID
	  driver.findElement(By.id("ctl00_cp1_txtEXUSER")).sendKeys(data[0]);
	  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Verify the error 
	  System.out.println(driver.findElement(By.id("ctl00_cp1_lblErr")).getText());
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblErr")).getText().equals("Description cannot be blank"));
	  //Add Description
	  driver.findElement(By.id("ctl00_cp1_txtEXDESC")).sendKeys(data[1]);
	  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Verify the error 
	  System.out.println(driver.findElement(By.id("ctl00_cp1_lblErr")).getText());
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblErr")).getText().equals("Password cannot be blank"));
	  //Add Password
	  driver.findElement(By.id("ctl00_cp1_txtEXPWD")).sendKeys(data[2]);
	  //Add Confirm Password
	  driver.findElement(By.id("ctl00_cp1_txtConf")).sendKeys(data[2]);
	  //Add Email
	  driver.findElement(By.id("ctl00_cp1_txtEXEMAIL")).sendKeys(data[3]);
	  
	  //Click Save button
	  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Verify the count title is increased
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText().contains(Integer.toString((Integer.parseInt(User_List_Count)) + 1))); 
	  System.out.println("Users List increased. " +  driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText());
	  
	  //Verify the new User is added to the last
	  
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 1);
	  System.out.println("There is User on the list.");
	  NextBtn_isEnable = true;
	  
	  do{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			table = driver.findElement(By.id("ctl00_cp1_tbl"));
			table_row = table.findElements(By.tagName("tr"));
			
			Users_count = Users_count + table_row.size() - 1;
			
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
				Assert.assertTrue(table_data.get(2).getText().equals(data[1]));
				Assert.assertTrue(table_data.get(3).getText().equals(data[3]));
				
			}

		} while (NextBtn_isEnable == true);
	  
	  Assert.assertTrue(Integer.toString(Users_count).equals(Integer.toString(Integer.parseInt(User_List_Count) + 1)));
	  System.out.println("Correct Users List count");
	  
	  System.out.println("Pass Add Users.");
	  
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
