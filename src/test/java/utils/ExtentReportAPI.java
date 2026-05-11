package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportAPI {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/reports/API_Report.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("LMS B2B API Automation");
            sparkReporter.config().setReportName("API Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("Tester", "Diaz");
            extent.setSystemInfo("Environment", "Staging");
            extent.setSystemInfo("Module", "GraphQL API");
            extent.setSystemInfo("API Documentation", "https://lmsb2b.do.dibimbing.id/graphql");
        }
        return extent;
    }
}