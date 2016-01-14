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

public class Transmission_Log_Filter {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int tran_log_count = 0;
	private WebElement NextBtn;
	private WebElement PrevBtn;
	private boolean Is_Hide_Inactive;
	private List<String> Doc_Sources_data = new ArrayList<String>();
	private String[] data = {"TESTING", "test@docman.com"};
	private int Num = 1;
	private List<String> First_ID = new ArrayList<String>();
	private List<String> Last_ID = new ArrayList<String>();
	private String ID_check;
	private String End_Point;
  @Test
  public void main() {
	  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  //Click Document Sources under Configuration
	  driver.findElement(By.xpath("//*[contains(text(), 'Transmission Log')]")).click();
	  //Verify the Title
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  String Transmission_Log_title = driver.getTitle();
	  Assert.assertTrue(Transmission_Log_title.contains("Transmission Log"));
	  System.out.println(Transmission_Log_title);
	  
	  //Verify the item count header
	  Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Transmission Log"), "table header does not contains (Transmission Log)");
	  System.out.println("Correct Item Count header");
	  String Tran_Log_Count_Title = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	  System.out.println(Tran_Log_Count_Title);
	  String[] Tran_Log_Count_Split = Tran_Log_Count_Title.split(" ");
	  String Tran_Log_Count = Tran_Log_Count_Split[Tran_Log_Count_Split.length - 1];
	  System.out.println(Tran_Log_Count);
	  
	  //Get the End Point from first transmission log
	  if (driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 1){
		  NextBtn_isEnable = true;
		  System.out.println("There is transmission log on the list.");
		  End_Point = driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(2).getText();
	  } else {
		  NextBtn_isEnable = false;
		  System.out.println("No transmission log on the list.");
	  }
	  
	  //Select End Point from filter
	  Select selected_EDT_Point = new Select(driver.findElement(By.id("ctl00_cp1_cboFPrac")));
	  selected_EDT_Point.selectByVisibleText(End_Point);
	  
	  System.out.println("Selected " + End_Point);
	  
	  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  
	  //Verify the document list count header
	  String Tran_Log_header = driver.findElement(By.tagName("h1")).getText();
	  System.out.println(Tran_Log_header);
	  Assert.assertTrue(Tran_Log_header.contains("Transmission Log"));
	  
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
				String Doc_End_Point = table_data.get(2).getText();
				System.out.println("Doc ID: " + table_data.get(0).getText() + ", Doc End Point: " + Doc_End_Point );
				
				Assert.assertTrue(Doc_End_Point.equals(End_Point));
				Assert.assertTrue(table_data.get(7).isEnabled());
				
				tran_log_count ++;
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
	  
	  System.out.println( "There are " + tran_log_count + " transmission logs in the list after filter");
	  Assert.assertTrue(Tran_Log_header.contains(Integer.toString(tran_log_count)));
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
