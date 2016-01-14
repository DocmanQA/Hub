package UAT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class Archive_BackupAllOption {
	private WebDriver driver;
	private WebElement table;
	private List<WebElement> table_row;
	private String[] table_data = new String[5];
	private String[] Updated_table_data = new String[5];
	private int Task_no = 0;
	private boolean File_Match;
	private String Log_File_Name;
	private BufferedReader reader = null;
	private String Line = null;
	private boolean Backup_Bool = false;
	private boolean ArchiveDoc_Bool = false;
	private boolean ArchiveMeta_Bool = true;
    private boolean ArchiveAudit_Bool = true;
    private boolean ArchiveAccepted_Bool = true;
    private boolean ArchiveDelivered_Bool = true;
    private boolean ArchiveRejected_Bool = true;
    private boolean ArchiveDeleted_Bool = true;
    private String Task_ID;
	
  @Test
  public void main() {
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  //Click Users under Configuration
	  String parentHandle = driver.getWindowHandle();
	  driver.findElement(By.xpath("//*[contains(text(), 'Archive Console')]")).click();
	  //Verify the Title
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  for (String Handle : driver.getWindowHandles()) {
		  driver.switchTo().window(Handle);
	  }
	  String Archive_title = driver.getTitle();
	  System.out.println(Archive_title);
	  Assert.assertTrue(Archive_title.contains("Docman Hub Archive Console"), "Wrong title name");
	  
	  Assert.assertTrue(driver.findElement(By.tagName("h2")).getText().equals("Archive Tasks"));
	  
	  
	  table = driver.findElement(By.cssSelector("table[class='table table-striped table-condensed']"));
	  table_row = table.findElements(By.tagName("tr"));
	  System.out.println(table_row.size());
	  Task_no = table_row.size() - 1;
	  if (table_row.size() > 1){
		  Assert.assertTrue(driver.findElement(By.tagName("h3")).getText().equals(Task_no + " Archive Tasks found"), "Wrong archive tasks found number.");
	  }
	  
	  //Assert.assertTrue((table_row.size()) == 1);
	  
	  //Click the create task button
	  System.out.println(driver.findElement(By.cssSelector("button[class='btn btn-success']")).getText());
	  Assert.assertTrue(driver.findElement(By.cssSelector("button[class='btn btn-success']")).getText().equals("Create Archive Task"));
	  driver.findElement(By.cssSelector("button[class='btn btn-success']")).click();
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  
	  Assert.assertTrue(driver.findElement(By.tagName("h2")).getText().equals("Create Archive Task"), "The header is not Create Archive Task.");
	  Assert.assertTrue(driver.findElement(By.id("btnSubmit")).getAttribute("value").equals("Run Task"), "The button is not Run Task.");
	  
	  Date now = new Date();
	  SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
	  
	  driver.findElement(By.id("dateFrom")).sendKeys(ft.format(now));
	  driver.findElement(By.id("dateTo")).sendKeys(ft.format(now));
	  
	  if (!driver.findElement(By.id("chkArcStatusDelivered")).isSelected()){
		  driver.findElement(By.id("chkArcStatusDelivered")).click();
	  }
	  
	  if (!driver.findElement(By.id("chkArcMetadata")).isSelected()){
		  driver.findElement(By.id("chkArcMetadata")).click();
	  }
	  
	  if (!driver.findElement(By.id("chkArcAudit")).isSelected()){
		  driver.findElement(By.id("chkArcAudit")).click();
	  }
	  
	  if (!driver.findElement(By.id("chkArcStatusAccepted")).isSelected()){
		  driver.findElement(By.id("chkArcStatusAccepted")).click();
	  }
	  
	  if (!driver.findElement(By.id("chkArcStatusRejected")).isSelected()){
		  driver.findElement(By.id("chkArcStatusRejected")).click();
	  }
	  
	  if (!driver.findElement(By.id("chkArcStatusDelBySender")).isSelected()){
		  driver.findElement(By.id("chkArcStatusDelBySender")).click();
	  }
	  
	  driver.findElement(By.id("rbBackup")).click();
	
	  driver.findElement(By.id("txtBackupPath")).clear();
	  driver.findElement(By.id("txtBackupPath")).sendKeys("C:\\Backup");
	  driver.findElement(By.id("logPath")).clear();
	  driver.findElement(By.id("logPath")).sendKeys("C:\\Backup");
	  
	  try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  //Click the run task button
	  driver.findElement(By.id("btnSubmit")).click();
	  
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  
	  System.out.println(driver.findElement(By.tagName("h3")).getText());
	  Assert.assertTrue(driver.findElement(By.tagName("h3")).getText().contains("Archive Tasks found"));
	  
	  table = driver.findElement(By.cssSelector("table[class='table table-striped table-condensed']"));
	  table_row = table.findElements(By.tagName("tr"));
	  System.out.println(table_row.size());
	  
	  //one Task add to the table
	  Assert.assertTrue(table_row.size() == Task_no + 2, "Task number did not increase.");
	  Task_no = table_row.size() - 1;
	  
	  for (int i = 0; i < 5; i++){
		  table_data[i] = table_row.get(1).findElements(By.tagName("td")).get(i).getText();
		  System.out.println(table_data[i]);
	  }
	  
	  System.out.println(table_data[3]);
	  Assert.assertTrue(table_row.get(1).findElements(By.tagName("td")).get(3).getText().equals("Admin (Override)"));
	  
	  System.out.println(table_data[4]);
	  Assert.assertTrue(table_row.get(1).findElements(By.tagName("td")).get(4).getText().equals("Pending"));
	  
	  System.out.println(driver.findElement(By.cssSelector("table[class='table table-condensed']")).findElement(By.tagName("td")).getText());
	  Assert.assertTrue(driver.findElement(By.cssSelector("table[class='table table-condensed']")).findElement(By.tagName("td")).getText().equals("No new archive tasks can be created while there are tasks pending"));
	  
	  Assert.assertFalse(driver.findElement(By.cssSelector("button[class='btn btn-success']")).isEnabled(), "Button is enabled.");
	  
	  
	  //View task
	  driver.findElement(By.xpath("//*[contains(text(), 'View')]")).click();
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  System.out.println(driver.findElement(By.tagName("h2")).getText());
	  Assert.assertTrue(driver.findElement(By.tagName("h2")).getText().equals("Show Archive Task"));
	  
	  table = driver.findElement(By.cssSelector("table[class='table table-condensed']"));
	  table_row = table.findElements(By.tagName("tr"));
	  
	  System.out.println(table_row.get(0).findElements(By.tagName("td")).get(1).getText());
	  Task_ID = table_row.get(0).findElements(By.tagName("td")).get(1).getText();
	  Assert.assertTrue(table_row.get(0).findElements(By.tagName("td")).get(1).getText().equals(table_data[0]));
	  System.out.println(table_row.get(0).findElements(By.tagName("td")).get(4).getText());
	  Assert.assertTrue(table_row.get(0).findElements(By.tagName("td")).get(4).getText().equals(table_data[3]));
	  System.out.println(table_row.get(1).findElements(By.tagName("td")).get(1).getText());
	  Assert.assertTrue(table_row.get(1).findElements(By.tagName("td")).get(1).getText().equals(table_data[1]));
	  System.out.println(table_row.get(1).findElements(By.tagName("td")).get(4).getText());
	  Assert.assertTrue(table_row.get(1).findElements(By.tagName("td")).get(4).getText().equals(table_data[2]));
	  System.out.println(table_row.get(5).findElements(By.tagName("td")).get(1).getText());
	  Assert.assertTrue(table_row.get(5).findElements(By.tagName("td")).get(1).getText().equals(table_data[4]));
	  
	  //Verify Go Back button enable
	  System.out.println(driver.findElement(By.cssSelector("button[class='btn btn-success']")).isEnabled());
	  Assert.assertTrue(driver.findElement(By.cssSelector("button[class='btn btn-success']")).isEnabled(), "Button is not enabled.");
	  System.out.println(driver.findElement(By.cssSelector("button[class='btn btn-success']")).getText());
	  Assert.assertTrue(driver.findElement(By.cssSelector("button[class='btn btn-success']")).getText().equals("Go Back"), "Button is not display 'Go Back'.");
	  
	  //Wait for 5 minutes
	  try {
		Thread.sleep(300000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  //Click the Go Back button
	  driver.findElement(By.cssSelector("button[class='btn btn-success']")).click();
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  
	  //Verify the task is completed.
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  
	  table = driver.findElement(By.cssSelector("table[class='table table-striped table-condensed']"));
	  table_row = table.findElements(By.tagName("tr"));
	  System.out.println(table_row.size());
	  
	  //Verify it has same task number
	  Assert.assertTrue(table_row.size() == Task_no + 1, "Task number is not the same.");
	  Task_no = table_row.size() - 1;
	  
	  for (int i = 0; i < 5; i++){
		  Updated_table_data[i] = table_row.get(1).findElements(By.tagName("td")).get(i).getText();
		  System.out.println(Updated_table_data[i]);
	  }
	  
	  Assert.assertTrue(Updated_table_data[0].equals(table_data[0]), "Wrong Task ID");
	  Assert.assertTrue(Updated_table_data[1].contains(ft.format(now).toString()), "It does not display today's date.");
	  //Assert.assertTrue(Updated_table_data[1].equals(Updated_table_data[2]), "It is not the same date and time.");
	  Assert.assertTrue(Updated_table_data[3].equals(table_data[3]), "Wrong Request By");
	  Assert.assertTrue(Updated_table_data[4].equals("Completed"), "Wrong Status");
	  
	  //Button is enabled.
	  Assert.assertTrue(driver.findElement(By.cssSelector("button[class='btn btn-success']")).isEnabled(), "Button is not enabled.");
	  
	  //Click the view button 
	  driver.findElement(By.xpath("//*[contains(text(), 'View')]")).click();
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  
	  //Verify the show archive task data
	  System.out.println(driver.findElement(By.tagName("h2")).getText());
	  Assert.assertTrue(driver.findElement(By.tagName("h2")).getText().equals("Show Archive Task"));
	  
	  table = driver.findElement(By.cssSelector("table[class='table table-condensed']"));
	  table_row = table.findElements(By.tagName("tr"));
	  
	  System.out.println(table_row.get(0).findElements(By.tagName("td")).get(1).getText());
	  Assert.assertTrue(table_row.get(0).findElements(By.tagName("td")).get(1).getText().equals(Updated_table_data[0]));
	  System.out.println(table_row.get(0).findElements(By.tagName("td")).get(4).getText());
	  Assert.assertTrue(table_row.get(0).findElements(By.tagName("td")).get(4).getText().equals(Updated_table_data[3]));
	  System.out.println(table_row.get(1).findElements(By.tagName("td")).get(1).getText());
	  Assert.assertTrue(table_row.get(1).findElements(By.tagName("td")).get(1).getText().equals(Updated_table_data[1]));
	  System.out.println(table_row.get(1).findElements(By.tagName("td")).get(4).getText());
	  Assert.assertTrue(table_row.get(1).findElements(By.tagName("td")).get(4).getText().equals(Updated_table_data[2]));
	  System.out.println(table_row.get(3).findElements(By.tagName("td")).get(4).getText());
	  Assert.assertTrue(table_row.get(3).findElements(By.tagName("td")).get(4).getText().equals("Backup"));
	  System.out.println(table_row.get(4).findElements(By.tagName("td")).get(1).getText());
	  Assert.assertTrue(table_row.get(4).findElements(By.tagName("td")).get(1).getText().equals("Accepted, Delivered, Rejected, Deleted By Sender"));
	  System.out.println(table_row.get(4).findElements(By.tagName("td")).get(4).getText());
	  Assert.assertTrue(table_row.get(4).findElements(By.tagName("td")).get(4).getText().equals("C:\\Backup"));
	  System.out.println(table_row.get(5).findElements(By.tagName("td")).get(1).getText());
	  Assert.assertTrue(table_row.get(5).findElements(By.tagName("td")).get(1).getText().equals(Updated_table_data[4]));
	  System.out.println(table_row.get(6).findElements(By.tagName("td")).get(1).getText());
	  Assert.assertTrue(table_row.get(6).findElements(By.tagName("td")).get(1).getText().equals("0"));
	  System.out.println(table_row.get(6).findElements(By.tagName("td")).get(4).getText());
	  Assert.assertTrue(table_row.get(6).findElements(By.tagName("td")).get(4).getText().equals("0"));
	  
	  
	  //Verify Go Back button enable
	  System.out.println(driver.findElement(By.cssSelector("button[class='btn btn-success']")).isEnabled());
	  Assert.assertTrue(driver.findElement(By.cssSelector("button[class='btn btn-success']")).isEnabled(), "Button is not enabled.");
	  System.out.println(driver.findElement(By.cssSelector("button[class='btn btn-success']")).getText());
	  Assert.assertTrue(driver.findElement(By.cssSelector("button[class='btn btn-success']")).getText().equals("Go Back"), "Button is not display 'Go Back'.");
	  
	  //Click the Go Back button
	  driver.findElement(By.cssSelector("button[class='btn btn-success']")).click();
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  
	  Log_File_Name = "Archive_" + Updated_table_data[1].replace("/", "").replace(":", "").replace(" ", "") + ".xml";
	  System.out.println("Log_File_Name is " + Log_File_Name);
	  
	  //Verify the archive log file exist in the FTP folder on the server PC
	  //String server = "192.168.1.69";
	  int port = 21;
	  String user = "admin";
	  String pass = "Password1";
	  
	  FTPClient ftpClient = new FTPClient();
	  
	  try{
		  ftpClient.connect(LoginPage.server, port);
		  //System.out.println(ftpClient.getReplyString());
		  ftpClient.login(user, pass);
		  System.out.println(ftpClient.getReplyString());
		  //ftpClient.enterLocalPassiveMode();
		  
		  String remoteFilePath = "/";
		  //ftpClient.cwd("Backup");
		  //ftpClient.cdup();
		  
		  FTPFile[] ftpFile = ftpClient.listFiles(remoteFilePath);
		  for (FTPFile file : ftpFile) {
			  String details = file.getName();
			
			  
			  if (details.equals(Log_File_Name) ){
				  System.out.println("It matches the .xml file.");
				  InputStream stream = ftpClient.retrieveFileStream(Log_File_Name);
				  reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
				  
				  while ((Line = reader.readLine()) != null) {
					  //Line = reader.readLine();
					  
					  if (Line.contains("<Backup_Docs>Yes</Backup_Docs>")){
						  Backup_Bool = true;
					  }
					  if (Line.contains("<Archive_Docs>Yes</Archive_Docs>")){
						  ArchiveDoc_Bool = true;
					  }
					  if (Line.contains("<Archive_Metadata>No</Archive_Metadata>")){
						  ArchiveMeta_Bool = true;
					  }
					  if (Line.contains("<Archive_Audit>No</Archive_Audit>")){
						  ArchiveAudit_Bool = true;
					  }
					  if (Line.contains("<Archive_Accepted_Docs>No</Archive_Accepted_Docs>")){
						  ArchiveAccepted_Bool = true;
					  }
					  if (Line.contains("<Archive_Delivered_Docs>Yes</Archive_Delivered_Docs>")){
						  ArchiveDelivered_Bool = true;
					  }
					  if (Line.contains("<Archive_Rejected_Docs>No</Archive_Rejected_Docs>")){
						  ArchiveRejected_Bool = true;
					  }
					  if (Line.contains("<Archive_Deleted_By_Sender_Docs>No</Archive_Deleted_By_Sender_Docs>")){
						  ArchiveDeleted_Bool = true;
					  }
				  
					  System.out.println(Line);
				  }
				  File_Match = true;
				  
			  }
			  
			  System.out.println(details);
			  
			  
			  
		  }
		  
		  
	  } catch (IOException ex){
		  ex.printStackTrace();
	  } finally {
		  if (ftpClient.isConnected()){
			  try{
				  ftpClient.disconnect();
			  } catch (IOException ex) {
				  ex.printStackTrace();
			  }
		  }
	  }
	  
	  Assert.assertTrue(File_Match, "The archive log file name is not exist.");
	  System.out.println("Pass. File match.");
	  Assert.assertTrue(Backup_Bool, "Backup file is false.");
	  System.out.println("Pass. Backup file is true.");
	  Assert.assertTrue(ArchiveDoc_Bool, "Archive document is false.");
	  System.out.println("Pass. Archive document is true.");
	  Assert.assertTrue(ArchiveMeta_Bool, "Archive Meta is false.");
	  System.out.println("Pass. Archive Meta is true.");
	  Assert.assertTrue(ArchiveAudit_Bool, "Archive Audit is false.");
	  System.out.println("Pass. Archive Audit is true.");
	  Assert.assertTrue(ArchiveAccepted_Bool, "Archive accepted is false.");
	  System.out.println("Pass. Archive accepted is true.");
	  Assert.assertTrue(ArchiveDelivered_Bool, "Archive delivered is false.");
	  System.out.println("Pass. Archive delivered is true.");
	  Assert.assertTrue(ArchiveRejected_Bool, "Archive rejected is false.");
	  System.out.println("Pass. Archive rejected is true.");
	  Assert.assertTrue(ArchiveDeleted_Bool, "Archive deleted is false.");
	  System.out.println("Pass. Archive deleted is true.");
	  
	//Database check
	  MySQL mysql = new MySQL();
	  
	  mysql.SQL_Connection();
	  
	  //Assert.assertTrue(MySQL.Arch_DB.equals("null"), "Arch is not null.");
	  Assert.assertTrue(MySQL.Arch_ID_DB == Integer.parseInt(Task_ID), "Archive Task ID does not match in database.");
	  
	  System.out.println("Finish.");
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
