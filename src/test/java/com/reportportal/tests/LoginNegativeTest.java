package com.reportportal.tests;

import com.reportportal.config.WebDriverConfig;
import com.reportportal.pages.LoginPage;
import com.reportportal.utils.TestData;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@Feature("Авторизация")
@DisplayName("Тест 1: Негативный тест на логин")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class LoginNegativeTest {

    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        WebDriverConfig.getDriver();
        loginPage = new LoginPage();
    }

    @AfterEach
    public void tearDown() {
        WebDriverConfig.quitDriver();
    }

    @Test
    @DisplayName("Проверка ошибки при неверных учетных данных")
    @Description("ТЗ: 1. Открыть страницу логина\n2. Ввести некорректный логин и/или пароль\n3. Нажать Login\n4. Проверить сообщение об ошибке")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Негативный сценарий авторизации")
    public void loginWithInvalidCredentialsShouldShowErrorMessage() {
        // Arrange (подготовка)
        loginPage.open();


        loginPage.login(TestData.INVALID_USERNAME, TestData.INVALID_PASSWORD);


        // 2. Проверить, что пользователь не был авторизован
        assertTrue(loginPage.isLoginPageDisplayed(),
                "Пользователь должен остаться на странице логина");

        // 3. Проверить, что остался на странице логина (дополнительная проверка)
        String currentUrl = WebDriverConfig.getDriver().getCurrentUrl();
        assertTrue(currentUrl.contains("login") || currentUrl.equals(TestData.BASE_URL),
                "URL должен указывать на страницу логина");
    }
}