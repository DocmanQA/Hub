package UAT;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import java.io.*;
import java.net.*;
import java.text.*;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class Maintenance {
	private WebDriver driver;
	private boolean Pattern1_ZIP = false;
	private boolean Pattern2_XML = false;
	private WebElement table;
	private List<WebElement> table_row;
	private List<WebElement> table_data;
	private int User_count = 0;
	private WebElement NextBtn;
	private boolean Is_Hide_Inactive;
	private String BackupTime;
	private String Filename;
	private String pattern1; 
	private String pattern2; 
	
	
	public static boolean exists(String URLname) {
		  try {
			  HttpURLConnection.setFollowRedirects(false);
			  HttpURLConnection con = (HttpURLConnection) new URL(URLname).openConnection();
			  con.setRequestMethod("POST");
			  con.connect();
			  BufferedReader in = new BufferedReader (new InputStreamReader(con.getInputStream()));
			  String line;
			  while ((line = in.readLine()) != null){
				  System.out.println(line);
			  }
			  
		
			  return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		  }
		  catch (Exception e) {
			  e.printStackTrace();
			  return false;
		  }
	  }
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
	  
	  Date now = new Date();
	  Calendar Time = Calendar.getInstance();
	  SimpleDateFormat ft_hour = new SimpleDateFormat ("HH");
	  SimpleDateFormat ft_min = new SimpleDateFormat ("mm");
	  long t = Time.getTimeInMillis();
	  Date BackupTimeinMillis = new Date(t + 60000);
	  
	  BackupTime = ft_hour.format(BackupTimeinMillis) + ":" + ft_min.format(BackupTimeinMillis);
	  System.out.println("Backup Time is " + BackupTime);
	  
	  SimpleDateFormat backupFileName = new SimpleDateFormat ("ddMMyyyy");
	  System.out.println(backupFileName.format(now));
	  //String backupFileName = "1111201511";
	  
	  pattern1 = "^_" + backupFileName.format(now).toString() + ".*\\.zip$";
	  System.out.println(pattern1);
	  pattern2 = "^BackupLog_" + backupFileName.format(now).toString() + ".*\\.xml$";
	  System.out.println(pattern2);
	  
	  //Set backup data
	  driver.findElement(By.id("BackupStartTimeDisplay")).clear(); 
	  driver.findElement(By.id("BackupStartTimeDisplay")).sendKeys(BackupTime);
	  driver.findElement(By.id("BackupFolderLocation")).clear();
	  driver.findElement(By.id("BackupFolderLocation")).sendKeys("C:\\Backup");
	  driver.findElement(By.id("BackupLogLocation")).clear();
	  driver.findElement(By.id("BackupLogLocation")).sendKeys("C:\\Backup");
	  //driver.findElement(By.cssSelector("input[type='button']")).click();
	  try {
		Thread.sleep(1000);
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  driver.findElement(By.cssSelector("button[class='btn btn-success']")).click();
	
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  System.out.println(driver.findElement(By.xpath("//*[contains(text(), 'successfully')]")).getText());
	  
	  try {
		Thread.sleep(240000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  //Check backup files from server
	  //System.out.println(exists("http://TO_Hub_900/welcome.png"));
	  //System.out.println(exists("http://TO_Hub_900/Backup/_10112015103910.zip"));
	  //System.out.println(exists("http://TO_Hub_900/Backup"));
	  /*
	  List<String> results = new ArrayList<String>();
	  
	  //File[] files = new File("C:\\Selenium Doc").listFiles();
	
	  File[] files;
	try {
		URL urlname = new URL("http://TO_Hub_900/Backup/_10112015103910.zip");
		int startIndex = urlname.toString().lastIndexOf('/');
		String filename = urlname.toString().substring(startIndex + 1);
		System.out.println(filename);
		files = new File(urlname.toURI()).listFiles();
		 for (File file : files) {
			  if (file.isFile()){
				  results.add(file.getName());
			  }
		  }
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	  
	  System.out.println(results);
	  
	  
	  File f = new File("\\\\192.168.1.174\\Shared");
	  //File f = new File("\\\\192.168.1.123\\Share");
	  File [] list = f.listFiles();
	  
	  for (File item : list){
		  System.out.println(item.getPath());
	  }
	  
	  
	  Document doc;
	try {
		doc = Jsoup.connect("http://TO_Hub_900/Backup").get();
		Elements links = doc.getAllElements(); //.getElementsByTag("a");
		for (Element link : links) {
			System.out.println(link.text());
		}
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	*/
	  
	  //Login to the server PC FTP site to check the backup file name
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
			  if (file.isDirectory()) {
				  details = "[" + details + "]";
			  }
			  
			  //System.out.println(backupFileName);
			  //System.out.println(details.substring(1, 11));
			  
			  //if (backupFileName.equals(details.substring(1, 11)) ){
				 // Filename = details;
				  //System.out.println(Filename);
			  //}
			  
			  //System.out.println(Pattern.compile(pattern1).matcher(details));
			  //System.out.println(Pattern.compile(pattern2).matcher(details));
			  
			  if (Pattern.compile(pattern1).matcher(details).matches() ){
				  System.out.println("It matches the .zip file.");
				  Pattern1_ZIP = true;
				  
			  }
			  
			  if (Pattern.compile(pattern2).matcher(details).matches() ){
				  System.out.println("It matches the .xml file.");
				  Pattern2_XML = true;
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
	  
	Assert.assertTrue(Pattern1_ZIP, "No .zip file match.");
	Assert.assertTrue(Pattern2_XML, "No .xml file match.");
	
	System.out.println("Finish");
	  
	  
	  

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
