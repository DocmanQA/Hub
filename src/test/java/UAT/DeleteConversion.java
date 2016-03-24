package UAT;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class DeleteConversion {
	private WebDriver driver;
  @Test
  public void main() {
	  driver.findElement(By.cssSelector("a[href='ViewDocList.aspx?DCSTAT=4007']")).click();
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  
	  for(int x = 0; x < 500; x++ )
	  {
	  driver.findElement(By.id("ctl00_cp1_tbl")).findElements(By.tagName("tr")).get(1).findElement(By.cssSelector("*[id^='ctl00_cp1_lnkDCID|']")).click();
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  driver.findElement(By.id("ctl00_cp1_cmdDelete")).click();
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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
