package com.neolians.website.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.neolians.common.utils.Util;

/**
 * Retrieve Version from the backend
 */
public class Version {

	public static void main(String[] args) throws Exception {
		getGitVersion();
	}

	/**
	 * @return displayed Version Name
	 */
	public static String getVersionName() {
		return null;
	}

	private static String _gitVersion = null;

	/**
	 * Get Git Version
	 *
	 * @return Git Version
	 */
	public static String getGitVersion() throws IOException, InterruptedException, ParseException {
		if (_gitVersion == null) {
			final String branch = Util.executeCmd("git", new String[] { "rev-parse", "--abbrev-ref", "HEAD" }, true);
			//
			final String fullDate = Util.executeCmd("git", new String[] { "log", "-1", "--format=%cd" }, true);
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
			final Date date = sdf.parse(fullDate.trim());
			sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
			_gitVersion = branch.trim() + "-" + sdf.format(date);
		}
		return _gitVersion;
	}

}
