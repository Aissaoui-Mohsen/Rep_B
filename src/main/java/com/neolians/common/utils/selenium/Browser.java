package com.neolians.common.utils.selenium;

import com.neolians.common.utils.params.ConfigHelper;

public enum Browser {
	IE, FIREFOX, CHROME, EDGE, REMOTECHROME, REMOTEFIREFOX, REMOTEEDGE, REMOTECHROMECI;

	/**
	 * Get the default browser from the environment Variable: browser Default IE
	 *
	 * @return the default browser execution
	 */
	public static Browser getBrowser() {

		final String defaultBrowser = ConfigHelper.BROWSER.getValue();
		if (defaultBrowser == null) {
			return Browser.FIREFOX;
		}
		switch (defaultBrowser.toUpperCase().trim()) {
		case "IE":
			return IE;
		case "REMOTECHROMECI":
			return REMOTECHROMECI;
		case "REMOTECHROME":
			return REMOTECHROME;
		case "REMOTEEDGE":
			return REMOTEEDGE;
		case "REMOTEFIREFOX":
			return REMOTEFIREFOX;
		case "FIREFOX":
			return FIREFOX;
		case "EDGE":
			return EDGE;
		case "CHROME":
		default:
			return CHROME;
		}
	}

}
