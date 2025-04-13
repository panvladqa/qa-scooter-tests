package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertTrue;

/**
 * Тесты для проверки корректности ссылок логотипов на главной странице
 */
public class MainPageLinksTests {
    private WebDriver driver;
    private final String pageUrl = "https://qa-scooter.praktikum-services.ru";
    private final String expectedYandexUrl = "//yandex.ru";
    private final String expectedScooterUrl = "//qa-scooter.praktikum-services.ru";

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();
        this.driver.get(this.pageUrl);
    }

    @After
    public void cleanup() {
        this.driver.quit();
    }

    /**
     * Проверяет корректность ссылки и открытия логотипа Яндекса
     */
    @Test
    public void verifyYandexLogoLink() {
        MainPage mainPage = new MainPage(this.driver);

        assertTrue(
                "Ссылка логотипа Яндекса не ведет на " + this.expectedYandexUrl,
                mainPage.getYandexLogoUrl().contains(this.expectedYandexUrl)
        );

        assertTrue(
                "Ссылка логотипа Яндекса не открывается в новой вкладке",
                mainPage.isYandexLinkOpensInNewTab()
        );
    }

    /**
     * Проверяет корректность ссылки логотипа Самоката
     */
    @Test
    public void verifyScooterLogoLink() {
        MainPage mainPage = new MainPage(this.driver);

        assertTrue(
                "Ссылка логотипа Самоката не ведет на " + this.expectedScooterUrl,
                mainPage.getScooterLogoUrl().contains(this.expectedScooterUrl)
        );
    }
}