package com.neolians.common.utils.report;

import java.util.Optional;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.opentest4j.TestAbortedException;

import com.relevantcodes.extentreports.LogStatus;
import com.neolians.common.utils.selenium.Driver;

import io.github.artsok.RepeatedIfExceptionsTest;

public class JunitTestWatcher implements TestWatcher {
	@Override
	public void testAborted(ExtensionContext extensionContext, Throwable throwable) {
		if (throwable.getCause() != null) {
			Report.info("1rst Execution Exception: \n" + throwable.getCause().getMessage(), true);
		}
		System.out.println("status testAborted: " + getFullTestName(extensionContext));
		endTestcase(throwable);
	}

	@Override
	public void testDisabled(ExtensionContext extensionContext, Optional<String> optional) {
		System.out.println("status testDisabled: " + getFullTestName(extensionContext));
		endTestcase(null);
	}

	@Override
	public void testFailed(ExtensionContext extensionContext, Throwable throwable) {

		final String className = throwable.getClass().getName();
		if (!className.equalsIgnoreCase("java.lang.AssertionError")) {
			Report.error("Exception: \n" + throwable.getMessage(), throwable, false);
			throwable.printStackTrace();
		}
		System.out.println("status testFailed: " + getFullTestName(extensionContext));
		Report.failed("Test Failed: " + getFullTestName(extensionContext), true);
		Report.getExtentTest().getTest().setStatus(LogStatus.FAIL);
		endTestcase(throwable); // do something
	}

	@Override
	public void testSuccessful(ExtensionContext extensionContext) {
		if (JunitTestcase.hasFailure) {
			Report.failed("ERROR - Check previous verifications");
			testFailed(extensionContext, new AssertionError("ERROR - Check previous verifications"));
		} else {
			Report.success("Test Succeed: " + getFullTestName(extensionContext));
			Report.getExtentTest().getTest().setStatus(LogStatus.PASS);
			System.out.println("status testSuccessful: " + getFullTestName(extensionContext));
			endTestcase(null);
		} // do something
	}

	private String getFullTestName(ExtensionContext testInfo) {
		String tcName = testInfo.getDisplayName();
		if (testInfo.getTestMethod().isPresent()
				&& testInfo.getDisplayName().startsWith(RepeatedIfExceptionsTest.SHORT_DISPLAY_NAME.substring(0, 18))) {
			tcName = testInfo.getTestMethod().get().getName();
		}
		if (testInfo.getTestClass().isPresent()) {
			return testInfo.getTestClass().get().getCanonicalName() + " (" + tcName + ")";
		}
		return tcName;
	}

	private void endTestcase(Throwable throwable) {
		// noinspection RedundantIfStatement
		if (throwable instanceof TestAbortedException) {
			// on est en mode Repeat, on re-fait le cas de test
			Report.endTest(true);
		} else {
			Report.endTest(false);
		}

		Driver.closeAll();

	}

}