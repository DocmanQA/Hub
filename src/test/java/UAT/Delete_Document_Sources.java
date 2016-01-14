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

public class Delete_Document_Sources {
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
	  
	  //Verify there is document source on the list
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).size() > 1);
	  System.out.println("There is document source on the list.");
	  NextBtn_isEnable = true;
	  
	  do{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			table = driver.findElement(By.id("ctl00_cp1_tbl"));
			table_row = table.findElements(By.tagName("tr"));
			
			Doc_Source_count = Doc_Source_count + table_row.size() - 1;
			
			NextBtn = driver.findElement(By.id("ctl00_cp1_cmdNext"));
			
			if (NextBtn.isEnabled()){
				NextBtn_isEnable = true;
				//Click the next button to next page.
				NextBtn.click();
			} else {
				NextBtn_isEnable = false;
				
				table = driver.findElement(By.id("ctl00_cp1_tbl"));
				table_row = table.findElements(By.tagName("tr"));
				table_data = table_row.get(table_row.size() - 1).findElements(By.tagName("td"));
				Assert.assertTrue(table_data.get(1).getText().equals(data[0]));
				Assert.assertTrue(table_data.get(2).getText().equals(data[1]));
				System.out.println("Correct user data");
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				//Click the edit button	
				table_data.get(6).findElement(By.cssSelector("*[id ^='ctl00_cp1_lnkSOID']")).click();
				System.out.println("Click the edit button");
				
			}

		} while (NextBtn_isEnable == true);
	  
	  	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Before delete, there are " + Doc_Source_count + " Document Sources.");
		
		driver.findElement(By.id("ctl00_cp1_cmdDelete")).click();
		System.out.println("Click delete button.");
		driver.switchTo().alert().accept();
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		String Doc_Source_List_Count_Title2 = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
		System.out.println(Doc_Source_List_Count_Title2);
		String[] Doc_Source_List_Count_Split2 = Doc_Source_List_Count_Title2.split(" ");
		String Doc_Source_List_Count2 = Doc_Source_List_Count_Split2[Doc_Source_List_Count_Split2.length - 1];
		System.out.println(Doc_Source_List_Count2);
		
		Assert.assertTrue(Doc_Source_List_Count2.equals(Integer.toString(Doc_Source_count - 1)), "Wrong document source count header.");
		System.out.println("Correct document source count number. 1 has been deleted.");
		System.out.println("Pass Delete Document Source.");
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
