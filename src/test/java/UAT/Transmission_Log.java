package UAT;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class Transmission_Log {
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
  @Test
  public void main() {
	  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  //Click Transmission Log under Configuration
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
	  
	  //Verify the table header for each column
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_tbl")).findElement(By.tagName("tr")).getText().equals("Trans ID Date End Point Total D/L Deliv'd"));
	  System.out.println("Correct table headers. " + driver.findElement(By.id("ctl00_cp1_tbl")).findElement(By.tagName("tr")).getText());
	  
	  //Verify there is transmission log on the list
	  if (driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 1){
		  NextBtn_isEnable = true;
		  System.out.println("There is transmission log on the list.");
	  } else {
		  NextBtn_isEnable = false;
		  System.out.println("No transmission log on the list.");
	  }
	  
	  do{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			table = driver.findElement(By.id("ctl00_cp1_tbl"));
			table_row = table.findElements(By.tagName("tr"));
			
			for (int x = 1; x < table_row.size(); x++){
				table_data = table_row.get(x).findElements(By.tagName("td"));
				System.out.println(table_data.get(0).getText());
				Assert.assertTrue(table_data.get(7).isEnabled(), "View button is disabled");
				System.out.println("View button is enabled");
				
				tran_log_count ++;
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

	  Assert.assertTrue(Tran_Log_Count.contains(Integer.toString(tran_log_count)), "Wrong transmission log count");
	  System.out.println("There are " + tran_log_count + " transmission log on the list.");
	  
	  
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
