package com.neolians.common.utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;

import com.neolians.common.utils.report.JunitTestcase;
import com.neolians.common.utils.report.Report;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import com.neolians.common.utils.selenium.Driver;
import com.sun.mail.pop3.POP3Message;

public class DoAssert {
	private static final String NotNullPattern = "NotNullPattern";
	private static final String NullPattern = "NullPattern";
	/**
	 * Manage Verify action for a cucumber step
	 *
	 * @see DoAssert#verifyDisplayInfo(boolean, StringBuilder, Message)
	 * @see JunitTestcase#hasFailure
	 */
	public static boolean currentStepHasFailure;
	/**
	 * Store different error messages in order to display then at the end of the
	 * testcase in case of Verify actions
	 *
	 * @see DoAssert#verifyDisplayInfo(boolean, StringBuilder, Message)
	 * @see JunitTestcase#hasFailure
	 */
	public static StringBuilder currentErrorMessages;

	public static void main(String[] args) {
	}

	// region Assert functions

	// region Assert Equals

	/**
	 * Verifies that two specified objects are equal. The assertion fails if the
	 * objects are equal.and a snapshot is taken
	 *
	 * @param expected expected Value
	 * @param actual   actual Value
	 */
	public static void assertEquals(String expected, String actual) {
		assertEquals(expected, actual, null, false, true);
	}

	/**
	 * Verifies that two specified objects are not equal. The assertion fails if the
	 * objects are equal.and a snapshot is taken
	 *
	 * @param expected expected Value
	 * @param actual   actual Value
	 * @param message  Information message to display
	 */
	public static void assertEquals(String expected, String actual, String message) {
		assertEquals(expected, actual, message, false, false);
	}

	/**
	 * Verifies that two specified objects are not equal. The assertion fails if the
	 * objects are equal.and a snapshot is taken
	 *
	 * @param expected expected Value
	 * @param actual   actual Value
	 * @param message  Information message to display
	 */
	public static void assertEquals(int expected, int actual, String message) {
		assertEquals("" + expected, "" + actual, message, false, true);
	}

	/**
	 * Verifies that two specified objects are not equal. The assertion fails if the
	 * objects are equal.and a snapshot is taken
	 *
	 * @param expected expected Value
	 * @param actual   actual Value
	 * @param message  Information message to display
	 */
	public static void assertEquals(boolean expected, boolean actual, String message) {
		assertEquals(expected, actual, message, false);
	}

	/**
	 * Verifies that two specified objects are not equal. The assertion fails if the
	 * objects are equal.and a snapshot is taken
	 *
	 * @param expected       expected Value
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertEquals(boolean expected, boolean actual, String message, boolean takeScreenshot) {
		assertEquals("" + expected, "" + actual, message, false, takeScreenshot);
	}

	/**
	 * Verifies that two specified objects are equal. The assertion fails if the
	 * objects are equal. and a snapshot is taken
	 *
	 * @param expected   expected Value
	 * @param actual     actual Value
	 * @param message    Information message to display
	 * @param ignoreCase Ignore case for comparison
	 */
	public static void assertEquals(String expected, String actual, String message, boolean ignoreCase) {
		assertEquals(expected, actual, message, ignoreCase, true);
	}

	/**
	 * Verifies that two specified objects are equal. The assertion fails if the
	 * objects are equal.
	 *
	 * @param expected       expected Value
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertEquals(List<String> expected, List<String> actual, String message,
			boolean takeScreenshot) {
		if (expected == null && actual == null) {
			return;
		}
		if (expected == null) {
			assertNotNull(null, message);
			return;
		}
		Report.info("Assert List - expected values:" + expected.toString());
		Report.info("Assert List - actual values:" + actual.toString());
		assertEquals(expected.size(), actual.size(), message + " - Verify elements number", takeScreenshot);
		// Sort and compare the two lists
		Collections.sort(expected);
		Collections.sort(actual);
		assertTrue(expected.equals(actual), message + " - Verify each element of 2 lists", takeScreenshot);
	}

	/**
	 * Verifies that two specified objects are equal. The assertion fails if the
	 * objects are equal.
	 *
	 * @param expected       expected Value
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param ignoreCase     Ignore case for comparison
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertEquals(String expected, String actual, String message, boolean ignoreCase,
			boolean takeScreenshot) {
		assertEquals(expected, actual, message, ignoreCase, takeScreenshot, null);
	}

	public static void assertEquals(String expected, String actual, String message, boolean ignoreCase,
			boolean takeScreenshot, String bugID) {
		if (actual != null) {
			actual = actual.replace("\u00A0", " ");
		}
		if (expected != null) {
			expected = expected.replace("\u00A0", " ");
		}
		try {
			if (ignoreCase) {
				Assert.assertEquals((expected != null ? expected.toLowerCase() : null),
						(actual != null ? actual.toLowerCase() : null));
			} else {
				Assert.assertEquals(expected, actual);
			}
		} catch (final AssertionError ex) {
			checkResult(false, expected, actual, message, takeScreenshot, ex, null, bugID);
			return;
		}
		checkResult(true, expected, actual, message, takeScreenshot, null);
	}

	/**
	 * Verifies that two specified objects are equal. The assertion fails if the
	 * objects are equal.
	 *
	 * @param expected       expected Value
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param linkFile       Attach a file to the log
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertEquals(String expected, String actual, String message, boolean takeScreenshot,
			File linkFile) {
		try {
			Assert.assertEquals(expected, actual);
		} catch (final AssertionError ex) {
			checkResult(false, expected, actual, message, takeScreenshot, ex, linkFile);
			return;
		}
		checkResult(true, expected, actual, message, takeScreenshot, null, linkFile);
	}

	/**
	 * Verifies that two specified objects are equal. The assertion fails if the
	 * objects are equal. and a snapshot is taken
	 *
	 * @param expected       expected Value
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertEquals(Date expected, Date actual, String message, boolean takeScreenshot) {
		final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		assertEquals(sdf.format(expected), sdf.format(actual), message, false, takeScreenshot);
	}

	/**
	 * Verifies that two specified objects are equal. The assertion fails if the
	 * objects are equal.
	 *
	 * @param expected       expected Value
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertEquals(int expected, BigDecimal actual, String message, boolean takeScreenshot) {
		final BigDecimal expectedBd = BigDecimal.valueOf(expected);
		try {
			Assert.assertEquals(expectedBd, actual);
		} catch (final AssertionError ex) {
			checkResult(false, expectedBd, actual, message, takeScreenshot, ex);
			return;
		}
		checkResult(true, expectedBd, actual, message, takeScreenshot, null);
	}

	/**
	 * Verifies that two specified objects are equal. The assertion fails if the
	 * objects are equal.
	 *
	 * @param expected       expected Value
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertEquals(boolean expected, Object actual, String message, boolean takeScreenshot) {
		final boolean bActual = Util.toBoolean(actual);
		try {
			Assert.assertEquals(expected, bActual);
		} catch (final AssertionError ex) {
			checkResult(false, expected, bActual, message, takeScreenshot, ex);
			return;
		}
		checkResult(true, expected, bActual, message, takeScreenshot, null);
	}

	/**
	 * Verifies that two specified objects are equal. The assertion fails if the
	 * objects are equal.
	 *
	 * @param expected       expected Value
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertEquals(Object expected, Object actual, String message, boolean takeScreenshot) {
		assertEquals(expected, actual, message, takeScreenshot, null);
	}

	/**
	 * Verifies that two specified objects are equal. The assertion fails if the
	 * objects are equal.
	 *
	 * @param expected       expected Value
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertEquals(Object expected, Object actual, String message, boolean takeScreenshot,
			String bugId) {
		expected = Util.toObjectOrInteger(expected);
		actual = Util.toObjectOrInteger(actual);
		try {
			Assert.assertEquals(expected, actual);
		} catch (final AssertionError ex) {
			checkResult(false, expected, actual, message, takeScreenshot, ex, null, bugId);
			return;
		}
		checkResult(true, expected, actual, message, takeScreenshot, null);
	}

	/**
	 * Verifies that two specified objects are equal and a snapshot is taken
	 *
	 * @param expected expected Value
	 * @param actual   actual Value
	 */
	public static void assertEquals(Pattern expected, String actual) {
		assertEquals(expected, actual, null, true);
	}

