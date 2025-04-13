package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.MainPage;
import pageObjects.OrderPage;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Тестирование полного потока успешного оформления заказа
 */
@RunWith(Parameterized.class)
public class OrderPageTests {
    private WebDriver driver;
    private final String pageUrl = "https://qa-scooter.praktikum-services.ru";
    private final String name, surname, address, metroStation, phoneNumber;
    private final String deliveryDate, rentalPeriod, scooterColor, comment;
    private final String expectedSuccessMessage = "Заказ оформлен";

    /**
     * Конструктор для параметризованного теста
     */
    public OrderPageTests(
            String name,
            String surname,
            String address,
            String metroStation,
            String phoneNumber,
            String deliveryDate,
            String rentalPeriod,
            String scooterColor,
            String comment
    ) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metroStation = metroStation;
        this.phoneNumber = phoneNumber;
        this.deliveryDate = deliveryDate;
        this.rentalPeriod = rentalPeriod;
        this.scooterColor = scooterColor;
        this.comment = comment;
    }

    /**
     * Параметры для тестовых случаев
     */
    @Parameterized.Parameters(name = "Оформление заказа. Пользователь: {0} {1}")
    public static Object[][] provideOrderTestData() {
        return new Object[][] {
                {"Клава", "Птичкина", "Москва, ул. Дорожная, д. 12, кв. 34", "Сокол", "81234567890",
                        "01.05.2023", "четверо суток", "чёрный жемчуг", "Коммент!"},
                {"Иван", "Петров", "Москва, ул. Скобелевская, д. 26, кв. 1", "Улица Скобелевская", "89876543210",
                        "21.05.2023", "трое суток", "серая безысходность", "Привезите в первой половине дня"},
        };
    }

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();
        this.driver.get(pageUrl);
    }

    @After
    public void cleanup() {
        this.driver.quit();
    }

    /**
     * Проверка оформления заказа через кнопку в шапке сайта
     */
    @Test
    public void testOrderViaHeaderButtonSuccess() {
        MainPage mainPage = new MainPage(this.driver);
        OrderPage orderPage = new OrderPage(this.driver);

        mainPage.acceptCookies();
        mainPage.clickHeaderOrderButton();
        completeOrderForm(orderPage);

        MatcherAssert.assertThat(
                "Не удалось оформить заказ",
                orderPage.getOrderConfirmationMessage(),
                containsString(this.expectedSuccessMessage)
        );
    }

    /**
     * Проверка оформления заказа через кнопку в теле сайта
     */
    @Test
    public void testOrderViaMainButtonSuccess() {
        MainPage mainPage = new MainPage(this.driver);
        OrderPage orderPage = new OrderPage(this.driver);

        mainPage.acceptCookies();
        mainPage.clickMainOrderButton();
        completeOrderForm(orderPage);

        MatcherAssert.assertThat(
                "Не удалось оформить заказ",
                orderPage.getOrderConfirmationMessage(),
                containsString(this.expectedSuccessMessage)
        );
    }

    /**
     * Заполняет и отправляет форму заказа
     */
    private void completeOrderForm(OrderPage orderPage) {
        orderPage.waitForFormToLoad();

        // Заполнение информации о клиенте
        orderPage.enterName(this.name);
        orderPage.enterSurname(this.surname);
        orderPage.enterAddress(this.address);
        orderPage.selectMetroStation(this.metroStation);
        orderPage.enterPhoneNumber(this.phoneNumber);
        orderPage.clickContinueButton();

        // Заполнение информации об аренде
        orderPage.setDeliveryDate(this.deliveryDate);
        orderPage.selectRentalPeriod(this.rentalPeriod);
        orderPage.chooseScooterColor(this.scooterColor);
        orderPage.addComment(this.comment);

        orderPage.confirmOrder();
    }
}