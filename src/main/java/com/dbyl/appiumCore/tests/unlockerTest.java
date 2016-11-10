package main.java.com.dbyl.appiumCore.tests;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class unlockerTest {
	private AndroidDriver<MobileElement> driver;

	@Test
	public void Demo() throws Exception {
		// set up appium
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
		// for native app set null, for web test please set chrome or firefox
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");

		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
		// simulator version 4.4
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4");
		// if no need install don't add this
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "apps");
		File app = new File(appDir, "Locker.apk");
		capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

		// package name
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.AppiumGirls.locker");
		// // support Chinese
		capabilities.setCapability("unicodeKeyboard", "True");
		capabilities.setCapability("resetKeyboard", "True");
		// no need sign
		capabilities.setCapability("noSign", "True");
		// launcher activity
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");
		String url = "http://localhost:4723/wd/hub";
		driver = new AndroidDriver<MobileElement>(new URL(url), capabilities);

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		MobileElement button = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"设置手势密码\")");
		// tap 
		button.tap(1, 1000);
		// get all the items of gesture locker
		List<MobileElement> items = driver.findElementsByClassName("android.widget.ImageView");
		for (MobileElement item : items) {
			/**
			 * 0 1 2 3 4 5 6 7 8
			 */
			item.click();
		}

		// create a Z from 0->1->2->4->6->7->8
		TouchAction touches1 = new TouchAction(driver);
		touches1.press(items.get(0)).waitAction(1000).moveTo(items.get(1)).waitAction(1000).moveTo(items.get(2))
				.waitAction(1000).moveTo(items.get(4)).moveTo(items.get(6)).waitAction(1000).moveTo(items.get(7))
				.waitAction(1000).moveTo(items.get(8)).release();
		touches1.perform();
		Thread.sleep(2000);
		//create 0->1->2
		TouchAction touches2 = new TouchAction(driver);
		touches2.press(items.get(0)).waitAction(1000).moveTo(items.get(1)).waitAction(1000).moveTo(items.get(2))
				.waitAction(1000).moveTo(items.get(4)).release();
		touches2.perform();
		MobileElement alert =driver.findElementById("com.AppiumGirls.locker:id/text_tip");
		Assert.assertTrue(alert.getText().contains("与上一次绘制不一致，请重新绘制"));

	}
}