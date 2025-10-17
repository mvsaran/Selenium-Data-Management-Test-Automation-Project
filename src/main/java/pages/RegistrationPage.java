package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pojo.UserData;

public class RegistrationPage {
    private WebDriver driver;

    private By firstName = By.id("customer.firstName");
    private By lastName = By.id("customer.lastName");
    private By address = By.id("customer.address.street");
    private By city = By.id("customer.address.city");
    private By state = By.id("customer.address.state");
    private By zip = By.id("customer.address.zipCode");
    private By phone = By.id("customer.phoneNumber");
    private By ssn = By.id("customer.ssn");
    private By username = By.id("customer.username");
    private By password = By.id("customer.password");
    private By confirm = By.id("repeatedPassword");
    private By registerButton = By.cssSelector("input[value='Register']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillRegistrationForm(UserData user) {
        driver.findElement(firstName).sendKeys(user.getFirstName());
        driver.findElement(lastName).sendKeys(user.getLastName());
        driver.findElement(address).sendKeys(user.getAddress());
        driver.findElement(city).sendKeys(user.getCity());
        driver.findElement(state).sendKeys(user.getState());
        driver.findElement(zip).sendKeys(user.getZipCode());
        driver.findElement(phone).sendKeys(user.getPhone());
        driver.findElement(ssn).sendKeys(user.getSsn());
        driver.findElement(username).sendKeys(user.getUsername());
        driver.findElement(password).sendKeys(user.getPassword());
        driver.findElement(confirm).sendKeys(user.getPassword());
    }

    public void clickRegister() {
        driver.findElement(registerButton).click();
    }
}
