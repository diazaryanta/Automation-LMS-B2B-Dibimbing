package UI.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    private By emailField = By.id("input-username-or-email");
    private By passwordField = By.id("input-password");
    private By signInButton = By.id("button-sign-in");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void inputEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void inputPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickSignIn() {
        driver.findElement(signInButton).click();
    }

    public void loginToLms(String email, String password) {
        inputEmail(email);
        inputPassword(password);
        clickSignIn();
    }
}