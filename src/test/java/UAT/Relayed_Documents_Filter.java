package UAT;

import java.sql.Driver;
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

public class Relayed_Documents_Filter {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int Relayed_Doc_count = 0;
	private WebElement NextBtn;
	private WebElement PrevBtn;
	private boolean Is_Hide_Inactive;
	private List<String> Doc_Sources_data = new ArrayList<String>();
	private String[] data = {"TESTING", "test@docman.com"};
	private int Num = 1;
	private List<String> First_ID = new ArrayList<String>();
	private List<String> Last_ID = new ArrayList<String>();
	private String ID_check;
  @Test
  public void main() {
	  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  //Click Document Sources under Configuration
	  driver.findElement(By.xpath("//*[contains(text(), 'Relayed Documents')]")).click();
	  //Verify the Title
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  String Relayed_Doc_title = driver.getTitle();
	  Assert.assertTrue(Relayed_Doc_title.contains("Relayed Documents"));
	  System.out.println(Relayed_Doc_title);
	  //Verify the item count header
	  Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Relayed Documents"), "table header does not contains (Document Source List)");
	  System.out.println("Correct Item Count header");
	  String Relayed_Doc_Count_Title = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	  System.out.println(Relayed_Doc_Count_Title);
	  String[] Relayed_Doc_Count_Split = Relayed_Doc_Count_Title.split(" ");
	  String Relayed_Doc_Count = Relayed_Doc_Count_Split[Relayed_Doc_Count_Split.length - 1];
	  System.out.println(Relayed_Doc_Count);
	  
	  //Select Relayed To (EDT Hub) from filter
	  Select selected_EDT_Hub = new Select(driver.findElement(By.id("ctl00_cp1_cboServer")));
	  selected_EDT_Hub.selectByIndex(2);
	  String EDT_Hub = selected_EDT_Hub.getFirstSelectedOption().getText();
	  System.out.println("Selected " + EDT_Hub);
	  
	  //Select Relayed To (End Point) from filter
	  Select selected_End_Point = new Select(driver.findElement(By.id("ctl00_cp1_cboEndPoint")));
	  selected_End_Point.selectByIndex(1);
	  String End_Point = selected_End_Point.getFirstSelectedOption().getText();
	  System.out.println("Selected" + End_Point);
	  
	  //Click search button
	  driver.findElement(By.id("ctl00_cp1_btnResults")).click();
	  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  
	  //Verify the document list count header
	  String Relayed_Doc_header = driver.findElement(By.tagName("h1")).getText();
	  System.out.println(Relayed_Doc_header);
	  Assert.assertTrue(Relayed_Doc_header.contains("Relayed Documents"));
	  
	  //Verify there is document on the list
	  if (driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 1){
		  	NextBtn_isEnable = true;
		 } else {
			NextBtn_isEnable = false;
			System.out.println("No document on the list");
		 }
	  
	//Verify every document details
	  do{
			driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
			table = driver.findElement(By.id("ctl00_cp1_tbl"));
			table_row = table.findElements(By.tagName("tr"));
			
			for (int x = 1; x < table_row.size(); x++){
				table_data = table_row.get(x).findElements(By.tagName("td"));
				String Doc_Dest_Hub = table_data.get(7).getText();
				String Doc_Relayed_To = table_data.get(2).getText();
				System.out.println("Doc ID: " + table_data.get(0).getText() + ", Doc Relayed To: " + Doc_Relayed_To + ", Doc Dest Hub: " + Doc_Dest_Hub);
				Assert.assertTrue(Doc_Dest_Hub.equals(EDT_Hub));
				Assert.assertTrue(Doc_Relayed_To.equals(End_Point));
				Assert.assertTrue(table_data.get(8).isEnabled());
				
				Relayed_Doc_count ++;
			}

			//driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			NextBtn = driver.findElement(By.id("ctl00_cp1_cmdNext"));
			
			if (NextBtn.isEnabled()){
				NextBtn_isEnable = true;
				//Click the next button to next page.
				NextBtn.click();
				System.out.println("Next page");
			} else {
				NextBtn_isEnable = false;
			}

		} while (NextBtn_isEnable == true);
	  
	  System.out.println( "There are " + Relayed_Doc_count + " documents in the list after filter");
	  Assert.assertTrue(Relayed_Doc_header.contains(Integer.toString(Relayed_Doc_count)));
		  
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
