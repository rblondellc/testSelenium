package cl.evaluacion.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.relevantcodes.extentreports.ExtentTest;

import cl.evaluacion.qa.helpers.PageWeb;

public class PaginaPrincipalPage extends PageWeb {

	// Atributos
    private By linkInicio;


	// Constructor
	public PaginaPrincipalPage(WebDriver driver, ExtentTest test, Boolean TAKE_SS, int seconds) {
		super(driver, test, TAKE_SS, seconds);
    
    this.linkInicio = By.xpath("//*[@id=\"flash\"]");
		
	}
	// Metodo para dar click al boton Iniciar sesion en la pagina principal de trello
	
    public void clickLogin () 
    {
      wait.until(ExpectedConditions.visibilityOf(driver.findElement(linkInicio))).click();
      	
    }
	
	
}
