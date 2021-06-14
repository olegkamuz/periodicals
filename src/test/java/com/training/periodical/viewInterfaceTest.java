package com.training.periodical;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * Testing view interface (sorting, filtering, searching, paging)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class viewInterfaceTest {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "ext/chromedriver");

        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("headless");
        driver = new ChromeDriver(opt);

        driver.get("http://localhost:8080");
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    /**
     * Filter testing
     */
    @Test
    public void testCase_1() {
        try {
            driver.findElement(By.id("dropdownMenuLinkFilter")).click();
            for (WebElement we : driver.findElements(By.className("dropdown-item"))) {
                if (we.getText().equals("Спорт")) {
                    we.click();
                    break;
                }
            }

            List<WebElement> elements = driver.findElements(By.xpath("//div[@class='col-xl-3 col-custom col-custom-index']"));
            List<String> actualList = new ArrayList<>();

            for (WebElement we : elements) {
                actualList.add(we.findElement(By.className("name")).getText());
            }

            List<String> expectedList = new ArrayList<>();
            expectedList.add("Golf Monthly");
            expectedList.add("Procycling");
            expectedList.add("Bow International");
            expectedList.add("FourFourTwo");
            expectedList.add("Motor Boat And Yachting");
            assertEquals(expectedList, actualList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Filter + sorting testing
     */
    @Test
    public void testCase_2() {
        try {
            driver.findElement(By.id("dropdownMenuLinkFilter")).click();
            for (WebElement we : driver.findElements(By.className("dropdown-item"))) {
                if (we.getText().equals("Спорт")) {
                    we.click();
                    break;
                }
            }

            driver.findElement(By.id("dropdownMenuLink")).click();
            for (WebElement we : driver.findElements(By.className("dropdown-item"))) {
                if (we.getText().equals("Сортировать по имени по возрастанию")) {
                    we.click();
                    break;
                }
            }

            List<WebElement> elements = driver.findElements(By.xpath("//div[@class='col-xl-3 col-custom col-custom-index']"));
            List<String> actualList = new ArrayList<>();

            for (WebElement we : elements) {
                actualList.add(we.findElement(By.className("name")).getText());
            }

            List<String> expectedList = new ArrayList<>();
            expectedList.add("Bow International");
            expectedList.add("FourFourTwo");
            expectedList.add("Golf Monthly");
            expectedList.add("Horses And Hound");
            expectedList.add("Legends Of The NBA");

            assertEquals(expectedList, actualList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Filter + sorting + search testing
     */
    @Test
    public void testCase_3() {
        try {
            driver.findElement(By.id("dropdownMenuLinkFilter")).click();
            for (WebElement we : driver.findElements(By.className("dropdown-item"))) {
                if (we.getText().equals("Спорт")) {
                    we.click();
                    break;
                }
            }

            driver.findElement(By.id("dropdownMenuLink")).click();
            for (WebElement we : driver.findElements(By.className("dropdown-item"))) {
                if (we.getText().equals("Сортировать по имени по возрастанию")) {
                    we.click();
                    break;
                }
            }

            driver.findElement(By.name("search")).sendKeys("Legends");
            driver.findElement(By.id("search")).click();

            List<WebElement> elements = driver.findElements(By.xpath("//div[@class='col-xl-3 col-custom col-custom-index']"));
            List<String> actualList = new ArrayList<>();

            for (WebElement we : elements) {
                actualList.add(we.findElement(By.className("name")).getText());
            }

            List<String> expectedList = new ArrayList<>();
            expectedList.add("Legends Of The NBA");
            expectedList.add("Legends Of The NFL");

            assertEquals(expectedList, actualList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Filter + sorting + search + page testing
     */
    @Test
    public void testCase_4() {
        try {
            WebElement el = new WebDriverWait(driver, 10).until(presenceOfElementLocated(By.id("dropdownMenuLinkFilter")));
            el.click();
            for (WebElement we : driver.findElements(By.className("dropdown-item"))) {
                if (we.getText().equals("Музыка")) {
                    we.click();
                    break;
                }
            }

            driver.findElement(By.id("dropdownMenuLink")).click();
            for (WebElement we : driver.findElements(By.className("dropdown-item"))) {
                if (we.getText().equals("Сортировать по цене по убыванию")) {
                    we.click();
                    break;
                }
            }

            driver.findElement(By.id("search_input")).clear();
            driver.findElement(By.id("search_input")).sendKeys("");
            driver.findElement(By.id("search")).click();

            for (WebElement we : driver.findElements(By.className("page_number"))) {
                if (we.getText().equals("3")) {
                    we.click();
                    break;
                }
            }

            List<WebElement> elements = driver.findElements(By.xpath("//div[@class='col-xl-3 col-custom col-custom-index']"));
            List<String> actualList = new ArrayList<>();

            for (WebElement we : elements) {
                actualList.add(we.findElement(By.className("name")).findElement(By.tagName("p")).getText());
            }

            List<String> expectedList = new ArrayList<>();
            expectedList.add("The Story Of Burice Springsteen");
            expectedList.add("Classic Rock Special Metallica");
            expectedList.add("Classic Rock Special AC/DC");
            expectedList.add("100 Greatest Rock Songs Of All Time");
            expectedList.add("Guitar Technique");

            assertEquals(expectedList, actualList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