	/**
	 * Verifies that the actual value match the expected regex and a snapshot is
	 * taken
	 *
	 * @param expected expected Value
	 * @param actual   actual Value
	 * @param message  Information message to display
	 */
	public static void assertEquals(Pattern expected, String actual, String message) {
		assertEquals(expected, actual, message, false);
	}

	/**
	 * Verifies that the actual value match the expected regex.
	 *
	 * @param expected       expected Value
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	private static void assertEquals(Pattern expected, String actual, String message, boolean takeScreenshot) {

		final Matcher matcher = expected.matcher(actual);
		checkResult(matcher.find(), expected, actual, message, takeScreenshot, null);
	}

	/**
	 * Verifies that two specified objects are equal. The assertion fails if the
	 * objects are equal.and a snapshot is taken
	 *
	 * @param expected expected Value
	 * @param actual   actual Value
	 * @param delta    delta Value
	 */
	public static void assertEquals(double expected, double actual, double delta) {
		assertEquals(expected, actual, delta, null, false);
	}

	/**
	 * Verifies that two specified objects are equal. The assertion fails if the
	 * objects are equal. and a snapshot is taken
	 *
	 * @param expected expected Value
	 * @param actual   actual Value
	 * @param delta    delta Value
	 * @param message  Information message to display
	 */
	public static void assertEquals(double expected, double actual, double delta, String message) {
		assertEquals(expected, actual, delta, message, false);
	}

	/**
	 * Verifies that two specified objects are equal. The assertion fails if the
	 * objects are equal.
	 *
	 * @param expected       expected Value
	 * @param actual         actual Value
	 * @param delta          delta Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertEquals(double expected, double actual, double delta, String message,
			boolean takeScreenshot) {
		try {
			Assert.assertEquals(expected, actual, delta);
		} catch (final AssertionError ex) {
			checkResult(false, expected, actual, message, takeScreenshot, ex);
			return;
		}
		checkResult(true, expected, actual, message, takeScreenshot, null);
	}
//endregion

	// region Assert Not Contains

	/**
	 * assertNotContains Verifies that the expected value is not included in the
	 * actual one.
	 *
	 * @param expected expected Value
	 * @param actual   actual Value
	 * @param message  Information message to display
	 */
	public static void assertNotContains(String expected, String actual, String message) {
		assertNotContains(expected, actual, message, false);
	}

	/**
	 * assertNotContains Verifies that the expected value is not included in the
	 * actual one.
	 *
	 * @param expected expected Value
	 * @param actual   actual Value
	 * @param message  Information message to display
	 */
	public static void assertNotContains(String expected, String actual, String message, boolean takeScreenshot) {
		if (actual != null) {
			actual = actual.replace("\u00A0", " ");
		}
		if (expected != null) {
			expected = expected.replace("\u00A0", " ");
		}
		final boolean bRet = actual.contains(expected);

		checkResult(!bRet, expected, actual, message, takeScreenshot, null);
	}
//endregion

	// region Assert Not Equals
	public static void assertNotEquals(String expected, String actual, String message, boolean ignoreCase,
			boolean takeScreenshot, String bugID) {
		boolean bRet;
		if (ignoreCase) {
			bRet = !actual.equalsIgnoreCase(expected);
		} else {
			bRet = !actual.equals(expected);
		}

		checkResult(bRet, expected, actual, message, takeScreenshot, null, null, bugID);
	}

	/**
	 * assertNotEquals Verifies that the expected value is not equal to the actual
	 * one
	 */
	public static void assertNotEquals(String expected, String actual, String message) {
		assertNotEquals(expected, actual, message, false, false, null);
	}

	/**
	 * assertNotEquals Verifies that the expected value is not equal to the actual
	 * one
	 */
	public static void assertNotEquals(int expected, int actual, String message) {
		assertNotEquals("" + expected, "" + actual, message, false, false, null);
	}
//endregion

	// region Assert Contains

	/**
	 * Verifies that the expected value is included in the actual one.
	 *
	 * @param expected expected Value
	 * @param actual   actual Value
	 * @param message  Information message to display
	 */
	public static void assertContains(String expected, String actual, String message) {
		if (actual != null) {
			actual = actual.replace("\u00A0", " ");
		}
		if (expected != null) {
			expected = expected.replace("\u00A0", " ");
		}
		final boolean bRet = actual.contains(expected);

		checkResult(bRet, expected, actual, message, false, null);
	}

