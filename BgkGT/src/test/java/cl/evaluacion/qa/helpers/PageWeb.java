package cl.evaluacion.qa.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;

public abstract class PageWeb {
	// atributos
	protected WebDriver driver;
	protected ExtentTest test;
	protected Boolean TAKE_SS;
	protected WebDriverWait wait;

	public PageWeb(WebDriver driver, ExtentTest test, Boolean TAKE_SS, int seconds) {
		this.driver = driver;
		this.test = test;
		this.TAKE_SS = TAKE_SS;
		wait = new WebDriverWait(driver, seconds);
	}

}
