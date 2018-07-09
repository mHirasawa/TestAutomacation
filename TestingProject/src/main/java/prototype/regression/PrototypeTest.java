package prototype.regression;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.im4java.core.CompareCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.im4java.process.StandardStream;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.reporters.*;
import pages.sample.test.ReserveConfilm;
import pages.sample.test.ReserveForm;

/**
 * プロトタイプ用のテスト自動化クラス
 * 
 * @author mhirasawa
 * @since 0.1
 * */
public class PrototypeTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();
	private int countSchreenshot = 0;
	String baseURL;

	/**
	 * 事前処理
	 * */
	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		// Chrome Driverのパスを指定
		System.setProperty("webdriver.chrome.driver", "src/main/resources/webdrivers/chromedriver.exe");
		// ImageMagickのパスを指定（im4javaはラッパークラスの為）
		ProcessStarter.setGlobalSearchPath("C:/Program Files/ImageMagick-7.0.7-Q16");
		// baseURL指定
		baseURL="http://example.selenium.jp";
		// Chromeを選択
		driver = new ChromeDriver();
	}
	
	
	@Test
	public void helloWorldTest() throws IOException {
		ReserveForm rF = new ReserveForm(driver);
		ReserveConfilm rC = new ReserveConfilm(driver);
		rF.openPage();
		getScreenshot();
		rF.setReserveDay("26");
		rF.setGuestName("せれにうむ男");
		rF.goToNext();
		getScreenshot();
		rC.commit();
		getScreenshot();
		Reporter.log("<b>テスト</b>");
		try {
			assertEquals(driver.getTitle(), "予約完了");
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

//	@Test
//	public void testHelloWorld2() throws IOException {
//		ReserveForm rF = new ReserveForm(driver);
//		ReserveConfilm rC = new ReserveConfilm(driver);
//		rF.openPage();
//		getScreenshot();
//		rF.setReserveDay("16");
//		rF.setGuestName("せれにうむ男");
//		rF.goToNext();
//		getScreenshot();
//		rC.commit();
//		getScreenshot();
//		try {
//			assertEquals(driver.getTitle(), "予約完了");
//		} catch (Error e) {
//			verificationErrors.append(e.toString());
//		}
//	}
	/**
	 * 事後処理
	 * 
	 * */
	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		compare();
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	public void getScreenshot() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile,
				new File("C:\\Jenkins\\jobs\\maven\\workspace\\テスト用スクショ\\work\\" + countSchreenshot + ".png"));
		countSchreenshot++;
	}

	public void compare() {
		// Compare the images
		for (int i = 0; i < countSchreenshot; i++) {
			boolean compareSuccess = compareImages(
					"C:/Jenkins/jobs/maven/workspace/テスト用スクショ/correct/" + i + ".png",
					"C:/Jenkins/jobs/maven/workspace/テスト用スクショ/work/" + i + ".png",
					"C:/Jenkins/jobs/maven/workspace/テスト用スクショ/result/" + i + ".png");
			System.out.println(i + "：" + compareSuccess);
		}
	}

	boolean compareImages(String exp, String cur, String diff) {
		// This instance wraps the compare command
		CompareCmd compare = new CompareCmd();

		// For metric-output
		compare.setErrorConsumer(StandardStream.STDERR);
		IMOperation cmpOp = new IMOperation();
		// Set the compare metric
		cmpOp.metric("mae");

		// Add the expected image
		cmpOp.addImage(exp);

		// Add the current image
		cmpOp.addImage(cur);

		// This stores the difference
		cmpOp.addImage(diff);

		try {
			// Do the compare
			compare.run(cmpOp);
			return true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
}