	/**
	 * Verifies that the expected value is included in the actual one.
	 *
	 * @param expected   expected Value
	 * @param actual     actual Value
	 * @param message    Information message to display
	 * @param linkedFile File to attach
	 */
	public static void assertContains(String expected, String actual, String message, File linkedFile) {
		final boolean bRet = actual.contains(expected);

		checkResult(bRet, expected, actual, message, false, null, linkedFile);
	}

	public static void assertContains(String expected, String actual, String message, boolean takeScreenshot,
			String bugID) {
		final boolean bRet = actual.contains(expected);

		checkResult(bRet, expected, actual, message, takeScreenshot, null, null, bugID);
	}

	/**
	 * Verifies that the expected value is included in the actual one.
	 *
	 * @param expected       expected Value
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertContains(String expected, String actual, String message, boolean takeScreenshot) {
		assertContains(expected, actual, message, takeScreenshot, false);
	}

	/**
	 * Verifies that the expected value is included in the actual one.
	 *
	 * @param expected       expected Value
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertContains(String expected, String actual, String message, boolean takeScreenshot,
			boolean ignoreCase) {
		assertContains(expected, actual, message, takeScreenshot, ignoreCase, null);
	}

	/**
	 * Verifies that the expected value is included in the actual one.
	 *
	 * @param expected       expected Value
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 * @param ignoreCase     true to do a ignore case (default false)
	 * @param bugId          bug id or JiraId linked to the verification
	 */
	public static void assertContains(String expected, String actual, String message, boolean takeScreenshot,
			boolean ignoreCase, String bugId) {
		boolean bRet;
		if (expected == null) {
			expected = "";
		}
		if (actual == null) {
			actual = "";
		}
		if (ignoreCase) {
			bRet = actual.toLowerCase().contains(expected.toLowerCase());
		} else {
			bRet = actual.contains(expected);
		}

		checkResult(bRet, expected, actual, message, takeScreenshot, null, null, bugId);
	}

	// endregion

	// region Assert Present In Web page

	/**
	 * Verifies that the expected value is included in the web page.
	 *
	 * @param expected       expected Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertPresentInWebPage(String expected, String message, boolean takeScreenshot) {
		final boolean bRet = Driver.getDriver().getPageSource().contains(expected);

		checkResult(bRet, expected, null, message, takeScreenshot, null);
	}
//endregion

	// region Assert True

	/**
	 * Verifies that the value is True and a snapshot is taken
	 *
	 * @param actual actual Value
	 */
	public static void assertTrue(boolean actual) {
		assertTrue(actual, null, false);
	}

	/**
	 * Verifies that the value is True and no snapshot is taken
	 *
	 * @param actual  actual Value
	 * @param message Information message to display
	 */
	public static void assertTrue(boolean actual, String message) {
		assertTrue(actual, message, false);
	}

	/**
	 * Verifies that the value is True
	 *
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertTrue(boolean actual, String message, boolean takeScreenshot) {

		assertTrue(actual, message, takeScreenshot, null);
	}

	/**
	 * Verifies that the value is True
	 *
	 * @param actual  actual Value
	 * @param message Information message to display
	 * @param bugId   Jira defectId
	 */
	public static void assertTrue(boolean actual, String message, String bugId) {

		assertTrue(actual, message, false, null, bugId);
	}

	/**
	 * Verifies that the value is True
	 *
	 * @param actual                      actual Value
	 * @param message                     Information message to display
	 * @param takeScreenshot              Take screenshot in case of error
	 * @param displayWithActualVsExpected true: display with actual and Expected,
	 *                                    false with success and fails
	 */
	public static void assertTrue(boolean actual, String message, boolean takeScreenshot,
			boolean displayWithActualVsExpected) {
		assertTrue(actual, message, takeScreenshot, null, null, displayWithActualVsExpected);
	}

	/**
	 * Verifies that the value is True
	 *
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 * @param linkedFile     Attach a file to the assert
	 */
	public static void assertTrue(boolean actual, String message, boolean takeScreenshot, File linkedFile) {
		assertTrue(actual, message, takeScreenshot, linkedFile, null);
	}

	/**
	 * Verifies that the value is True
	 *
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 * @param linkedFile     Attach a file to the assert
	 * @param bugId          Jira defectId
	 */
	public static void assertTrue(boolean actual, String message, boolean takeScreenshot, File linkedFile,
			String bugId) {
		assertTrue(actual, message, takeScreenshot, linkedFile, bugId, true);
	}

	/**
	 * Verifies that the value is True
	 *
	 * @param actual                      actual Value
	 * @param message                     Information message to display
	 * @param takeScreenshot              Take screenshot in case of error
	 * @param linkedFile                  Attach a file to the assert
	 * @param displayWithActualVsExpected true: display with actual and Expected,
	 *                                    false with success and fails
	 * @param bugId                       Jira defectId
	 */
	public static void assertTrue(boolean actual, String message, boolean takeScreenshot, File linkedFile, String bugId,
			boolean displayWithActualVsExpected) {
		try {
			Assert.assertTrue(actual);
		} catch (final AssertionError ex) {
			if (displayWithActualVsExpected) {
				checkResult(false, true, actual, message, false, ex, linkedFile, bugId);
			} else {
				checkResult(false, null, null, message, false, ex, linkedFile, bugId);
			}
			return;
		}
		if (displayWithActualVsExpected) {
			checkResult(true, true, true, message, takeScreenshot, null, linkedFile, bugId);
		} else {
			checkResult(true, null, null, message, takeScreenshot, null, linkedFile, bugId);
		}
	}

	// endregion

	// region Assert False

	/**
	 * Verifies that the value is False and a snapshot is taken
	 *
	 * @param actual actual Value
	 */
	public static void assertFalse(boolean actual) {
		assertFalse(actual, null, false, null, null);
	}

	/**
	 * Verifies that the value is False and a snapshot is taken
	 *
	 * @param actual  actual Value
	 * @param message Information message to display
	 */
	public static void assertFalse(boolean actual, String message) {
		assertFalse(actual, message, false, null, null);
	}

	/**
	 * Verifies that the value is True
	 *
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertFalse(boolean actual, String message, boolean takeScreenshot) {
		assertFalse(actual, message, takeScreenshot, null, null);
	}

	/**
	 * Verifies that the value is True
	 *
	 * @param actual  actual Value
	 * @param message Information message to display
	 * @param bugId   Jira defectId
	 */
	public static void assertFalse(boolean actual, String message, String bugId) {

		assertFalse(actual, message, false, null, bugId);
	}

