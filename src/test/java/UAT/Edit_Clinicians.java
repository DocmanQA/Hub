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
import org.openqa.selenium.support.ui.*;

public class Edit_Clinicians {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int EndPoint_count = 0;
	private WebElement NextBtn;
	private boolean Is_Hide_Inactive;
	private List<String> Clinician_data = new ArrayList<String>();
  @Test
  public void main() {
	  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  //Click End Points under Configuration
	  driver.findElement(By.xpath("//*[contains(text(), 'Clinician')]")).click();
	  //Verify the Title
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  String Clinician_List_title = driver.getTitle();
	  Assert.assertTrue(Clinician_List_title.contains("Clinician List"));
	  System.out.println(Clinician_List_title);
	  //Verify Hide Inactive is enabled
	  Is_Hide_Inactive = driver.findElement(By.id("ctl00_cp1_chkfInac")).isSelected();
	  System.out.println(Is_Hide_Inactive);
	  Assert.assertTrue(Is_Hide_Inactive);
	  Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Clinician List"));
	  System.out.println("Correct Item Count header");
	  String Clinician_List_Count = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	  System.out.println(Clinician_List_Count);
	  
	  //Verify there is Clinician on the list
	  int Clinician_No_1st_Page = driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size();
	  int Edit_No = 0;
	  if (Clinician_No_1st_Page > 1){
		  
		  if (Clinician_No_1st_Page > 4){
			  //Edit the first 3 clinicians
			  Edit_No = 4;
			  
		  } else {
			  Edit_No = Clinician_No_1st_Page;
		  }
		  for (int x = 1; x < Edit_No; x++){
			  driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(x).findElements(By.tagName("td")).get(4).findElement(By.cssSelector("*[id ^='ctl00_cp1_lnkXRID']")).click();
			  
			  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
			  //Get data from the clinician details
			  Clinician_data.clear();
			  Clinician_data.add(driver.findElement(By.id("ctl00_cp1_lblXRID")).getText());
			  Clinician_data.add(driver.findElement(By.id("ctl00_cp1_txtXRTITL")).getAttribute("value"));
			  Clinician_data.add(driver.findElement(By.id("ctl00_cp1_txtXRFORE")).getAttribute("value"));
			  Clinician_data.add(driver.findElement(By.id("ctl00_cp1_txtXRSURN")).getAttribute("value"));
			  Clinician_data.add(driver.findElement(By.id("ctl00_cp1_txtXRCODE")).getAttribute("value"));
			  Clinician_data.add(driver.findElement(By.id("ctl00_cp1_txtXRLOC")).getAttribute("value"));
			  Select select_EndPoint = new Select(driver.findElement(By.id("ctl00_cp1_cboXRPRID")));
			  Clinician_data.add(select_EndPoint.getFirstSelectedOption().getText());
			  //Clinician_data.add(driver.findElement(By.id("ctl00_cp1_chkXRINAC")).isSelected());
			  
			  System.out.println(Clinician_data);
			  
			  //Key in new data
			  driver.findElement(By.id("ctl00_cp1_txtXRFORE")).clear();
			  driver.findElement(By.id("ctl00_cp1_txtXRFORE")).sendKeys("Testing");
			
			  
			  //Click save button
			  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
			  
			  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			  //Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_txtPRNAME")).getText().contains("Testing"));
			  
			  table_data = driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(x).findElements(By.tagName("td"));
			  String Clinician_name[] = (table_data.get(1).getText()).split(" ");
			  Assert.assertTrue((Clinician_name[Clinician_name.length - 1]).equals("Testing"));
			  System.out.println("Clinician Forename " + x + " has been edited.");
			  
			  
			  //Change the data back to origin
			  driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(x).findElements(By.tagName("td")).get(4).findElement(By.cssSelector("*[id ^='ctl00_cp1_lnkXRID']")).click();
			  
			  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			  //Key in new data
			  driver.findElement(By.id("ctl00_cp1_txtXRFORE")).clear();
			  driver.findElement(By.id("ctl00_cp1_txtXRFORE")).sendKeys(Clinician_data.get(2));
			  //Save
			  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
			  
			  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			  System.out.println(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(x).findElements(By.tagName("td")).get(1).getText());

		  }
	  } else {
		  
		  System.out.println("No End Point on the list");
	  }
	  
	  System.out.println("Pass Edit Clinician List.");
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
