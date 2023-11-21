package com.neolians.common.utils.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.neolians.common.utils.params.ConfigHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Test;
import com.neolians.common.utils.ResourceFile;
import com.neolians.common.utils.selenium.Driver;

import io.qameta.allure.Allure;

public class ExtentReport extends ExtentReports {
	private static final long serialVersionUID = 3617125517702971934L;

	private static File _reportFile;
	private static boolean replaceExisting = true;

	/**
	 * Get the folder report path
	 *
	 * @return the path
	 */
	public static String initReportFolder() {
		return initReportFolder(true);
	}

	/**
	 * Get the folder report path
	 *
	 * @param withBrowserInPath true add folder name in it, false no browser name
	 * @return the path
	 */
	public static String initReportFolder(boolean withBrowserInPath) {
		if (isMavenExecution() && withBrowserInPath) {
			return "target/reports/" + ConfigHelper.BROWSER.getValue().toUpperCase() + "/"
					+ ConfigHelper.REPORT_JOBEXECUTIONID.getValue();
		}
		if (isMavenExecution() && !withBrowserInPath) {
			return "target/reports/" + ConfigHelper.REPORT_JOBEXECUTIONID.getValue();
		} else {
			// Default launch
			return "reports/manualLaunch";
		}
	}

	/**
	 * Initialize report file name
	 */
	private static void initializeReportFile() {

		_reportFile = new File(initReportFolder() + "/report.html");
		System.out.println("Report File: " + _reportFile.getAbsolutePath());

		if (isMavenExecution()) {
			// Launch is done by Maven, an append is needed
			replaceExisting = false;
		} else {
			// Default launch
			replaceExisting = true;
			try {
				if (_reportFile.getParentFile().exists()) {
					FileUtils.cleanDirectory(_reportFile.getParentFile());
				}

			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		// noinspection ResultOfMethodCallIgnored
		_reportFile.getParentFile().mkdirs();

	}

	private static boolean isMavenExecution() {
		final String mavenJobId = null; // ConfigHelper.REPORT_JOBEXECUTIONID.getValue();
		return mavenJobId != null;
	}

	static ExtentReport Instance() {
		ExtentReport extent;
		initializeReportFile();
		extent = new ExtentReport(_reportFile, replaceExisting);
		extent.isMavenLaunch = isMavenExecution();
		try {
			extent.loadConfig(ResourceFile.getFileInTargetsFolder("report/extent-config.xml"));
		} catch (final IOException ignored) {

		}
		return extent;
	}

	/**
	 * Get the Report File
	 *
	 * @return the report File
	 */
	private static File getReportFile() {
		if (_reportFile == null) {
			initializeReportFile();
		}
		return _reportFile;
	}

	/**
	 * Get the Report Folder
	 *
	 * @return the Report Folder
	 */
	static File getReportFolder() {
		return getReportFile().getParentFile();
	}

	/**
	 * Get the Video Folder
	 *
	 * @return the Video Folder
	 */
	public static File getVideoFolder() {
		return new File(FileUtils.getTempDirectory().getAbsolutePath());
	}

	/**
	 * True si le lancement se fait à partir de Maven, False pour un execution
	 * locale
	 */
	boolean isMavenLaunch = false;

	/**
	 * Initializes the reporting by setting the file-path
	 *
	 * @param reportFile      Path of the file, in .htm or .html format
	 * @param replaceExisting Setting to overwrite (TRUE) the existing file or
	 *                        append (FALSE) to it <br>
	 *                        &nbsp;&nbsp;<b>true</b>: the file will be replaced
	 *                        with brand new markup, and all existing data will be
	 *                        lost. Use this option to create a brand new report
	 *                        <br>
	 *                        &nbsp;&nbsp;<b>false</b>: existing data will remain,
	 *                        new tests will be appended to the existing report
	 */
	private ExtentReport(File reportFile, boolean replaceExisting) {
		super(reportFile.getAbsolutePath(), replaceExisting);
	}

	/**
	 * Get the report file expose la methode getFilePath a Public
	 */
	public File getFile() {
		final String filePath = super.getFilePath();
		return new File(filePath);

	}

	/**
	 * Capture a screen shot of the current execution and store it in a file for a
	 * driver capture a png and an html file for Http capture an html file
	 *
	 * @return the relative file path or null if no image is taken
	 */
	static String captureScreen() {
		final String currentDate = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-S").format(new Date());
		String relativeFilePath;
		if (!Driver.isSessionRunning()) {
			return null;
		}
		final WebDriver driver = Driver.getDriver();
		try {
			final TakesScreenshot oScn = (TakesScreenshot) driver;
			final File oScnShot = oScn.getScreenshotAs(OutputType.FILE);
			final String relativeHtmlPath = "/images/img-" + currentDate + ".html";
			relativeFilePath = "/images/img-" + currentDate + ".png";
			final File oScnDest = new File(getReportFolder().getAbsolutePath() + relativeFilePath);
			final File oHtmlDest = new File(getReportFolder().getAbsolutePath() + relativeHtmlPath);
			try {
				FileUtils.copyFile(oScnShot, oScnDest);
				final String pageSource = removeSomeHtml(driver.getPageSource());
				FileUtils.writeStringToFile(oHtmlDest, pageSource, Charset.defaultCharset());
				try {
					Allure.addAttachment("Screenshot", new FileInputStream(oScnDest));
					Allure.addAttachment("Html Page", new FileInputStream(oHtmlDest));
				} catch (final FileNotFoundException ignored) {
				}
			} catch (final IOException e) {
				System.out.println(e.getMessage());
			}
		} catch (final Exception ex) {
			return null;
		}
		return "." + relativeFilePath;
	}

	/**
	 * Remove some Pattern from the Html
	 */
	private static String removeSomeHtml(String pageSource) {
		/*
		 * Remove Script (common) </title><script>if (window.location.pathname.slice(-1)
		 * !== '/') { window.location.assign(window.location.pathname + '/');
		 * }</script><link
		 */
		pageSource = pageSource.replaceAll("</title><script>[^<]*</script><link", "</title><link");

		return pageSource;
	}

	/**
	 * Compteur de fichier lors de la g�n�ration de plusieurs fichier dans la même
	 * seconde
	 */
	private static int fileCounter = 0;

	static String copyFileToReportFolder(File linkFile) {
		fileCounter++;
		final String currentDate = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-S").format(new Date());
		String extension = "";
		final int i = linkFile.getAbsolutePath().lastIndexOf('.');
		if (i > 0) {
			extension = linkFile.getAbsolutePath().substring(i + 1);
		}
		final String relativeFilePath = "/file/file-" + currentDate + "_" + String.format("%05d", fileCounter) + "."
				+ extension;
		final File oDest = new File(getReportFolder().getAbsolutePath() + relativeFilePath);
		try {
			FileUtils.copyFile(linkFile, oDest);
		} catch (final IOException e) {
			System.out.println(e.getMessage());
		}
		return "." + relativeFilePath;
	}

	@Override
	protected void finalizeTest(Test test) {
		super.finalizeTest(test);

		// Manage newStep
		if (test.getStatus() == LogStatus.SKIP) {
			test.setStatus(LogStatus.PASS);
		}
	}
}