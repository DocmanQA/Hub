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
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class End_Point_Next_Prev {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int User_count = 0;
	private WebElement NextBtn;
	private WebElement PrevBtn;
	private boolean Is_Hide_Inactive;
	private String[] data = {"Test_End_Point_", "P9870", "Test_User_", "test@docman.com"};
	private String ID_check;
	private List<String> First_ID = new ArrayList<String>();
	private List<String> Last_ID = new ArrayList<String>();
	private int Num = 1;
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
	  //Verify the Count title
	  String End_Point_List_Count_Title = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	  System.out.println(End_Point_List_Count_Title);
	  String[] End_Point_List_Count_Split = End_Point_List_Count_Title.split(" ");
	  String End_Point_List_Count = End_Point_List_Count_Split[End_Point_List_Count_Split.length - 1];
	  System.out.println("There are " + End_Point_List_Count + " End Point on the list.");
	  
	  //Verify the next and prev button is enabled
	  NextBtn = driver.findElement(By.id("ctl00_cp1_cmdNext"));
	  PrevBtn = driver.findElement(By.id("ctl00_cp1_cmdPrev"));
	  
	  if (Integer.parseInt(End_Point_List_Count) > 30){
		  Assert.assertTrue((driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() == 31));
		
		  Assert.assertTrue(NextBtn.isEnabled(), "Fail, Next button is disabled when more than 30 end points on the list.");
		  Assert.assertFalse(PrevBtn.isEnabled(), "Fail, Prev button is enabled on the first page.");
		  
	  } else {
		  
		  Assert.assertFalse(NextBtn.isEnabled(), "Fail, Next button is enabled when less than 30 end points on the list.");
		  Assert.assertFalse(PrevBtn.isEnabled(), "Fail, Prev button is enabled on the first page.");
		  
		  
		 //Add more End Point until next button enable
		  do{
			  //Verify the end point list count
			  End_Point_List_Count_Title = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
			  End_Point_List_Count_Split = End_Point_List_Count_Title.split(" ");
			  End_Point_List_Count = End_Point_List_Count_Split[End_Point_List_Count_Split.length - 1];
			  //Click add button
			  driver.findElement(By.id("ctl00_cp1_cmdNew")).click();
			  
			  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			  
			  Assert.assertTrue(driver.findElement(By.tagName("th")).getText().equals("End Point Details"));
			  System.out.println("Correct Header. " + driver.findElement(By.tagName("th")).getText());
			  
			  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblPRID")).getText().equals("NEW"));
			  System.out.println("Correct ID. " + driver.findElement(By.id("ctl00_cp1_lblPRID")).getText());
			  
			  //Add Name
			  driver.findElement(By.id("ctl00_cp1_txtPRNAME")).sendKeys(data[0] + Integer.toString(Num));
			  //Add NACS Code
			  driver.findElement(By.id("ctl00_cp1_txtPRNACS")).sendKeys(data[1]);
			  //Add Contact name
			  driver.findElement(By.id("ctl00_cp1_txtPRCONT")).sendKeys(data[2] + Integer.toString(Num));
			  //Add Email
			  driver.findElement(By.id("ctl00_cp1_txtPRCEML")).sendKeys(data[3]);
			  //Click Save
			  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
			  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			  //Verify the count title
			  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText().contains(Integer.toString((Integer.parseInt(End_Point_List_Count)) + 1)), "End Point count title does not increase."); 
			  System.out.println("End Point List increased. " +  driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText());
			  Num++;
			  
		  } while (Integer.parseInt(End_Point_List_Count) < 31);
		  
	  }
	  
	  //Verify the item ID on each page
	  for (int x=0; x<3; x++){
		  System.out.println("Page number " + x);
		  //Get the first and last end point id on the page 
		  String End_Point_ID_label = driver.findElement(By.id("ctl00_cp1_lbl")).getText();
		  String[] End_Point_ID_label_Split = End_Point_ID_label.split(" ");
		  First_ID.add(End_Point_ID_label_Split[4]);
		  System.out.println("The first ID is " + First_ID);
		  Last_ID.add(End_Point_ID_label_Split[6]);
		  System.out.println("The last ID is " + Last_ID);
		  Assert.assertTrue(Integer.parseInt(Last_ID.get(x)) >= Integer.parseInt(First_ID.get(x)), "The Last ID is not bigger or equal first ID.");
		  System.out.println("The last ID is bigger or equal first ID.");
		  
		  //Verify the ID from edit button
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
			  //Click the edit button
			  table_row.get(n).findElements(By.tagName("td")).get(10).findElement(By.cssSelector("*[id ^='ctl00_cp1_lnkPRID']")).click();
			  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			  //Verify the ID is the same with the label.
			  System.out.println("ID_check is " + ID_check);
			  System.out.println("ID id " + driver.findElement(By.id("ctl00_cp1_lblPRID")).getText());
			  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblPRID")).getText().equals(ID_check), "Wrong ID");
			  System.out.println("It is the same ID with the label.");
			  //Click cancel to go back, after click cancel it always go back to first page
			  //driver.findElement(By.id("ctl00_cp1_cmdCancel")).click();
			  driver.navigate().back();
			  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
	  
	  System.out.println("Pass End Point Next Prev.");
	  
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
