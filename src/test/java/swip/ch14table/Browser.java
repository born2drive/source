package swip.ch14table;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import swip.ch13elements.framework.DelegatingWebDriver;
import swip.ch13elements.framework.Retry;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class Browser extends DelegatingWebDriver implements ExplicitWait, SearchScope {

    public Browser(WebDriver driver) {
        super(driver);
    }

    @Override
    public Element findElement(Supplier<By> by) {
        return new Element(super.findElement(by.get()));
    }

    @Override
    public Stream<Element> findElements(Supplier<By> by) {
        return findElements(by.get()).stream().map(Element::new);
    }

    public void setInputText(Supplier<By> by, String value) {
        Retry retry = new Retry(5, 1, TimeUnit.SECONDS);

        retry.attempt(
            () -> {
                Element element = findElement(by);
                element.clear();
                element.sendKeys(value);
                assertEquals(value, element.getAttribute("value"));
            }
        );
    }

    public String getInputText(Supplier<By> by) {
        return untilFound(by).getAttribute("value");
    }

    public void setCheckboxValue(Supplier<By> by, boolean value) {
        Element checkbox = untilFound(by);
        if (checkbox.isSelected() != value) {
            checkbox.click();
        }
    }

    public boolean isChecked(Supplier<By> by) {
        return untilFound(by).isSelected();
    }

    public void setRadio(By by, String value) {
        for (WebElement e : findElements(by)) {
            if (e.getAttribute("value").equals(value)) {
                e.click();
                return;
            }
        }
        throw new IllegalArgumentException("unable to find element with value " + value);
    }

    public String getRadio(Supplier<By> by) {
        for (WebElement e : findElements(by.get())) {
            if (e.getAttribute("checked").equals("true")) {
                return e.getAttribute("value");
            }
        }
        throw new IllegalArgumentException(
            "unable to find checked element in group located by " + by);
    }

    public Select getSelect(Supplier<By> by) {
        Element element = untilFound(by);
        new WebDriverWait(this, 3, 100)
            .until((WebDriver driver) -> {
                element.click();
                return !element.findElements(By.tagName("option")).isEmpty();
            });
        return new Select(element);
    }

}