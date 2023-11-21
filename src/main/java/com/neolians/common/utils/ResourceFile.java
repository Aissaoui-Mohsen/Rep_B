package com.neolians.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.neolians.common.utils.params.ConfigHelper;

public class ResourceFile {

	/**
	 * Get the File from a file in the squashTa targets Folder
	 *
	 * @param fileName relative file name
	 * @return Existing File
	 * @throws IOException if IO error
	 */
	public static File getFileInTargetsFolder(String fileName) throws IOException {
		return getFileInTargetsFolder(fileName, true);
	}

	/**
	 * Get the File from a file in the squashTa targets Folder
	 *
	 * @param fileName       relative file name
	 * @param throwException true throw an exception if not found, false return null
	 *                       if not found
	 * @return Existing File or Null if not found
	 * @throws IOException if file not found in case of throwException=true
	 */
	public static File getFileInTargetsFolder(String fileName, boolean throwException) throws IOException {
		String initFolder = "/config/";
		final File f = getFileInTargetsFolder(fileName, initFolder, false);
		if (f != null && f.exists()) {
			return f;
		}

		initFolder = "/src/main/resources/";
		return getFileInTargetsFolder(fileName, initFolder, throwException);
	}

	/**
	 * @param fileName       file to lookfor in the resource folder
	 * @param initFolder     resource folder
	 * @param throwException true throw an exception if not found, false return null
	 *                       if not found
	 * @return the file or null if not present
	 * @throws IOException if file not found in case of throwException=true
	 */
	private static File getFileInTargetsFolder(String fileName, String initFolder, boolean throwException)
			throws IOException {
		String relativeTargetsFolder, relativeTargetsFolder2;
		final String rootFolder = ConfigHelper.GIT_ROOT_FOLDER.getValue();

		relativeTargetsFolder = "." + initFolder;
		relativeTargetsFolder2 = "./" + rootFolder + initFolder;

		File f = new File(relativeTargetsFolder + fileName);
		if (f.exists()) {
			return f;
		}
		f = new File(relativeTargetsFolder2 + fileName);
		if (f.exists()) {
			return f;
		}
		final File fclass = new File(ResourceFile.class.getProtectionDomain().getCodeSource().getLocation().getPath());

		final int pos = fclass.getAbsolutePath().indexOf(rootFolder);
		if (pos > 0) {
			final String folder = fclass.getAbsolutePath().substring(0, pos + rootFolder.length()) + initFolder;
			f = new File(folder + fileName);
			if (f.exists()) {
				return f;
			}
		}

		if (throwException) {
			throw new IOException(
					"File '" + fileName + "' does not exist in init Folder (" + f.getAbsolutePath() + ")");
		}
		return null;

	}

	/**
	 * Get a list of file in the resource foler
	 *
	 * @param filePattern Regex to find the list of files
	 * @return list of Files or empty list
	 * @throws IOException if IOException
	 */
	public static ArrayList<File> getFilesInTargetsFolder(String filePattern) throws IOException {
		final String parentFolder = filePattern.substring(0, filePattern.lastIndexOf("/"));
		final File folder = getFileInTargetsFolder(parentFolder, false);
		if (!folder.exists()) {
			return null;
		}
		final String regex = filePattern.substring(filePattern.lastIndexOf("/") + 1);
		return new ArrayList<>(Arrays.asList(Util.listFilesMatching(folder, regex)));
	}

	/**
	 * Get the qa root folder absolute path
	 *
	 * @return a root folder absolute path
	 * @throws IOException if not found
	 */
	public static String getRootFolder() throws IOException {
		final File fclass = new File(ResourceFile.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		final String classPath = fclass.getAbsolutePath().replace("%20", " ");
		final String rootFolder = ConfigHelper.GIT_ROOT_FOLDER.getValue().replace("%20", " ");

		final int pos = classPath.indexOf(rootFolder);
		if (pos > 0) {
			String folder = classPath.substring(0, pos + rootFolder.length());
			folder = folder.replace("%20", " ");
			final File f = new File(folder);
			if (f.exists()) {
				return f.getCanonicalPath();
			}
		}
		return "";
	}

}