package com.neolians.common.utils.params;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.neolians.common.utils.ResourceFile;

/**
 * This Class is used to define property
 */
public class StringParam {

	private static void readProperties() {
		properties = new Properties();
		readProperties("default.properties");
		readProperties("softwareUnderTest.properties");
		readProperties("execution.properties");
		readProperties("localComputer.properties");
	}

	private static void readPropertiesWithTestEnvironment(String testEnvironment) {
		properties = new Properties();
		readProperties("default.properties");
		if (testEnvironment != null) {
			readProperties("softwareUnderTest.properties." + testEnvironment);
		}
		readProperties("softwareUnderTest.properties");
		readProperties("execution.properties");
		readProperties("localComputer.properties");
		readProperties("allure.properties");
	}

	private static void readProperties(String fileName) {
		try {
			final InputStream stream = StringParam.class.getClassLoader().getResourceAsStream(fileName);
			if (stream != null) {
				try {
					properties.load(stream);
				} finally {
					stream.close();
				}
			} else {
				try {
					final File f = ResourceFile.getFileInTargetsFolder(fileName);
					properties.load(new FileInputStream(f.getAbsolutePath()));
				} catch (final Exception ignored) {

				}
			}
		} catch (final IOException ignored) {
		}

	}

	private final String name;
	private StringParam defaultValue = null;

	private String value;

	private static Properties properties = null;

	public StringParam(final String name) {
		this.name = name;
	}

	StringParam(final String name, StringParam defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
	}

	/**
	 * Return value as an boolean, false if not present
	 *
	 * @return the property value
	 */
	public boolean getBooleanValue() {
		return getValue() != null && getValue().equalsIgnoreCase("true");
	}

	/**
	 * Return value as an Integer, -1 if not present
	 *
	 * @return the property value
	 */
	public int getIntegerValue() {
		final String value = getValue();
		if (value == null || value.equalsIgnoreCase("")) {
			return -1;
		}
		return Integer.parseInt(value);
	}

	private String testEnvironment = null;

	private String getTestEnvironment() {
		if (testEnvironment == null) {
			readProperties();
			testEnvironment = System.getProperty(ConfigHelper.SUT_TEST_ENVIRONMENT.name);
			if (testEnvironment == null) {
				testEnvironment = properties.getProperty(ConfigHelper.SUT_TEST_ENVIRONMENT.name);
			}
			if (testEnvironment != null) {
				try {
					ResourceFile.getFileInTargetsFolder("softwareUnderTest.properties." + testEnvironment);
				} catch (final IOException ignored) {
					System.err.println("Test environment variable '" + testEnvironment
							+ "' is invalid. Check your execution.properties file...");
				}
			}
		}
		return testEnvironment;

	}

	/**
	 * Return value as an String, empty string if not present
	 *
	 * @return the property value
	 */
	public String getValue() {
		if (value == null || value.equalsIgnoreCase("")) {
			if (properties == null || properties.size() == 0) {
				readPropertiesWithTestEnvironment(getTestEnvironment());
			}
			value = System.getProperty(name);
			if (value == null) {
				value = properties.getProperty(name);
			}
		}
		if ((value == null || value.equalsIgnoreCase("")) && defaultValue != null) {
			value = defaultValue.getValue();
		}
		return value;
	}

	@Override
	public String toString() {
		return name + ": " + value;
	}

	public String getName() {
		return name;
	}

	public void setValue(String value) {
		System.setProperty(name, value);
		this.value = value;
	}
}