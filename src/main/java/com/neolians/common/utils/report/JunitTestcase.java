package com.neolians.common.utils.report;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Message;

import com.neolians.common.utils.params.ConfigHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

import com.neolians.common.utils.DoAssert;
import com.neolians.common.utils.selenium.Driver;

import atu.testrecorder.ATUTestRecorder;
import io.github.artsok.RepeatedIfExceptionsTest;

/**
 * Manage testcase setup, tearDown,...
 */
@ExtendWith(JunitTestWatcher.class)
public abstract class JunitTestcase {
	/**
	 * Manage Verify action for a testcase
	 *
	 * @see DoAssert#verifyDisplayInfo(boolean, StringBuilder, Message)
	 * @see DoAssert#currentStepHasFailure
	 */
	public static boolean hasFailure = false;
	private double startTime = 0.0;
	private ATUTestRecorder recorder;
	private String tcVideoName;
	/**
	 * Application Version
	 */
	private static Object sutVersion = null;

	/**
	 * @return a String for the versionName or a JSONObject when there is all
	 *         microservice version
	 */
	public static Object getSutVersion() {
		return sutVersion;
	}

	/**
	 * Initialize Application Version
	 */
	public static void initSutVersion() {

	}

	/**
	 * Common action at suite start
	 */
	public static void testsuiteBegin() {
		System.out.println("Start test at: " + (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())));
		Report.initialize();
	}

	@AfterAll
	public static void testsuiteEnd() {
		try {
			System.out.println("\nlog File is: " + Report.getFile().getCanonicalPath() + "\n");
		} catch (final IOException e) {
			System.out.println("\nlog File is: " + Report.getFile().getAbsolutePath() + "\n");
		}
	}

	/**
	 * current Jira Test Id
	 */
	private String testId;
	/**
	 * 1rst existing bug id, set through the JunitTestInfo tag
	 */
	protected String bugId;
	private double testDurationInSecond = 0;

	/**
	 * Should be public because called inTestResultDbApi
	 *
	 * @return duration in second
	 */
	public int getTestDurationInSecond() {
		return (int) Math.round(testDurationInSecond);
	}

	@BeforeEach
	protected void testcaseBegin(TestInfo testInfo) throws Exception {
		hasFailure = false;
		DoAssert.currentErrorMessages = new StringBuilder();
		DoAssert.currentStepHasFailure = false;
		System.out.println("\n\n=================================");
		final JunitTestInfo junitTestInfo = testInfo.getTestMethod().get().getAnnotation(JunitTestInfo.class);

		Driver.closeAll();
		startTime = System.nanoTime();

		String tcName = testInfo.getDisplayName();
		final String className = (testInfo.getTestClass().isPresent() ? testInfo.getTestClass().get().getName() : "");
		if (testInfo.getTestMethod().isPresent()
				&& testInfo.getDisplayName().startsWith(RepeatedIfExceptionsTest.SHORT_DISPLAY_NAME.substring(0, 18))) {
			tcName = testInfo.getTestMethod().get().getName();
		}
		Report.startTest(testInfo);
		if (junitTestInfo != null) {
			testId = junitTestInfo.id().replace("_", "-");
			Report.info("Id: " + testId, false);
			bugId = junitTestInfo.bugId().replace("_", "-");
			/*
			 * 2nd existing bug id, set through the JunitTestInfo tag
			 *
			 * String bugId2 = junitTestInfo.bugId2().replace("_", "-");
			 *//*
				 * 3rd existing bug id, set through the JunitTestInfo tag
				 *
				 * String bugId3 = junitTestInfo.bugId3().replace("_", "-");
				 */
		}
		System.out.println("Test: " + tcName);
		System.out.println("Class: " + className);
		System.out.println("Browser: " + ConfigHelper.BROWSER.getValue().toUpperCase());
		System.out.println("       --");
		if (!ConfigHelper.BROWSER_HEADLESS.getBooleanValue()) {
			// No recorder en mode HeadLess

			tcVideoName = tcName.replaceAll("[\\\\/:*?\"<>|]", "");
			recorder = new ATUTestRecorder(ExtentReport.getVideoFolder().getAbsolutePath(), tcVideoName, false);
			System.out.println("Start recording");
			recorder.start();

		} else {
			recorder = null;
		}
		Driver.clearSession();
	}

	@AfterEach
	void testcaseEnd(TestInfo testInfo) {

		if (recorder != null) {
			try {
				recorder.stop();
				final File videoPath = new File(
						ExtentReport.getVideoFolder().getAbsolutePath() + "/" + tcVideoName + ".MOV");
				Report.copyVideoFileToReportFolder(videoPath);
			} catch (final Exception ex) {
				ex.printStackTrace();
			}
		}

		final double finishTime = System.nanoTime();
		testDurationInSecond = (finishTime - startTime) / 1000000000;
		Report.endTC(testInfo.getDisplayName());
		Report.updateTestDescription(testInfo);
		System.out.println(testInfo.getDisplayName() + "  => execution time of current test is: "
				+ getTestDurationInSecond() + " seconds");
		System.out.println("===========================================\n\n");
	}

	@AfterEach
	protected void getLogRecover() {
		Report.afterEach();

	}

	/**
	 * this function should be called at the end of each testcase his aim is to fail
	 * the Junit testcase if a verification failure occurs
	 *
	 * @see DoAssert#initVerify(String)
	 * @see DoAssert#verifyDisplayInfo(boolean, StringBuilder)
	 */
	protected void checkFailureEndTestcase() {
		if (hasFailure) {
			DoAssert.fail("ERROR - Check previous verifications <br>" + DoAssert.currentErrorMessages, null, null,
					false);
		}
	}
}