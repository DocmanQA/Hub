package UAT;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class Document_Sources_Filter {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int Doc_Source_count = 0;
	private WebElement NextBtn;
	private boolean Is_Hide_Inactive;
	private List<String> Doc_Sources_data = new ArrayList<String>();
	private String[] data = {"TESTING", "test@docman.com"};
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
	  String Doc_Source_List_Count_Title = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	  System.out.println(Doc_Source_List_Count_Title);
	  String[] Doc_Source_List_Count_Split = Doc_Source_List_Count_Title.split(" ");
	  String Doc_Source_List_Count = Doc_Source_List_Count_Split[Doc_Source_List_Count_Split.length - 1];
	  System.out.println(Doc_Source_List_Count);
	  
	  //Filter Source name
	  driver.findElement(By.id("ctl00_cp1_txtName")).sendKeys("Biochemistry");
	  driver.findElement(By.id("ctl00_cp1_txtName")).sendKeys(Keys.RETURN);
	  
	//Verify there is User on the list
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  
	  if (driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 1){
		  NextBtn_isEnable = true;
	  } else {
		  NextBtn_isEnable = false;
		  System.out.println("No document source on the list after filter");
	  }
	  
	  
	  do{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			table = driver.findElement(By.id("ctl00_cp1_tbl"));
			table_row = table.findElements(By.tagName("tr"));
			
			for (int x = 1; x < table_row.size(); x++){
				table_data = table_row.get(x).findElements(By.tagName("td"));
				Assert.assertTrue(table_data.get(1).getText().equals("Biochemistry"));
				Doc_Sources_data.add(table_data.get(1).getText());
				
				Doc_Source_count ++;
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
	  
	  System.out.println("There are " + Doc_Source_count + " document sources. After filter out.");
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText().contains(Integer.toString(Doc_Source_count)));
	  System.out.println("Correct Document Source Count Title.");
	  
	  System.out.println(Doc_Sources_data);
	  System.out.println("Pass Document Sources filter.");
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
