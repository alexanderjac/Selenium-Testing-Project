package com.group1.selenium;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class App {

	public static void main(String[] args) throws InterruptedException, IOException {

	}

	// Northeastern Credentials Login
	public static void northeasternLogin(WebDriver driver, Properties prop) throws InterruptedException {
		driver.get(prop.getProperty("MYNEU_URL"));
		Utils.takeScreenShot(driver, "myneu_main_page");
		driver.findElement(By.linkText("Go To Login")).click();
		// Retrive UserName & Password
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement username = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
		username.sendKeys(prop.getProperty("NEU_USERNAME"));
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys(prop.getProperty("NEU_PASSWORD"));

		Utils.takeScreenShot(driver, "LoginCreds");
		driver.findElement(By.xpath("/html/body/section/div/div[1]/div/form/div[3]/button")).click();

		// code to switch to iframe for duo two factor notification
		Thread.sleep(3000);

		driver.switchTo().frame("duo_iframe");
		wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
		Utils.takeScreenShot(driver, "login_duo");
		driver.findElement(By.xpath(" //*[@id=\"auth_methods\"]/fieldset/div[1]/button")).click();
		wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
		driver.switchTo().defaultContent();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		// test.log(Status.INFO, "Expected: Login with 2FA, Actual: Logged in with
		// 2FA");
		Thread.sleep(15000);

	}

	// Scenario 1: Add to favorites
	public static void AddToFavorites(WebDriver driver, Properties prop) throws InterruptedException {
		// Student hub: Resources
		driver.get(prop.getProperty("STUDENT_HUB_URL"));
		// Wait driver
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3000));

		// login
		Utils.takeScreenShot(driver, "TS1_1-Before entering email");
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("i0116")));
		element.sendKeys(prop.getProperty("NEU_EMAIL"));
		Utils.takeScreenShot(driver, "TS1_2-After entering email");
		// wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));// .implicitlyWait(100000, TimeUnit.SECONDS);
		// click next
		WebElement nextButton = driver.findElement(By.id("idSIButton9"));
		nextButton.click();
		// Click on active directory
		WebElement activeDirectory = driver.findElement(By.className("idpDescription"));
		activeDirectory.click();
		// Username + Password
		Utils.takeScreenShot(driver, "TS1_3-Before entering username password");
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys(prop.getProperty("NEU_USERNAME"));
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys(prop.getProperty("NEU_PASSWORD"));
		Utils.takeScreenShot(driver, "TS1_4-After entering username password");

		// click login button
		WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
		loginButton.click();
		// do you want to stay signed in ? click yes button
		Utils.takeScreenShot(driver, "TS1_5-Before clicking yes");
		WebElement yesButton = driver.findElement(By.id("idSIButton9"));
		Utils.takeScreenShot(driver, "TS1_6-After clicking yes");
		yesButton.click();
		// Scroll down
		WebElement middleLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//*[@id=\"7b3083e7-1956-4f64-968b-920d938ba636\"]/div/div/div/div/div[2]/div[1]/div/div[7]/div/p")));
		middleLink.click();

		// See All favourites
		WebElement myLinks = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//*[@id=\"7b3083e7-1956-4f64-968b-920d938ba636\"]/div/div/div/div/div[2]/div[1]/div/div[1]/div/p")));
		myLinks.click();
		// wait
		// take screenshot
//        Utils.takeScreenshot(driver,"Scenario1-Before adding fav");
		Utils.takeScreenShot(driver, "TS1_7-Before adding fav");
		driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));

		// go to any link
		WebElement academicLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//*[@id=\"7b3083e7-1956-4f64-968b-920d938ba636\"]/div/div/div/div/div[2]/div[1]/div/div[2]/div/p")));
		academicLink.click();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Thread.sleep(3000);

		// click the star
		WebElement fav = driver.findElement(By.xpath(
				"/html/body/div[1]/div[2]/div[1]/div/div[3]/section/article/div[1]/div/div/div[2]/div[1]/div/div/div/div/div/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/div/div[1]/div/i"));
		Actions actions = new Actions(driver);
		actions.moveToElement(fav);
		actions.click().build().perform();