	/**
	 * Verifies that the value is False
	 *
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertFalse(boolean actual, String message, boolean takeScreenshot, File linkedFile,
			String bugId) {
		try {
			Assert.assertFalse(actual);
		} catch (final AssertionError ex) {
			checkResult(false, false, actual, message, takeScreenshot, ex, linkedFile, bugId);
			return;
		}
		checkResult(true, false, false, message, takeScreenshot, null, linkedFile, bugId);
	}
//endregion

	// region Assert Null

	/**
	 * Verifies that the value is NotNull and a snapshot is taken
	 *
	 * @param actual  actual Value
	 * @param message Information message to display
	 */
	public static void assertNull(Object actual, String message) {
		try {
			Assert.assertNull(actual);
		} catch (final AssertionError ex) {
			checkResult(false, NullPattern, actual, message, false, ex);
			return;
		}
		checkResult(true, NullPattern, null, message, false, null);
	}

	/**
	 * For checking table fields according to DataBase type
	 *
	 * @param actual actual value
	 */
	public static void assertEmpty(Object actual, String message) {
		if (actual == null) {
			actual = "";
		}
		DoAssert.assertEquals("", actual.toString(), message);
	}
//endregion

	// region Assert Not Null

	/**
	 * Verifies that the value is NotNull and a snapshot is taken
	 *
	 * @param actual  actual Value
	 * @param message Information message to display
	 */
	public static void assertNotNull(Object actual, String message) {
		assertNotNull(actual, message, false, null);
	}

	/**
	 * Verifies that the value is NotNull and a snapshot is taken
	 *
	 * @param actual  actual Value
	 * @param message Information message to display
	 */
	public static void assertNotNull(Object actual, String message, File linkedFile) {
		assertNotNull(actual, message, false, linkedFile);
	}

	/**
	 * Verifies that the value is notNull
	 *
	 * @param actual         actual Value
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void assertNotNull(Object actual, String message, boolean takeScreenshot, File linkedFile) {
		try {
			Assert.assertNotNull(actual);
		} catch (final AssertionError ex) {
			checkResult(false, NotNullPattern, actual, message, takeScreenshot, ex, linkedFile);
			return;
		}
		checkResult(true, NotNullPattern, actual, message, takeScreenshot, null, linkedFile);
	}

	/**
	 * Verifies that the value is notNull
	 *
	 * @param actual  actual Value
	 * @param message Information message to display
	 * @param bugId   Bug Id
	 */
	public static void assertNotNull(Object actual, String message, String bugId) {
		try {
			Assert.assertNotNull(actual);
		} catch (final AssertionError ex) {
			checkResult(false, NotNullPattern, actual, message, false, ex, null, bugId);
			return;
		}
		checkResult(true, NotNullPattern, actual, message, false, null);
	}
//endregion

	// endregion

	// region Fail

	/**
	 * Fails a verification
	 *
	 * @param message Information message to display
	 */
	public static void fail(String message) {
		fail(message, (String) null);
	}

	/**
	 * Fails a verification
	 *
	 * @param message message to display
	 * @param bugId   Jira Bug Id
	 */
	public static void fail(String message, String bugId) {
		fail(message, null, bugId);
	}

	/**
	 * Fails a verification
	 *
	 * @param message Information message to display
	 * @param ex      stackTrace to display in case of exception
	 */
	static void fail(String message, Exception ex) {
		checkResult(false, null, null, message, false, ex);
	}

	/**
	 * Fails a verification
	 *
	 * @param message    message to display
	 * @param bugId      Jira Bug Id
	 * @param linkedFile File to attach
	 */
	public static void fail(String message, File linkedFile, String bugId) {
		fail(message, linkedFile, bugId, true);
	}

	/**
	 * Fails a verification
	 *
	 * @param message    message to display
	 * @param bugId      Jira Bug Id
	 * @param linkedFile File to attach
	 * @param escapeHtml to escape Html character in message
	 */
	public static void fail(String message, File linkedFile, String bugId, boolean escapeHtml) {
		checkResult(false, null, null, message, false, null, linkedFile, bugId, escapeHtml);
	}
	// endregion

	// region Success

	/**
	 * Success a verification
	 *
	 * @param message Information message to display
	 */
	public static void success(String message) {
		success(message, false);
	}

	/**
	 * success a verification
	 *
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public static void success(String message, boolean takeScreenshot) {
		success(message, takeScreenshot, null);
	}

	/**
	 * success a verification
	 *
	 * @param message        Information message to display
	 * @param takeScreenshot Take screenshot in case of error
	 * @param bugId          Jira Bug Id
	 */
	public static void success(String message, boolean takeScreenshot, String bugId) {
		checkResult(true, null, null, message, takeScreenshot, null, null, bugId);
	}

	// endregion

	// region Display Method Check result

	/**
	 * check a validation and display the right message
	 *
	 * @param isCheckOk      True if we have to log only a report info, False to
	 *                       throw an exception
	 * @param expected       Expected value
	 * @param actual         The Actual object. (Can be a String, an Integer,
	 *                       RepoItemInfo)
	 * @param message        message to display
	 * @param takeScreenshot true if we want to take a Web driver screenshot
	 * @param stackTrace     stackTrace to display in case of exception
	 */
	private static void checkResult(boolean isCheckOk, Object expected, Object actual, String message,
			boolean takeScreenshot, Throwable stackTrace) {
		checkResult(isCheckOk, expected, actual, message, takeScreenshot, stackTrace, null);
	}

	/**
	 * check a validation and display the right message
	 *
	 * @param isCheckOk      True if we have to log only a report info, False to
	 *                       throw an exception
	 * @param expected       Expected value
	 * @param actual         The Actual object. (Can be a String, an Integer,
	 *                       RepoItemInfo)
	 * @param message        message to display
	 * @param takeScreenshot true if we want to take a Web driver screenshot
	 * @param stackTrace     stackTrace to display in case of exception
	 * @param linkedFile     File to attach
	 */
	private static void checkResult(boolean isCheckOk, Object expected, Object actual, String message,
			boolean takeScreenshot, Throwable stackTrace, File linkedFile) {
		checkResult(isCheckOk, expected, actual, message, takeScreenshot, stackTrace, linkedFile, null);
	}

