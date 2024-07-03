import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import util.EmailUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.mail.MessagingException;


public class BasicsTest {
    ExtentReports extent;
    ExtentTest test;
    ExtentSparkReporter sparkReporter;
	String reportPath = "test-output/ExtentReport.html"; // Initialize reportPath

    @BeforeTest
    public void setUp() {
    	String reportPath;
    	reportPath = "test-output/ExtentReport.html"; // Initialize reportPath
        sparkReporter = new ExtentSparkReporter(reportPath);
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

        // Email configuration
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "loadtestingatjmeter@gmail.com"; // replace with your email
        String password = "enrp mtbg rzfm znsj"; // replace with your email password

        // Outgoing message information
        String[] mailTo = {"nishith.in@jai-kisan.com", "ramesh.patel@jai-kisan.com"}; // replace with recipient emails
        String subject = "Test Report for Repayments Api";
        String message = "Please find the attached test report.";

        // Attachments
        String[] attachFiles = new String[1];
        attachFiles[0] = reportPath;

        try {
            EmailUtil.sendEmailWithAttachments(host, port, mailFrom, password, mailTo, subject, message, attachFiles);
            System.out.println("Email sent successfully.");
        } catch (MessagingException ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    }
}
    

