package com.neolians.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.neolians.common.utils.report.Report;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;

import com.neolians.common.utils.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath;
import com.neolians.common.utils.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;

public class Util {
	public static void main(String[] args) {
		String xpath;
		// xpath = Util.convertCssSelectorToXpath(".firco-select__input__drop-down");
		// System.out.println(xpath);
		xpath = Util.convertCssSelectorToXpath(
				".firco-filters-form-item:nth-child(%1$s) .firco-filters-form-item__column-field");
		System.out.println(xpath);
		xpath = Util.convertCssSelectorToXpath(".firco-filters-form-item:nth-child(2)");
		System.out.println(xpath);
		xpath = Util.convertCssSelectorToXpath(
				".firco-filters-form-item:nth-child(2) .firco-filters-form-item__column-field");
		System.out.println(xpath);
	}

	/**
	 * Liste les fichiers dont le nom correspond a la regex en param�tre
	 *
	 * @param folder Repertoire ou faire la recherche
	 * @param regex  expression r�guli�re de recherche
	 * @return liste de fichiers
	 */
	static File[] listFilesMatching(File folder, String regex) {
		if (!folder.isDirectory()) {
			throw new IllegalArgumentException(folder + " is no directory.");
		}
		final Pattern p = Pattern.compile(regex); // careful: could also throw
		// an exception!
		return folder.listFiles(file -> p.matcher(file.getName()).matches());
	}

	static String escapeRegex(String stringValue) {
		return stringValue.replace("\"", "\\\"").replace("?", "\\?").replace("&", "\\$").replace(".", "\\.")
				.replace("/", "\\/").replace("*", "\\*").replace("[", "\\[").replace("]", "\\]").replace("(", "\\(")
				.replace(")", "\\)");
	}

	/**
	 * @param inputString String where to find
	 * @param patternStr  pattern to find
	 * @return null if pattern is not present in the input string, or the pattern
	 *         result
	 */
	public static String regexGetFirstItem(String inputString, String patternStr) {
		final Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE + Pattern.MULTILINE);
		final Matcher matcher = pattern.matcher(inputString);
		final boolean matchFound = matcher.find();