	/**
	 * check a validation and display the right message
	 *
	 * @param isCheckOk      True if we have to log only a report info, False to
	 *                       throw an exception
	 * @param expected       Expected value
	 * @param actual         The Actual object. (Can be a String, an Integer,
	 *                       RepoItemInfo)
	 * @param message        message to display
	 * @param takeScreenshot true if we want to take a Web driver screenshot
	 * @param stackTrace     stackTrace to display in case of exception
	 * @param bugId          Jira Bug Id
	 * @param linkedFile     File to attach
	 */
	private static void checkResult(boolean isCheckOk, Object expected, Object actual, String message,
			boolean takeScreenshot, Throwable stackTrace, File linkedFile, String bugId) {
		checkResult(isCheckOk, expected, actual, message, takeScreenshot, stackTrace, linkedFile, bugId, true);
	}

	/**
	 * check a validation and display the right message
	 *
	 * @param isCheckOk      True if we have to log only a report info, False to
	 *                       throw an exception
	 * @param expected       Expected value
	 * @param actual         The Actual object. (Can be a String, an Integer,
	 *                       RepoItemInfo)
	 * @param message        message to display
	 * @param takeScreenshot true if we want to take a Web driver screenshot
	 * @param stackTrace     stackTrace to display in case of exception
	 * @param bugId          Jira Bug Id
	 * @param linkedFile     File to attach
	 * @param escapeHtml     to escape Html character in message
	 */
	private static void checkResult(boolean isCheckOk, Object expected, Object actual, String message,
			boolean takeScreenshot, Throwable stackTrace, File linkedFile, String bugId, boolean escapeHtml) {
		String msg = formatMessage(actual, expected, message);
		if (!isCheckOk) {
			final String addmsg = "*** Validation FAILED *** ";

			msg = addmsg + msg;

			Report.failed(msg + "\r\n" + displayErrorForWeb(stackTrace), false, linkedFile, bugId, escapeHtml);
			throw new AssertionError((bugId != null ? "Defect: " + bugId + " - " : "") + msg);
		}
		Report.success(msg, takeScreenshot, linkedFile, escapeHtml);

	}

	/**
	 * @param completeMessage the complete message to display for Verify
	 */
	private static void checkResult(StringBuilder completeMessage, boolean isCheckOk, Object expected, Object actual,
			String message) {
		checkResult(completeMessage, isCheckOk, expected, actual, message, null);
	}

	/**
	 * in case of a verify action
	 *
	 * @param completeMessage the complete message to display for Verify
	 * @param bugId           bugId if verification is linked to a bugId, null
	 *                        otherwise
	 * @param isCheckOk       true verification is success, false verification fails
	 * @param messagePattern  message to display for the line
	 */
	private static void checkResult(StringBuilder completeMessage, boolean isCheckOk, Object expected, Object actual,
			String messagePattern, String bugId) {
		final String msgToDisplay = formatMessage(true, actual, expected, messagePattern, isCheckOk, bugId);
		messageLine(completeMessage, isCheckOk, msgToDisplay);
	}

	/**
	 * Display one line in case of verify
	 *
	 * @param completeMessage the complete message to display for Verify
	 * @param isCheckOk       true verification is success, false verification fails
	 * @param msgToDisplay    message to display
	 */
	private static void messageLine(StringBuilder completeMessage, boolean isCheckOk, String msgToDisplay) {
		completeMessage.append(" <li class='check").append(isCheckOk ? "OK" : "KO").append("'>").append(msgToDisplay)
				.append(" => ").append(isCheckOk ? "OK" : "KO").append("</li>");
	}

	/**
	 * Format the Assert Message
	 *
	 * @param expected The Expected object. (Can be a String, an Integer, or a
	 *                 Regular Expression)
	 * @param actual   The Actual object. (Can be a String, an Integer, a
	 *                 NullPattern, a WebElement
	 * @param message  Optional Message to display
	 */
	private static String formatMessage(Object actual, Object expected, String message) {
		String msg = "";
		if (message != null) {
			msg = message.replace("%", "%%");
		}
		if (expected == null && actual == null) {
			// Case a direct failure
			// no extra message
			msg = "" + msg;
		} else if (expected == null) {
			// && actual != null
			msg = msg + " for Actual value: '%1$s'";
		} else if ((expected.equals(NullPattern)) && actual == null) {
			msg = msg + " Expected should be NULL and Actual value is NULL";
		} else if ((expected.equals(NullPattern)) && actual != null) {
			msg = msg + " Expected should be NULL but Actual value is '%1$s'";
		} else if (expected.equals(NotNullPattern) && actual == null) {
			msg = msg + " Expected should be not null but Actual value is null";
		} else if (expected.equals(NotNullPattern) && actual != null) {
			msg = msg + " Expected should be not null and Actual value is '%1$s'";
		} else if (actual == null) {
			msg = msg + " for Expected value: '%2$s'";
		} else if (message != null && (expected) instanceof Pattern) {
			msg = msg + " Contains RegexPattern value:'%2$s' ";
		} else if ((expected) instanceof Pattern) {
			msg = msg + " Actual value: '%1$s'  Contains RegexPattern value:'%2$s' ";
		} else if ((actual) instanceof WebElement) {
			msg = msg + " WebElement value: '%1$s' Exists ";

		} else {
			msg = msg + "\r\n* Actual value: '%1$s' \r\n* Expected value: '%2$s' ";
		}

		return String.format(msg, actual, expected);
	}

