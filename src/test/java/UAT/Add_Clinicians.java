package UAT;

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

public class Add_Clinicians {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int Clinician_count = 0;
	private WebElement NextBtn;
	private boolean Is_Hide_Inactive;
	private String[] data = {"Mr", "Tony", "Yip", "Pioneers Way 2"};
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
	  //Verify Hide Inactive is enabled
	  Is_Hide_Inactive = driver.findElement(By.id("ctl00_cp1_chkfInac")).isSelected();
	  System.out.println(Is_Hide_Inactive);
	  Assert.assertTrue(Is_Hide_Inactive);
	  Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Clinician List"));
	  System.out.println("Correct Item Count header");
	  String Clinician_List_Count_title = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	  System.out.println(Clinician_List_Count_title);
	  
	  String[] Clinician_List_Count_Split = Clinician_List_Count_title.split(" ");
	  String Clinician_List_Count = Clinician_List_Count_Split[Clinician_List_Count_Split.length - 1];
	  System.out.println(Clinician_List_Count);
	  
	  //Click Add button
	  driver.findElement(By.id("ctl00_cp1_cmdNew")).click();
	  
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  
	  Assert.assertTrue(driver.findElement(By.tagName("th")).getText().equals("Clinician Details"));
	  System.out.println("Correct Header. " + driver.findElement(By.tagName("th")).getText());
	  
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblXRID")).getText().equals("NEW"));
	  System.out.println("Correct ID. " + driver.findElement(By.id("ctl00_cp1_lblXRID")).getText());
	  
	  //Add Title
	  driver.findElement(By.id("ctl00_cp1_txtXRTITL")).sendKeys(data[0]);
	  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  //Verify the error 
	  System.out.println(driver.findElement(By.id("ctl00_cp1_lblErr")).getText());
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblErr")).getText().equals("Forename code cannot be blank"));
	  
	  //Add Forename
	  driver.findElement(By.id("ctl00_cp1_txtXRFORE")).sendKeys(data[1]);
	  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  
	  //Verify the error 
	  System.out.println(driver.findElement(By.id("ctl00_cp1_lblErr")).getText());
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblErr")).getText().equals("Surname cannot be blank"));
	  
	//Add Surname
	  driver.findElement(By.id("ctl00_cp1_txtXRSURN")).sendKeys(data[2]);
	 
	//Select End Point
	  Select select_EndPoint = new Select(driver.findElement(By.id("ctl00_cp1_cboXRPRID")));
	  select_EndPoint.selectByVisibleText(data[3]);
	  driver.findElement(By.id("ctl00_cp1_cmdSave")).click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText().contains(Integer.toString((Integer.parseInt(Clinician_List_Count)) + 1))); 
	  System.out.println("Clinician List increased. " +  driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText());
	  
	  //Verify the new End Point is added to the last
	  
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 1);
	  System.out.println("There is End Point on the list.");
	  NextBtn_isEnable = true;
	  
	  do{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			table = driver.findElement(By.id("ctl00_cp1_tbl"));
			table_row = table.findElements(By.tagName("tr"));
			
			Clinician_count = Clinician_count + table_row.size() - 1;
			
			//driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			NextBtn = driver.findElement(By.id("ctl00_cp1_cmdNext"));
			
			if (NextBtn.isEnabled()){
				NextBtn_isEnable = true;
				//Click the next button to next page.
				NextBtn.click();
			} else {
				NextBtn_isEnable = false;
				table_row = driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr"));
				table_data = table_row.get(table_row.size() - 1).findElements(By.tagName("td"));
				Assert.assertTrue(table_data.get(1).getText().contains(data[0]));
				Assert.assertTrue(table_data.get(1).getText().contains(data[1]));
				Assert.assertTrue(table_data.get(1).getText().contains(data[2]));
				Assert.assertTrue(table_data.get(2).getText().contains(data[3]));
				
			}

		} while (NextBtn_isEnable == true);
	  
	  Assert.assertTrue(Integer.toString(Clinician_count).equals(Integer.toString(Integer.parseInt(Clinician_List_Count) + 1)));
	  System.out.println("Correct clinician List count");
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
