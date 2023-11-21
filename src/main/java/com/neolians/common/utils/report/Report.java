package com.neolians.common.utils.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import com.neolians.common.utils.params.ConfigHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.TestInfo;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.TestAttribute;
import com.neolians.common.utils.DoAssert;
import com.neolians.common.utils.ResourceFile;
import com.neolians.common.utils.Util;
import com.neolians.common.utils.selenium.Browser;
import com.neolians.common.utils.selenium.Driver;
import com.neolians.website.utils.Version;

import io.cucumber.java.Scenario;
import io.github.artsok.RepeatedIfExceptionsTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class Report {

	public static String currentBugId;
	private static String video;
	private static ExtentTest extentTest;
	// region Report
	private static ExtentReport report = null;
	private static int testcaseExecutionNb = 1;
	public static File lastScreenShot = null;
	public static File lastHtmlPage = null;

	static void initialize() {
		if (report == null) {
			report = ExtentReport.Instance();
		}
	}

	public static File getFile() {
		return report.getFile();
	}

	// endregion
	// region Test
	static ExtentTest getExtentTest() {
		if (extentTest == null) {
			startTest((TestInfo) null);
		}

		return extentTest;
	}

	/**
	 * @param testInfo : Test/ method name
	 */
	static void startTest(TestInfo testInfo) {
		if (report == null) {
			initialize();
		}
		lastScreenShot = null;
		lastHtmlPage = null;
		currentBugId = null;
		if (testcaseExecutionNb > 1) {
			// on est en mode de repetition d'un cas de test
			getExtentTest().log(LogStatus.UNKNOWN, "<span class='relaunch'>" + "Start AGAIN THE TESTCASE  iteration: #"
					+ testcaseExecutionNb + "</span>");

		} else {
			testcaseExecutionNb = 1;
			if (testInfo == null) {
				extentTest = report.startTest("default Test");
			} else {
				extentTest = report.startTest(testInfo.getDisplayName());
			}

			setTestcaseName(testInfo);
		}
	}

	/**
	 * @param scenario : Test/ method name
	 */
	public static void startTest(Scenario scenario) {
		if (report == null) {
			initialize();
		}
		currentBugId = null;
		DoAssert.currentErrorMessages = new StringBuilder();
		if (scenario == null) {
			extentTest = report.startTest("default Test");
		} else {
			extentTest = report.startTest(scenario.getName());
			setReportInformation(scenario);
			final String file = scenario.getUri().getPath();
			final String[] relPaths = file.substring(file.indexOf("resources") + "resources".length() + 1).split("/");
			final StringBuilder category = new StringBuilder();
			for (int i = 0; i < (relPaths.length - 1); i++) {
				if (i > 0) {
					category.append("->");
				}
				category.append(relPaths[i]);
			}
			setName(scenario.getName(), category.toString());
		}
	}

	public static void endTest(boolean needTestReexecution) {
		if (needTestReexecution) {
			testcaseExecutionNb++;
		} else {
			testcaseExecutionNb = 1;
			report.endTest(Report.getExtentTest());
		}
		report.flush();
	}

	private static void setReportInformation(TestInfo testInfo) {
		if (!testInfo.getTestMethod().isPresent()) {
			return;
		}
		final JunitTestInfo junitTestInfo = testInfo.getTestMethod().get().getAnnotation(JunitTestInfo.class);

		String testcaseDescription = "";
		setTestcaseName(testInfo);
		if (junitTestInfo != null) {
			testcaseDescription = junitTestInfo.description();
		}

		testcaseDescription = testcaseDescription.replaceAll("\n", "<br/>");
		testcaseDescription += "<br/>";

		// add Browser type
		String iconBrowser = "navigateur.png";
		if (Browser.getBrowser() == Browser.FIREFOX) {
			iconBrowser = "firefox.png";
		} else if (Browser.getBrowser() == Browser.IE) {
			iconBrowser = "ie.png";
		} else if (Browser.getBrowser() == Browser.CHROME) {
			iconBrowser = "chrome.png";
		}

		testcaseDescription = testcaseDescription + formatHtmlDescription(ConfigHelper.BROWSER.getValue().toUpperCase()
				+ (Driver.browserVersion != null ? " " + Driver.browserVersion : ""), iconBrowser);

		// add Version type
		/*
		 * String iconversion = "version.png"; testcaseDescription = testcaseDescription
		 * + formatHtmlDescription(LSVersion.getVersionName(), iconversion);
		 */
		// add Video
		if (Report.video != null) {
			testcaseDescription = testcaseDescription + formatHtmlDescription(
					"<a title='Video' href='" + Report.video + "' target=_blank>" + "Video</a>", "video.png");
		}

		testcaseDescription += "<hr/>";
		Report.setDescription(testcaseDescription);
	}

	private static void setReportInformation(Scenario scenario) {

		StringBuilder testcaseDescription = new StringBuilder();
		// add tags
		for (final String tag : scenario.getSourceTagNames()) {
			testcaseDescription.append(formatHtmlDescription(tag, "cucumber.jpg"));
		}
		testcaseDescription = new StringBuilder(testcaseDescription.toString().replaceAll("\n", "<br/>"));
		testcaseDescription.append("<br/>");

		// add Browser type
		String iconBrowser = "navigateur.png";
		if (Browser.getBrowser() == Browser.FIREFOX) {
			iconBrowser = "firefox.png";
		} else if (Browser.getBrowser() == Browser.IE) {
			iconBrowser = "ie.png";
		} else if (Browser.getBrowser() == Browser.CHROME) {
			iconBrowser = "chrome.png";
		}

		testcaseDescription.append(formatHtmlDescription(ConfigHelper.BROWSER.getValue().toUpperCase()
				+ (Driver.browserVersion != null ? " " + Driver.browserVersion : ""), iconBrowser));

		// add Version type
		final String iconversion = "version.png";
		testcaseDescription.append(formatHtmlDescription(Version.getVersionName(), iconversion));
		// add Video
		if (Report.video != null) {
			testcaseDescription.append(formatHtmlDescription(
					"<a title='Video' href='" + Report.video + "' target=_blank>" + "Video</a>", "video.png"));
		}

		testcaseDescription.append("<hr/>");
		Report.setDescription(testcaseDescription.toString());
	}

	/**
	 * Update Report information at the test End in order to update bugId
	 *
	 * @param testInfo current test (JUnit)
	 */
	static void updateTestDescription(TestInfo testInfo) {
		setReportInformation(testInfo);
	}

	/**
	 * Update Report information at the test End in order to update bugId
	 *
	 * @param scenario current scenario (Gherkin)
	 */
	public static void updateTestDescription(Scenario scenario) {
		setReportInformation(scenario);
	}

	private static void setTestcaseName(TestInfo testInfo) {
		if (testInfo == null || !testInfo.getTestMethod().isPresent()) {
			return;
		}

		final StringBuilder category = new StringBuilder();
		if (testInfo.getTestClass().isPresent()) {
			final String[] pkgs = testInfo.getTestClass().get().getName().split("\\.");
			for (int i = 4; i < pkgs.length - 1; i++) {
				if (i > 4) {
					category.append(".");
				}
				category.append(pkgs[i]);
			}
		}
		final JunitTestInfo junitTestInfo = testInfo.getTestMethod().get().getAnnotation(JunitTestInfo.class);

		String tcName;
		String defaultTcName = testInfo.getDisplayName();
		if (junitTestInfo != null) {
			// Ajout du Dataset si présent
			if (!junitTestInfo.dataset().equals("")) {
				defaultTcName = defaultTcName + " (Dataset: " + junitTestInfo.dataset() + ")";
			}
		}
		if (junitTestInfo != null) {
			tcName = junitTestInfo.id() + " - " + defaultTcName;
		} else if (testInfo.getTestMethod().isPresent()
				&& testInfo.getDisplayName().startsWith(RepeatedIfExceptionsTest.SHORT_DISPLAY_NAME.substring(0, 18))) {
			// if no JUnitTestInfo Annotation
			tcName = testInfo.getTestMethod().get().getName();
		} else {
			tcName = defaultTcName;
		}
		Report.setName(tcName, category.toString());
	}

	private static String formatHtmlDescription(String itemDesc, String iconFile) {
		return formatHtmlDescription(itemDesc, iconFile, "blue");
	}

	private static String formatHtmlDescription(String itemDesc, String iconFile, String labelColot) {
		try {
			final File pathImgJira = ResourceFile.getFileInTargetsFolder("report/" + iconFile);
			final String relativeHtmlPath = "/images/" + iconFile;
			final File oDestJira = new File(ExtentReport.getReportFolder().getAbsolutePath() + relativeHtmlPath);
			FileUtils.copyFile(pathImgJira, oDestJira);
			return "<span class='descItem'><img SRC='." + relativeHtmlPath + "' />"
					+ "<span class='test-started-time label " + labelColot + " lighten-1 text-white'>" + itemDesc
					+ "</span></span>";
		} catch (final IOException ignored) {
		}
		return "";
	}

	// endregion
// region testcaseName
	/*
	
	
	 */

	/**
	 * @param testcaseDescription : testcase description
	 */
	public static void setDescription(String testcaseDescription) {
		extentTest.getTest().setDescription(testcaseDescription);
	}

	/**
	 * @param testName  : Test/ method name
	 * @param className : class name
	 */
	private static void setName(String testName, String className) {
		String squashTestName = testName;
		if (report.isMavenLaunch) {
			squashTestName = className + " -> " + testName;
		}
		extentTest.getTest().setName(squashTestName);

		boolean found = false;
		for (final TestAttribute cat : extentTest.getTest().getCategoryList()) {
			if (cat.getName().equalsIgnoreCase(className)) {
				found = true;
			}
		}
		if (!found) {
			extentTest.assignCategory(className);
		}
	}
//endregion

	/**
	 * Display a Failure in the extentTest log
	 *
	 * @param message message to display
	 */
	public static void failed(String message) {
		failed(message, false);
	}

	/**
	 * Display a Failure in the extentTest log
	 *
	 * @param message  message to display
	 * @param linkFile File to attach
	 */
	public static void failed(String message, File linkFile) {
		failed(message, false, linkFile, null);
	}

	/**
	 * Display a Failure in the extentTest log
	 *
	 * @param message        message to display
	 * @param takeScreenshot true to take web driver snapshot
	 */
	public static void failed(String message, boolean takeScreenshot) {
		failed(message, takeScreenshot, null, null);
	}

	/**
	 * Display a Failure in the extentTest log
	 *
	 * @param message        message to display
	 * @param takeScreenshot true to take web driver snapshot
	 * @param linkFile       File to attach
	 */
	public static void failed(String message, boolean takeScreenshot, File linkFile, String bugID) {
		failed(message, takeScreenshot, linkFile, bugID, true);
	}

	/**
	 * Display a Failure in the extentTest log
	 *
	 * @param message        message to display
	 * @param takeScreenshot true to take web driver snapshot
	 * @param linkFile       File to attach
	 * @param escapeHtml     to escape Html character in message
	 */
	@Step("FAILURE - {message}.")
	public static void failed(String message, boolean takeScreenshot, File linkFile, String bugID, boolean escapeHtml) {
		String encodedMsg = displayBugId(bugID, false);

		encodedMsg += (escapeHtml ? StringEscapeUtils.escapeHtml4(message) : message);
		String imgPathHtml;
		String msg = "";
		if (linkFile != null) {
			imgPathHtml = copyFileToReportFolder(linkFile);
			msg = msg + imgPathHtml;
		}
		imgPathHtml = getSnapshotHtml(takeScreenshot);
		getExtentTest().log(LogStatus.FAIL,
				"<font color='red'>" + encodedMsg.replace("\n", "<br/>").concat("</font>") + imgPathHtml + msg);
		System.out.println(logPrefix("FAILURE") + (bugID != null ? "Defect: " + bugID + " - " : "") + message);
	}

	/**
	 * Display bug icon in HTML extentTest
	 *
	 * @param bugID   bug id
	 * @param oneLine display info in one line (Used for Verify action)
	 * @return bug icon in HTML extentTest
	 */
	public static String displayBugId(String bugID, boolean oneLine) {

		if (bugID == null || bugID.equalsIgnoreCase("")) {
			return "";
		}
		return _displayBugId(bugID, oneLine);
	}

	/**
	 * Display bug icon in HTML extentTest
	 *
	 * @param bugID   bug id
	 * @param oneLine display info in one line (Used for Verify action)
	 * @return bug icon in HTML extentTest
	 */
	public static String _displayBugId(String bugID, boolean oneLine) {

		return "";
	}

	/**
	 * Display a Success in the extentTest log
	 *
	 * @param message message to display
	 */
	public static void success(String message) {
		success(message, false);
	}

	/**
	 * Display a Success in the extentTest log
	 *
	 * @param message        message to display
	 * @param takeScreenshot true to take web driver snapshot
	 */
	public static void success(String message, boolean takeScreenshot) {
		success(message, takeScreenshot, null);
	}

	/**
	 * Display a Success in the extentTest log
	 *
	 * @param message  message to display
	 * @param linkFile File to attach
	 */
	public static void success(String message, File linkFile) {
		success(message, false, linkFile);
	}

	/**
	 * Display a Success in the extentTest log
	 *
	 * @param message        message to display
	 * @param takeScreenshot true to take web driver snapshot
	 * @param linkFile       File to attach
	 */
	public static void success(String message, boolean takeScreenshot, File linkFile) {
		success(message, takeScreenshot, linkFile, true);
	}

	/**
	 * Display a Success in the extentTest log
	 *
	 * @param message        message to display
	 * @param takeScreenshot true to take web driver snapshot
	 * @param linkFile       File to attach
	 */
	@Step("Success - {message}.")
	public static void success(String message, boolean takeScreenshot, File linkFile, boolean escapeHtml) {
		String imgPathHtml = getSnapshotHtml(takeScreenshot);
		if (linkFile != null) {
			imgPathHtml = copyFileToReportFolder(linkFile);
		}
		final String encodedMsg = (escapeHtml ? StringEscapeUtils.escapeHtml4(message) : message);
		getExtentTest().log(LogStatus.PASS,
				"<font color='green'>" + encodedMsg.replace("\n", "<br/>").concat("</font>") + imgPathHtml);
		System.out.println(logPrefix("SUCCESS") + message);
	}

	/**
	 * Display a Info in the extentTest log
	 *
	 * @param message message to display
	 */
	public static void info(String message) {
		info(message, false);
	}

	/**
	 * Display a Info in the extentTest log
	 *
	 * @param message        message to display
	 * @param takeScreenshot true to take web driver snapshot
	 */
	public static void info(String message, boolean takeScreenshot) {
		info(message, takeScreenshot, true);
	}

	/**
	 * Display a Info in the extentTest log
	 *
	 * @param message        message to display
	 * @param takeScreenshot true to take web driver snapshot
	 * @param escapeHtml     false if message contains html
	 */
	public static void info(String message, boolean takeScreenshot, boolean escapeHtml) {
		final String imgPathHtml = getSnapshotHtml(takeScreenshot);
		final String encodedMsg = (escapeHtml ? StringEscapeUtils.escapeHtml4(message) : message);

		getExtentTest().log(LogStatus.INFO,
				"<font color='blue'>" + encodedMsg.replace("\n", "<br/>").concat("</font>") + imgPathHtml);
		System.out.println(logPrefix("INFO") + message);
	}

	/**
	 * Display a Info in the extentTest log
	 *
	 * @param message message to display
	 */
	public static void skip(String message) {

		final String encodedMsg = StringEscapeUtils.escapeHtml4(message);

		getExtentTest().log(LogStatus.SKIP, encodedMsg.replace("\n", "<br/>"));
		System.out.println(logPrefix("SKIP") + message);
	}

	/**
	 * Display a Info in the extentTest log
	 *
	 * @param stepNumber       step number
	 * @param shortDescription short step description
	 * @param takeScreenshot   true to take web driver snapshot
	 */
	public static void infoStep(int stepNumber, String shortDescription, boolean takeScreenshot) {
		final String imgPathHtml = getSnapshotHtml(takeScreenshot);
		final String encodedMsg = StringEscapeUtils.escapeHtml4(stepNumber + " - " + shortDescription);
		getExtentTest().log(LogStatus.INFO, "************ TC STEP : "
				+ encodedMsg.replace("\n", "<br/>").concat("</font>") + " ************" + imgPathHtml);
	}

	/**
	 * Display a Info in the extentTest log
	 *
	 * @param shortDescription short step description
	 * @param takeScreenshot   true to take web driver snapshot
	 */
	public static void infoStep(String shortDescription, boolean takeScreenshot) {
		final String imgPathHtml = getSnapshotHtml(takeScreenshot);
		final String encodedMsg = StringEscapeUtils.escapeHtml4(shortDescription);
		getExtentTest().log(LogStatus.INFO, "************ TC STEP : "
				+ encodedMsg.replace("\n", "<br/>").concat("</font>") + " ************" + imgPathHtml);
	}

	/**
	 * Display a Info in the extentTest log et lie un fichier
	 *
	 * @param message  message to display
	 * @param linkFile Fichier à lier
	 */
	public static void info(String message, File linkFile) {
		final String encodedMsg = StringEscapeUtils.escapeHtml4(message);
		String imgPathHtml = "";
		if (linkFile != null) {
			imgPathHtml = copyFileToReportFolder(linkFile);

		}
		getExtentTest().log(LogStatus.INFO, encodedMsg.replace("\n", "<br/>") + imgPathHtml);
		System.out.println(logPrefix("INFO") + message);
	}

	/**
	 * Display a Warning in the extentTest log
	 *
	 * @param message message to display
	 */
	public static void warning(String message) {
		warning(message, false);
	}

	/**
	 * Display a Warning in the extentTest log
	 *
	 * @param message        message to display
	 * @param takeScreenshot true to take web driver snapshot
	 */
	public static void warning(String message, boolean takeScreenshot) {
		final String imgPathHtml = getSnapshotHtml(takeScreenshot);
		final String encodedMsg = StringEscapeUtils.escapeHtml4(message);

		getExtentTest().log(LogStatus.WARNING,
				"<font color='#ff8c00'>" + encodedMsg.replace("\n", "<br/>").concat("</font") + imgPathHtml);
		System.out.println(logPrefix("WARNING") + message);
	}

	/**
	 * Display a Error in the extentTest log
	 *
	 * @param ex exception to log
	 */
	public static void error(Throwable ex) {
		error(ex.getMessage(), ex.getStackTrace());
	}

	/**
	 * Display a Error in the extentTest log
	 *
	 * @param messageEx message
	 */
	public static void error(String messageEx, StackTraceElement[] stackTraceElements) {
		final String stackMessage = DoAssert.displayErrorForWeb(stackTraceElements);
		error(messageEx + "\n" + stackMessage, false);
	}

	/**
	 * Display a Error in the extentTest log
	 *
	 * @param message message to display
	 */
	public static void error(String message) {
		error(message, false);
	}

	/**
	 * Display a Error in the extentTest log
	 *
	 * @param message        message to display
	 * @param takeScreenshot true to take web driver snapshot
	 */
	public static void error(String message, boolean takeScreenshot) {
		final String imgPathHtml = getSnapshotHtml(takeScreenshot);
		final String encodedMsg = StringEscapeUtils.escapeHtml4(message);

		getExtentTest().log(LogStatus.ERROR,
				"<font color='red'>" + encodedMsg.replace("\n", "<br/>").concat("</font>") + imgPathHtml);
		System.out.println(logPrefix("ERROR") + message);
	}

	/**
	 * Display a Success in the extentTest log
	 *
	 * @param stepName       specify a category
	 * @param takeScreenshot true to take web driver snapshot
	 */
	public static void error(String stepName, Throwable ex, boolean takeScreenshot) {
		final String imgPathHtml = getSnapshotHtml(takeScreenshot);
		final StringBuilder message = new StringBuilder("Error on " + stepName + "\n");
		message.append(ex.getMessage());
		final String root = "com.sinext";
		boolean start = false;

		int i = 0;
		while (true) {
			final String current = ex.getStackTrace()[i].toString();
			if (!start || current.contains(root)) {
				message.append("\n").append(current);
			}
			i++;
			if (current.contains(root)) {
				start = true;
			}
			if (start && !current.contains(root)) {
				break;
			}
			if (!start) {
				continue;
			}
			if (i == ex.getStackTrace().length) {
				break;
			}
		}
		final String encodedMsg = StringEscapeUtils.escapeHtml4(message.toString());

		getExtentTest().log(LogStatus.ERROR,
				"<font color='red'>" + encodedMsg.replace("\n", "<br/>").concat("</font>") + imgPathHtml);
		System.out.println(logPrefix("ERROR") + message);
	}

	/**
	 * take a screen shot of running execution
	 *
	 * @param takeScreenshot true if we display snapshot
	 * @return screen shot html of running execution
	 */
	private static String getSnapshotHtml(boolean takeScreenshot) {
		String imgPathHtml = "";

		if (takeScreenshot) {
			final String snapshotFile = ExtentReport.captureScreen();
			if (snapshotFile != null) {
				if (snapshotFile.endsWith(".html")) {
					imgPathHtml = "<a href='" + snapshotFile + "' target='_blank'>actual page</a>";
					lastHtmlPage = new File(Util.concatenatePath(Report.getFile().getParent(), snapshotFile, "/"));
					try {
						Allure.addAttachment("Attached File", new FileInputStream(lastHtmlPage));
					} catch (final FileNotFoundException ignored) {
					}
				} else {
					lastScreenShot = new File(Util.concatenatePath(Report.getFile().getParent(), snapshotFile, "/"));
					imgPathHtml = getExtentTest().addScreenCapture(snapshotFile);
					lastHtmlPage = new File(Util.concatenatePath(Report.getFile().getParent(), snapshotFile, "/")
							.replace(".png", ".html"));
					imgPathHtml += "<a href='" + snapshotFile.replace(".png", ".html")
							+ "' target='_blank'>actual page</a>";
					try {
						Allure.addAttachment("Screenshot", new FileInputStream(lastScreenShot));
						Allure.addAttachment("Attached File", new FileInputStream(lastHtmlPage));
					} catch (final FileNotFoundException ignored) {
					}
				}
			}
		}

		return imgPathHtml;
	}

	/**
	 * Copy the file to link in Report imagele lien Html to a specific link
	 *
	 * @param linkFile File to link
	 * @return html link
	 */
	private static String copyFileToReportFolder(File linkFile) {
		final String relativeFilePath = ExtentReport.copyFileToReportFolder(linkFile);
		String imgFile = "";
		try {
			final File pathImgAttachedFile = ResourceFile.getFileInTargetsFolder("report/file.png");
			if (pathImgAttachedFile.exists()) {
				final String relativeHtmlPath = "/images/file.png";
				final File oDestJira = new File(ExtentReport.getReportFolder().getAbsolutePath() + relativeHtmlPath);
				FileUtils.copyFile(pathImgAttachedFile, oDestJira);
				imgFile = "<img width='30px' src='." + relativeHtmlPath + "' />";
			}
		} catch (final IOException ignored) {

		}
		try {
			Allure.addAttachment("Attached File", new FileInputStream(new File(relativeFilePath)));
		} catch (final FileNotFoundException ignored) {
		}
		return " <a href='" + relativeFilePath + "' target='_blank'>" + imgFile + " Attached File</a>";
	}

	/**
	 * Copy the video file to link in Report video folder
	 *
	 * @param tempVideoFile temp video File
	 */
	public static void copyVideoFileToReportFolder(File tempVideoFile) throws IOException {
		final String relativeHtmlPath = "/video/video.mov";
		final File oDestVideo = new File(ExtentReport.getReportFolder().getAbsolutePath() + relativeHtmlPath);
		FileUtils.copyFile(tempVideoFile, oDestVideo);

		Report.video = "." + relativeHtmlPath;
		Report.info("<a href='" + oDestVideo.getCanonicalPath() + "' target='_blank'>Recorded video</a>", false, false);
		System.out.println("\nVideo file: " + oDestVideo.getCanonicalPath());
	}

	// region newStep

	/**
	 * New step info
	 *
	 * @param stepInfo step name
	 */
	public static void newStep(String stepInfo) {
		newStep(null, stepInfo, false);
	}

	/**
	 * New step info
	 *
	 * @param stepInfo   step name
	 * @param stepNumber Step number
	 */
	public static void newStep(int stepNumber, String stepInfo) {
		newStep(Integer.toString(stepNumber), stepInfo, false);
	}

	/**
	 * New step info
	 *
	 * @param stepInfo   step name
	 * @param stepNumber Step number
	 */
	public static void newStep(String stepNumber, String stepInfo) {
		newStep(stepNumber, stepInfo, false);
	}

	private static Long startStepTimestamp = null;
	public static String currentStepName = null;

	/**
	 * Display a NewStep in the extentTest log
	 *
	 * @param stepInfo       step name
	 * @param takeScreenshot true to take web driver snapshot
	 */
	@Step("Step {stepNumber}: {stepInfo}.")
	public static void newStep(String stepNumber, String stepInfo, boolean takeScreenshot) {
		final String imgPathHtml = getSnapshotHtml(takeScreenshot);
		String message;
		if (StringUtils.isEmpty(stepNumber)) {
			message = "Steps " + stepInfo;
		} else if (stepNumber.contains("-")) {
			message = "Steps " + stepNumber + " " + stepInfo;
		} else {
			message = "Step " + stepNumber + " - " + stepInfo;
		}

		String msgDuration = "";
		if (startStepTimestamp != null) {
			final int sec = Math.toIntExact(System.currentTimeMillis() - startStepTimestamp) / 1000;
			msgDuration = " <span class='stepDuration'>(previous step duration: " + Util.timeHumanReadable(sec) + ", "
					+ sec + " s )</span>";
		}
		final String encodedMsg = "<span class='newStep'>" + StringEscapeUtils.escapeHtml4(message) + msgDuration
				+ "</span>";
		getExtentTest().log(LogStatus.UNKNOWN, encodedMsg.replace("\n", "<br/>") + imgPathHtml);
		System.out.println("\n\n" + message);
		startStepTimestamp = System.currentTimeMillis();
		currentStepName = message;
	}
//endregion

	/**
	 * add date in log file
	 *
	 * @param logLevel INFO, ERROR
	 * @return Current date "2020-05-10 14:54:23 INFO - "
	 */
	private static String logPrefix(String logLevel) {
		return Util.dateToString(new Date(), Util.DateFormat.DATETIME) + " " + logLevel + " - ";
	}

	/**
	 * Add a new bug
	 *
	 * @param bugId defect id
	 */
	public static void addBug(String bugId) {
		currentBugId = bugId.replace("_", "-");
	}

	public static void beforeEach() {
		final String message = "Initialisation - Prerequirement";
		startStepTimestamp = System.currentTimeMillis();
		final String encodedMsg = "<span class='beforeEach'>" + message + "</span>";
		getExtentTest().log(LogStatus.UNKNOWN, encodedMsg.replace("\n", "<br/>"));
		System.out.println("\n" + message);
	}

	public static void beforeEachEnd() {
		final String message = Util.dateToString(new Date(), Util.DateFormat.DATETIME) + " "
				+ "Initialisation - Prerequirement -Fin";
		final String encodedMsg = "<span class='beforeEach'>" + message + "</span>";
		getExtentTest().log(LogStatus.UNKNOWN, encodedMsg.replace("\n", "<br/>"));
		System.out.println("\n" + message);
	}

	public static void afterEach() {
		final String message = Util.dateToString(new Date(), Util.DateFormat.DATETIME) + " "
				+ "Teardown - After End testcase";
		final String encodedMsg = "<span class='afterEach'>" + message + "</span>";
		getExtentTest().log(LogStatus.UNKNOWN, encodedMsg.replace("\n", "<br/>"));
		System.out.println("\n" + message);
	}

	public static void endTC(String testcaseName) {
		final String message = "End testcase: " + testcaseName;
		final String encodedMsg = "<span class='endTC'>" + message + "</span>";
		getExtentTest().log(LogStatus.UNKNOWN, encodedMsg.replace("\n", "<br/>"));
		System.out.println("\n" + message);
	}

}