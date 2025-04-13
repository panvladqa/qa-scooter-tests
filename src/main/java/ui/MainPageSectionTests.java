package ui;

import pageObjects.MainPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.hamcrest.MatcherAssert;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.fail;

/**
 * Тестирование секции "Вопросы о важном" на главной странице
 */
@RunWith(Parameterized.class)
public class MainPageSectionTests {
    private WebDriver driver;
    private final String pageUrl = "https://qa-scooter.praktikum-services.ru";
    private final int sectionIndex;
    private final String expectedHeader;
    private final String expectedContent;

    /**
     * Конструктор тестового класса
     * @param sectionIndex Номер тестируемой секции
     * @param expectedHeader Ожидаемый текст заголовка
     * @param expectedContent Ожидаемое содержимое секции
     */
    public MainPageSectionTests(int sectionIndex, String expectedHeader, String expectedContent) {
        this.sectionIndex = sectionIndex;
        this.expectedHeader = expectedHeader;
        this.expectedContent = expectedContent;
    }

    /**
     * Параметры для параметризованного теста
     */
    @Parameterized.Parameters(name = "Проверка секции: {1}")
    public static Object[][] provideTestData() {
        return new Object[][] {
                {0, "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        };
    }

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
     * Проверяет корректность работы и содержимого секции
     */
    @Test
    public void verifySectionContent() {
        MainPage mainPage = new MainPage(this.driver);

        mainPage.acceptCookies();
        mainPage.expandSection(this.sectionIndex);
        mainPage.waitForSectionContent(this.sectionIndex);

        if (mainPage.isSectionContentVisible(this.sectionIndex)) {
            MatcherAssert.assertThat(
                    "Неверный текст заголовка для секции #" + this.sectionIndex,
                    this.expectedHeader,
                    equalTo(mainPage.getSectionHeaderText(this.sectionIndex))
            );
            MatcherAssert.assertThat(
                    "Неверное содержимое секции #" + this.sectionIndex,
                    this.expectedContent,
                    equalTo(mainPage.getSectionContent(this.sectionIndex))
            );
        }
        else {
            fail("Секция #" + this.sectionIndex + " не загрузилась");
        }
    }
}