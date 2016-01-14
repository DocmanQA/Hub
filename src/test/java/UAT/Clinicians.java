package UAT;

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

public class Clinicians {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int Clinician_count = 0;
	private WebElement NextBtn;
	private boolean Is_Hide_Inactive;
  @Test
  public void main() {
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  //Click clinician under Configuration
	  //driver.findElement(By.cssSelector("a[href='ViewClinician.aspx']")).click();
	  driver.findElement(By.xpath("//*[contains(text(), 'Clinician')]")).click();
	  //Verify the Title
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
	  if (driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 1){
		  NextBtn_isEnable = true;
		  System.out.println("There is Clinician on the list.");
	  } else {
		  NextBtn_isEnable = false;
		  System.out.println("No Clinician on the list.");
	  }
	  
	  do{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			table = driver.findElement(By.id("ctl00_cp1_tbl"));
			table_row = table.findElements(By.tagName("tr"));
			
			for (int x = 1; x < table_row.size(); x++){
				table_data = table_row.get(x).findElements(By.tagName("td"));
				System.out.println(table_data.get(1).getText());
				Assert.assertTrue(table_data.get(3).getText().equals("No"));
				System.out.println("Clinician is not inactive.");
				Assert.assertTrue(table_data.get(4).isEnabled());
				System.out.println("View button is enabled");
				
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

	  System.out.println("There are " + Clinician_count + " clinicians on the list.");
	  
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
