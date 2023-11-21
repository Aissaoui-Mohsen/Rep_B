package com.neolians.common.utils.selenium.elements;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;

/**
 * Class to Map Input Text WebElement
 */
public class InputTextElement extends SeleniumObjectsBase {

	public InputTextElement(String elementName, By elementDefinition) {
		super(elementName, elementDefinition);
	}

	public InputTextElement(String elementName, String xpathExpression) {
		super(elementName, xpathExpression);
	}

	public InputTextElement(By elementDefinition) {
		super(elementDefinition);
	}

	public InputTextElement(SeleniumObjectsBase parentWebElement, String xpathExpression) {
		super(parentWebElement, xpathExpression);
	}

	public InputTextElement(String xpathExpression) {
		super(xpathExpression);
	}

	public InputTextElement(String elementName, SeleniumObjectsBase parentWebElement, By elementDefinition) {
		super(elementName, parentWebElement, elementDefinition);
	}

	public InputTextElement(String elementName, SeleniumObjectsBase parentWebElement, String xpathExpression) {
		super(elementName, parentWebElement, xpathExpression);
	}

	/**
	 * Set a text inside an input Component
	 *
	 * @param text Text to set
	 */
	public void setText(int text) {
		setText("" + text);
	}

	/**
	 * Set a text inside an input Component
	 *
	 * @param date Text to set
	 */
	public void setText(Date date) {
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		setText(sdf.format(date));
	}

	/**
	 * Set a text inside an input Component
	 *
	 * @param text Text to set
	 */
	public void setText(String text) {
		displayElementName("Set value on '%1$s' => " + escapeElementNameCharacter(text));
		super.clear();
		super.sendKeys(false, text);
	}

	/**
	 * Get the value of an inputTextElement
	 *
	 * @return the input value
	 */
	public String getValue() {
		return super.getAttribute("value").replace("\u00A0", " ").trim();
	}

	@Override
	public String toString() {
		return "InputTextElement " + (getElementName() != null ? getElementName() + ", " : "")
				+ (parentElement != null ? "ParentElement:" + parentElement.toString() + " ," : "") + "Def="
				+ getBaseDefinition() + "]";
	}
}