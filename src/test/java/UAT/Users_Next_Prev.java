package UAT;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class Users_Next_Prev {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int Users_count = 0;
	private WebElement NextBtn;
	private WebElement PrevBtn;
	private boolean Is_Hide_Inactive;
	private List<String> Users_data = new ArrayList<String>();
	private String[] data = {"TEST_", "System Administrator", "Testing", "123", "Test@Docman.com"};
	private int Num = 1;
	private List<String> First_ID = new ArrayList<String>();
	private List<String> Last_ID = new ArrayList<String>();
	private String ID_check;
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
	
	  //Verify the Count title
	  String User_List_Count_Title = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	  System.out.println(User_List_Count_Title);
	  String[] User_List_Count_Split = User_List_Count_Title.split(" ");
	  String User_List_Count = User_List_Count_Split[User_List_Count_Split.length - 1];
	  System.out.println("There are " + User_List_Count + " users on the list.");
	  
	  //Verify the next and prev button is enabled
	  NextBtn = driver.findElement(By.id("ctl00_cp1_cmdNext"));
	  PrevBtn = driver.findElement(By.id("ctl00_cp1_cmdPrev"));
	  
	  if (Integer.parseInt(User_List_Count) > 30){
		  Assert.assertTrue((driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() == 31));
		
		  Assert.assertTrue(NextBtn.isEnabled(), "Fail, Next button is disabled when more than 30 users on the list.");
		  Assert.assertFalse(PrevBtn.isEnabled(), "Fail, Prev button is enabled on the first page.");
		  
	  } else {
		  
		  Assert.assertFalse(NextBtn.isEnabled(), "Fail, Next button is enabled when less than 30 users on the list.");
		  Assert.assertFalse(PrevBtn.isEnabled(), "Fail, Prev button is enabled on the first page.");
		  
		  
		 //Add more users until next button enable
		  do{
			  //Verify the end point list count
			  User_List_Count_Title = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
			  User_List_Count_Split = User_List_Count_Title.split(" ");
			  User_List_Count = User_List_Count_Split[User_List_Count_Split.length - 1];
			  //Click add button
			  driver.findElement(By.id("ctl00_cp1_cmdNew")).click();
			  
			  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			  
			  Assert.assertTrue(driver.findElement(By.tagName("th")).getText().equals("User Details"));
			  System.out.println("Correct Header. " + driver.findElement(By.tagName("th")).getText());
			  
			  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblEXID")).getText().equals("NEW"));
			  System.out.println("Correct ID. " + driver.findElement(By.id("ctl00_cp1_lblEXID")).getText());
			  
			  //Add UserID
			  driver.findElement(By.id("ctl00_cp1_txtEXUSER")).sendKeys(data[0] + Integer.toString(Num));
			  //Select Role
			  Select select_Role = new Select(driver.findElement(By.id("ctl00_cp1_cboRole")));
			  select_Role.selectByVisibleText(data[1]); 
			  //Add Description
			  driver.findElement(By.id("ctl00_cp1_txtEXDESC")).sendKeys(data[2]);
			  //Add Password
			  driver.findElement(By.id("ctl00_cp1_txtEXPWD")).sendKeys(data[3]);
			  //Add Confirm Password
			  driver.findElement(By.id("ctl00_cp1_txtConf")).sendKeys(data[3]);
			  //Add Email
			  driver.findElement(By.id("ctl00_cp1_txtEXEMAIL")).sendKeys(data[4]);
			  //Click Save
			  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
			  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			  //Verify the count title
			  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText().contains(Integer.toString((Integer.parseInt(User_List_Count)) + 1)), "User count title does not increase."); 
			  System.out.println("User List increased. " +  driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText());	
			  Num++;
			  
		  } while (Integer.parseInt(User_List_Count) < 31);
		  
	  }
	  
	//Verify the item ID on each page
	  for (int x=0; x<3; x++){
		  System.out.println("Page number " + x);
		  //Get the first and last end point id on the page 
		  String User_ID_label = driver.findElement(By.id("ctl00_cp1_lbl")).getText();
		  String[] User_ID_label_Split = User_ID_label.split(" ");
		  First_ID.add(User_ID_label_Split[3]);
		  System.out.println("The first ID is " + First_ID);
		  Last_ID.add(User_ID_label_Split[5]);
		  System.out.println("The last ID is " + Last_ID);
		  Assert.assertTrue(Integer.parseInt(Last_ID.get(x)) >= Integer.parseInt(First_ID.get(x)), "The Last ID is not bigger or equal first ID.");
		  System.out.println("The last ID is bigger or equal first ID.");
		  
		  //Verify the ID from the table
		  for (int i=1; i<3; i++){
			  int n;
			  table_row = driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr"));
			  
			  if (i == 2){
				  n = table_row.size() - 1;
				  System.out.println("row size is " + (table_row.size() - 1));
				  ID_check = Last_ID.get(x);
			  } else{
				  n = 1;
				  ID_check = First_ID.get(x);
			  }
			  
			  
			  //Verify the ID is the same with the label.
			  System.out.println("ID_check is " + ID_check);
			  System.out.println("ID id " + table_row.get(n).findElements(By.tagName("td")).get(0).getText());
			  Assert.assertTrue(table_row.get(n).findElements(By.tagName("td")).get(0).getText().equals(ID_check), "Wrong ID");
			  System.out.println("It is the same ID with the label.");
		  }
		  
		  
		  if (x == 0){
			//click next button
			  driver.findElement(By.id("ctl00_cp1_cmdNext")).click();
		  } else if (x == 1){
			  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_cmdPrev")).isEnabled(), "Prev button is not enabled.");
			  driver.findElement(By.id("ctl00_cp1_cmdPrev")).click();
		  }
		  
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  
	  }
	  
	  System.out.println(First_ID);
	  System.out.println(Last_ID);
	  Assert.assertTrue(Integer.parseInt(First_ID.get(1)) > Integer.parseInt(First_ID.get(0)), "Next page First ID is smaller than first page.");
	  Assert.assertTrue(Integer.parseInt(Last_ID.get(1)) > Integer.parseInt(Last_ID.get(0)), "Next page Last ID is smaller than first page.");
	  System.out.println("Next page ID is bigger than first page.");
	  Assert.assertTrue((First_ID.get(0)).equals(First_ID.get(2)), "First ID is different. It is on wrong page.");
	  System.out.println("It is the same first item id.");
	  Assert.assertTrue((Last_ID.get(0)).equals(Last_ID.get(2)), "Last ID is different. It is on wrong page.");
	  System.out.println("It is the same Last item id. It is the same page.");
	  
	  System.out.println("Pass Users Next Prev.");

	  
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
