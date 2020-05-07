package Testcases;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC17Azure {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		
//To disable notifications
			 ChromeOptions options= new ChromeOptions();
			 options.addArguments("--disable-notifications");
			 ChromeDriver driver=new ChromeDriver(options);
			 System.setProperty("webdriver.chrome.silentOutput", "true");

//			1) Go to https://azure.microsoft.com/en-in/   
			driver.get("https://azure.microsoft.com/en-in/");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
						
//			2) Click on Pricing  
			driver.findElementById("navigation-pricing").click();
			Thread.sleep(3000);
			
//			3) Click on Pricing Calculator  
			driver.findElementByLinkText("Pricing calculator").click();
			Thread.sleep(3000);
			
//			4) Click on Containers
			driver.findElementByXPath("//button[@value='containers']").click();
			
//			5) Select Container Instances  
			driver.findElementByXPath("(//span[text()='Container Instances'])[3]").click();
			
//			6) Click on Container Instance Added View 
			WebDriverWait wait= new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-module-loc"))).click(); 
			
//			7) Select Region as "South India"  
			WebElement region = driver.findElementByXPath("(//select[@name='region'])[1]");
			Select dropdown= new Select(region);
			dropdown.selectByVisibleText("South India");
			
//			8) Set the Duration as 180000 seconds 
			driver.findElementByXPath("(//input[@name='seconds'])[1]").sendKeys(Keys.BACK_SPACE,"80000");
			
//			9) Select the Memory as 4GB  
			WebElement memory =driver.findElementByXPath("(//select[@name='memory'])[1]");
			Select dropdown1= new Select(memory);
			dropdown1.selectByVisibleText("4 GB");
			
//			10) Enable SHOW DEV/TEST PRICING  
			driver.findElementByCssSelector("button#devtest-toggler").click();
			
//			11) Select Indian Rupee as currency  
			WebElement currency =driver.findElementByXPath("//select[@class='select currency-dropdown']");
			Select dropdown2= new Select(currency);
			dropdown2.selectByVisibleText("Indian Rupee (₹)");
			
//			12) Print the Estimated monthly price  
			String estimated = driver.findElementByXPath("(//span[@class='numeric'])[6]").getText();
			System.out.println("Estimated monthly price: "+ estimated);
			
//			13) Click on Export to download the estimate as excel  
			driver.findElementByXPath("//button[text()='Export']").click();
			
//			14) Verify the downloded file in the local folder
			File folder = new File("C:\\Users\\Vimalraj_k\\Downloads\\ExportedEstimate.xlsx");
			while (folder.exists()!=true) {
			Thread.sleep(1000);}			
			System.out.println("Container Instance estimation file downladed");			
			
//			15) Navigate to Example Scenarios and Select CI/CD for Containers  
			Actions builder = new Actions(driver);
			builder.moveToElement(driver.findElementByXPath("//li[@id='solution-architectures-picker']")).click().perform();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='CI/CD for Containers']"))).click();

//			16) Click Add to Estimate  
			WebElement addToEstimate = driver.findElementByXPath("//button[text()='Add to estimate']");
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", addToEstimate);
			Thread.sleep(2000); 
			
//			17) Change the Currency as Indian Rupee 
			WebElement indian =driver.findElementByXPath("//select[@class='select currency-dropdown']");
			Select dropdown3= new Select(indian);
			dropdown3.selectByVisibleText("Indian Rupee (₹)");

//			18) Enable SHOW DEV/TEST PRICING
			driver.findElementByXPath("(//span[@class='toggler-label'])[1]").click();
			Thread.sleep(3000);
			
//			19) Export the Estimate  
			String estimate1 = driver.findElementByXPath("(//span[@class='numeric']//span)[2]").getText();
			System.out.println("Estimate: "+ estimate1);
			
//			Click on Export
			driver.findElementByXPath("//button[text()='Export']").click();
			Thread.sleep(3000);
						
//			20) Verify the downloded file in the local folder  
			File folder1 = new File("C:\\Users\\Vimalraj_k\\Downloads\\ExportedEstimate (1).xlsx");
			while (folder1.exists()!=true) {
			Thread.sleep(1000);}			
			System.out.println("CI/CD estimation file downladed");	
			
			//close browser
			driver.quit();

	}

}
