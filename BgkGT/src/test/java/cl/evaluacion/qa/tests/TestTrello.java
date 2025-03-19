package cl.evaluacion.qa.tests;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import cl.evaluacion.qa.helpers.Helper;
import cl.evaluacion.qa.pages.LoginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


@SuppressWarnings("unused")
public class TestTrello {
	private WebDriver driver;
	private ExtentReports extent;
	private ExtentTest test;
	private static String SUBDIR = "Tests//";
	private static Boolean TAKE_SS = true;
	private static int WAITING = 10;

	@BeforeSuite
	public void configExtentReports() {
		// ExtentReports config
		this.extent = new ExtentReports("ExtentReports/PruebaCurso.html", true);
		this.extent.addSystemInfo("Host Name", "Framework Selenium");
		this.extent.addSystemInfo("Enviroment", "Automation Testing");
		this.extent.addSystemInfo("User Name", "Rakel Blondell");
        //llamada a objeto de configuracion de Extent report
		File conf = new File("src/test/resources/extentReports/" + "extent-config.xml");
		this.extent.loadConfig(conf);
	}

	@BeforeMethod
	@Parameters({ "URL_PRINCIPAL" })
	public void configSelenium(String URL_PRINCIPAL) {
		// Selenium config
		//String projectPath = System.getProperty("user.dir");
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("TestWeb", "QA");
		System.setProperty("webdriver.chrome.driver", "DRIVERS/chromedriver");
		Helper.robotMoveMouse(30000, 30000);
		
		driver = new ChromeDriver();
		//Implicit Waits No usar si se estan usando explicit wait
		driver.manage().timeouts().implicitlyWait(WAITING, TimeUnit.SECONDS);
		//Maximizar Ventana
		driver.manage().window().maximize();
		//Navegar a url principal
		driver.navigate().to(URL_PRINCIPAL);
	}

	/*
	 * Caso de Prueba 01: Inicio de sesion 
	*/
	
	@Test
	public void inicioSesion() {
		String subDir = SUBDIR + Thread.currentThread().getStackTrace()[1].getMethodName();
		test = extent.startTest("Iniciar Sesion", "Sesion Iniciando");
		test.log(LogStatus.INFO, "Usuario Incorrecto");
		LoginPage login = new LoginPage(driver, test, TAKE_SS, 2);
		login.inicioSesion(subDir);		
		login.assertIngreso("Your username is invalid!");
	}

	
	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, "Test failed.- <br>" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Test skipped.- <br>" + result.getThrowable());
		} else {
			test.log(LogStatus.PASS, "Test passed.-");
		}
		driver.close();
		extent.endTest(test);
	}

	@AfterSuite
	public void closeExtentReports() {
		// Escribimos los datos al reporte.
		extent.flush();
		driver.quit();
	}
	
}
