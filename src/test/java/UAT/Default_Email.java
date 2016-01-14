package UAT;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class Default_Email {
	private WebDriver driver;
	private boolean NextBtn_isEnable = true;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int User_count = 0;
	private WebElement NextBtn;
	private boolean Is_Hide_Inactive;
  @Test
  public void main() {
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  //Click Users under Configuration
	  String parentHandle = driver.getWindowHandle();
	  driver.findElement(By.xpath("//*[contains(text(), 'Configure Maintenance')]")).click();
	  //Verify the Title
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  for (String Handle : driver.getWindowHandles()) {
		  driver.switchTo().window(Handle);
	  }
	  String Maintenance_title = driver.getTitle();
	  System.out.println(Maintenance_title);
	  Assert.assertTrue(Maintenance_title.contains("Docman Hub Maint. Config."), "Wrong title name");
	  
	  //Verify the default E-mail settings
	  System.out.println(driver.findElement(By.id("EmailServer")).getAttribute("value"));
	  Assert.assertTrue(driver.findElement(By.id("EmailServer")).getAttribute("value").equals("localhost"));
	  System.out.println(driver.findElement(By.id("EmailPort")).getAttribute("value"));
	  Assert.assertTrue(driver.findElement(By.id("EmailPort")).getAttribute("value").equals("25"));
	  System.out.println(driver.findElement(By.id("EmailFromAddress")).getAttribute("value"));
	  Assert.assertTrue(driver.findElement(By.id("EmailFromAddress")).getAttribute("value").equals("autonotify@edthub.com"));
	  System.out.println(driver.findElement(By.id("EmailFriendlyAddress")).getAttribute("value"));
	  Assert.assertTrue(driver.findElement(By.id("EmailFriendlyAddress")).getAttribute("value").equals(""));
	  System.out.println(driver.findElement(By.id("EmailSendAutomated")).isSelected());
	  Assert.assertTrue(driver.findElement(By.id("EmailSendAutomated")).isSelected());
	  System.out.println(driver.findElement(By.id("chkAuth")).isSelected());
	  Assert.assertFalse(driver.findElement(By.id("chkAuth")).isSelected());
	  System.out.println(driver.findElement(By.id("EmailSystemSummaries")).isSelected());
	  Assert.assertTrue(driver.findElement(By.id("EmailSystemSummaries")).isSelected());
	  System.out.println(driver.findElement(By.id("EmailPracticeSummaries")).isSelected());
	  Assert.assertTrue(driver.findElement(By.id("EmailPracticeSummaries")).isSelected());
	  System.out.println(driver.findElement(By.id("EmailUseSSL")).isSelected());
	  Assert.assertFalse(driver.findElement(By.id("EmailUseSSL")).isSelected());
	  System.out.println(driver.findElement(By.id("EmailConversionPending")).isSelected());
	  Assert.assertTrue(driver.findElement(By.id("EmailConversionPending")).isSelected());
  }
  @BeforeTest
  public void beforeTest() {
	  driver =  Set_Web_Browser.Set_Web_Browser();
  }

  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

}
