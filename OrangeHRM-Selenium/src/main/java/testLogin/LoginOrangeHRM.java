package testLogin;

import java.io.File;
//Import required packages
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//Webdriver setup - Acts as an interface between code and web browser
public class LoginOrangeHRM {
	WebDriver driver = new ChromeDriver();
	Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	// OrangeHRM Login Setup Test
	@BeforeMethod
	public void setup() {
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");//Open Chrome Browser
		driver.manage().window().maximize();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='username']")));
	}

	// OrangeHRM Login validation Test
	@Test
	public void test() {
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("Admin"); //Send valid username
		driver.findElement(By.name("password")).sendKeys("admin123");//send valid password
		driver.findElement(By.cssSelector("[type=submit]")).click();//click login button
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h6")));//wait until dashboard page is visible
			WebElement dashBoardHeader = driver.findElement(By.tagName("h6"));//Identify dashboard element
			String headerExpected = "Dashboard";//Expected Dashboard element value
			String headerActual = dashBoardHeader.getText();//Actual Dashboard element value
			//Validation of expected and actual 
			if (headerActual.contentEquals(headerExpected)) {
				System.out.println("OrangeHRM application Login successful and user able to Login !!");
				// Capture Screenshot code
				TakesScreenshot captureScreen = (TakesScreenshot) driver;
				File file = captureScreen.getScreenshotAs(OutputType.FILE);
				File path = new File(
						"C:\\Users\\eeear\\OneDrive\\Desktop\\Java\\OrangeHRM-Selenium\\screenshot\\Dashboard.png");
				FileUtils.copyFile(file, path);
			} else {
				// Dashboard page not exist error message
				Assert.fail("OrangeHRM application Dashboard page not available !!");
			}
		} catch (Exception e) {
			// invalid login screen error message
			Assert.fail("OrangeHRM application login not successful !! Invalid credentials!!");
		}
	}

	// OrangeHRM close Browser
	@AfterMethod
	public void closetest() {
		driver.quit();
	}

}
