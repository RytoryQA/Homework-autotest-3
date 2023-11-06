package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CallbackTest {

    @BeforeEach
    void openApplication() {

        open("http://localhost:9999/");
    }

    @Test
    void formSubmissionSuccess() {

        SelenideElement form = $(".form");

        form.$("[data-test-id=name] input").setValue("Виктория Викторовна");
        form.$("[data-test-id=phone] input").setValue("+79272323232");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void nameInLatin() {

        SelenideElement form = $(".form");

        form.$("[data-test-id=name] input").setValue("Viktorya");
        form.$("[data-test-id=phone] input").setValue("+79272323232");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void nameWithSymbols() {

        SelenideElement form = $(".form");

        form.$("[data-test-id=name] input").setValue("Vikto&#*");
        form.$("[data-test-id=phone] input").setValue("+79272323232");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void nameWithNumbers() {

        SelenideElement form = $(".form");

        form.$("[data-test-id=name] input").setValue("+79272323232");
        form.$("[data-test-id=phone] input").setValue("+79272323232");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void nameIsEmpty() {

        SelenideElement form = $(".form");

        form.$("[data-test-id=phone] input").setValue("+79272323232");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));

    }

    @Test
    void phoneLengthMoreValidValue() {
        SelenideElement form = $(".form");

        form.$("[data-test-id=name] input").setValue("Анна-Мария Иванова");
        form.$("[data-test-id=phone] input").setValue("+792723232329");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void phoneLengthLessValidValue() {
        SelenideElement form = $(".form");

        form.$("[data-test-id=name] input").setValue("петров-оглы");
        form.$("[data-test-id=phone] input").setValue("+7927232323");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneWithSymbols() {
        SelenideElement form = $(".form");

        form.$("[data-test-id=name] input").setValue("Виктория Викторова");
        form.$("[data-test-id=phone] input").setValue("№#%%?&*~");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void phoneWithLetters() {
        SelenideElement form = $(".form");

        form.$("[data-test-id=name] input").setValue("Виктория Викторова");
        form.$("[data-test-id=phone] input").setValue("Петров");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));


    }

    @Test
    void phoneIsEmpty() {
        SelenideElement form = $(".form");

        form.$("[data-test-id=name] input").setValue("Виктория Викторова");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));

    }


    @Test
    void checkboxNoAgreement() {
        SelenideElement form = $(".form");

        form.$("[data-test-id=name] input").setValue("Виктория Викторовна");
        form.$("[data-test-id=phone] input").setValue("+79272323232");
        form.$(".button").click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }
    

}
