package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Класс для работы со страницей оформления заказа
 */
public class OrderPage {
    private final WebDriver driver;

    // Локаторы элементов формы заказа
    private final By orderForm = By.xpath(".//div[starts-with(@class, 'Order_Form')]");
    private final By nameField = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Имя')]");
    private final By surnameField = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Фамилия')]");
    private final By addressField = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Адрес')]");
    private final By metroField = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Станция метро')]");
    private final By phoneField = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Телефон')]");
    private final By commentField = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Комментарий')]");

    private final By metroDropdown = By.className("select-search__select");
    private final By metroOptions = By.xpath(".//div[@class='select-search__select']//div[starts-with(@class,'Order_Text')]");
    private final By continueButton = By.xpath(".//div[starts-with(@class, 'Order_NextButton')]/button");

    private final By datePicker = By.xpath(".//div[starts-with(@class, 'react-datepicker__input-container')]//input");
    private final By selectedDate = By.className("react-datepicker__day--selected");

    private final By rentalPeriodDropdown = By.className("Dropdown-root");
    private final By rentalPeriodOptions = By.className("Dropdown-option");

    private final By colorOptions = By.xpath(".//div[starts-with(@class, 'Order_Checkboxes')]//label");

    private final By orderButton = By.xpath(".//div[starts-with(@class, 'Order_Buttons')]/button[not(contains(@class,'Button_Inverted'))]");
    private final By confirmOrderButton = By.xpath(".//div[starts-with(@class, 'Order_Modal')]//button[not(contains(@class,'Button_Inverted'))]");
    private final By orderSuccessMessage = By.xpath(".//div[starts-with(@class, 'Order_Modal')]//div[(starts-with(@class,'Order_ModalHeader'))]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Ожидает загрузки формы заказа
     */
    public void waitForFormToLoad() {
        new WebDriverWait(this.driver, 3)
                .until(ExpectedConditions.visibilityOf(this.driver.findElement(this.orderForm)));
    }

    /**
     * Заполняет поле имени
     */
    public void enterName(String name) {
        this.driver.findElement(this.nameField).sendKeys(name);
    }

    /**
     * Заполняет поле фамилии
     */
    public void enterSurname(String surname) {
        this.driver.findElement(this.surnameField).sendKeys(surname);
    }

    /**
     * Заполняет поле адреса
     */
    public void enterAddress(String address) {
        this.driver.findElement(this.addressField).sendKeys(address);
    }

    /**
     * Выбирает станцию метро
     */
    public void selectMetroStation(String station) {
        this.driver.findElement(this.metroField).sendKeys(station);
        waitForElement(this.metroDropdown);
        selectFromDropdown(this.metroOptions, station);
    }

    /**
     * Заполняет поле телефона
     */
    public void enterPhoneNumber(String phone) {
        this.driver.findElement(this.phoneField).sendKeys(phone);
    }

    /**
     * Нажимает кнопку продолжения
     */
    public void clickContinueButton() {
        this.driver.findElement(this.continueButton).click();
    }

    /**
     * Устанавливает дату доставки
     */
    public void setDeliveryDate(String date) {
        this.driver.findElement(this.datePicker).sendKeys(date);
        waitForElement(this.selectedDate);
        clickSelectedDate();
    }

    /**
     * Выбирает срок аренды
     */
    public void selectRentalPeriod(String period) {
        expandRentalPeriodDropdown();
        selectFromDropdown(this.rentalPeriodOptions, period);
    }

    /**
     * Выбирает цвет самоката
     */
    public void chooseScooterColor(String color) {
        selectFromDropdown(this.colorOptions, color);
    }

    /**
     * Добавляет комментарий к заказу
     */
    public void addComment(String comment) {
        this.driver.findElement(this.commentField).sendKeys(comment);
    }

    /**
     * Оформляет заказ
     */
    public void confirmOrder() {
        clickOrderButton();
        waitForElement(this.confirmOrderButton);
        clickConfirmOrderButton();
    }

    /**
     * Возвращает сообщение об успешном оформлении заказа
     */
    public String getOrderConfirmationMessage() {
        return this.driver.findElement(this.orderSuccessMessage).getText();
    }

    private void waitForElement(By element) {
        new WebDriverWait(this.driver, 3)
                .until(ExpectedConditions.visibilityOf(this.driver.findElement(element)));
    }

    private void clickOrderButton() {
        this.driver.findElement(this.orderButton).click();
    }

    private void clickConfirmOrderButton() {
        this.driver.findElement(this.confirmOrderButton).click();
    }

    private void selectFromDropdown(By optionsLocator, String value) {
        List<WebElement> options = this.driver.findElements(optionsLocator);
        for (WebElement option : options) {
            if (option.getText().equals(value)) {
                option.click();
                break;
            }
        }
    }

    private void clickSelectedDate() {
        this.driver.findElement(this.selectedDate).click();
    }

    private void expandRentalPeriodDropdown() {
        this.driver.findElement(this.rentalPeriodDropdown).click();
    }
}