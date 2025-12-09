package com.reportportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    // Основные локаторы
    @FindBy(xpath = "//input[@type='text']")
    private WebElement usernameInput;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//div[contains(@class, 'notification')]")
    private WebElement errorMessage;

    public LoginPage() {
        PageFactory.initElements(driver, this);
    }

    @Step("Открыть страницу логина Report Portal")
    public void open() {
        driver.get(com.reportportal.utils.TestData.BASE_URL);
        waitForPageLoad();
    }

    @Step("Выполнить вход с учетными данными: {username}")
    public void login(String username, String password) {
        // Ищем поле логина
        WebElement usernameField = findUsernameField();
        usernameField.clear();
        usernameField.sendKeys(username);

        // Ищем поле пароля
        WebElement passwordField = findPasswordField();
        passwordField.clear();
        passwordField.sendKeys(password);

        // Ищем кнопку входа
        WebElement loginBtn = findLoginButton();
        loginBtn.click();

        // Ждем после логина
        waitAfterLogin();
    }

    @Step("Получить текст сообщения об ошибке")
    public String getErrorMessage() {
        try {
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }

    @Step("Проверить, что страница логина отображается")
    public boolean isLoginPageDisplayed() {
        try {
            return usernameInput.isDisplayed() ||
                    driver.getCurrentUrl().contains("login");
        } catch (Exception e) {
            return false;
        }
    }

    // Вспомогательные методы
    private WebElement findUsernameField() {
        try {
            if (usernameInput.isDisplayed()) {
                return usernameInput;
            }
        } catch (Exception e) {
            // Продолжаем поиск
        }

        // Альтернативный поиск
        return driver.findElement(By.xpath("//input[@type='text' or @name='login']"));
    }

    private WebElement findPasswordField() {
        try {
            if (passwordInput.isDisplayed()) {
                return passwordInput;
            }
        } catch (Exception e) {
            // Продолжаем поиск
        }

        // Альтернативный поиск
        return driver.findElement(By.xpath("//input[@type='password']"));
    }

    private WebElement findLoginButton() {
        try {
            if (loginButton.isDisplayed()) {
                return loginButton;
            }
        } catch (Exception e) {
            // Продолжаем поиск
        }

        // Альтернативный поиск
        return driver.findElement(By.xpath("//button[contains(text(), 'Login') or @type='submit']"));
    }

    private void waitForPageLoad() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void waitAfterLogin() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}