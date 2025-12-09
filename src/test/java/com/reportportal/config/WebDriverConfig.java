package com.reportportal.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class WebDriverConfig {
    private static WebDriver driver;

    private WebDriverConfig() {}

    public static WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();

            // Основные аргументы для стабильной работы
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");

            options.addArguments("--incognito");
            options.addArguments("--disable-save-password-bubble");
            options.addArguments("--disable-autofill-keyboard-accessory-view");


            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("useAutomationExtension", false);

            // Настройки для отключения сохранения паролей
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.default_content_setting_values.notifications", 2); // Блокировать уведомления
            options.setExperimentalOption("prefs", prefs);

            // Дополнительные настройки для стабильности
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");

            driver = new ChromeDriver(options);

            // Настройки таймаутов
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

            // Удаляем cookies перед началом
            driver.manage().deleteAllCookies();
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.close();
        }
    }



    public static void clearBrowserData() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
            try {
                // Очистка localStorage и sessionStorage через JavaScript
                driver.get("about:blank");
            } catch (Exception e) {
                System.out.println("Не удалось очистить данные браузера: " + e.getMessage());
            }
        }
    }

    public static void navigateTo(String url) {
        if (driver != null) {
            driver.get(url);
        }
    }

    public static String getCurrentUrl() {
        if (driver != null) {
            return driver.getCurrentUrl();
        }
        return "";
    }

    public static String getPageTitle() {
        if (driver != null) {
            return driver.getTitle();
        }
        return "";
    }
}