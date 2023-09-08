package Sample;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Runner {

    private static WebDriver driver = null;
    public void setDriver(WebDriver webDriver) { driver = webDriver; }
    public WebDriver getDriver() { return driver; }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Runner runner = new Runner();
        runner.sessionOpen();
    }

    @Before
    public void setUp() throws Exception {
   }
    @After

    public void tearDown() throws Exception {
    }

    @Test
    public void scenario() {
        this.scenarioTest();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Runner runner = new Runner();
        runner.sessionClose();
    }

    private void sessionOpen() {
        WebDriver webDriver;
        String url = "https://illuminarean.com/";

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        webDriver = new ChromeDriver(options);
        webDriver.manage().window().setPosition(new Point(0, 0));
        webDriver.manage().window().setSize(new Dimension(1300, 1600));
        webDriver.get(url);
        new WebDriverWait(webDriver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains(webDriver.getCurrentUrl()));
        this.setDriver(webDriver);
    }

    private void sessionClose() {
        this.getDriver().quit();
    }

    //click
    private void click(WebDriver webDriver, String xpath) throws Exception {
        WebElement webElement = webDriver.findElement(By.xpath(xpath));
        webElement.click();
    }

    //loadWait
    private void loadWait(WebDriver webDriver) {
        new WebDriverWait(webDriver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains(webDriver.getCurrentUrl()));
    }

    private void loadWait(WebDriver webDriver, String xpath) throws Exception {
        FluentWait<WebDriver> fluentWait = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class);

        fluentWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    //scroll
    private void scroll(WebDriver webDriver, String xpath) throws Exception {
        WebElement webElement = webDriver.findElement(By.xpath(xpath));
        ((RemoteWebDriver) webDriver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",webElement);
    }

    //displayed
    private boolean displayed(WebDriver webDriver, String xpath) throws Exception {
        WebElement webElement = webDriver.findElement(By.xpath(xpath));
        return webElement.isDisplayed();
    }

    //sendkey
    private void sendKey(WebDriver webDriver, String xpath, String contents) throws Exception {
        WebElement webElement = webDriver.findElement(By.xpath(xpath));
        webElement.sendKeys(contents);
    }

    //switchTab
    public void switchTab(WebDriver webDriver, String url) throws Exception {
        List<String> tab = new ArrayList<>(webDriver.getWindowHandles());
        for(String temp : tab) {
            webDriver.switchTo().window(temp);
            this.setDriver(webDriver);
            this.loadWait(webDriver);
            if(webDriver.getCurrentUrl().contains(url)) {
                this.loadWait(webDriver);
                break;
            } else
                Thread.sleep(1000);
        }
    }

    //xpath
    private String text(String contents) {
        return "//*[contains(text(),'" +contents+ "')]";
    }

    private String xpath(String target) {
        String xpath = "";
        switch (target) {
            case "registration":
                xpath = "//button[contains(@class, 'css-1lby940 e1iwydzj0')]";
                break;
            case "work":
                xpath = "//span[normalize-space()='Work']";
                break;
            case "workTitle":
                xpath = "//div[contains(@class, 'work_title')]";
                break;
            case "freeTrial":
                xpath = "(//button[contains(text(),'무료 체험 신청')])[1]";
                break;
            case "companyName":
                xpath = "//input[contains(@id, 'companyName')]";
                break;
            case "ceoName":
                xpath = "//input[contains(@id, 'ceoName')]";
                break;
            case "manager":
                xpath = "//input[contains(@id, 'name')]";
                break;
            case "email":
                xpath = "//input[contains(@id, 'email')]";
                break;
            case "phoneNumber":
                xpath = "//input[contains(@id, 'mobile')]";
                break;
            case "responsibilities":
                xpath = "//div[@class='css-l5eay4 el0tj993']";
                break;
            case "responsibilitiesAdd":
                xpath = "//button[contains(text(),'등록')]";
                break;
            case "responsibilitiesSearch":
                xpath = "//input[@placeholder='업무명 검색']";
                break;
            case "responsibilitiesSearchResult":
                xpath = "//*[contains(text(), '의전') and ./parent::*[contains(@class, 'css-s0v51g el0tj995')]]";
                break;
            case "termsService":
                xpath = "//label[@for='agreeTermsOfUse']";
                break;
            case "personalInfo":
                xpath = "//span[contains(text(),'개인정보 취급방침 동의')]";
                break;
            default:
                System.out.println("not Selected");
                break;
        }
        return xpath;
    }

    private void scenarioTest() {
        String goodvibe = "https://works.goodvibe.kr/";

        try {
            System.out.println("인재 POOL 등록하기 - 표시 대기");
            this.loadWait(this.getDriver(), this.xpath("registration"));
            System.out.println("인재 POOL 등록하기 - 닫기 \n");
            this.click(this.getDriver(), this.xpath("registration"));
            Thread.sleep(2000);

            System.out.println("Work - 버튼 클릭");
            this.click(this.getDriver(), this.xpath("work"));
            System.out.println("Work - 페이지 표시 대기 \n");
            this.loadWait(this.getDriver(), this.xpath("workTitle"));

            System.out.println("GOODVIBE WORKS 바로가기 - 화면 이동");
            this.scroll(this.getDriver(), this.text("GOODVIBE WORKS 바로가기"));
            Thread.sleep(2000);
            System.out.println("GOODVIBE WORKS 바로가기 - 버튼 클릭 \n");
            this.click(this.getDriver(), this.text("GOODVIBE WORKS 바로가기"));

            System.out.println("탬 전환 - " +goodvibe);
            this.switchTab(this.getDriver(), goodvibe);

            System.out.println("무료 체험 신청 - 표시 대기");
            this.loadWait(this.getDriver(), this.xpath("freeTrial"));
            System.out.println("무료 체험 신청 - 버튼 클릭 \n");
            this.click(this.getDriver(), this.xpath("freeTrial"));

            System.out.println("서비스 이용신청 - 표시 대기");
            this.loadWait(this.getDriver(), this.xpath("companyName"));

            System.out.println("회사명 - 입력");
            this.sendKey(this.getDriver(), this.xpath("companyName"), "테스트 자동화");

            System.out.println("대표자명 - 입력");
            this.sendKey(this.getDriver(), this.xpath("ceoName"), "자동화");

            System.out.println("사업자 유형 및 직원수 - Skip");
            System.out.println("담당자명 - 입력");
            this.sendKey(this.getDriver(), this.xpath("manager"), "홍길동");

            System.out.println("이메일 - 입력");
            this.sendKey(this.getDriver(), this.xpath("email"), "automation@gmail.gmail");

            System.out.println("휴대폰 번호 - 입력");
            this.sendKey(this.getDriver(), this.xpath("phoneNumber"), "01012345678");

            System.out.println("담당업무 - 선택");
            this.click(this.getDriver(), this.xpath("responsibilities"));
            this.click(this.getDriver(), this.text("인사"));
            Thread.sleep(3000);

            System.out.println("담당업무 - 입력");
            this.sendKey(this.getDriver(), this.xpath("responsibilitiesSearch"), "의전");
            Thread.sleep(3000);

            System.out.println("담당업무 - 검색결과 표시 확인");
            if(this.displayed(this.getDriver(), this.xpath("responsibilitiesSearchResult"))) {
                System.out.println("검색결과 화면에서 '의전' 항목 식별 성공");
                this.click(this.getDriver(), this.xpath("responsibilitiesSearchResult"));
                Thread.sleep(3000);
                this.click(this.getDriver(), this.xpath("responsibilitiesAdd"));
            } else {
                System.out.println("검색결과 화면에서 '의전' 항목 식별 실패");
                throw new Exception();
            }

            System.out.println("서비스 이용약관 동의 - 클릭");
            this.click(this.getDriver(), this.xpath("termsService"));

            System.out.println("개인정보 취급방침 동의 - 클릭");
            this.click(this.getDriver(), this.xpath("personalInfo"));
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
