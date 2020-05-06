package Testcases;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC16Ajio {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		
//To disable notifications
			 ChromeOptions options= new ChromeOptions();
			 options.addArguments("--disable-notifications");
			 ChromeDriver driver=new ChromeDriver(options);
			 System.setProperty("webdriver.chrome.silentOutput", "true");

//			1) Go to to https://www.ajio.com/shop/women  
			driver.get("https://www.ajio.com/shop/women");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			
//			2) Enter Bags in the Search field and Select Bags in Women Handbags 
//			WebDriverWait wait= new WebDriverWait(driver, 10);
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='modal-content']//div)[1]"))).click();
			driver.findElementByXPath("//input[@placeholder='Search AJIO']").click();
			driver.findElementByXPath("//input[@placeholder='Search AJIO']").sendKeys("bags");
			driver.findElementByXPath("(//span[text()='Women Handbags'])[1]").click();
						
//			3) Click on five grid and Select SORT BY as "What's New"
			driver.findElementByClassName("five-grid").click();
			driver.findElementByTagName("select").click();
			driver.findElementByXPath("//option[contains(text(),\"What's New\")]").click();
			
//			4) Enter Price Range Min as 2000 and Max as 5000  
			driver.findElementByXPath("//span[text()='price']").click();
			driver.findElementById("minPrice").sendKeys("2000");
			driver.findElementById("maxPrice").sendKeys("5000");
			driver.findElementByXPath("(//button[@type='submit'])[2]").click();
			
//			5) Click on the product "Puma Ferrari LS Shoulder Bag"  
			Thread.sleep(3000);
			driver.findElementByXPath("//div[text()='Ferrari LS Shoulder Bag']").click();
			
//			6) Verify the Coupon code for the price above 2690 is applicable for your product, if applicable the get the Coupon Code and Calculate the discount price for the coupon 
			Set<String> windowHandles = driver.getWindowHandles();
			List<String> handles = new ArrayList<String>(windowHandles);
			driver.switchTo().window(handles.get(1));
			double productPrice = Double.parseDouble(driver.findElementByXPath("//div[@class='prod-sp']").getText().replaceAll("\\D", ""));
			if(productPrice > 2690) {
				System.out.println("Product eligible for discount");
			} else
				System.out.println("Product not eligible for discount");
			String offerCode = driver.findElementByXPath("//div[@class='promo-title']").getText().replaceAll("Use Code", "");
			System.out.println(offerCode);
			Thread.sleep(3000);
			
			double discountPrice = Double.parseDouble(driver.findElementByXPath("//div[@class='promo-discounted-price']/span").getText().replaceAll("\\D", ""));
			double discount = productPrice - discountPrice;
			long finalDiscount = Math.round(discount);
			System.out.println("Discount: Rs."+finalDiscount);
			
//			7) Check the availability of the product for pincode 560043, print the expected delivery date if it is available  
			driver.findElementByXPath("//span[text()='Enter pin-code to know estimated delivery date.']").click();
			driver.findElementByXPath("//span[text()='Enter Pincode']/following::input").click();
			driver.findElementByXPath("//span[text()='Enter Pincode']/following::input").sendKeys("641002");
			driver.findElementByXPath("//button[text()='CONFIRM PINCODE']").click();
			String expected = driver.findElementByXPath("//li[contains(text(),'Expected Delivery:')]").getText();
			System.out.println(expected);			
			
//			8) Click on Other Informations under Product Details and Print the Customer Care address, phone and email
			driver.findElementByXPath("//div[text()='Other information']").click();
			String address = driver.findElementByXPath("(//li[@class='detail-list mandatory-info'])[6]").getText();
			System.out.println("Customer Care address"+address);
			
//			9) Click on ADD TO BAG and then GO TO BAG
			driver.findElementByXPath("//div[@class='pdp-addtocart-button']//div[1]").click();
			Thread.sleep(3000);
			WebElement gotToBagEle = driver.findElementByXPath("//span[text()='GO TO BAG']");
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", gotToBagEle);
			Thread.sleep(2000);
			
//			10) Check the Order Total before apply coupon
			String total = driver.findElementByXPath("(//section[@id='orderTotal']/span)[2]").getText();
			total = total.substring(4);
			double orderTotal = Double.parseDouble(total.replaceAll(",", ""));
			System.out.println("Order Total before apply coupon"+orderTotal);
			
//			11) Enter Coupon Code and Click Apply
			driver.findElementByXPath("//input[@placeholder='Enter coupon code']").click();
			driver.findElementByXPath("//input[@placeholder='Enter coupon code']").sendKeys(offerCode);
			driver.findElementByXPath("//button[text()='Apply']").click();
			
//			12) Verify the Coupon Savings amount(round off if it in decimal) under Order Summary and the matches the amount calculated in Product details
			String discPrice = driver.findElementByXPath("(//span[@class='price-value discount-price'])[2]").getText();
			discPrice = discPrice.substring(4);
			double savingPrice = Double.parseDouble(discPrice.replaceAll(",", ""));
			long finalSavings = Math.round(savingPrice);
			System.out.println("Coupon Savings: Rs."+finalSavings);
			if (finalDiscount==finalSavings) {
				System.out.println("Coupon Savings amount matches the amount calculated in Product details");								
			} else
				System.out.println("Coupon Savings amount not matches the amount calculated in Product details");
						
//			13) Click on Delete and Delete the item from Bag
			driver.findElementByXPath("//div[text()='Delete']").click();
			driver.findElementByXPath("//div[text()='DELETE']").click(); 
			System.out.println("Delete the item from Bag");
			Thread.sleep(2000);
			
//			14) Close all the browsers
			driver.quit();

	}

}
