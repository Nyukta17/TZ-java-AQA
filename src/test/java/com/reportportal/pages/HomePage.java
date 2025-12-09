package com.reportportal.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class HomePage extends BasePage {

    @Step("Проверить, что пользователь авторизован")
    public boolean isUserAuthorized() {
        try {
            Thread.sleep(2000);
            // Проверяем URL - после успешного логина он должен измениться
            String url = driver.getCurrentUrl();
            return !url.contains("login") && !url.equals(com.reportportal.utils.TestData.BASE_URL);
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Перейти на страницу Dashboard")
    public void goToDashboardPage() {
        // Кликаем на Dashboard в сайдбаре
        driver.findElement(By.xpath("//a[contains(@href, 'dashboard')]")).click();

        // Ждем загрузки страницы со списком дашбордов
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Перешли на страницу дашбордов. URL: " + driver.getCurrentUrl());
    }
}