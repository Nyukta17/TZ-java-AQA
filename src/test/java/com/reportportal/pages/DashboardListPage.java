package com.reportportal.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class DashboardListPage extends BasePage {

    @Step("Выбрать существующий дашборд из списка")
    public void selectExistingDashboard() {
        System.out.println("Выбор существующего дашборда...");

        // Ждем загрузки таблицы
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Ищем все ссылки на дашборды в таблице
        List<WebElement> dashboardLinks = driver.findElements(
                By.xpath("//a[contains(@class, 'dashboardTable__name') and contains(@href, 'dashboard/')]")
        );

        System.out.println("Найдено дашбордов: " + dashboardLinks.size());

        if (dashboardLinks.isEmpty()) {
            throw new RuntimeException("Не найдено существующих дашбордов");
        }

        // Выбираем первый существующий дашборд
        WebElement firstDashboard = dashboardLinks.get(0);
        System.out.println("Выбираю дашборд: " + firstDashboard.getText());
        firstDashboard.click();

        // Ждем загрузки конкретного дашборда
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}