	/**
	 * Format the Verify Message
	 *
	 * @param expected The Expected object. (Can be a String, an Integer, or a
	 *                 Regular Expression)
	 * @param actual   The Actual object. (Can be a String, an Integer, a
	 *                 NullPattern, a WebElement
	 * @param message  Optional Message to display
	 * @param oneLine  if dislay information in one line (for verify action)
	 * @param bugId    bugId if verification is linked to a bugId, null otherwise
	 * @param isOk     True if verification is success, false if it is failure
	 */
	private static String formatMessage(boolean oneLine, Object actual, Object expected, String message, boolean isOk,
			String bugId) {
		String msg = "";
		if (message != null) {
			msg = message.replace("%", "%%");
		}
		if (expected == null && actual == null) {
			// Case a direct failure
			// no extra message
			msg = "" + msg;
		} else if (expected == null) {
			// && actual != null
			msg = msg + " for Actual value: '%1$s'";
		} else if ((expected.equals(NullPattern)) && actual == null) {
			if (oneLine) {
				msg = msg + "(Value: Null)";
			} else {
				msg = msg + " Expected should be NULL and Actual value is NULL";
			}
		} else if ((expected.equals(NullPattern)) && actual != null) {
			msg = msg + " Expected should be NULL but Actual value is '%1$s'";
		} else if (expected.equals(NotNullPattern) && actual == null) {
			msg = msg + " Expected should be not null but Actual value is null";
		} else if (expected.equals(NotNullPattern) && actual != null) {
			if (oneLine) {
				msg = msg + "(Actual: '%1$s' - Expected: NotNull)";
			} else {
				msg = msg + " Expected should be not null and Actual value is '%1$s'";
			}
		} else if (actual == null) {
			msg = msg + " for Expected value: '%2$s'";
		} else if ((expected) instanceof Pattern) {
			msg = msg + " Actual value: '%1$s'  Contains RegexPattern value:'%2$s' ";
		} else if ((actual) instanceof WebElement) {
			msg = msg + " WebElement value: '%1$s' Exists ";
		} else if (oneLine) {
			if (isOk) {
				msg = msg + " (value: '%2$s')";
			} else {
				msg = msg + " (Actual: '%1$s' - Expected: '%2$s')";
			}
		} else {
			msg = msg + "\r\n* Actual value: '%1$s' \r\n* Expected value: '%2$s' ";
		}
		if (actual instanceof POP3Message) {
			try {
				actual = ((POP3Message) actual).getSubject();
			} catch (final MessagingException ignored) {
			}
		}
		final String bugHtml = (!isOk && bugId != null ? Report.displayBugId(bugId, oneLine) : "");
		return String.format(msg, actual, expected) + bugHtml;
	}

	/**
	 * format a stack trace for html
	 *
	 * @param t exception
	 * @return the html stack trace
	 */
	private static String displayErrorForWeb(Throwable t) {
		if (t == null) {
			return "";
		}
		return displayErrorForWeb(t.getStackTrace());
	}

	/**
	 * format a stack trace for html
	 *
	 * @param stackTraceElements stacktraceElements
	 * @return the html stack trace
	 */
	public static String displayErrorForWeb(StackTraceElement[] stackTraceElements) {
		final String root = "com.fircosoft";
		boolean start = false;

		int i = 0;
		final StringBuilder message = new StringBuilder();
		while (true) {
			if (i == stackTraceElements.length) {
				break;
			}
			final String current = stackTraceElements[i].toString();
			if (!start || current.contains(root)) {
				message.append("\n").append(current);
			}
			i++;
			if (current.contains(root)) {
				start = true;
				continue;
			}
			if (start && !current.contains(root)) {
				break;
			}

		}
		return message.toString();
	}
//endregion

	// region Verify Actions

	// region Verify Equals

	/**
	 * Do an equality control between 2 values Append information in completeMessage
	 * string
	 *
	 * @param completeMessage the complete message to display for Verify
	 * @param actual          Actual value
	 * @param expected        Expected value
	 * @param message         information message
	 * @return true if check Ok, false if verify fails
	 * @link verifyDisplayInfo to display result in log file
	 */
	public static boolean verifyEquals(StringBuilder completeMessage, String expected, String actual, String message) {
		return verifyEquals(completeMessage, expected, actual, message, null);
	}

	/**
	 * Do an equality control between 2 values Append information in completeMessage
	 * string
	 *
	 * @param completeMessage the complete message to display for Verify
	 * @param actual          Actual value
	 * @param expected        Expected value
	 * @param message         information message
	 * @param bugId           if failure is link to a bugId
	 * @return true if check Ok, false if verify fails
	 * @link verifyDisplayInfo to display result in log file
	 */
	public static boolean verifyEquals(StringBuilder completeMessage, String expected, String actual, String message,
			String bugId) {

		try {
			final String sExpected = (expected != null
					? expected.toLowerCase().replaceAll("\u00A0", " ").replaceAll("\u00C2", "")
					: "").trim().replaceAll(" {2}", " ");
			final String sActual = (actual != null
					? actual.toLowerCase().replaceAll("\u00A0", " ").replaceAll("\u00C2", "")
					: "").trim().replaceAll(" {2}", " ");
			Assert.assertEquals(sExpected, sActual);
		} catch (final AssertionError ex) {
			checkResult(completeMessage, false, expected, actual, message, bugId);
			return false;
		}
		checkResult(completeMessage, true, expected, actual, message);
		return true;
	}

	/**
	 * Do an equality control between 2 values Append information in completeMessage
	 * string
	 *
	 * @param completeMessage the complete message to display for Verify
	 * @param actual          Actual value
	 * @param expected        Expected value
	 * @param message         information message
	 * @return true if check Ok, false if verify fails
	 * @link verifyDisplayInfo to display result in log file
	 */
	public static boolean verifyEquals(StringBuilder completeMessage, boolean expected, boolean actual,
			String message) {
		return verifyEquals(completeMessage, "" + expected, "" + actual, message);
	}

	/**
	 * Do an equality control between 2 values Append information in completeMessage
	 * string
	 *
	 * @param completeMessage the complete message to display for Verify
	 * @param actual          Actual value
	 * @param expected        Expected value
	 * @param precision       double delta for verification
	 * @param message         information message
	 * @return true if check Ok, false if verify fails
	 * @link verifyDisplayInfo to display result in log file
	 */
	public static boolean verifyEquals(StringBuilder completeMessage, Double expected, Double actual, double precision,
			String message) {

		try {
			expected = Util.round(expected, precision);
			actual = Util.round(actual, precision);
			Assert.assertEquals(expected, actual, precision);
		} catch (final AssertionError ex) {
			checkResult(completeMessage, false, expected, actual, message);
			return false;
		}
		checkResult(completeMessage, true, expected, actual, message);
		return true;
	}

	/**
	 * Do an equality control between 2 values Append information in completeMessage
	 * string
	 *
	 * @param completeMessage the complete message to display for Verify
	 * @param actual          Actual value
	 * @param expected        Expected value
	 * @param message         information message
	 * @param bugId           bug Id
	 * @return true if check Ok, false if verify fails
	 * @link verifyDisplayInfo to display result in log file
	 */
	public static boolean verifyEquals(StringBuilder completeMessage, int expected, int actual, String message,
			String bugId) {
		try {
			Assert.assertEquals(expected, actual);
		} catch (final AssertionError ex) {
			checkResult(completeMessage, false, expected, actual, message, bugId);
			return false;
		}
		checkResult(completeMessage, true, expected, actual, message, bugId);
		return true;
	}
//endregion

