package com.reportportal.tests;

import com.reportportal.config.WebDriverConfig;
import com.reportportal.pages.HomePage;
import com.reportportal.pages.LoginPage;
import com.reportportal.utils.TestData;
import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Feature("Авторизация")
@DisplayName("Тест 2: Позитивный тест на логин")
public class LoginPositiveTest {

    @BeforeEach
    public void setUp() {
        WebDriverConfig.getDriver();
    }

    @AfterEach
    public void tearDown() {
        WebDriverConfig.quitDriver();
    }

    @Test
    @DisplayName("Проверка успешной авторизации")
    @Description("Требования ТЗ:\n1. Открыть страницу логина Report Portal\n2. Ввести корректный логин и пароль\n3. Нажать кнопку Login\n4. Проверить авторизацию")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithValidCredentialsShouldBeSuccessful() {
        LoginPage loginPage = new LoginPage();
        HomePage homePage = new HomePage();

        // 1. Открыть страницу логина
        loginPage.open();

        // 2. Ввести корректные данные и нажать Login
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

        // 3. Проверить, что пользователь авторизован
        assertTrue(homePage.isUserAuthorized(),
                "Пользователь должен быть успешно авторизован");
    }
}