		String value = null;
		if (matchFound) {
			// Get all groups for this match
			for (int i = 1; i <= matcher.groupCount(); i++) {
				value = matcher.group(i);
			}
		}
		return value;
	}

	/**
	 * Execute a regex on a String *
	 *
	 * @param inputString String where to find
	 * @param patternStr  pattern to find
	 * @return true if pattern is not present in the input string, or the pattern
	 *         result
	 */
	public static boolean regexMatchItem(String inputString, String patternStr) {
		final Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(inputString);
		return matcher.find();

	}

	/**
	 * @param firstIem   Folder Name
	 * @param secondItem File Name
	 * @return folder\file : Concatenate 2 path in order to remove double separator
	 */
	public static String concatenatePath(String firstIem, String secondItem, String thirdItem, boolean isUnix) {
		final String part1 = concatenatePath(firstIem, secondItem, isUnix);
		return concatenatePath(part1, thirdItem, isUnix);
	}

	/**
	 * @param firstIem   Folder Name
	 * @param secondItem File Name
	 * @param isUnix     is unix environment
	 * @return folder\file : Concatenate 2 path in order to remove double separator
	 */
	public static String concatenatePath(String firstIem, String secondItem, boolean isUnix) {
		final String sep = (isUnix ? "/" : "\\");
		return concatenatePath(firstIem, secondItem, sep);
	}

	/**
	 * @param firstIem   Folder Name
	 * @param secondItem File Name
	 * @param separator  separator
	 * @return folder\file : Concatenate 2 path in order to remove double separator
	 */
	public static String concatenatePath(String firstIem, String secondItem, String separator) {
		firstIem = firstIem.replace("/", separator);
		firstIem = firstIem.replace("\\", separator);
		secondItem = secondItem.replace("/", separator);
		secondItem = secondItem.replace("\\", separator);
		if (secondItem.startsWith("." + separator)) {
			secondItem = secondItem.substring(2);
		}
		String path = firstIem;
		if (!firstIem.endsWith(separator) && !firstIem.endsWith(separator)) {
			path = path + separator;
		}
		if (secondItem.startsWith(separator)) {
			secondItem = secondItem.substring(separator.length());
		}
		return path + secondItem;
	}

	// region Object convertion
	public static Integer toInt(Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof Integer) {
			return (Integer) o;
		} else if (o instanceof BigDecimal) {
			return ((BigDecimal) o).intValue();
		} else {
			return Integer.valueOf(o.toString());
		}
	}

	/**
	 * Check if a string is an integer or not
	 *
	 * @param s string to check
	 * @return true if integer
	 */
	static boolean isInteger(String s) {
		final int radix = 10;
		if (s.isEmpty()) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1) {
					return false;
				}
				continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0) {
				return false;
			}
		}
		return true;
	}

	public static Boolean toBoolean(Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof Integer) {
			return ((Integer) o) > 0;
		} else if (o instanceof BigDecimal) {
			return ((BigDecimal) o).intValue() > 0;
		} else {
			return Boolean.valueOf(o.toString());
		}
	}

	/**
	 * Convert a bigDecimal to Integer
	 *
	 * @param o object to convert
	 * @return an Integer if it is a BigDecimal otherwise the same object
	 */
	public static Object toObjectOrInteger(Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof BigDecimal) {
			return ((BigDecimal) o).intValue();
		} else {
			return o;
		}
	}

	public static String toCamelCase(final String init) {
		if (init == null) {
			return null;
		}

		final StringBuilder ret = new StringBuilder(init.length());

		for (final String word : init.split(" ")) {
			if (!word.isEmpty()) {
				ret.append(Character.toUpperCase(word.charAt(0)));
				ret.append(word.substring(1).toLowerCase());
			}
			if (!(ret.length() == init.length())) {
				ret.append(" ");
			}
		}

		return ret.toString();
	}

	/**
	 * Round to certain number of decimals
	 *
	 * @param d          number to round
	 * @param dPrecision Precision de l'arrondi ex: 1E-3
	 * @return rounded number
	 */
	public static double round(double d, double dPrecision) {
		final BigDecimal bdPrecision = new BigDecimal(Double.toString(dPrecision));
		final int decimalPlace = bdPrecision.scale() - bdPrecision.precision() + 1;

		BigDecimal bd = new BigDecimal(Double.toString(d));
		bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * Round to certain number of decimals
	 *
	 * @param value     number to round
	 * @param precision Precision de l'arrondi ex: 3
	 * @return rounded number
	 */
	public static double roundDouble(double value, int precision) {
		final double scale = Math.pow(10, precision);
		value = Math.round(value * scale) / scale;
		return value;
	}

	// endregion

	/**
	 * return a random number into a range
	 *
	 * @param min range min
	 * @param max range max
	 * @return Random number into a range
	 */
	public static double random(int min, int max) {
		return min + (Math.random() * (max - min));
	}

	public static String[] addItem(String[] originalArray, String newItem) {
		final int currentSize = originalArray.length;
		final int newSize = currentSize + 1;
		final String[] tempArray = new String[newSize];
		System.arraycopy(originalArray, 0, tempArray, 0, currentSize);
		tempArray[newSize - 1] = newItem;
		return tempArray;
	}

	// region execute command

	/**
	 * Execute a local ExeFile with its parameters and waitFor the end
	 *
	 * @param exeFile exe full path
	 * @param params  Exe parameters
	 * @return the output
	 * @throws IOException          if file does not exist
	 * @throws InterruptedException if execution error
	 */
	public static String executeCmd(File exeFile, String... params) throws IOException, InterruptedException {
		return executeCmd(exeFile.getAbsolutePath(), params, true, null, false);
	}

	/**
	 * Execute a local ExeFile with its parameters and waitFor the end
	 *
	 * @param exeFile exe full path
	 * @param params  Exe parameters
	 * @return the output
	 * @throws IOException          if file does not exist
	 * @throws InterruptedException if execution error
	 */
	public static String executeCmd(String exeFile, String[] params, boolean wait)
			throws IOException, InterruptedException {
		return executeCmd(exeFile, params, wait, null, false);
	}

	/**
	 * Execute a local ExeFile with its parameters and waitFor the end
	 *
	 * @param exeFile exe full path
	 * @param params  Exe parameters
	 * @return the output
	 * @throws IOException          if file does not exist
	 * @throws InterruptedException if execution error
	 */
	public static String executeCmd(File exeFile, String[] params, String waitTillEndLine)
			throws IOException, InterruptedException {
		return executeCmd(exeFile.getAbsolutePath(), params, false, waitTillEndLine, false);
	}

	/**
	 * Execute an ExeFile with its parameters and waitFor the end
	 *
	 * @param exeFilePath exe full path
	 * @param params      Exe parameters
	 * @return the output
	 * @throws IOException          if file does not exist
	 * @throws InterruptedException if execution error
	 */
	public static String executeCmd(String exeFilePath, String[] params, boolean wait, String waitTillEndLine,
			boolean displayOutput) throws IOException, InterruptedException {
		final String[] pbParameters = (params != null ? new String[params.length + 1] : new String[1]);
		pbParameters[0] = exeFilePath;
		if (params != null) {
			// noinspection ManualArrayCopy
			for (int cnt = 0; cnt < params.length; cnt++) {
				pbParameters[cnt + 1] = params[cnt];
			}
		}
		final ProcessBuilder pb = new ProcessBuilder(pbParameters);
		pb.directory(new File(exeFilePath).getParentFile());
		final Process process = pb.start();
		if (wait) {
			process.waitFor();
		}
		final StringBuilder output = new StringBuilder(getOutput(process, displayOutput));
		final long end = System.currentTimeMillis() + 120000;
		while (waitTillEndLine != null && !output.toString().contains(waitTillEndLine)
				&& System.currentTimeMillis() < end) {
			output.append(getOutput(process, displayOutput));
		}

		return output.toString();
	}

	private static String getOutput(Process process, boolean displayOutput) throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		final StringBuilder builder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			if (displayOutput) {
				System.out.println(line);
			}
			builder.append(line);
			builder.append(System.getProperty("line.separator"));
			if (line.contains(">pause")) {
				break;
			}
		}
		return builder.toString();
	}
