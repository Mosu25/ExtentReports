package amazon;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AttachScreenshot {


	ExtentReports extent;
	WebDriver driver;

	@BeforeSuite
	public void Setup() throws IOException {
		
		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("index.html");
		spark.loadXMLConfig(new File("extentconfig.xml"));
		extent.attachReporter(spark);

	}

	@AfterSuite
	public void tearDown() throws IOException {
		
		extent.flush();
		Desktop.getDesktop().browse(new File("index.html").toURI());
		driver.quit();
		
	}

	@Test
	public void attachScreenshotTest() throws IOException {
		
		ExtentTest test = extent.createTest("Verify Page Title").assignAuthor("Subash").assignDevice("Windows").assignCategory("Functional Testing").assignDevice("ChromeBrowser");
		extent.setSystemInfo("Environment", "QA");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		test.pass("Browser Opened");
		driver.get("https://www.amazon.in/");
		test.info("Capturing the page title");
		String pagetitle = driver.getTitle();
		
		if(pagetitle.equals("Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in")){
			test.pass("Page Title is Matched "+pagetitle, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64()).build());	
		}else {
			test.fail("Page Title is Matched "+pagetitle, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64()).build());
		}
		
//		test.assignAuthor("Mohan");
//		test.assignCategory("Smoke");
//		extent.setSystemInfo("Environment","QA");
		test.pass("Test Finished");
	}
	
	public String getBase64() {
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
	}
	
	
}
