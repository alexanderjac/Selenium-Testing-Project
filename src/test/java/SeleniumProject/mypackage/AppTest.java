package SeleniumProject.mypackage;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.group1.selenium.App;




/**
 * Unit test for simple App.
 */
public class AppTest 
{
	Properties prop = new Properties();
	WebDriver driver = null;
	static Logger logger = Logger.getLogger(AppTest.class.getName());
	ExtentSparkReporter reporter;
	ExtentReports reports;
	ExtentTest test;
	
	@BeforeClass
	public void beforeClass() {
		try (InputStream input = new FileInputStream("./config.properties")) {
			//Set properties
			prop.load(input);

			reporter = new ExtentSparkReporter("./test-output-extent/selenium_test_report.html");
			reports = new ExtentReports();
			reports.attachReporter(reporter);

			//reporter.config().setChartVisibilityOnOpen(true);
			reporter.config().setDocumentTitle("Selenium Test Report - Group 1");
			reporter.config().setReportName("Test Report");
			//reporter.config().setTestViewChartLocation(ChartLocation.TOP);
			reporter.config().setTheme(Theme.STANDARD);
			reporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
	
	@Test(priority = 0)
	public void scenario1Test() {
		test = reports.createTest("Scenario 1: Add to Favorite");

		try {
			// Login
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			App.AddToFavorites(driver, prop);
			
			test.pass("Scenario 1 : PASSED");
		} catch (Exception e) {
			// print stacktrace
			AssertJUnit.fail("Scenario 1 : FAILED");
			e.printStackTrace();
		}

	}
	
	@Test(priority = 1)
	public void scenario2Test() {
		test = reports.createTest("Scenario 2: Remove from favorites");

		try {
			// Login
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			App.northeasternLogin(driver, prop);
			App.RemoveFromFavorite(driver, prop);

			test.pass("Scenario 2 : PASSED");
		} catch (Exception e) {
			// print stacktrace
			AssertJUnit.fail("Scenario 2 : FAILED");
			e.printStackTrace();
		}

	}
	
	@Test(priority = 2)
	public void scenario3Test() {
		test = reports.createTest("Scenario 3: Browse Full Summer Classes");

		try {
			// Login
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			App.browseClasses(driver, prop);
			
			test.pass("Scenario 3 : PASSED");
		} catch (Exception e) {
			// print stacktrace
			AssertJUnit.fail("Scenario 3 : FAILED");
			e.printStackTrace();
		}

	}
	
	@Test(priority = 3)
	public void scenario4Test() {
		test = reports.createTest("Scenario 4: Add Item To Cart");

		try {
			// Login
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			App.AddItemToCart(driver, prop);
			
			test.pass("Scenario 4 : PASSED");
		} catch (Exception e) {
			// print stacktrace
			AssertJUnit.fail("Scenario 4 : FAILED");
			e.printStackTrace();
		}

	}
	
	@Test(priority = 4)
	public void scenario5Test() {
		test = reports.createTest("Scenario 5: Create Course Registration Plan");

		try {
			// Login
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			App.createCoursePlan(driver, prop);
			
			test.pass("Scenario 5 : PASSED");
		} catch (Exception e) {
			// print stacktrace
			AssertJUnit.fail("Scenario 5 : FAILED");
			e.printStackTrace();
		}

	}
	
	
	@BeforeTest(enabled = true)
	public void beforeTest() {
		logger.log(Level.INFO, "Before test: Instantiating driver");
		if (driver == null) {
			System.setProperty("webdriver.chrome.driver", "/Users/mrigesh/chromedriver");
			driver = new ChromeDriver();
			driver.manage().window().maximize();

		}
	}

	@AfterMethod(enabled = true)
	@AfterTest(enabled = true)
	public void afterTest() {
		if (driver != null) {
			driver.quit();
		}
		driver = null;
	}
	
	@AfterClass
	public void afterClass() {	
		reports.flush();
	}
}
