package tests;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.PropertiesReader;

import java.io.IOException;
import java.time.Duration;

public class Test1 {
    private WebDriver driver;
    private Faker faker;

    @BeforeClass
    public void beforeClass() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

//        System.setProperty("webdriver.chrome.driver", PropertiesReader.fetchProperty("WEBDRIVER.CHROME.PATH"));
//        driver = new ChromeDriver();
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get("https://www.google.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().getPageLoadTimeout();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterClass
    public void close() {
        driver.quit();
    }

    @Test
    public void test1() {
        driver.findElement(By.tagName("input")).sendKeys("slike");
        driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[2]/div[2]/div[2]/ul[1]/div/ul/li[1]/div/div[2]/div[1]/span")).click();
        System.out.println(driver.findElement(By.xpath("//*[@id=\"rso\"]/div[2]/div/div[1]/div[1]/div/a/h3")).getText());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"rso\"]/div[2]/div/div[1]/div[1]/div/a/h3")).getText().contains("Moderne Slike za Tvoj Dom - Kupi Već Danas - IKEA"));
    }

    //    primer testa sa prioritetima
    @Test(priority = 1) //sto manjibroj, test je veceg prioriteta i pre se izvrsava
    public void firstTest() throws InterruptedException {
        driver.findElement(By.xpath("//body/div[1]/div[3]/form[1]/div[1]/div[1]/div[1]/div[1]/div[2]/input[1]")).sendKeys("images");

//        Thread.sleep(2000);
//          or
//        explicit wait
        WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body[1]/div[1]/div[3]/form[1]/div[1]/div[1]/div[2]/div[2]/div[2]/ul[1]/li[1]/div[1]/div[2]/div[1]/span[1]")));

        driver.findElement(By.xpath("//body[1]/div[1]/div[3]/form[1]/div[1]/div[1]/div[2]/div[2]/div[2]/ul[1]/li[1]/div[1]/div[2]/div[1]/span[1]")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"rso\"]/div[1]/div/div[2]/g-section-with-header/div[1]")).getText().contains("images"));

//      js executor koristimo da u konzoli ispisemo bilo koji js kod koje ce da se izvrsi
        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.scrollBy(0, 500)");
        js.executeScript("alert('HI!')");

    }

    //    primer testa sa dataProvider
    @Test(dataProvider = "dataProv")
    public void testDataProvider(String name, String lastName, int num) { //proizvoljno unosimo tip, izvrsice se kastovanje iz objekta u odabrani tip
        System.out.println("the test is performed as many times as there are rows in the data provider");
        System.out.println(name + " " + lastName + " " + num);
    }

    @DataProvider(name = "dataProv")
    public Object[][] mans() {                //uvek mora da bude vracen dvodimenzionalni niz tipa objekat
        Object[][] mans = new Object[][] {
                {"Milan", "Peric", 2},
                {"Borjan", "Ilic", 5}
        };
        return mans;
    }

    //    primer testa sa fakerom
    @Test(dataProvider = "dataProvFaker")
    public void testFaker(String name, int age) {
        System.out.println(name + " " + age);
    }

    @DataProvider(name = "dataProvFaker")
    public Object[][] faker() {
        faker = new Faker();
        return new Object[][] {
                {faker.name().firstName(), faker.number().numberBetween(18, 99)}, //nije dovoljno samo number ili name
                {faker.name().firstName(), faker.number().numberBetween(18, 99)}
        };
    }

//        Select pr = new Select(wd.findElement(By.name("_sacat")));
//        pr.selectByVisibleText("Crafts");
//        System.out.println(Random.getRandomUserEmail());

//        List<WebElement> links = wd.findElements(By.className("sf-with-ul"));
//        for (WebElement link : links) {
//            System.out.println(link.getAttribute("href"));
//        }

//        Actions actions = new Actions(wd);
//        WebElement arow = wd.findElement(By.xpath("//a[contains(text(),'Next')]"));
//        actions.moveToElement(arow).perform(); //hover

//    @AfterClass
//    public void exit() throws IOException {
////        Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");  //korisno staviti uz driver.close(), gasi sve zaostale prozore;
////        driver.close();
//    }
}