//endregion

	/**
	 * Transform an htm string to Plain text removing Html tags
	 *
	 * @param html Html string
	 * @return plain string
	 */
	public static String html2text(String html) {
		return Jsoup.parse(html).text();
	}

	/**
	 * Get a file from the resource folder or from its absolute path
	 *
	 * @param localFile relative path from the resource folder
	 * @return File object or null if not found
	 */
	public static File retrieveFile(String localFile) {
		File _lfile = new File(localFile);
		if (!_lfile.exists()) {
			// check in the resource folder
			try {
				_lfile = ResourceFile.getFileInTargetsFolder(localFile);
			} catch (final IOException e) {
				System.out.println(e.getMessage());
				return null;
			}

		}
		if (!_lfile.exists()) {
			Report.error("Local file to copy does not exist");
			return null;

		}
		return _lfile;
	}

	/**
	 * Write content to a temporary file
	 *
	 * @param filecontent file content to write
	 * @param filePrefix  file name
	 * @param fileSuffix  file extension
	 * @return File object
	 * @throws IOException if an error occured
	 */
	public static File writeToTempFile(String filecontent, String filePrefix, String fileSuffix) throws IOException {
		filePrefix = (filePrefix == null ? "tempFile" : filePrefix);
		fileSuffix = (fileSuffix == null ? ".tmp" : fileSuffix);
		final File tmpFile = File.createTempFile(filePrefix, fileSuffix);
		final FileWriter writer = new FileWriter(tmpFile);
		writer.write(filecontent);
		writer.close();

		final BufferedReader reader = new BufferedReader(new FileReader(tmpFile));
		reader.close();
		return tmpFile;
	}

	/**
	 * Read the file content
	 *
	 * @param file     File to read
	 * @param encoding Charset.UTF-8
	 * @return file content
	 * @throws IOException if file does not exist
	 */
	public static String readFile(File file, Charset encoding) throws IOException {
		final byte[] encoded = Files.readAllBytes(Paths.get(file.getCanonicalPath()));
		return new String(encoded, encoding);
	}

	/**
	 * Write the file content
	 *
	 * @param file        File to read
	 * @param fileContent file content
	 * @param charset     Charset.UTF-8
	 * @throws IOException if file does not exist
	 */
	public static void writeFile(File file, String fileContent, Charset charset) throws IOException {
		final File directory = file.getParentFile();
		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				System.err.println("Cannot create directory:" + directory.getAbsolutePath());
			}
		}

		Files.write(Paths.get(file.getCanonicalPath()), fileContent.getBytes(charset));
	}

	/**
	 * list of file in the last X time and with a specific extension
	 *
	 * @param path
	 * @param instant in the last X minutes (Instant.now().minus(3,
	 *                ChronoUnit.MINUTES))
	 * @return
	 * @throws IOException
	 */
	public static List<Path> findByLastModifiedTime(Path path, Instant instant, String extension) throws IOException {

		List<Path> result;
		try (Stream<Path> pathStream = Files.find(path, Integer.MAX_VALUE, (p, basicFileAttributes) -> {

			if (Files.isDirectory(p) || !Files.isReadable(p)) {
				return false;
			}

			FileTime fileTime = basicFileAttributes.lastModifiedTime();
			// negative if less, positive if greater
			// 1 means fileTime equals or after the provided instant argument
			// -1 means fileTime before the provided instant argument
			int i = fileTime.toInstant().compareTo(instant);
			return i > 0 && p.getFileName().toString().endsWith(extension);
		}

		)) {
			result = pathStream.collect(Collectors.toList());
		}
		return result;

	}

	public static int countCsvFileRows(File csvFile) throws Exception {

		Report.info("Count Number of Rows in the Csv File " + csvFile.getAbsolutePath());
		final FileReader fr = new FileReader(csvFile);
		final LineNumberReader lnr = new LineNumberReader(fr);

		int linenumber = 0;

		while (lnr.readLine() != null) {
			linenumber++;
		}

		lnr.close();

		return linenumber - 1;

	}

	public static int countXLSFileRows(File xlsFile) throws IOException {

		Report.info("Count Number of Rows in the XLS File " + xlsFile.getAbsolutePath());
		final FileInputStream fis = new FileInputStream(xlsFile);

		try (HSSFWorkbook myWorkBook = new HSSFWorkbook(fis)) {
			final HSSFSheet mySheet = myWorkBook.getSheet("DATA");
			final int rowCount = mySheet.getLastRowNum() + 1;

			return rowCount - 1;
		}
	}

	/**
	 * Tri une liste suivant un ordre alphabetique Français
	 *
	 * @param stringList Liste à trier
	 */
	public static void sortListFrenchLocal(List<String> stringList) {
		final Collator coll = Collator.getInstance(Locale.FRENCH);
		coll.setStrength(Collator.PRIMARY);
		stringList.sort(coll);
	}

	// region DateTime
	public static Date Today() {
		return Calendar.getInstance().getTime();
	}

	public enum DateFormat {
		DATE("yyyy-MM-dd"), DATE_MONTH_DAY_YEAR("MM/dd/yyyy"), DATETIME("yyyy-MM-dd HH:mm:ss"),
		DATE_HHMM("yyyy-MM-dd HH:mm"), DATETIME_MILLISECOND("MM/dd/yyyy HH:mm:ss:SSS"), TIME("HH:mm:ss"),
		TIME_HHMM("HH:mm");

		private final String format;

		DateFormat(String format) {
			this.format = format;
		}
	}

	/**
	 * @param durationInSec duration in second
	 * @return second in hour/minute/second
	 */
	public static String timeHumanReadable(int durationInSec) {
		final String sign = (durationInSec > 0 ? "" : "- ");
		durationInSec = Math.abs(durationInSec);
		final long h = durationInSec / 3600;
		durationInSec = durationInSec % 3600;
		final long mn = durationInSec / 60;
		final long s = durationInSec % 60;
		if (h != 0) {
			return String.format(sign + "%1$dh %2$dmn %3$ds", h, mn, s);
		}
		if (mn > 0) {
			return String.format(sign + "%2$dmn %3$ds", h, mn, s);
		} else {
			return String.format(sign + "%3$ds", h, mn, s);
		}
	}

	/**
	 * Parse a String to a Date
	 *
	 * @param date       String to parse
	 * @param dateFormat predifined dateFormat
	 * @return Date object
	 * @throws ParseException if invalid format
	 */
	public static Date stringToDate(Object date, DateFormat dateFormat) throws ParseException {
		if (date == null) {
			return null;
		}
		if (!(date instanceof String)) {
			return null;
		}
		String sDate = (String) date;
		if (dateFormat == DateFormat.DATE && sDate.contains(" ")) {
			sDate = sDate.split(" ")[0];
		}
		try {
			return new SimpleDateFormat(dateFormat.format).parse(sDate);
		} catch (final ParseException ex) {
			return new SimpleDateFormat(dateFormat.format.replace("-", "/")).parse(sDate);
		}
	}

	/**
	 * Convert a date to a string
	 *
	 * @param cal        Date to convert
	 * @param dateFormat predifined dateFormat
	 * @return String parsed date
	 */
	public static String dateToString(Calendar cal, DateFormat dateFormat) {
		if (cal == null) {
			return null;
		}
		return new SimpleDateFormat(dateFormat.format).format(cal.getTime());
	}

	/**
	 * Convert a date to a string
	 *
	 * @param date       Date to convert
	 * @param dateFormat predifined dateFormat
	 * @return String parsed date
	 */
	public static String dateToString(Date date, DateFormat dateFormat) {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat(dateFormat.format).format(date);
	}

	/**
	 * Get the month number
	 *
	 * @param englishMonthName English month name
	 * @return String Month number (0->11)
	 */
	public static int getEnglishMonthNb(String englishMonthName) {
		switch (englishMonthName.toLowerCase()) {
		case "january":
		case "jan":
			return Calendar.JANUARY;
		case "february":
		case "feb":
			return Calendar.FEBRUARY;
		case "march":
		case "mar":
			return Calendar.MARCH;
		case "april":
		case "apr":
			return Calendar.APRIL;
		case "may":
			return Calendar.MAY;
		case "june":
		case "jun":
			return Calendar.JUNE;
		case "july":
		case "jul":
			return Calendar.JULY;
		case "august":
		case "aug":
			return Calendar.AUGUST;
		case "september":
		case "sep":
		case "sept":
			return Calendar.SEPTEMBER;
		case "october":
		case "oct":
			return Calendar.OCTOBER;
		case "november":
		case "nov":
			return Calendar.NOVEMBER;
		case "december":
		case "dec":
			return Calendar.DECEMBER;
		}
		return 0;
	}
//endregion

	// region css query

	/**
	 * Convert css selector to xpath
	 *
	 * @param cssSelector Css selector to convert
	 * @return xPath
	 */
	public static String convertCssSelectorToXpath(String cssSelector) {
		try {
			return new CssElementCombinatorPairsToXpath().convertCssSelectorStringToXpathString(cssSelector);
		} catch (final CssSelectorToXPathConverterException e) {
			System.out.println(e.getMessage());
			return "Css Query Error";
		}
	}
	// endregion

	public static String encodeFileToBase64Binary(File file) throws IOException {
		final byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		return new String(encoded, StandardCharsets.US_ASCII);
	}
}
