package com.neolians.common.utils.selenium.elements;

import org.openqa.selenium.By;

/**
 * Class to Map Button WebElement
 *
 * @author palbaret
 */
public class ButtonElement extends SeleniumObjectsBase {

	public ButtonElement(String elementName, By elementDefinition) {
		super(elementName, elementDefinition);
	}

	public ButtonElement(String elementName, String xpathExpression) {
		super(elementName, xpathExpression);
	}

	public ButtonElement(By elementDefinition) {
		super(elementDefinition);
	}

	public ButtonElement(SeleniumObjectsBase parentElement, String xpathExpression) {
		super(parentElement, xpathExpression);
	}

	public ButtonElement(String elementName, SeleniumObjectsBase parentElement, String xpathExpression) {
		super(elementName, parentElement, xpathExpression);
	}

	public ButtonElement(String xpathExpression) {
		super(xpathExpression);
	}

	@Override
	public String toString() {
		return "ButtonElement [" + (getElementName() != null ? getElementName() + ", " : "")
				+ (parentElement != null ? "ParentElement:" + parentElement.toString() + " ," : "") + "Def="
				+ getBaseDefinition() + "]";
	}
}