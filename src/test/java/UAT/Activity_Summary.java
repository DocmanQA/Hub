package UAT;

import java.util.List;

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

import UAT.LoginPage;

public class Activity_Summary {
	private WebDriver driver;
	
  @Test
  public void main() {
	  //Verify the text GOOD on the server health.
	  String health = driver.findElement(By.id("ctl00_cp1_tblHealth")).findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(1).findElements(By.tagName("b")).get(0).getText();
	  System.out.println(health);
	  Assert.assertTrue(health.equals("GOOD"), "Health is not GOOD.");
			
	  //Verify all the titles from table Activity Summary
	  List<WebElement> Act_Sum = driver.findElement(By.id("ctl00_cp1_tblStats")).findElements(By.tagName("tr"));
	  String Act_Sum_title = Act_Sum.get(0).findElement(By.tagName("th")).getText();
	  System.out.println(Act_Sum_title);
	  Assert.assertTrue(Act_Sum_title.equals("Activity Summary"), "Title is not Activity Summary.");
	  String[] Act_Sum_Name = {"Documents Waiting", "Documents On Hold", "Documents Delivered",  "Documents Accepted", "Documents Rejected", "Documents Rejected By Conversion", 
			  "Documents Rejected By Receiver", "Documents Rejected By System", "Documents Pending Conversion", "Total Document Count",  
			  "", "Active End Points", "Active Document Sources", "", "End Points not Collected (last 24hrs)",
			  "Transmission Errors (last 24hrs)", "Documents Added Today", "Documents Downloaded Today", "Documents Added Yesterday",
			  "Documents Downloaded Yesterday", "Documents Added This Week", "Documents Downloaded This Week"};
	  
	  for (int x = 0; x < 22; x++){
		  Assert.assertTrue(Act_Sum.get(x + 1).findElements(By.tagName("td")).get(0).getText().equals(Act_Sum_Name[x]), "Activity Summary Table name is not correct.");
		  System.out.println(Act_Sum.get(x + 1).findElements(By.tagName("td")).get(0).getText());
		  System.out.println(Act_Sum.get(x + 1).findElements(By.tagName("td")).get(1).getText());
		  if (Act_Sum_Name[x] != ""){
			  if ((Integer.parseInt(Act_Sum.get(x + 1).findElements(By.tagName("td")).get(1).getText()) > 0) && (Act_Sum_Name[x] != "Total Document Count" )){
				  Assert.assertTrue(Act_Sum.get(x + 1).findElements(By.tagName("td")).get(2).getText().equals("View"), "View button is not displaying.");
			  }
		  }
	  }

	  //Verify all the titles from table Relayed Summary
	  List<WebElement> Rel_Sum = driver.findElement(By.id("ctl00_cp1_tblRelayed")).findElements(By.tagName("tr"));
	  String Rel_Sum_title = Rel_Sum.get(0).findElement(By.tagName("th")).getText();
	  System.out.println(Rel_Sum_title);
	  Assert.assertTrue(Rel_Sum_title.equals("Relayed Summary"), "The Relayed Summary title is not correct.");
	  
	  String[] Rel_Sum_Name = {"Documents Waiting", "Documents On Hold", "Documents Delivered", "Documents Accepted", "Documents Deleted", "Documents Rejected By Receiver",
			  "Documents Rejected By System", "Documents Pending Conversion", "Total Documents Relayed"};
	  for (int x = 0; x < 9; x++){
		  Assert.assertTrue(Rel_Sum.get(x + 1).findElements(By.tagName("td")).get(0).getText().equals(Rel_Sum_Name[x]), "The Relayed Summary Table name is not correct ");
		  System.out.println(Rel_Sum.get(x + 1).findElements(By.tagName("td")).get(0).getText());
		  System.out.println(Rel_Sum.get(x + 1).findElements(By.tagName("td")).get(1).getText());
	  }
	  
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
