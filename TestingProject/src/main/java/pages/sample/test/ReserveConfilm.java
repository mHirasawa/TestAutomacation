package pages.sample.test;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReserveConfilm {
    private WebDriver driver;
    private int timeout = 15;

    @FindBy(css = "a.lb-close")
    @CacheLookup
    private WebElement close;

    @FindBy(css = "a.lb-cancel")
    @CacheLookup
    private WebElement loading;

    private final String pageLoadedText = "";

    private final String pageUrl = "/reserveApp/check_info.html?reserve_y=2018&reserve_m=5&reserve_d=15&reserve_t=1&hc=1&bf=on&gname=asdfasdf";

    public ReserveConfilm() {
    }

    public ReserveConfilm(WebDriver driver) {
        this();
        this.driver = driver;
    }

    public ReserveConfilm(WebDriver driver, Map<String, String> data) {
        this(driver);
    }

    public ReserveConfilm(WebDriver driver, Map<String, String> data, int timeout) {
        this(driver, data);
        this.timeout = timeout;
    }

    public void commit() {
    	driver.findElement(By.id("commit")).click();
    }

    /**
     * Click on Loading Link.
     *
     * @return the ReserveConfilm class instance.
     */
    public ReserveConfilm clickLoadingLink() {
        loading.click();
        return this;
    }


    /**
     * Verify that the page loaded completely.
     *
     * @return the ReserveConfilm class instance.
     */
    public ReserveConfilm verifyPageLoaded() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getPageSource().contains(pageLoadedText);
            }
        });
        return this;
    }

    /**
     * Verify that current page URL matches the expected URL.
     *
     * @return the ReserveConfilm class instance.
     */
    public ReserveConfilm verifyPageUrl() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getCurrentUrl().contains(pageUrl);
            }
        });
        return this;
    }
}
