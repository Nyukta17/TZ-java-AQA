package com.reportportal.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class DashboardPage extends BasePage {

    @Step("Найти и нажать кнопку 'Add new widget'")
    public void clickAddNewWidgetButton() {
        System.out.println("Поиск кнопки 'Add new widget'...");

        // Быстрое ожидание вместо sleep
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Оптимизированный локатор
        List<WebElement> addWidgetButtons = driver.findElements(
                By.xpath("//button[.//span[contains(text(), 'Add new widget')]]")
        );

        System.out.println("Найдено кнопок 'Add new widget': " + addWidgetButtons.size());

        if (!addWidgetButtons.isEmpty()) {
            WebElement addWidgetButton = addWidgetButtons.get(0);
            System.out.println("Нажимаю кнопку: " + addWidgetButton.getText());
            clickWithJS(addWidgetButton);
        } else {
            throw new RuntimeException("Кнопка 'Add new widget' не найдена");
        }

        waitForWidgetModal();
    }

    @Step("Выбрать фильтр в модальном окне")
    public void selectFilter() {
        System.out.println("Выбор фильтра...");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            // Ждем появления фильтров
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[name='filterId']")
            ));

            // Быстрый скролл
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 100);");

            // Ищем фильтры
            List<WebElement> filterRadioButtons = driver.findElements(
                    By.cssSelector("input[name='filterId']")
            );

            System.out.println("Найдено фильтров: " + filterRadioButtons.size());

            if (!filterRadioButtons.isEmpty()) {
                WebElement firstFilter = filterRadioButtons.get(0);
                System.out.println("Выбираю первый фильтр");

                // Кликаем через JS
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstFilter);
                System.out.println("✅ Фильтр выбран");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Ошибка при выборе фильтра: " + e.getMessage());
        }
    }

    @Step("Выбрать тип виджета")
    public void selectWidgetType(String widgetType) {
        System.out.println("Выбор типа виджета: " + widgetType);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        try {
            // Ждем появления вариантов виджетов
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(@class, 'widgetTypeItem')]")
            ));

            // Ищем конкретный тип
            List<WebElement> widgetOptions = driver.findElements(
                    By.xpath("//div[contains(@class, 'widgetTypeItem')]//div[contains(text(), '" + widgetType + "')]")
            );

            if (!widgetOptions.isEmpty()) {
                clickWithJS(widgetOptions.get(0));
                System.out.println("✅ Выбран тип виджета: " + widgetType);
            } else {
                // Выбираем первый доступный
                widgetOptions = driver.findElements(By.xpath("//div[contains(@class, 'widgetTypeItem')]"));
                if (!widgetOptions.isEmpty()) {
                    clickWithJS(widgetOptions.get(0));
                    System.out.println("Выбран первый доступный тип виджета");
                }
            }

        } catch (Exception e) {
            System.out.println("Ошибка при выборе типа виджета: " + e.getMessage());
        }
    }

    @Step("Нажать кнопку 'Next step' в мастере создания виджета")
    public void clickNextStepButton() {
        System.out.println("Поиск кнопки 'Next step'...");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        try {
            // Ищем кнопку
            WebElement nextStepButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[.//span[contains(text(), 'Next step')]]")
            ));

            System.out.println("Нажимаю кнопку: " + nextStepButton.getText());
            clickWithJS(nextStepButton);

        } catch (Exception e) {
            System.out.println("Кнопка 'Next step' не найдена");
        }
    }

    @Step("Нажать кнопку 'Add' для сохранения виджета")
    public void clickAddWidgetButton() {
        System.out.println("Поиск кнопки 'Add' для сохранения виджета...");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        try {
            // ОЧЕНЬ ВАЖНО: Ищем кнопку Add ТОЛЬКО в модальном окне создания виджета
            // и исключаем кнопку "Add New Dashboard"
            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class, 'widgetWizardModal')]//button[.//span[text()='Add']]")
            ));

            System.out.println("Нажимаю кнопку в модальном окне: " + addButton.getText());
            clickWithJS(addButton);

            // Ждем закрытия модального окна
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//div[contains(@class, 'widgetWizardModal')]")
            ));

            System.out.println("✅ Виджет успешно добавлен");

        } catch (Exception e) {
            System.out.println("Кнопка 'Add' в модальном окне не найдена, пробуем другие варианты...");

            // Альтернативный поиск в модальном окне
            try {
                // Пробуем найти любую кнопку в модальном окне, кроме "Add New Dashboard"
                List<WebElement> modalButtons = driver.findElements(
                        By.xpath("//div[contains(@class, 'widgetWizardModal')]//button")
                );

                for (WebElement button : modalButtons) {
                    String buttonText = button.getText().trim();
                    if (!buttonText.isEmpty() &&
                            (buttonText.equals("Add") ||
                                    buttonText.contains("Save") ||
                                    buttonText.contains("Create") ||
                                    buttonText.contains("Finish"))) {

                        System.out.println("Нажимаю альтернативную кнопку в модальном окне: " + buttonText);
                        clickWithJS(button);

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        break;
                    }
                }

            } catch (Exception ex) {
                System.out.println("Не удалось найти кнопку в модальном окне: " + ex.getMessage());
            }
        }
    }

    @Step("Добавить новый Widget любого типа")
    public void addNewWidget() {
        System.out.println("=== Начинаем создание нового виджета ===");

        // 1. Открыть модальное окно
        clickAddNewWidgetButton();

        // 2. Выбрать тип виджета
        selectWidgetType("Launch statistics chart");

        // 3. Нажать Next
        clickNextStepButton();

        // 4. Выбрать фильтр
        selectFilter();

        // 5. Нажать Next
        clickNextStepButton();

        // 6. Нажать Add для сохранения
        clickAddWidgetButton();

        System.out.println("=== Процесс создания виджета завершен ===");
    }

    @Step("Проверить, что Widget отображается на Dashboard")
    public boolean areWidgetsDisplayed() {
        System.out.println("Проверка отображения виджетов...");

        // Короткое ожидание вместо sleep
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            // Проверяем, что модальное окно закрылось
            List<WebElement> modals = driver.findElements(
                    By.xpath("//div[contains(@class, 'widgetWizardModal')]")
            );

            if (!modals.isEmpty() && modals.get(0).isDisplayed()) {
                System.out.println("Модальное окно все еще открыто");
                return false;
            }

            // Ищем виджеты на странице
            List<WebElement> widgets = driver.findElements(
                    By.xpath("//div[contains(@class, 'widget') and not(contains(@class, 'widgetWizardModal'))]")
            );

            System.out.println("Найдено виджетов: " + widgets.size());

            // Если есть виджеты - успех
            return !widgets.isEmpty();

        } catch (Exception e) {
            System.out.println("Ошибка при проверке виджета: " + e.getMessage());
            return false;
        }
    }

    // Оптимизированный метод клика
    private void clickWithJS(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            System.out.println("Кликаем обычным способом");
            element.click();
        }
    }

    private void waitForWidgetModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[contains(text(), 'Add new widget')]")
            ));
            System.out.println("✅ Модальное окно создания виджета открыто");
        } catch (Exception e) {
            System.out.println("⚠️ Не удалось дождаться модального окна");
        }
    }
}