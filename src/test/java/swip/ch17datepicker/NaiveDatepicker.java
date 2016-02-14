package swip.ch17datepicker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import swip.ch15pageflow.framework.Browser;
import swip.ch15pageflow.framework.Element;

import java.time.Month;
import java.util.Date;
import java.util.List;


public class NaiveDatepicker {

    private Browser browser;

    public NaiveDatepicker(Browser browser) {
        this.browser = browser;
    }

    public String pickDate(Date date) {
        browser.get("/date-picker.html");        //<1>

        //show - begin
        Element trigger = browser.untilFound(() -> By.id("datepicker"));
        trigger.click();
        //show – end

        //pickYear - begin
        WebElement datepicker = browser.findElement(By.id("ui-datepicker-div"));

        String year = datepicker.findElement(
            By.className("ui-datepicker-year")).getText();
        if (Integer.parseInt(year) < date.getYear() + 1900) {
            while (Integer.parseInt(year) !=  date.getYear() + 1900) {
                datepicker.findElement(
                    By.className("ui-datepicker-next")).click();
                datepicker = browser.findElement(By.id("datepicker"));
                year = datepicker.findElement(
                    By.className("ui-datepicker-year")).getText();
            }
        } else if (Integer.parseInt(year) > date.getYear()) {
            while (Integer.parseInt(year) != date.getYear() + 1900) {
                datepicker.findElement(
                    By.className("ui-datepicker-prev")).click();
                datepicker = browser.findElement(
                    By.id("ui-datepicker-div"));
                year = datepicker.findElement(
                    By.className("ui-datepicker-year")).getText();
            }
        }
        //pickYear - end

        //pickMonth - begin
        String month = datepicker.findElement(
            By.className("ui-datepicker-month")).getText();
        if (Month.valueOf(month.toUpperCase()).ordinal() < date.getMonth()) {
            while (Month.valueOf(month.toUpperCase()).ordinal() != date.getMonth()) {
                datepicker.findElement
                    (By.className("ui-datepicker-next")).click();
                datepicker = browser.findElement(By.id("ui-datepicker-div"));
                month = datepicker.findElement(
                    By.className("ui-datepicker-month")).getText();
            }
        } else if (Month.valueOf(month.toUpperCase()).ordinal() > date.getMonth()) {
            while (Month.valueOf(month.toUpperCase()).ordinal() != date.getMonth()) {
                datepicker.findElement(
                    By.className("ui-datepicker-prev")).click();
                datepicker = browser.findElement(By.id("ui-datepicker-div"));
                month = datepicker.findElement(
                    By.className("ui-datepicker-month")).getText();
            }
        }
        //pickMonth - end

        //pickDay - begin
        List<WebElement> tds = datepicker.findElements(By.tagName("td"));
        for (WebElement td : tds) {
            if (td.getText().equals(String.valueOf(date.getDay() + 1))) {
                td.click();
            }
        }

        //pickDay - end
        return browser.findElement(By.id("datepicker")).getAttribute("value");
    }
}