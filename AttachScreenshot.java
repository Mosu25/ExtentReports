package amazon;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
		
		ExtentTest test = extent.createTest("First Test");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		test.pass("Browser Opened");
		driver.get("https://google.com");
		driver.findElement(By.name("q")).sendKeys("AbdulKalam",Keys.ENTER);
		test.pass("Value Entered", MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64()).build());
		test.pass("Test Finished");
	}
	
	public String getBase64() {
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
	}
	
	
}
