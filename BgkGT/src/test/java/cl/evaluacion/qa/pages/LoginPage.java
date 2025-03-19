package cl.evaluacion.qa.pages;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;

import cl.evaluacion.qa.helpers.Helper;
import cl.evaluacion.qa.helpers.PageWeb;

public class LoginPage extends PageWeb {

	//Inicio sesion
	private By username;
	private By clave;
	private By validarIngreso;
	private By imputLogin;

		
	// Constructor
	public LoginPage(WebDriver driver, ExtentTest test, Boolean TAKE_SS, int seconds) {
		super(driver, test, TAKE_SS, seconds);

		
		this.validarIngreso = By.id("flash");
		
		//Localizadores formulario login
		this.username = By.id("username");
		this.clave = By.id("password");
		this.imputLogin = By.xpath("//*[@id=\"login\"]/button");
		
	}
	
    
    public void inicioSesion(String subDir) {
    	Helper.waitSeconds(2);
		Helper.addEvidence(TAKE_SS, driver, test, "Formulario sin datos", subDir, "Inicio");
		driver.findElement(username).sendKeys("rblondell");
		driver.findElement(clave).sendKeys("123456");
		Helper.addEvidence(TAKE_SS, driver, test, "Formulario con datos", subDir, "Inicio con datos");
		driver.findElement(imputLogin).click();
		Helper.addEvidence(TAKE_SS, driver, test, "Mensaje de validacion", subDir, "Your username is invalid!");
    }
    
   
	
	public void assertIngreso(String titulo) {
		Assert.assertEquals(driver.findElement(validarIngreso).getText(), "Your username is invalid!");

	}
}
