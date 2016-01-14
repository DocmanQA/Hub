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
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class Clinicians_Filter {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int Clinician_count = 0;
	private WebElement NextBtn;
	private boolean Is_Hide_Inactive;
	private List<String> EndPoint = new ArrayList<String>();
  @Test
  public void main() {
	  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  //Click End Points under Configuration
	  driver.findElement(By.xpath("//*[contains(text(), 'Clinician')]")).click();
	  //Verify the Title
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  String Clinician_List_title = driver.getTitle();
	  Assert.assertTrue(Clinician_List_title.contains("Clinician List"));
	  System.out.println(Clinician_List_title);
	  
	//Filter type Practice
	  Select select_type = new Select(driver.findElement(By.id("ctl00_cp1_cboFPrac")));
	  select_type.selectByVisibleText("Pioneers Way 2");

	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  
	  if (driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 1){
		  NextBtn_isEnable = true;
	  } else {
		  NextBtn_isEnable = false;
		  System.out.println("No clinician on the list after filter");
	  }
	  
	  do{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			table = driver.findElement(By.id("ctl00_cp1_tbl"));
			table_row = table.findElements(By.tagName("tr"));
			
			for (int x = 1; x < table_row.size(); x++){
				table_data = table_row.get(x).findElements(By.tagName("td"));
				Assert.assertTrue(table_data.get(2).getText().equals("Pioneers Way 2"));
				
				Clinician_count ++;
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
	  
	  System.out.println("There are " + Clinician_count + " clinicians. After filter out.");
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText().contains(Integer.toString(Clinician_count)));
	  System.out.println("Correct Clinician Count Title.");
	  
	  //System.out.println(Name);
	  //System.out.println(NACS);
	
	  
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
