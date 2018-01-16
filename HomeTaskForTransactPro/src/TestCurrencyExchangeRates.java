import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import nordea.lv.CurrencyExchangeRates;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

public class TestCurrencyExchangeRates {
	
	WebDriver driver;
	
	@Before
	public void setUp() {
		//path to chrome driver
		System.setProperty("webdriver.chrome.driver", "C:\\eclipse\\chromedriver.exe");
	}
	
	@After
	public void tearDown() {
		//close the browser
		driver.close();
	}
	
	@Test
	public void testSellRates1EuroForEachCurrencyInCash() throws Exception  {
		//open browser
		driver = new ChromeDriver();
		
		//maximize the window
		driver.manage().window().maximize();
		
		//got to Currency Exchange Rates
		driver.get("http://www.nordea.lv/Priv%C4%81tperson%C4%81m/Ikdienas%20pakalpojumi/Maks%C4%81jumi/Val%C5%ABtu%20kursi/1059212.html?lnkID=top-user-goal_exchange-rates_15-04-2013");
		
		//wait till page loading
		CurrencyExchangeRates.WaitTillPageLoaded(driver);
		
		//switch to first frame: table
		driver.switchTo().frame(0);
		
		List<CurrencyDetails> currencyDetails = new ArrayList<>();
		List<WebElement> ratesTableElements = CurrencyExchangeRates.getCurrencyItems(driver);		
		
		//get data from currency table
		for (int i = 0; i < ratesTableElements.size(); i++) {
			currencyDetails.add(new CurrencyDetails(ratesTableElements.get(i)));
		}
		
		//switch back to parent frame
		driver.switchTo().parentFrame();
		
		//switch to second frame: calculator
		driver.switchTo().frame(1);
		
		//check that client sells 1 euro for each currency and get value from column 'Client sells 1 EUR'
		//enter 1 euro to sell
		String euroAmount = "1";
		CurrencyExchangeRates.CurrencyCalculator.iWishToSellField(driver).sendKeys(euroAmount);
		
		//select EURO
		String currencySell = "EUR";
		CurrencyExchangeRates.CurrencyCalculator.selectCurencySell(driver, currencySell);
		
		//select Cash
		CurrencyExchangeRates.CurrencyCalculator.cashRadioButton(driver).click();
		
		for (int i = 0; i < currencyDetails.size(); i++) {
			if (!currencyDetails.get(i).cashRatesClientSells.equals("--")) {
				//select currency to buy
				CurrencyExchangeRates.CurrencyCalculator.selectCurencyBuy(driver, currencyDetails.get(i).code);
				
				CurrencyExchangeRates.CurrencyCalculator.calculateButton(driver).click();
				
				//wait till frame loaded
				CurrencyExchangeRates.CurrencyCalculator.WaitTillFrameLoaded(driver);
				
				assertTrue(CurrencyExchangeRates.CurrencyCalculator.resultRate(driver).getText(), CurrencyExchangeRates.CurrencyCalculator.resultRate(driver).getText().equals("Kurss: " + euroAmount + " " + currencySell + " = " + Double.parseDouble(currencyDetails.get(i).cashRatesClientSells) + " " + currencyDetails.get(i).code));				
			}
		}
	}
	
	class CurrencyDetails {
		String name;
		String code;
		String cashRatesClientSells;
		String cashRatesClientBuys;
		String transferRatesClientSells;
		String transferRatesClientBuys;
		
		public CurrencyDetails(WebElement webElement) {
			name = webElement.findElements(By.tagName("td")).get(0).getText();
			code = webElement.findElements(By.tagName("td")).get(1).getText();
			cashRatesClientSells = webElement.findElements(By.tagName("td")).get(2).getText();
			cashRatesClientBuys = webElement.findElements(By.tagName("td")).get(3).getText();
			transferRatesClientSells = webElement.findElements(By.tagName("td")).get(4).getText();
			transferRatesClientBuys = webElement.findElements(By.tagName("td")).get(5).getText();
		}
	}
}