//      
		Thread.sleep(5000);
		// go to middle of the page
		middleLink.click();
		// click on my links
		// see favourite added
		myLinks.click();

		// take screenshot
		Utils.takeScreenShot(driver, "TS1_8-After adding fav");
		Thread.sleep(7000);
	}

	// Scenario 2: Remove from Favorites
	public static void RemoveFromFavorite(WebDriver driver, Properties prop) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
		Thread.sleep(15000);

		// Launch StudentHub
		driver.get(prop.getProperty("STUDENT_HUB_URL"));
		Utils.takeScreenShot(driver, "TS2_1");

		// Enter microsoft login credentials
		WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.id("i0116")));
		email.sendKeys(prop.getProperty("NEU_EMAIL"));
		Utils.takeScreenShot(driver, "TS2_2");
		driver.findElement(By.id("idSIButton9")).click();

		driver.findElement(By.cssSelector("span[class='largeTextNoWrap indentNonCollapsible']")).click();
		Thread.sleep(5000);
		Utils.takeScreenShot(driver, "TS2_3");
		driver.findElement(By.id("idSIButton9")).click();

		Thread.sleep(10000);
		Utils.takeScreenShot(driver, "TS2_4");
		// Click MyFavorite Icon
		driver.findElement(By.xpath(
				"//div[@id='7b3083e7-1956-4f64-968b-920d938ba636']/div/div/div/div/div[2]/div[2]/div/h1/button/span/i"))
				.click();
		Utils.takeScreenShot(driver, "TS2_5");
		Thread.sleep(3000);
		// Hover over Cancel button and click cancel
		WebElement cancelButton = driver.findElement(By.xpath("//div[2]/div/div/div/i"));
		Actions actions = new Actions(driver);
		actions.moveToElement(cancelButton);
		actions.click().build().perform();

		Thread.sleep(5000);
		Utils.takeScreenShot(driver, "TS2_6");
		// Remove button
		driver.findElement(By.xpath("//button[2]/span/span/span")).click();
		Thread.sleep(5000);
		Utils.takeScreenShot(driver, "TS2_7");
		// Cancel window
		driver.findElement(By.xpath("//div[2]/div/button/span/i")).click();
		Thread.sleep(5000);

	}

	// Scenario 3: Browse Classes for Full Summer Semester
	public static void browseClasses(WebDriver driver, Properties prop) throws InterruptedException {
		driver.get(prop.getProperty("MYNEU_URL"));
		driver.manage().window().maximize();
		driver.findElement(By.linkText("Go To Login")).click();
//		WebDriverWait wait = new WebDriverWait(driver, 100);
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
		WebElement username = driver.findElement(By.id("username"));
		username.clear();
		username.sendKeys(prop.getProperty("NEU_USERNAME"));
		WebElement password = driver.findElement(By.id("password"));
		password.clear();
		password.sendKeys(prop.getProperty("NEU_PASSWORD"));
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);

		WebElement Loginbtn = driver.findElement(By.name("_eventId_proceed"));
		Loginbtn.click();
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);

		driver.get(prop.getProperty("STUDENT_HUB_URL"));
		// Wait driver
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3000));

		// login
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("i0116")));
		element.sendKeys(prop.getProperty("NEU_EMAIL"));
		// wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));// .implicitlyWait(100000, TimeUnit.SECONDS);

		WebElement nextButton = driver.findElement(By.id("idSIButton9"));
		nextButton.click();

		WebElement activeDirectory = driver.findElement(By.className("idpDescription"));
		activeDirectory.click();

		WebElement usernameS = driver.findElement(By.id("username"));
		usernameS.sendKeys(prop.getProperty("NEU_USERNAME"));
		WebElement passwordS = driver.findElement(By.id("password"));
		passwordS.sendKeys(prop.getProperty("NEU_PASSWORD"));

		WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
		loginButton.click();
		WebElement yesButton = driver.findElement(By.id("idSIButton9"));
		yesButton.click();

		WebElement middleLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//*[@id=\"7b3083e7-1956-4f64-968b-920d938ba636\"]/div/div/div/div/div[2]/div[1]/div/div[7]/div/p")));
		middleLink.click();

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElement(By.xpath(
				"//*[@id=\"7b3083e7-1956-4f64-968b-920d938ba636\"]/div/div/div/div/div[2]/div[1]/div/div[2]/div/p"))
				.click();
		driver.findElement(By.xpath(
				"//*[@id=\"7b3083e7-1956-4f64-968b-920d938ba636\"]/div/div/div/div/div[2]/div[2]/div/div[10]/div/div/a"))
				.click();

		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);

		String window = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			window = handle;
		}
		driver.switchTo().window(window);
		Thread.sleep(2000);

		driver.findElement(By.id("classSearch")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id=\"s2id_txt_term\"]/a/span[2]/b")).click();

		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[@id='202250']")).click();
		Thread.sleep(8000);
		driver.findElement(By.xpath("//*[@id=\"term-go\"]")).click();
		Thread.sleep(8000);
		driver.findElement(By.xpath("//*[@id=\"txt_courseNumber\"]")).sendKeys("6105");
		Thread.sleep(10000);

		driver.findElement(By.xpath("//*[@id=\"search-go\"]")).click();
		Thread.sleep(8000);
		// *[@id="search-go"]

	}

	// Scenario4: Add items to cart
	public static void AddItemToCart(WebDriver driver, Properties prop) throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Getting the bookstore URL

		driver.get("https://northeastern.bncollege.com/");

		Utils.takeScreenShot(driver, "Open url s4");

		String actualTitle = driver.getTitle();
		String expectedTitle = "Apparel, Gifts & Textbooks | Northeastern University Official Bookstore";

		// Login button
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3000));
		WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("bnedLoginButton")));
		loginBtn.click();
		;

		driver.switchTo().frame(driver.findElement(By.id("loginHeaderIframe1")));

		Utils.takeScreenShot(driver, "Before Login s4");

		// Adding the credentials

		driver.findElement(By.id("email")).sendKeys(prop.getProperty("NEU_EMAIL"));
		driver.findElement(By.id("password")).sendKeys(prop.getProperty("NEU_PASSWORD") + Keys.ENTER);

		Utils.takeScreenShot(driver, "After Login s4");
		// Search for Items

		Utils.takeScreenShot(driver, "Before Search");
		driver.findElement(By.id("bned_site_search")).sendKeys("Monitor" + Keys.ENTER);

		Utils.takeScreenShot(driver, "After Search s4");
		Thread.sleep(3000);

		WebElement parent = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='product__listing product__grid']")));

		Utils.takeScreenShot(driver, "After Search 4");

		// Searching the 1st item listed in the grid

		parent.findElement(By.cssSelector("div:nth-child(1)")).click();

		// Scroll functionality

		js.executeScript("javascript:window.scrollBy(300,300)");

		Thread.sleep(4000);

		Utils.takeScreenShot(driver, "After Item click s4");

		// Clicking add to cart
		WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(By.id("addToCartButton")));
		addToCart.click();

		Thread.sleep(3000);

		// Clicking on the cart icon
		WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(By.className("svgIconFill")));
		cartIcon.click();

		Utils.takeScreenShot(driver, "Cart s4");

		Thread.sleep(10000);

		// closing the window

		driver.close();
	}

	public static void createCoursePlan(WebDriver driver, Properties prop) throws InterruptedException {

		driver.get(prop.getProperty("MYNEU_URL"));
		System.out.println(driver.getTitle());
		driver.manage().window().maximize();

		WebElement LoginButton = driver.findElement(By.className("login-box"));
		LoginButton.click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3000));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
		Utils.takeScreenShot(driver, "SS_Name_MyNEUPortalLogin");
		// Login to NEU Account
		driver.findElement(By.id("username")).sendKeys(prop.getProperty("NEU_USERNAME"));
		driver.findElement(By.id("password")).sendKeys(prop.getProperty("NEU_PASSWORD"));

		WebElement Loginbtn = driver.findElement(By.name("_eventId_proceed"));
		Loginbtn.click();

		String oldTab = driver.getWindowHandle();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		// Launch StudentHUB
		WebElement LaunchHub = driver.findElement(By.xpath("//*[@id=\"alert-hub\"]/div/a"));
		LaunchHub.click();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);

		Set<String> s = driver.getWindowHandles();
		String child_window = null;

		//Open New Tab
		// Now iterate using Iterator
		Iterator<String> I1 = s.iterator();

		while (I1.hasNext()) {

			child_window = I1.next();

			if (!oldTab.equals(child_window)) {
				driver.switchTo().window(child_window);

			}
		}

		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		driver.findElement(By.id("i0116")).sendKeys(prop.getProperty("NEU_EMAIL"));
		driver.findElement(By.id("idSIButton9")).click();

		driver.findElement(By.xpath("//*[@id=\"bySelection\"]/div[2]/div/span")).click();

		driver.findElement(By.id("idSIButton9")).click();
		Utils.takeScreenShot(driver, "SS_Name_services&links_screenshot");
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		
		//Navigate to Academics, Classes and Registrations
		driver.findElement(By.xpath(
				"//*[@id=\"7b3083e7-1956-4f64-968b-920d938ba636\"]/div/div/div/div/div[2]/div[1]/div/div[2]/div/p"))
				.click();
		//Navigate to Course Registrations
		driver.findElement(By.xpath(
				"//*[@id=\"7b3083e7-1956-4f64-968b-920d938ba636\"]/div/div/div/div/div[2]/div[2]/div/div[10]/div/div/a"))
				.click();
		Utils.takeScreenShot(driver, "SS_Name_services&links_screenshot");

		Set<String> s1 = driver.getWindowHandles();

		// Now iterate using Iterator
		Iterator<String> I2 = s1.iterator();

		while (I2.hasNext()) {

			String child_window2 = I2.next();

			if ((!oldTab.equals(child_window2)) && (!child_window.equals(child_window2))) {
				driver.switchTo().window(child_window2);

			}
		}
		//Creating a plan 
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		// WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3000));
		// WebElement selectPlan=
		// wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[.//span[contains(@class,'title')][text()='Plan
		// Ahead']]")));
		// selectPlan.click();
		// Thread.sleep(3000);
		
		Utils.takeScreenShot(driver, "SS_Name_BeforeCourseRegistration");
		driver.findElement(By.xpath("//a[@id='planningLink']//span[contains(text(),'Plan Ahead')]")).click();
		Utils.takeScreenShot(driver, "SS_Name_BeforeTermSelection");
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		//Select Course Term
		driver.findElement(By.className("select2-arrow")).click();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		//Summer 2
		driver.findElement(By.id("202260")).click();
		Utils.takeScreenShot(driver, "SS_Name_AfterTermSelection");
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		driver.findElement(By.id("term-go")).click();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		driver.findElement(By.id("createPlan")).click();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		Utils.takeScreenShot(driver, "SS_Name_BeforeCourseNum");
		Thread.sleep(10000);
		//Select COurse Number
		driver.findElement(By.name("txt_courseNumber")).sendKeys("7250");
		Utils.takeScreenShot(driver, "SS_Name_AfterCourseNum");
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		Thread.sleep(8000);
		//Add Course
		driver.findElement(By.id("search-go")).click();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);

		// add course
		Utils.takeScreenShot(driver, "SS_Name_BeforeAddcourse");
		Thread.sleep(8000);
		driver.findElement(By.xpath("//*[@id=\"table1\"]/tbody/tr[4]/td[6]/div/button[2]")).click();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		// save plan button
		Utils.takeScreenShot(driver, "SS_Name_AfterAddcourse");
		Thread.sleep(8000);
		driver.findElement(By.xpath("//*[@id=\"saveButton\"]")).click();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		Thread.sleep(8000);
		Utils.takeScreenShot(driver, "SS_Name_BeforeSavePlan");
		// Entering Plan Name
		driver.findElement(By.id("txt_planDesc")).sendKeys("Plan");
		Utils.takeScreenShot(driver, "SS_Name_AfterSavePlan");
		Thread.sleep(8000);
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		//Save Plan
		driver.findElement(By.xpath("/html/body/div[24]/div[3]/div/button[2]")).click();
		Thread.sleep(5000);

	}

}
