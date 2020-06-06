package com.selenium.steps;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefs {


WebDriver driver;

	@Given("^I have open the browser$")
	public void I_have_open_the_browser() throws Throwable {	
		//System.setProperty("webdriver.chrome.driver", "A:\\Selenium\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "A:\\Selenium\\geckodriver.exe");
		driver=new FirefoxDriver();
		driver.manage().window().maximize();}

	@When("^I open Facebook website$")
	public void I_open_Facebook_website() throws Throwable {
		driver.get("http://localhost:8061/FestivalPortalR2_Participant/");
	}

	@When("^I enter \"([^\"]*)\" and valid \"([^\"]*)\"$")
	public void I_enter_and_valid(String arg1, String arg2) throws Throwable {
		driver.findElement(By.id("email")).sendKeys(arg1);
		driver.findElement(By.id("pass")).sendKeys(arg2);
		driver.findElement(By.id("login")).click();
	}

	@Then("^wait for some minutes$")
	public void wait_for_some_minutes() throws Throwable {
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
	}

	@Then("^search user$")
	public void search_user() throws Throwable {
		driver.findElement(By.cssSelector("[name*='eventname']")).sendKeys("Rose Parade");
		driver.findElement(By.cssSelector("[value='Search']")).sendKeys(Keys.ENTER);
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		//driver.findElement(By.className("_1ii5 _2yez")).click();
		
	}

	@Then("^user should login successfully$")
	public void user_should_login_successfully() throws Throwable {
	    // Express the Regexp above with the code you wish you had
		driver.close();
	}	


}
