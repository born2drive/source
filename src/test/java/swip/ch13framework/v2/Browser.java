package swip.ch13framework.v2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Browser {
    private final WebDriver driver;
    private final String baseUrl; // <1>

    public Browser(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.baseUrl = baseUrl;
    }

    public Element findElement(By by) {
        return new Element(driver.findElement(by));
    } // <3>

    public void get(String url) {
        driver.get(url.startsWith("http") ? url : baseUrl + url); // <2> Use a base URL.
    }
}
