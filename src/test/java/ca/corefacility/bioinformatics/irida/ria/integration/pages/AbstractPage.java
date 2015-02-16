package ca.corefacility.bioinformatics.irida.ria.integration.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.corefacility.bioinformatics.irida.ria.integration.utilities.TestUtilities;

import com.google.common.base.Strings;

/**
 * Represents the common elements in a page within the application.
 *
 * @author Josh Adam
 */
public class AbstractPage {
	private static final Logger logger = LoggerFactory.getLogger(AbstractPage.class);
	private static final String APPLICATION_PORT = Strings.isNullOrEmpty(System.getProperty("jetty.port")) ? "8080"
			: System.getProperty("jetty.port");
	protected static final String BASE_URL = "http://localhost:" + APPLICATION_PORT + "/";
	private static final Long TIME_OUT_IN_SECONDS = 10L;

	@FindBy(className = "error")
	private WebElement errors;

	protected WebDriver driver;

	public AbstractPage(WebDriver driver) {
		setDriver(driver);
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public String getErrors() {
		return errors.getText();
	}

	protected static void get(WebDriver driver, String relativeUrl) {
		String url = BASE_URL + relativeUrl;
		driver.get(url);
	}

	public void logout(WebDriver driver) {
		driver.get(BASE_URL + "logout");
	}

	public WebElement waitForElementToBeClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(this.driver, TIME_OUT_IN_SECONDS);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void clickElement(WebElement element) {
		boolean failed = true;
		int count = 10;
		while (failed && count > 0) {
			count--;
			try {
				element.click();
				failed = false;
			} catch (Exception e) {
				if (count == 0) {
					throw e;
				}
				try {
					Thread.sleep(250);
				} catch (InterruptedException e1) {
					logger.error("Cannot sleep thread.");
				}
			}
		}
	}

	public WebElement waitForElementVisible(By locator) {
		WebDriverWait wait = new WebDriverWait(this.driver, TIME_OUT_IN_SECONDS);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitForElementInvisible(By locator) {
		(new WebDriverWait(this.driver, TIME_OUT_IN_SECONDS)).until(ExpectedConditions
				.invisibilityOfElementLocated(locator));
	}

	public static void waitForTime(int length) {
		try {
			Thread.sleep(length);
		} catch (InterruptedException e) {
			logger.error("Cannot sleep the thread.");
		}
	}

	public WebElement openSelect2List(WebDriver driver) {
		driver.findElement(By.className("select2-choice")).click();
		waitForElementVisible(By.className("select2-results"));
		return driver.findElement(By.cssSelector(".select2-input"));
	}

	public boolean isElementOnScreen(String id) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists = driver.findElements(By.id(id)).size() != 0;
		driver.manage().timeouts().implicitlyWait(TestUtilities.DRIVER_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
		return exists;
	}

	// Cart is available on all pages.
	public int getCartCount() {
		return Integer.parseInt(driver.findElement(By.id("cart-count")).getText());
	}

	public void showCart() {
		driver.findElement(By.id("cart-show-btn")).click();
	}
	
	public void clearCart(){
		driver.findElement(By.id("clear-cart-btn")).click();
	}

	public boolean isCartVisible() {
		return driver.findElements(By.className("cart-open")).size() > 0;
	}

	public int getCartProjectCount() {
		return driver.findElements(By.cssSelector("#cart-project-list > li")).size();
	}

	/**
	 * Get the current JETTY port
	 * 
	 * @return
	 */
	public String getApplicationPort() {
		return APPLICATION_PORT;
	}
}
