package testcases;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Day3_MakeMyTrip {
	@Test
	public void hotelBoooking() throws InterruptedException
	{
		//LocalDate fetches the current date in 2020-04-16 format. getting it and fetching the month part using substring
		String strCurrentMonth = LocalDate.now().toString().substring(5, 7);
		//adding 1 to the current month value. This is used to select always the date from next month
		int monthInt = Integer.parseInt(strCurrentMonth)+1;
		//Month.of(monthInt) will give the Month name as a result. Ex: if month =4, then Month.of(4) will give April
		String monthName = Month.of(monthInt).toString();
		//Keeping the first leter of the month in upperCase and rest letters in lower case (so as to match with the 
		//datepicker in the application
		monthName = monthName.substring(0, 1)+monthName.substring(1, monthName.length()).toLowerCase();
		
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		//ChromeDriver driver = new ChromeDriver();
		System.out.println("Browser Launched");
		driver.manage().window().maximize();
		System.out.println("Browser Maximized");

		//1) Go to https://www.makemytrip.com/
		driver.get("https://www.makemytrip.com/");
		System.out.println("URL Loaded");
		
		// Clicking Hotels
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//a[@class='makeFlex hrtlCenter column']//span[text()='Hotels']")));
		driver.findElementByXPath("//a[@class='makeFlex hrtlCenter column']//span[text()='Hotels']").click();
		System.out.println("Hotels Clicked");
		
		//Entering the city name as Goa
		Actions action = new Actions(driver);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//div[contains(@class,'hsw_inputBox selectHtlCity')]")));
		action.moveToElement(driver.findElementByXPath("//div[contains(@class,'hsw_inputBox selectHtlCity')]")).click().build().perform();
		
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable((driver.findElementByXPath("//input[contains(@placeholder,'Enter city')]"))));
		action.moveToElement(driver.findElementByXPath("//input[contains(@placeholder,'Enter city')]")).click().build().perform();
		
		//driver.findElementByXPath("//div[@role='combobox']//input").click();
		driver.findElementByXPath("//input[contains(@placeholder,'Enter city')]").sendKeys("Goa",Keys.TAB);
		System.out.println("City Name entered as Goa");
		
		//Clicking the 15th day of next month. this will always select the next month 15th day
		driver.findElementByXPath("//div[text()='"+monthName+"']/ancestor::div[@class='DayPicker-Month']//div[text()='15']").click();
		System.out.println("From date selected");
		
		//to get the selected date so as to calcuate 5 days from the selected From Date
		int intFromDate = Integer.parseInt(driver.findElementByXPath("//div[text()='"+monthName+"']/ancestor::div[@class='DayPicker-Month']//div[text()='15']").getText());
		
		//adding 5 days to the From Date
		int toDateint = intFromDate+5;
		
		//converting the date to a string
		String toDateString = Integer.toString(toDateint);
		
		//Selecting the to Date - which is 5 days next to the From Date
		driver.findElementByXPath("//div[text()='"+monthName+"']/ancestor::div[@class='DayPicker-Month']//div[text()='"+toDateString+"']").click();
		System.out.println("To Date selected");
		driver.findElementById("guest").click();
		
		//hardcoded xpath just for this requirement as the requirement says to select 2 adults and 1 child
		driver.findElementByXPath("(//li[text()='2'])[1]").click();
		driver.findElementByXPath("(//li[text()='1'])[2]").click();
		System.out.println("Guests Selected");
		driver.findElementByXPath("//button[text()='APPLY']").click();
		System.out.println("Applu Buttion Clicked");
		driver.findElementByXPath("//button[text()='Search']").click();
		System.out.println("Search Button clicked");
		
		//clicking the black screen that appears after search button click
		driver.findElementByXPath("//div[@class='mmBackdrop wholeBlack']").click();
		
		//Selecting city as Baga
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//input[@id='mmLocality_checkbox_35']/following-sibling::label")));
		driver.findElementByXPath("//input[@id='mmLocality_checkbox_35']/following-sibling::label").click();
		System.out.println("Selected Baga");
		
		//Selecting 5 star category
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//ul[@class='filterList']//label[text()='5 Star']")));
		driver.findElementByXPath("//ul[@class='filterList']//label[text()='5 Star']").click();
		System.out.println("Selected 5 Star category");
		
		//clicking the first hotel displayed
		Thread.sleep(3000);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//div[@class='imgCont']")));
		List<WebElement> listOfHotels = driver.findElementsByXPath("//div[@class='imgCont']");
		listOfHotels.get(0).click();
		System.out.println("First Hotel in the search result clicked");

		//handling windows
		Set<String> windowHandlesSet = driver.getWindowHandles();
		List<String> windowHandlesList = new ArrayList<String>(windowHandlesSet);
		driver.switchTo().window(windowHandlesList.get(1));
		System.out.println("Switched to new Window");
		System.out.println("Hotel Name: "+driver.findElementById("detpg_hotel_name").getText());
		driver.findElementByXPath("//span[text()='MORE OPTIONS']").click();
		Thread.sleep(3000);
		driver.findElementByXPath("(//span[text()='SELECT'])[1]").click();
		System.out.println("3 months plan selected");
		driver.findElementByXPath("//span[@class='close']").click();
		driver.findElementByXPath("//a[text()='BOOK THIS NOW']").click();
		System.out.println("Book now button clicked");
		
		//printing the amount
		Thread.sleep(2000);
		System.out.println("Total Payable Amount: "+ driver.findElementById("revpg_total_payable_amt").getText());
		driver.quit();
	}
}
