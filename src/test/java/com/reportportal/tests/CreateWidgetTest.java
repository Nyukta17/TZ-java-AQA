package com.reportportal.tests;

import com.reportportal.config.WebDriverConfig;
import com.reportportal.pages.LoginPage;
import com.reportportal.pages.HomePage;
import com.reportportal.pages.DashboardListPage;
import com.reportportal.pages.DashboardPage;
import com.reportportal.utils.TestData;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@Feature("Управление Dashboard")
@DisplayName("Тест 3: Создание нового Widget")
public class CreateWidgetTest {

    private LoginPage loginPage;
    private HomePage homePage;
    private DashboardListPage dashboardListPage;
    private DashboardPage dashboardPage;

    @BeforeEach
    public void setUp() {
        WebDriverConfig.getDriver();
        loginPage = new LoginPage();
        homePage = new HomePage();
        dashboardListPage = new DashboardListPage();
        dashboardPage = new DashboardPage();
    }

    @AfterEach
    public void tearDown() {
        WebDriverConfig.quitDriver();
    }

    @Test
    @DisplayName("Проверка создания виджета на Dashboard")
    @Description("ТЗ: 1. Войти в систему\n2. Перейти на Dashboard\n3. Добавить Widget\n4. Проверить добавление")
    @Severity(SeverityLevel.NORMAL)
    public void createNewWidgetOnDashboard() {
        // 1. Войти в систему Report Portal
        loginPage.open();
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

        // Проверить авторизацию
        assertTrue(homePage.isUserAuthorized(),
                "Пользователь должен быть авторизован");

        // 2. Перейти на существующий Dashboard
        homePage.goToDashboardPage(); // Переходим на страницу списка дашбордов
        dashboardListPage.selectExistingDashboard(); // Выбираем конкретный дашборд

        System.out.println("Текущий URL после выбора дашборда: " + WebDriverConfig.getDriver().getCurrentUrl());

        // 3. Добавить новый Widget любого типа
        dashboardPage.addNewWidget();

        // 4. Проверить, что Widget успешно добавлен и отображается на Dashboard
        assertTrue(dashboardPage.areWidgetsDisplayed(),
                "Widget должен отображаться на Dashboard");

        System.out.println("✅ Тест 3 выполнен успешно!");
    }
}