import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class BasicsTest {
    ExtentReports extent;
    ExtentTest test;
   // ExtentHtmlReporter htmlReporter;
    ExtentSparkReporter sparkReporter;

    @BeforeTest
    public void setUp() {
        sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("Test Report");
        sparkReporter.config().setReportName("Extent Reports");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            extent.setSystemInfo("Host Name", hostName);
            extent.setSystemInfo("Environment", "UAT Environment");
            extent.setSystemInfo("User Name", "Nishith");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testVirtualAccountRepayment() {
        test = extent.createTest("Virtual Account Repayment Test");
        files.payload.VirtualAccountRepayment();
        test.pass("Virtual Account Repayment Test Passed");
    }

    @Test
    public void testExternalPayments() {
        test = extent.createTest("External Payments Test");
        files.payload.ExternalPayments();
        test.pass("External Payments Test Passed");
    }

    @Test
    public void testCredgenicsBulkPayment() {
        test = extent.createTest("Credgenics Bulk Payment Test");
        files.payload.CredgenicsBulkPayment();
        test.pass("Credgenics Bulk Payment Test Passed");
    }

    @Test
    public void testFosOnlineDeposit() {
        test = extent.createTest("Fos Online Deposit Test");
        files.payload.FosOnlineDeposit();
        test.pass("Fos Online Deposit Test Passed");
    }

    @Test
    public void testFosCashDeposit() {
        test = extent.createTest("Fos Cash Deposit Test");
        files.payload.FosCashDeposit();
        test.pass("Fos Cash Deposit Test Passed");
    }

    @Test
    public void testBkQrRepayment() {
        test = extent.createTest("Bk QR Repayment Test");
        files.payload.bkQrRepayment();
        test.pass("Bk QR Repayment Test Passed");
    }

    @Test
    public void testBkPaymentLink() {
        test = extent.createTest("Bk PaymentLink Repayment Test");
        files.payload.bkPaymentLink();
        test.pass("Bk PaymentLink Repayment Test Passed");
    }

//    @Test
//    public void testAuthApiAutomation() {
//        test = extent.createTest("Farmer App Payment Link Generation Test");
//        files.payload.AuthApiAutomation();
//        test.pass("Farmer App Test Passed");
//    }
//
//    @Test
//    public void testFarmerAppPaymentLink() {
//        test = extent.createTest("Farmer App Payment Link Generation Test");
//        files.payload.farmerAppPaymentLink();
//        test.pass("Farmer App Test Passed");
//    }

    @AfterSuite
    public void tearDown() {
        if (extent != null) {
            extent.flush();
        }
    }
}
