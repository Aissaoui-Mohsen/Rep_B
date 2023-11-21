package com.neolians.common.utils.selenium.elements;

import com.neolians.common.utils.DoAssert;
import com.neolians.common.utils.Util;
import com.neolians.common.utils.params.ConfigHelper;
import com.neolians.common.utils.report.Report;
import com.neolians.common.utils.selenium.Driver;

public class UrlElement {
	final private String url;
	protected String elementName;
	private String urlFirstParameter;

	public UrlElement(String url) {
		this.url = url;
	}

	public UrlElement(String elementName, String url) {
		this.url = url;
		this.elementName = elementName;
	}

	public void openUrl() {
		displayElementName();
		Driver.openUrl(getCurrentUrl());
	}

	public String getUrl(Object... urlArguments) {
		return String.format(url, urlArguments);
	}

	public String getCurrentUrl() {
		if (urlFirstParameter != null) {
			return String.format(url, urlFirstParameter);
		}
		return url;
	}

	public String getFullUrl() {
		String fullUrl = getCurrentUrl();

		if (!fullUrl.startsWith("https")) {
			final String sut_URL = ConfigHelper.getApplicationUrl();
			fullUrl = Util.concatenatePath(sut_URL, url, "/");
			final int position = sut_URL.length();
			fullUrl = fullUrl.substring(0, position) + "/" + fullUrl.substring(position + 1);
		}
		return fullUrl;
	}

	public void setParameter(String urlParameter) {
		urlFirstParameter = urlParameter;

	}

	public void assertPageTitle(String pageTitle) {
		final String actual = Driver.getDriver().getTitle();
		DoAssert.assertContains(pageTitle, actual, "Check Page Title");
	}

	/**
	 * Display an element name
	 */
	protected void displayElementName() {
		if (elementName != null) {
			String msg;
			msg = String.format("Open Page %1$s", elementName);
			Report.info(msg, false);
		}
	}

	@Override
	public String toString() {
		return "UrlElement [url=" + url + "]";
	}
}