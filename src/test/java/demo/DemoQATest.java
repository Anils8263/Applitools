import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.EyesRunner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.*;

import java.time.Duration;

public class DemoQATest {

    private WebDriver driver;
    private Eyes eyes;
    private EyesRunner runner;

    @BeforeClass
    public void setup() {
        // Set up WebDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Initialize the Runner and Eyes
        runner = new ClassicRunner();
        eyes = new Eyes(runner);
        eyes.setApiKey("wkA5vcNkQvbZfz11127ZFdtr9YuXkJKUCRXdKGHfk106iiY110"); // <-- Replace with your real API key

        // Optional: Set batch name
        eyes.setBatch(new BatchInfo("Demo QA Batch"));
    }

    @Test
    public void addFormVisualTest() {
        try {
            // Start the Applitools Eyes test
            eyes.open(driver, "Demo QA", "Add Form Visual Test", new RectangleSize(1024, 768));

            // Navigate to Web Tables page
            driver.get("https://demoqa.com/webtables");

            // Wait for and click the "Add" button
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("addNewRecordButton")));
            addButton.click();

            // Wait for the form to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));

            // Capture a visual snapshot of the form
            eyes.check("Add Form", Target.window());

            // Close Eyes test
            eyes.closeAsync();

        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() throws InterruptedException{
        if (driver != null) {
            try {
                Thread.sleep(5000);
                driver.quit();
            } catch (Exception e) {
                System.out.println("Failed to quit WebDriver: " + e.getMessage());
            }
        }

        if (eyes != null) {
            eyes.abortIfNotClosed();
        }
    }
}