	// region Verify Contains

	/**
	 * Verifies that the expected value is included in the actual one. Append
	 * information in completeMessage string
	 *
	 * @param completeMessage the complete message to display for Verify
	 * @param actual          Actual value
	 * @param expected        Expected value
	 * @param message         information message
	 * @return true if check Ok, false if verify fails
	 * @link verifyDisplayInfo to display result in log file
	 */
	public static boolean verifyContains(StringBuilder completeMessage, String expected, String actual,
			String message) {
		if (expected == null) {
			return true;
		}
		if (actual == null) {
			// si actual est null, on est en erreur
			checkResult(completeMessage, false, expected, null, message);
			return false;
		}
		final boolean ret = actual.contains(expected);
		try {
			Assert.assertTrue(ret);
		} catch (final AssertionError ex) {
			checkResult(completeMessage, false, expected, actual, message);
			return false;
		}
		checkResult(completeMessage, true, expected, actual, message);
		return true;
	}
//endregion

	// region Verify True

	/**
	 * Verifies that the value is True Append information in completeMessage string
	 *
	 * @param completeMessage the complete message to display for Verify
	 * @param actual          Actual value
	 * @param message         information message
	 * @return true if check Ok, false if verify fails
	 * @link verifyDisplayInfo to display result in log file
	 */
	public static boolean verifyTrue(StringBuilder completeMessage, boolean actual, String message) {
		return verifyTrue(completeMessage, actual, message, null);
	}

	/**
	 * Verifies that the value is True Append information in completeMessage string
	 *
	 * @param completeMessage the complete message to display for Verify
	 * @param actual          Actual value
	 * @param message         information message
	 * @param bugId           bugId if verification is linked to a bugId, null
	 *                        otherwise
	 * @return true if check Ok, false if verify fails
	 * @link verifyDisplayInfo to display result in log file
	 */
	public static boolean verifyTrue(StringBuilder completeMessage, boolean actual, String message, String bugId) {
		try {
			Assert.assertTrue(actual);
		} catch (final AssertionError ex) {
			checkResult(completeMessage, false, true, actual, message, bugId);
			return false;
		}
		checkResult(completeMessage, true, true, true, message);
		return true;

	}
//endregion

	// region Verify Null

	/**
	 * Verifies that the value is Null and a snapshot is taken Append information in
	 * completeMessage string
	 *
	 * @param actual          actual Value
	 * @param message         Information message to display
	 * @param completeMessage the complete message to display for Verify
	 * @return true if check Ok, false if verify fails
	 * @link verifyDisplayInfo to display result in log file
	 */
	public static boolean verifyNull(StringBuilder completeMessage, Object actual, String message) {
		return verifyNull(completeMessage, actual, message, null);
	}

	/**
	 * Verifies that the value is Null and a snapshot is taken Append information in
	 * completeMessage string
	 *
	 * @param actual          actual Value
	 * @param message         Information message to display
	 * @param completeMessage the complete message to display for Verify
	 * @return true if check Ok, false if verify fails
	 * @link verifyDisplayInfo to display result in log file
	 */
	public static boolean verifyNull(StringBuilder completeMessage, Object actual, String message, String bugId) {
		try {
			Assert.assertNull(actual);
		} catch (final AssertionError ex) {
			checkResult(completeMessage, false, NullPattern, actual, message, bugId);
			return false;
		}
		checkResult(completeMessage, true, NullPattern, null, message);
		return true;

	}
//endregion

	// region Verify Not Null

	/**
	 * Verifies that the value is NotNull and a snapshot is taken Append information
	 * in completeMessage string
	 *
	 * @param actual          actual Value
	 * @param message         Information message to display
	 * @param completeMessage the complete message to display for Verify
	 * @return true if check Ok, false if verify fails
	 * @link verifyDisplayInfo to display result in log file
	 */
	public static boolean verifyNotNull(StringBuilder completeMessage, Object actual, String message) {
		return verifyNotNull(completeMessage, actual, message, null);
	}

	/**
	 * Verifies that the value is NotNull and a snapshot is taken Append information
	 * in completeMessage string
	 *
	 * @param actual          actual Value
	 * @param message         Information message to display
	 * @param completeMessage the complete message to display for Verify
	 * @return true if check Ok, false if verify fails
	 * @link verifyDisplayInfo to display result in log file
	 */
	public static boolean verifyNotNull(StringBuilder completeMessage, Object actual, String message, String bugId) {
		try {
			Assert.assertNotNull(actual);
		} catch (final AssertionError ex) {
			checkResult(completeMessage, false, NotNullPattern, actual, message, bugId);
			return false;
		}
		checkResult(completeMessage, true, NotNullPattern, actual, message);
		return true;

	}

	// endregion

	// region Verify Success / Fail

	/**
	 * Verifies that the value is a success Append information in completeMessage
	 * string
	 *
	 * @param completeMessage the complete message to display for Verify
	 * @param message         Information message to display
	 * @param bugId           Jira Bug Id
	 * @return true if check Ok, false if verify fails
	 */
	public static boolean verifySuccess(StringBuilder completeMessage, String message, String bugId) {
		checkResult(completeMessage, true, null, null, message, bugId);
		return true;
	}

	/**
	 * Verifies that the value is a failure Append information in completeMessage
	 * string
	 *
	 * @param completeMessage the complete message to display for Verify
	 * @param message         Information message to display
	 * @param bugId           Jira Bug Id
	 * @return true if check Ok, false if verify fails
	 */
	public static boolean verifyFail(StringBuilder completeMessage, String message, String bugId) {
		checkResult(completeMessage, false, null, null, message, bugId);
		return false;
	}
	// endregion
	// region Display Verify Info (end of verification)

	/**
	 * Function to call at the end of verifications in order to display results in
	 * report file
	 *
	 * @param checkOk         résultat des vérification
	 * @param completeMessage message à afficher
	 * @apiNote At the end of a Junit testcase use
	 *          JunitTestcase.checkFailureEndTestcase()
	 * @apiNote At the end of a Cucumber testcase add step 'Then
	 *          checkFailureEndTestcase'
	 * @see DoAssert#initVerify(String)
	 */
	public static void verifyDisplayInfo(boolean checkOk, StringBuilder completeMessage) {
		verifyDisplayInfo(checkOk, completeMessage, false);
	}

