package pages.sample.test;

import java.util.List;
import java.util.Map;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sahagin.runlib.external.PageDoc;
import org.sahagin.runlib.external.TestDoc;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@PageDoc("予約フォーム")
public class ReserveForm {
    private Map<String, String> data;
    private WebDriver driver;
    private int timeout = 15;

    @FindBy(css = "a.lb-close")
    @CacheLookup
    private WebElement close;

    @FindBy(css = "a.lb-cancel")
    @CacheLookup
    private WebElement loading;

    @FindBy(id = "goto_next")
    private WebElement goto_next;

    private final String pageLoadedText = "";

    private final String pageUrl = "/reserveApp/";


    public ReserveForm() {
    }

    public ReserveForm(WebDriver driver) {
        this();
        this.driver = driver;
    }

    public ReserveForm(WebDriver driver, Map<String, String> data) {
        this(driver);
        this.data = data;
    }

    public ReserveForm(WebDriver driver, Map<String, String> data, int timeout) {
        this(driver, data);
        this.timeout = timeout;
    }
    
    @TestDoc("予約フォームを開く")
    public void openPage() {
    	driver.get("http://example.selenium.jp/reserveApp/");
    }
    
    @TestDoc("予約日「{reserveDay}」をセットする")
    public void setReserveDay(String reserveDay){
    	driver.findElement(By.id("reserve_day")).clear();
    	driver.findElement(By.id("reserve_day")).sendKeys(reserveDay);
    }
    
    @TestDoc("ゲスト名「{guestName}」をセットする")
    public void setGuestName(String guestName) {
    	driver.findElement(By.id("guestname")).sendKeys(guestName);
    }
   
    @TestDoc("「次へ」ボタンを押下")
    public ReserveForm goToNext() {
    	driver.findElement(By.id("goto_next")).click();
        return this;
    }
    
    /**
     * Click on Close Link.
     *
     * @return the test class instance.
     */
    public ReserveForm clickCloseLink() {
        close.click();
        return this;
    }

    /**
     * Click on Loading Link.
     *
     * @return the test class instance.
     */
    public ReserveForm clickLoadingLink() {
        loading.click();
        return this;
    }



    /**
     * Verify that the page loaded completely.
     *
     * @return the test class instance.
     */
    public ReserveForm verifyPageLoaded() {
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
     * @return the test class instance.
     */
    public ReserveForm verifyPageUrl() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getCurrentUrl().contains(pageUrl);
            }
        });
        return this;
    }
}
