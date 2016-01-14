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

public class Edit_Document_Sources {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int Doc_Source_count = 0;
	private WebElement NextBtn;
	private boolean Is_Hide_Inactive;
	private List<String> Doc_Sources_data = new ArrayList<String>();
  @Test
  public void main() {
	  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  //Click Document Sources under Configuration
	  driver.findElement(By.xpath("//*[contains(text(), 'Document Sources')]")).click();
	  //Verify the Title
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  String Doc_Sources_List_title = driver.getTitle();
	  Assert.assertTrue(Doc_Sources_List_title.contains("Source List"));
	  System.out.println(Doc_Sources_List_title);
	  
	//Verify Hide Inactive is enabled
	  Is_Hide_Inactive = driver.findElement(By.id("ctl00_cp1_chkInactive")).isSelected();
	  System.out.println(Is_Hide_Inactive);
	  Assert.assertTrue(Is_Hide_Inactive, "Is_hide_Inactive is false");
	  Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Document Source List"), "table header does not contains (Document Source List)");
	  System.out.println("Correct Item Count header");
	  String Doc_Source_List_Count = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	  System.out.println(Doc_Source_List_Count);
	  
	//Verify there is document sources on the list
	  int Doc_Source_No_1st_Page = driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size();
	  int Edit_No = 0;
	  if (Doc_Source_No_1st_Page > 1){
		  
		  if (Doc_Source_No_1st_Page > 4){
			  //Edit the first 3 Users
			  Edit_No = 10;
			  
		  } else {
			  Edit_No = Doc_Source_No_1st_Page;
		  }
		  for (int x = 7; x < Edit_No; x++){
			  driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(x).findElements(By.tagName("td")).get(6).findElement(By.cssSelector("*[id ^='ctl00_cp1_lnkSOID']")).click();
			  
			  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			  
			  //Verify the table header
			  Assert.assertTrue(driver.findElement(By.tagName("th")).getText().equals("Document Source Details"), "Wrong table header.");
			  System.out.println("Correct. table header.");
			  
			  //Get data from the Users details
			  Doc_Sources_data.clear();
			  Doc_Sources_data.add(driver.findElement(By.id("ctl00_cp1_lblSOID")).getText());
			  Doc_Sources_data.add(driver.findElement(By.id("ctl00_cp1_txtSODESC")).getAttribute("value"));
			  Doc_Sources_data.add(driver.findElement(By.id("ctl00_cp1_txtSOALERT")).getAttribute("value"));
			  
			  System.out.println(Doc_Sources_data);
			  
			  //Key in new data
			  driver.findElement(By.id("ctl00_cp1_txtSODESC")).clear();
			  driver.findElement(By.id("ctl00_cp1_txtSODESC")).sendKeys("Testing");
			
			  
			  //Click save button
			  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
			  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			  
			  //Verify the description has been edited
			  table_data = driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(x).findElements(By.tagName("td"));
			  Assert.assertTrue((table_data.get(1).getText()).equals("Testing"));
			  System.out.println("Description " + x + " has been edited.");  
			  
			  //Change the data back to origin
			  driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(x).findElements(By.tagName("td")).get(6).findElement(By.cssSelector("*[id ^='ctl00_cp1_lnkSOID']")).click();
			  
			  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			  //Key in new data
			  driver.findElement(By.id("ctl00_cp1_txtSODESC")).clear();
			  driver.findElement(By.id("ctl00_cp1_txtSODESC")).sendKeys(Doc_Sources_data.get(1));
			  //Save
			  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
			  
			  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			  System.out.println(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(x).findElements(By.tagName("td")).get(1).getText());

		  }
	  } else {
		  
		  System.out.println("No document source on the list");
	  }
	  
	  System.out.println("Pass Edit Document Sources List.");
	  
  }
  @BeforeTest
  public void beforeTest() {
	  driver =  Set_Web_Browser.Set_Web_Browser();
  }

  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

}
