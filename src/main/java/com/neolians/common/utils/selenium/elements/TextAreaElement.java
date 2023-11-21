package com.neolians.common.utils.selenium.elements;

import org.openqa.selenium.By;

/**
 * Class to Map Textarea WebElement
 *
 * @author palbaret
 */
public class TextAreaElement extends SeleniumObjectsBase {

	public TextAreaElement(String elementName, By elementDefinition) {
		super(elementName, elementDefinition);
	}

	public TextAreaElement(By elementDefinition) {
		super(elementDefinition);
	}

	/**
	 * Set a text inside an textarea Componant
	 */
	public void setText(String text) {
		super.clear();
		super.sendKeys(text);
	}

	/************ Fin */

	@Override
	public String toString() {
		return "TextAreaElement [" + (getElementName() != null ? getElementName() + ", " : "") + "Def="
				+ getBaseDefinition() + "]";
	}
}