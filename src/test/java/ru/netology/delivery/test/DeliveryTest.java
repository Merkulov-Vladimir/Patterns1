package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

//    @Test
//    void shouldGenerateUser() {
//        var info = DataGenerator.Registration.generateUser("ru");
//        printTestData(info);
//    }
//
//    private void printTestData(DataGenerator.UserInfo info) {
//        System.out.println("===========================================================");
//        System.out.println(info.getCity() + "\n" + info.getName() + "\n" + info.getPhone());
//        System.out.println("===========================================================");
//    }

    @DisplayName("Should successful plan and replan meeting")
    @Test
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 6;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder=Город]").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);//clear field
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $(byName("name")).setValue(validUser.getName());
        $(byName("phone")).setValue(validUser.getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=\"success-notification\"]").shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);//clear field
        $("[data-test-id='date'] input").setValue(secondMeetingDate);
        $(withText("Запланировать")).click();
        $("[data-test-id=\"replan-notification\"]").shouldHave(Condition.text("У вас уже запланирована встреча на другую дату."));
        $(withText("Перепланировать")).click();
        $("[data-test-id=\"success-notification\"]").shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(15));
    }
}


