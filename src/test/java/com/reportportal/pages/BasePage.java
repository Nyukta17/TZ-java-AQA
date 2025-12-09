package com.reportportal.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.reportportal.config.WebDriverConfig;
import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = WebDriverConfig.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
}