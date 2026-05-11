package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportUI {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/reports/UI_Report.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("LMS B2B UI Automation Report");
            sparkReporter.config().setReportName("UI Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("Tester", "Diaz");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Module", "UI Frontend");
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Operating System", "MacOS");
            extent.setSystemInfo("Version Details",
                    "- RestAssured: v5.3.0 <br>" +
                            "- Chrome: v147.0.7727.138 <br>" +
                            "- Postman for Mac: v12.2.4 <br>" +
                            "- MacOS Tahoe: v26.4.1 <br>" +
                            "- Java: v21 <br>" +
                            "- App Version: 1.0.0");
            extent.setSystemInfo("UI Base URL", "https://lms-b2b.do.dibimbing.id/dibimbingqa/login");
        }
        return extent;
    }
}