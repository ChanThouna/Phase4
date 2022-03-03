import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

public class MobileActions {

    AppiumDriver localAppiumDriver;

    public void scrollUp(AppiumDriver driver)  {
        localAppiumDriver = driver;
        int height = localAppiumDriver.manage().window().getSize().getHeight();
        int width = localAppiumDriver.manage().window().getSize().getWidth();

        int startX = (int) (0.5 * width);
        int endX = (int) (0.5 * width);

        int startY = (int) (0.90 * height);
        int endY = (int) (0.10 * height);


        TouchAction action = new TouchAction(localAppiumDriver);

        action
                .longPress(PointOption.point(startX, startY))
                .moveTo(PointOption.point(endX, endY))
                .release()
                .perform();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