	/**
	 * Function to call at the end of verifications in order to display results in
	 * report file
	 *
	 * @param checkOk         résultat des vérification
	 * @param completeMessage message à afficher
	 * @param takeScreenshot  True prise d'une impression ecran ou impression par
	 *                        element
	 * @apiNote At the end of a Junit testcase use
	 *          JunitTestcase.checkFailureEndTestcase()
	 * @apiNote At the end of a Cucumber testcase add step 'Then
	 *          checkFailureEndTestcase'
	 * @see DoAssert#initVerify(String)
	 */
	public static void assertDisplayInfo(boolean checkOk, StringBuilder completeMessage, boolean takeScreenshot) {
		verifyDisplayInfo(checkOk, completeMessage, null, takeScreenshot, null, true);
	}

	/**
	 * Function to call at the end of verifications in order to display results in
	 * report file
	 *
	 * @param checkOk                      résultat des vérification
	 * @param completeMessage              message à afficher
	 * @param emailContentToDisplayOnError Html email content to link with the
	 *                                     assertion
	 * @apiNote At the end of a Junit testcase use
	 *          JunitTestcase.checkFailureEndTestcase()
	 * @apiNote At the end of a Cucumber testcase add step 'Then
	 *          checkFailureEndTestcase'
	 * @see DoAssert#initVerify(String)
	 */
	public static void verifyDisplayInfo(boolean checkOk, StringBuilder completeMessage,
			Message emailContentToDisplayOnError) {
		verifyDisplayInfo(checkOk, completeMessage, emailContentToDisplayOnError, true, null, false);
	}

	/**
	 * Function to call at the end of verifications in order to display results in
	 * report file
	 *
	 * @param checkOk         résultat des vérification
	 * @param completeMessage message à afficher
	 * @param takeScreenshot  True prise d'une impression ecran ou impression par
	 *                        element
	 * @apiNote At the end of a Junit testcase use
	 *          JunitTestcase.checkFailureEndTestcase()
	 * @apiNote At the end of a Cucumber testcase add step 'Then
	 *          checkFailureEndTestcase'
	 * @see DoAssert#initVerify(String)
	 */
	public static void verifyDisplayInfo(boolean checkOk, StringBuilder completeMessage, boolean takeScreenshot) {
		verifyDisplayInfo(checkOk, completeMessage, takeScreenshot, false);
	}

	/**
	 * Function to call at the end of verifications in order to display results in
	 * report file
	 *
	 * @param checkOk         résultat des vérification
	 * @param completeMessage message à afficher
	 * @param takeScreenshot  True prise d'une impression ecran ou impression par
	 *                        element
	 * @apiNote At the end of a Junit testcase use
	 *          JunitTestcase.checkFailureEndTestcase()
	 * @apiNote At the end of a Cucumber testcase add step 'Then
	 *          checkFailureEndTestcase'
	 * @see DoAssert#initVerify(String)
	 */
	public static void verifyDisplayInfo(boolean checkOk, StringBuilder completeMessage, boolean takeScreenshot,
			boolean doAssert) {
		verifyDisplayInfo(checkOk, completeMessage, null, takeScreenshot, null, doAssert);
	}

	/**
	 * Function to call at the end of verifications in order to display results in
	 * report file *
	 *
	 * @param checkOk         résultat des vérification
	 * @param completeMessage message à afficher
	 * @param linkedFile      fichier à attacher au log
	 * @apiNote At the end of a Junit testcase use
	 *          JunitTestcase.checkFailureEndTestcase()
	 * @apiNote At the end of a Cucumber testcase add step 'Then
	 *          checkFailureEndTestcase'
	 * @see DoAssert#initVerify(String)
	 */
	public static void verifyDisplayInfo(boolean checkOk, StringBuilder completeMessage, File linkedFile) {
		verifyDisplayInfo(checkOk, completeMessage, null, false, linkedFile, false);
	}

	/**
	 * Function to call at the end of verifications in order to display results in
	 * report file
	 *
	 * @param checkOk                      résultat des vérification
	 * @param completeMessage              message à afficher
	 * @param takeScreenshot               True prise d'une impression ecran
	 * @param linkedFile                   fichier à attacher au log
	 * @param doAssert                     true to do an assertion, false a Report
	 *                                     info/failure
	 * @param emailContentToDisplayOnError Html email content to link with the
	 *                                     assertion
	 * @apiNote At the end of a Junit testcase use
	 *          JunitTestcase.checkFailureEndTestcase()
	 * @apiNote At the end of a Cucumber testcase add step 'Then
	 *          checkFailureEndTestcase'
	 * @see DoAssert#initVerify(String)
	 */
	private static void verifyDisplayInfo(boolean checkOk, StringBuilder completeMessage,
			Message emailContentToDisplayOnError, boolean takeScreenshot, File linkedFile, boolean doAssert) {
		completeMessage.append("</ul>");
		if (checkOk) {
			Report.success(completeMessage.toString(), takeScreenshot, linkedFile, false);
		} else {
			File outputMessage = null;
			if (emailContentToDisplayOnError != null) {
				try {
					final Document doc = Jsoup.parse(emailContentToDisplayOnError.getContent().toString());
					outputMessage = File.createTempFile("mail", ".html");
					FileUtils.writeStringToFile(outputMessage, doc.toString(), Charset.defaultCharset());
				} catch (IOException | MessagingException ignored) {
				}
			}
			if (outputMessage == null) {
				outputMessage = linkedFile;
			}
			if (doAssert) {
				fail("FAILURE\n" + completeMessage.toString(), outputMessage, null, false);
			} else {
				Report.failed("FAILURE\n" + completeMessage.toString(), takeScreenshot, outputMessage, null, false);
			}
			JunitTestcase.hasFailure = true;
			currentStepHasFailure = true;
			currentErrorMessages.append(Report.currentStepName).append("<br>---------------------<br>")
					.append(completeMessage);
		}
	}

	/**
	 * Initialize Verify display
	 *
	 * @param verifyTitle Verification title
	 * @return Verify String builder to be append
	 */
	public static StringBuilder initVerify(String verifyTitle) {
		final StringBuilder completeMessage = new StringBuilder(verifyTitle);
		completeMessage.append("<ul class='check'>");
		return completeMessage;
	}

//endregion
	// endregion
}