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

public class Source_Activity {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int Source_Act_count = 0;
	private WebElement NextBtn;
	private WebElement PrevBtn;
	private boolean Is_Hide_Inactive;
	private List<String> Doc_Sources_data = new ArrayList<String>();
	private String[] data = {"TESTING", "test@docman.com"};
	private int Num = 1;
	private List<String> Doc_Source = new ArrayList<String>();
	private List<String> Filter_Source = new ArrayList<String>();
	private String ID_check;
	private String End_Point;
  @Test
  public void main() {
	  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  //Click Source Activity under Configuration
	  driver.findElement(By.xpath("//*[contains(text(), 'Source Activity')]")).click();
	  //Verify the Title
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  String Source_Act_title = driver.getTitle();
	  Assert.assertTrue(Source_Act_title.contains("Source Activity"));
	  System.out.println(Source_Act_title);
	  //Verify the item count header
	  Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Source Activity"), "table header does not contains (Source Activity)");
	  System.out.println("Correct Item Count header");
	  String Source_Act_Count_Title = driver.findElement(By.id("ctl00_cp1_lblItemCount")).getText();
	  System.out.println(Source_Act_Count_Title);
	  String[] Source_Act_Count_Split = Source_Act_Count_Title.split(" ");
	  String Source_Act_Count = Source_Act_Count_Split[Source_Act_Count_Split.length - 1];
	  System.out.println(Source_Act_Count);
	  
	  //Verify the table header for each column
	  Assert.assertTrue(driver.findElement(By.id("ctl00_cp1_tbl")).findElement(By.tagName("tr")).getText().equals("Document Source Total Delivered Deleted On Hold User Rejected System Rejected Waiting"));
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
				Doc_Source.add(table_data.get(0).getText());
				
				Source_Act_count ++;
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

	  Select select_Source = new Select(driver.findElement(By.id("ctl00_cp1_cboDocSrc")));
	  for (int Y = 1; Y < (Source_Act_count + 1); Y++){
		  Filter_Source.add(select_Source.getOptions().get(Y).getText());
	  }
	  System.out.println(Doc_Source);
	  System.out.println(Filter_Source);
	  Assert.assertTrue(Filter_Source.equals(Doc_Source), "Document Source does not match the source filter data");
	  System.out.println("Document Source match the source from filter.");
	  Assert.assertTrue(Source_Act_Count.contains(Integer.toString(Source_Act_count)), "Wrong source activiy count");
	  System.out.println("There are " + Source_Act_count + " source activity on the list.");
	  
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
