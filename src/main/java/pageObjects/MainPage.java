package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Реализует взаимодействие с элементами главной страницы сервиса
 */
public class MainPage {
    private final WebDriver webDriver;

    // Локаторы элементов страницы
    private final By sectionHeaders = By.className("accordion__heading");
    private final By sectionContents = By.xpath(".//div[@class='accordion__panel']/p");
    private final By headerOrderButton = By.xpath(".//div[starts-with(@class, 'Header_Nav')]//button[starts-with(@class, 'Button_Button')]");
    private final By mainOrderButton = By.xpath(".//div[starts-with(@class, 'Home_RoadMap')]//button[starts-with(@class, 'Button_Button')]");
    private final By cookieAcceptButton = By.id("rcc-confirm-button");
    private final By yandexLogoLink = By.xpath(".//a[starts-with(@class,'Header_LogoYandex')]");
    private final By scooterLogoLink = By.xpath(".//a[starts-with(@class,'Header_LogoScooter')]");

    /**
     * Инициализирует новый экземпляр страницы
     */
    public MainPage(WebDriver driver) {
        this.webDriver = driver;
    }

    /**
     * Ожидает загрузки содержимого вложения
     */
    public void waitForSectionContent(int index) {
        new WebDriverWait(webDriver, 3)
                .until(ExpectedConditions.visibilityOf(webDriver.findElements(sectionContents).get(index)));
    }

    /**
     * Подтверждает использование cookies
     */
    public void acceptCookies() {
        webDriver.findElement(cookieAcceptButton).click();
    }

    /**
     * Возвращает текст заголовка секции
     */
    public String getSectionHeaderText(int index) {
        return webDriver.findElements(sectionHeaders).get(index).getText();
    }

    /**
     * Возвращает содержимое секции
     */
    public String getSectionContent(int index) {
        return webDriver.findElements(sectionContents).get(index).getText();
    }

    /**
     * Раскрывает указанную секцию
     */
    public void expandSection(int index) {
        webDriver.findElements(sectionHeaders).get(index).click();
    }

    /**
     * Проверяет видимость содержимого секции
     */
    public boolean isSectionContentVisible(int index) {
        return webDriver.findElements(sectionContents).get(index).isDisplayed();
    }

    /**
     * Нажимает кнопку заказа в шапке страницы
     */
    public void clickHeaderOrderButton() {
        webDriver.findElement(headerOrderButton).click();
    }

    /**
     * Нажимает основную кнопку заказа
     */
    public void clickMainOrderButton() {
        webDriver.findElement(mainOrderButton).click();
    }

    /**
     * Получает URL из логотипа Яндекса
     */
    public String getYandexLogoUrl() {
        return webDriver.findElement(yandexLogoLink).getAttribute("href");
    }

    /**
     * Получает URL из логотипа Самоката
     */
    public String getScooterLogoUrl() {
        return webDriver.findElement(scooterLogoLink).getAttribute("href");
    }

    /**
     * Проверяет, открывается ли ссылка Яндекса в новой вкладке
     */
    public boolean isYandexLinkOpensInNewTab() {
        return "_blank".equals(webDriver.findElement(yandexLogoLink).getAttribute("target"));
    }
}