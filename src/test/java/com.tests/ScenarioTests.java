package com.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class ScenarioTests {

    private WebDriver driver;
    private String username = "surbhi_jain1";
    private String accessKey = "iB3K2vQQsQiimn1wKPOVNrHW14xD5p0WSzPXfRGwGvqedGxdvg";
    private String hub = "https://hub.lambdatest.com/wd/hub";
    private String baseURL = "https://www.lambdatest.com/selenium-playground/";

    @Parameters({"Browser", "Version", "Platform"})
    @BeforeMethod
    public void setUp(String browser, String version, String platform) {
        // Setting ChromeOptions with LambdaTest capabilities
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName(platform);
        browserOptions.setBrowserVersion(version);

        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", username);
        ltOptions.put("accessKey", accessKey);
        ltOptions.put("project", "Cross Browser Testing");
        ltOptions.put("build", "1.7");
        ltOptions.put("name", "ScenarioTests");
        ltOptions.put("selenium_version", "4.0.0");
        ltOptions.put("w3c", true);
        ltOptions.put("console", "true");
        ltOptions.put("network", true);
        ltOptions.put("visual", true);
        ltOptions.put("video", true);
        ltOptions.put("browserName", browser);
        ltOptions.put("browserVersion", version);
        ltOptions.put("platform", platform);

        browserOptions.setCapability("LT:Options", ltOptions);

        try {
            driver = new RemoteWebDriver(new URL(hub), browserOptions);
            driver.manage().window().maximize();
            driver.get(baseURL);
            System.out.println("navigated to base url successfully");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test (timeOut = 20000)
    public void simpleFormDemo(){
        driver.findElement(By.linkText("Simple Form Demo")).click();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("simple-form-demo"));
        String str = "Welcome to LambdaTest";
        WebElement messageBox= driver.findElement(By.xpath("(//*[@id='user-message'])[1]"));
        messageBox.sendKeys(str);
        driver.findElement(By.id("showInput")).click();
        String actualText = driver.findElement(By.id("message")).getText();
        Assert.assertEquals(actualText, str);
    }

    @Test
    public void testDragAndDropSlider() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement dragDropSlidersLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Drag & Drop Sliders")));
        dragDropSlidersLink.click();

        WebElement slider = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='slider3']/div/input")));

        for (int i = 1; i <= 80; i++) {
            System.out.println(i);
            slider.sendKeys(Keys.ARROW_RIGHT);
        }
        String actualText = driver.findElement(By.id("rangeSuccess")).getText();
        Assert.assertEquals(actualText, "95");
    }

    @Test(timeOut = 40000)
    public void testInputFormSubmit() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement inputFormSubmitLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Input Form Submit")));
        inputFormSubmitLink.click();

        WebElement submitButton = driver.findElement(By.xpath("(//button[@type='submit'])[2]"));
        submitButton.click();

        WebElement nameField = driver.findElement(By.id("name"));
        WebElement emailField = driver.findElement(By.id("inputEmail4"));
        Assert.assertEquals(nameField.getAttribute("validationMessage"),"Please fill in this field.");

        nameField.sendKeys("Surbhi");
        emailField.sendKeys("abcd@xyz.com");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.name("company")).sendKeys("ABC Company");
        driver.findElement(By.name("website")).sendKeys("www.abc.com");
        WebElement countryDropdown = driver.findElement(By.name("country"));
        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByVisibleText("United States");
        driver.findElement(By.name("city")).sendKeys("Verna");
        driver.findElement(By.name("address_line1")).sendKeys("123 ");
        driver.findElement(By.name("address_line2")).sendKeys("101");
        driver.findElement(By.id("inputState")).sendKeys("GOA");
        driver.findElement(By.name("zip")).sendKeys("401101");

        submitButton.click();

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='success-msg hidden']")));
        String successText = successMessage.getText();
        System.out.println(successText);
        Assert.assertEquals(successText, "Thanks for contacting us, we will get back to you shortly.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
