package cl.evaluacion.qa.helpers;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * <P>
 * Clase utilitaria con herramientas comunes a todas las paginas web.
 * <p>
 * Se presentan los siguientes utilitarios:
 * <ul>
 * <li>{@link #addEvidence(Boolean, WebDriver, ExtentTest, String, String, String)}</li>
 * <li>{@link #waitSeconds(int)}</li>
 * <li>{@link #obtenerPaginaExcel(String, String)}</li>
 * <li>{@link #obtenerCelda(XSSFSheet, int, String)}</li>
 * <li>{@link #robotMoveMouse(int, int)}</li>
 * </ul>
 * 
 */
public class Helper {

	private static String PATH_EVIDENCE = "ExtentReports//Evidence";
	private static String DIR_EVIDENCE = ".//Evidence//";

	/**
	 * <p>
	 * Agrega la evidencia de prueba al reporte a generar.
	 * 
	 * @param TAKE_SS    boolean que indica si se debe adjuntar la evidencia.
	 * @param driver     WebDriver del navegador utilizado.
	 * @param test       referencia al reporte de ExtentReports.
	 * @param imageTitle titulo/descripci�n de la imagen.
	 * @param subDir     Subdirectorio en el cual se guardara la imagen.
	 * @param imageName  nombre de la imagen sin extension.
	 * 
	 */
	public static void addEvidence(Boolean TAKE_SS, WebDriver driver, ExtentTest test, String imageTitle, String subDir,
			String imageName) {
		if (TAKE_SS) {
			Helper.takeScreenShot(driver, subDir, imageName);
			Helper.takeEvidence(test, imageTitle, subDir, imageName);
		}
	}

	/**
	 * <p>
	 * Detiene la ejecucion la cantidad de segundos indicados por parametro.
	 * <p>
	 * Utilitario para usarse en conjunto con los implicitWait.
	 *
	 * @param seconds cantidad de segundos a detener la ejecucion.
	 */
	public static void waitSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	//Realiza Scroll Abajo
	public static void scrollDown(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		// Scroll down by a specific number of pixels        
		jse.executeScript("window.scrollBy(0,10000)");         
		// Alternatively, to scroll to the bottom of the page:        
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	
	
	//Realza Scroll Arriba
	public static void scrollUp(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0, -10000)");
	}


	/**
	 * <p>
	 * Toma una impresion de pantalla y la guarda en un directorio donde quedan
	 * todas las evidencias de prueba
	 *
	 * @param driver    driver navegador.
	 * @param subDir    directorio de las imagenes.
	 * @param imageName nombre de la imagen sin extension.
	 */
	private static void takeScreenShot(WebDriver driver, String subDir, String imageName) {
		// Directorio donde quedaran las imagenes guardadas
		File directory = new File(PATH_EVIDENCE);
		// dd-MM-yyyy
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" dd-MM-yyyy"); 
		LocalDate date = LocalDate.now();
		
	
		//date.format(formatter) +
		try {
			if (directory.isDirectory()) {
				// Toma la captura de imagen
				File imagen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				// Mueve el archivo a la carga especificada con el respectivo nombre
				FileUtils.copyFile(imagen,
						new File(directory.getAbsolutePath() + "//" + subDir + "//" + imageName + date.format(formatter) + ".png"));
			} else {
				// Se lanza la excepcion cuando no encuentre el directorio
				throw new IOException("ERROR : La ruta especificada no es un directorio!");
			}
		} catch (IOException e) {
			// Impresion de Excepciones
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Adjunta una imagen como evidencia de prueba, la imagen ya ha sido capturada
	 * por:
	 * <ul>
	 * <li>{@link #takeScreenShot(WebDriver, String, String)}</li>
	 * </ul>
	 *
	 * @param test       referencia a la instancia de ExtentReports.
	 * @param imageTitle titulo de la imagen.
	 * @param subDir     subdirectorio de la imagen.
	 * @param imageName  nombre de la imagen sin extension.
	 */
	private static void takeEvidence(ExtentTest test, String imageTitle, String subDir, String imageName) {
		File directory = new File(PATH_EVIDENCE);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" dd-MM-yyyy"); 
		LocalDate date = LocalDate.now();
		
		try {
			if (directory.isDirectory()) {
				test.log(LogStatus.INFO,
						imageTitle + test.addScreenCapture(DIR_EVIDENCE + subDir + "//" + imageName + date.format(formatter) + ".png"));

			} else {
				// Se lanza la excepcion cuando no encuentre el directorio
				throw new IOException("ERROR : La ruta especificada no es un directorio!");
			}
		} catch (IOException e) {
			// Impresion de Excepciones
			e.printStackTrace();
		}
	}





	/**
	 * <p>
	 * Utilizamos un robot para mover el mouse a la posicion x,y de la pantalla.
	 *
	 * @param x posicion x de la pantalla.
	 * @param y posicion y de la pantalla.
	 */
	
	public static void robotMoveMouse(int x, int y) {
		// Implementamos un robot para mover el mouse a la posicion x,y.
		// Obtenemos un objeto Robot.
		Robot robotObj;
		try {
			robotObj = new Robot();
			robotObj.mouseMove(x, y);
		} catch (AWTException e) {
			// Error al seleccionar el archivo.
			e.printStackTrace();
		}
	}
}
