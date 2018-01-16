package nordea.lv;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class CurrencyExchangeRates {
	/*
	 * getting currency items 
	 */
	public static List<WebElement> getCurrencyItems(WebDriver driver) {		
		WebElement currencyTable = driver.findElement(By.id("container")).findElement(By.cssSelector("div[id*='rates']")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));				
		return currencyTable.findElements(By.tagName("tr"));
	}	
	
	public static class CurrencyCalculator {
		//sell amount field
		public static WebElement iWishToSellField(WebDriver driver) {			
			return driver.findElement(By.id("sell_amount"));
		}
		
		//select currency from sell dropdown
		public static void selectCurencySell(WebDriver driver, String currency) {			
			WebElement dropdownTrigger = driver.findElements(By.cssSelector("div[class*='dropdown']")).get(0).findElement(By.cssSelector("div[class*='trigger']"));
			dropdownTrigger.click();
			//Select selectCurency = new Select(driver.findElement(By.cssSelector("div[class*='dropdown']")).findElement(By.tagName("ul")));
			List<WebElement> options = driver.findElements(By.cssSelector("div[class*='dropdown']")).get(0).findElement(By.tagName("ul")).findElements(By.tagName("li"));
			for (WebElement opt : options) {
	            if (opt.getText().equals(currency)) {
	                opt.click();
	                return;
	            }
	        }
		}
		
		//buy amount field
		public static WebElement iWishToBuy(WebDriver driver) {			
			return driver.findElement(By.id("buy_amount"));
		}
		
		//select currency from buy dropdown
		public static void selectCurencyBuy(WebDriver driver, String currency) {			
			WebElement dropdownTrigger = driver.findElements(By.cssSelector("div[class*='dropdown']")).get(1).findElement(By.cssSelector("div[class*='trigger']"));
			dropdownTrigger.click();
			//Select selectCurency = new Select(driver.findElement(By.cssSelector("div[class*='dropdown']")).findElement(By.tagName("ul")));
			List<WebElement> options = driver.findElements(By.cssSelector("div[class*='dropdown']")).get(1).findElement(By.tagName("ul")).findElements(By.tagName("li"));
			for (WebElement opt : options) {
	            if (opt.getText().equals(currency)) {
	                opt.click();
	                return;
	            }
	        }
		}
		
		//radio button: cash
		public static WebElement cashRadioButton(WebDriver driver) {			
			return driver.findElement(By.id("transaction_cash"));
		}
		
		//radio button: transfer
		public static WebElement transferRadioButton(WebDriver driver) {			
			return driver.findElement(By.id("transaction_transfer"));
		}
		
		//button: Calculate
		public static WebElement calculateButton(WebDriver driver) {			
			return driver.findElement(By.id("submit"));
		}
		
		//text label: rate result
		public static WebElement resultRate(WebDriver driver) {			
			return driver.findElement(By.cssSelector("div[class*='grid']")).findElements(By.cssSelector("div[class*='w l']")).get(2);
		}
		
		/*
		 * method for awaiting of frame loading
		 */
		public static void WaitTillFrameLoaded(WebDriver driver)
	    {
	        try
	        {
	            do
	            {
	            	Thread.sleep(1000);
	            }
	            while (!driver.findElement(By.id("submit")).isDisplayed());
	        }
	        catch (Exception e)
	        {

	            //throw;
	        }
	    }
	}
	
	/*
	 * method for awaiting of page loading 
	 */
	public static void WaitTillPageLoaded(WebDriver driver)
    {
        try
        {
            do
            {
            	Thread.sleep(1000);
            }
            while (!driver.findElement(By.cssSelector("div[class*='footerTop']")).isDisplayed());
        }
        catch (Exception e)
        {

            //throw;
        }
    }
}
