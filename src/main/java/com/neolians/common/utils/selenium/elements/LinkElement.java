package com.neolians.common.utils.selenium.elements;

import org.openqa.selenium.By;

/**
 * Class to Map Link (URl <a>) WebElement
 */
public class LinkElement extends SeleniumObjectsBase {

	public LinkElement(By elementDefinition) {
		super(elementDefinition);
	}

	public LinkElement(String elementName, SeleniumObjectsBase parentElement, By elementDefinition) {
		super(elementName, parentElement, elementDefinition);
	}

	/**
	 * returns the Href value of an link Element
	 *
	 * @return he Href value of an link Element
	 */
	public String getLink() {
		return super.getAttribute("href");
	}

	/************ Fin */
	@Override
	public String toString() {
		return "LinkElement [" + (getElementName() != null ? getElementName() + ", " : "") + "Def="
				+ getBaseDefinition() + "]";
	}
}