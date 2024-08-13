package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import pages.WebPage;
import data.SQLHelper;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestWebPage {

    WebPage webPages = new WebPage();

    @BeforeEach
    void clearDatabaseTables() {
        open("http://localhost:8080/");
        SQLHelper.clearTables();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test

    @DisplayName("Оплата тура со занчком Купить с валидной картой  с статусом APPROVED")
    public void testCashValidCardApproved() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getApprovedCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageSuccessfully();
        assertEquals("APPROVED", SQLHelper.findPayStatus());

    }


    @Test
    @DisplayName("Кредит за тур со значком Купить в кредит валидной картой с статусом APPROVED")
    public void testCreditValidCardApproved() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getApprovedCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageSuccessfully();
        assertEquals("APPROVED", SQLHelper.findCreditStatus());
    }


    @Test
    @DisplayName("Оплата тура со занчком Купить валидной картой с статусом DECLINED")
    public void testCashValidCardDeclined() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getDeclinedCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageError();
        assertEquals("DECLINED", SQLHelper.findPayStatus());
    }


    @Test
    @DisplayName("Кредит за тур со значком Купить в кредит валидной картой с статусом DECLINED")
    public void testCreditCashValidCardDeclined() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getDeclinedCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageError();
        assertEquals("DECLINED", SQLHelper.findCreditStatus());
    }

    @Test
    @DisplayName("Ввод данных с пустым полем Номер карты во вкладке Купить")
    public void testEmptyFieldNumberCard() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getEmptyNumberCard());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();
        assertEquals(0,SQLHelper.getOrderEntityCount());
    }

    @Test
    @DisplayName("Ввод данных в поле Номер карты менее 16 символов во вкладке Купить")
    public void testCardNumberLessThan16() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumberWithLength());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();
        assertEquals(0, SQLHelper.getOrderEntityCount());
    }

    @Test
    @DisplayName("Ввод данных в поле Номер карты  с несуществующей картой во вкладке Купить")
    public void testCashInvalidCard() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getCardNumberNothing());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageError();
        assertEquals(0, SQLHelper.getOrderEntityCount());
    }

    @Test
    @DisplayName("Ввод данных с пустым данными в поле Месяц во вкладке Купить")
    public void testEmptyMonth() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getEmptyMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Месяц со значением 00 во вкладке Купить")
    public void testInvalidMonth() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getInvalidMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageInvalidDate();

    }

    @Test
    @DisplayName("Ввод данных в поле Месяц со значением больше 12 месяцев во вкладке Купить")
    public void testNonExistentMonth() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getRandomMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageInvalidDate();

    }

    @Test
    @DisplayName("Ввод данных в поле Месяц со значением 1 цифры  во вкладке Купить")
    public void testInvalidNumber() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getMonthWithOneValue());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Владелец с пустым значением во вкладке Купить")
    public void testEmptyOwner() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getEmptyOwner());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageRequiredField();

    }

    @Test
    @DisplayName("Ввод данных в поле Владелец на кириллице во вкладке Купить")
    public void testOwnerCyrillic() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomCyrillicName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Владелец в цифровом значении во вкладке Купить")
    public void testNumbersOwner() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerNumber());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Владелец со спецсимволами во вкладке Купить")
    public void testSymbolsOwner() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getSpecialCharactersOwner());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();


    }

    @Test
    @DisplayName("Ввод данных в поле Год с пустым значением во вкладке Купить")
    public void testBlankValueYear() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getEmptyYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Год с 1 значением во вкладке Купить")
    public void testYearWithOneValue() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getYearWithOneValue());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();
    }

    @Test
    @DisplayName("Ввод данных в поле Год с меньшим значением текущего года во вкладке Купить")
    public void testLastYear() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getPreviousYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageCardExpired();

    }

    @Test
    @DisplayName("Ввод данных в поле Год со значением 6+ лет во вкладке Купить")
    public void testYearPlus5() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYearPlus5());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageInvalidDate();

    }

    @Test
    @DisplayName("Ввод данных в поле Год со значением 00 во вкладке Купить")
    public void testYear00() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getInvalidYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageCardExpired();
    }

    @Test
    @DisplayName("Ввод данных в поле CVC/CVV с пустым значением во вкладке Купить")
    public void testEmptyCvcCvv() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getEmptyCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();


    }

    @Test
    @DisplayName("Ввод данных в поле CVC/CVV с 1 значением во вкладке Купить")
    public void test1CVC() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getSingleDigitCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле CVC/CVV с 2 значением во вкладке Купить")
    public void test2CVC() {
        webPages.purcHaseCash();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getDoubleDigitCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных с пустым полем Номер карты во вкладке Купить в кредит")
    public void testCreditEmptyFieldNumberCard() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getEmptyNumberCard());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Номер карты менее 16 символов во вкладке Купить в кредит")
    public void testCreditCardNumberLessThan16() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumberWithLength());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Номер карты  с несуществующей картой во вкладке Купить в кредит")
    public void testCreditCashInvalidCard() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getCardNumberNothing());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageError();
    }

    @Test
    @DisplayName("Ввод данных с пустым данными в поле Месяц во вкладке Купить в кредит")
    public void testCreditEmptyMonth() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getEmptyMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Месяц со значением 00 во вкладке Купить в кредит")
    public void tesCredittInvalidMonth() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getInvalidMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageInvalidDate();

    }

    @Test
    @DisplayName("Ввод данных в поле Месяц со значением больше 12 месяцев во вкладке Купить в кредит")
    public void testCreditNonExistentMonth() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getRandomMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageInvalidDate();

    }

    @Test
    @DisplayName("Ввод данных в поле Месяц со значением 1 цифры  во вкладке Купить в кредит")
    public void testCreditInvalidNumber() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getMonthWithOneValue());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Владелец с пустым значением во вкладке Купить в кредит")
    public void testCreditEmptyOwner() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getEmptyOwner());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageRequiredField();

    }

    @Test
    @DisplayName("Ввод данных в поле Владелец на кириллице во вкладке Купить в кредит")
    public void testCreditOwnerCyrillic() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomCyrillicName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Владелец в цифровом значении во вкладке Купить в кредит")
    public void testCreditNumbersOwner() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerNumber());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле Владелец со спецсимволами во вкладке Купить в кредит")
    public void testCreditSymbolsOwner() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getSpecialCharactersOwner());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();


    }

    @Test
    @DisplayName("Ввод данных в поле Год с пустым значением во вкладке Купить в кредит")
    public void testCreditBlankValueYear() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getEmptyYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();
    }

    @Test
    @DisplayName("Ввод данных в поле Год с 1 значением во вкладке Купить в кредит")
    public void testCreditYearWithOneValue() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getYearWithOneValue());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();
    }

    @Test
    @DisplayName("Ввод данных в поле Год с меньшим значением текущего года во вкладке Купить в кредит")
    public void testCreditLastYear() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getPreviousYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageCardExpired();

    }

    @Test
    @DisplayName("Ввод данных в поле Год со значением 5+ лет во вкладке Купить в кредит")
    public void testCreditYearPlus5() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYearPlus5());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageInvalidDate();

    }

    @Test
    @DisplayName("Ввод данных в поле Год со значением 00 во вкладке Купить в кредит")
    public void testCreditYear00() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getInvalidYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getRandomCvc());
        webPages.clickContinueButton();
        webPages.messageCardExpired();
    }


    @Test
    @DisplayName("Ввод данных в поле CVC/CVV с пустым значением во вкладке Купить в кредит")
    public void testCreditEmptyCvcCvv() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getEmptyCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();


    }

    @Test
    @DisplayName("Ввод данных в поле CVC/CVV с 1 значением во вкладке Купить в кредит")
    public void testCredit1CVC() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getSingleDigitCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();

    }

    @Test
    @DisplayName("Ввод данных в поле CVC/CVV с 2 значением во вкладке Купить в кредит")
    public void testCredit2CVC() {
        webPages.buyInCredit();
        webPages.setCardNumber(DataHelper.getRandomCardNumber());
        webPages.setCardMonth(DataHelper.getNextMonth());
        webPages.setCardYear(DataHelper.getCurrentYear());
        webPages.setCardOwner(DataHelper.getRandomOwnerName());
        webPages.setCardCVV(DataHelper.getDoubleDigitCvc());
        webPages.clickContinueButton();
        webPages.messageIncorrectFormat();

    }
}