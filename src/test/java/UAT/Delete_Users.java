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

public class Delete_Users {
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
	  
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 1);
	  System.out.println("There is user on the list.");
	  NextBtn_isEnable = true;
	  
	  do{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			table = driver.findElement(By.id("ctl00_cp1_tbl"));
			table_row = table.findElements(By.tagName("tr"));
			
			Users_count = Users_count + table_row.size() - 1;
			
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
				Assert.assertTrue(table_data.get(2).getText().equals(data[1]));
				Assert.assertTrue(table_data.get(3).getText().equals(data[3]));
				System.out.println("Correct user data");
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				//Click the edit button	
				table_data.get(6).findElement(By.cssSelector("*[id ^='ctl00_cp1_lnkEXID']")).click();
				System.out.println("Click the edit button");
				
			}

		} while (NextBtn_isEnable == true);
	  
	  	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Before delete, there are " + Users_count + " users.");
		
		driver.findElement(By.id("ctl00_cp1_cmdDelete")).click();
		System.out.println("Click delete button.");
		driver.switchTo().alert().accept();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		String Users_List_Count_Title2 = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
		System.out.println(Users_List_Count_Title2);
		String[] Users_List_Count_Split2 = Users_List_Count_Title2.split(" ");
		String Users_List_Count2 = Users_List_Count_Split2[Users_List_Count_Split2.length - 1];
		System.out.println(Users_List_Count2);
		
		Assert.assertTrue(Users_List_Count2.equals(Integer.toString(Users_count - 1)));
		System.out.println("Correct user count number. 1 has been deleted.");
		System.out.println("Pass Delete Users.");
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
