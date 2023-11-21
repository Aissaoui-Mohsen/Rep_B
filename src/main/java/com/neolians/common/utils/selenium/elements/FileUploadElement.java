package com.neolians.common.utils.selenium.elements;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import com.neolians.common.utils.report.Report;
import com.neolians.common.utils.selenium.Browser;
import com.neolians.common.utils.selenium.Driver;

/**
 * Class to Map Input Text WebElement
 */
public class FileUploadElement extends InputTextElement {

	public FileUploadElement(String elementName, By elementDefinition) {
		super(elementName, elementDefinition);
	}

	@Override
	public String toString() {
		return "FileUploadElement [" + (getElementName() != null ? getElementName() + ", " : "")
				+ (parentElement != null ? "ParentElement:" + parentElement.toString() + " ," : "") + "Def="
				+ getBaseDefinition() + "]";
	}

	private String display, visibility;

	private void setVisibility(boolean visible) {
		if (Browser.getBrowser() == Browser.FIREFOX) {
			/* PAtch pout faire un upload de fichier sur un Input qui est hidden */
			final JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
			if (visible) {
				display = (String) js.executeScript("return  getComputedStyle(arguments[0])[ 'display']",
						getWebElement());
				visibility = (String) js.executeScript("return  getComputedStyle(arguments[0])[ 'visibility']",
						getWebElement());
				js.executeScript("arguments[0].style.visibility='visible';", getWebElement());
				js.executeScript("arguments[0].style.display='block';", getWebElement());
			} else {
				js.executeScript("arguments[0].style.visibility='" + visibility + "';", getWebElement());
				js.executeScript("arguments[0].style.display='" + display + "';", getWebElement());
			}
		}
	}

	/**
	 * Upload a File
	 *
	 * @param file file to upload
	 */
	public void upload(File file) throws IOException {
		if (!file.exists()) {
			throw new IOException("File '" + file.getAbsolutePath() + "' does not exist");
		}
		final String fullPath = file.getCanonicalPath();
		displayElementName("Upload File on element '%1$s': " + fullPath);
		setVisibility(true);
		sendKeys(fullPath);
		setVisibility(false);
		Report.info("Upload element Hidden:", true);

	}
}