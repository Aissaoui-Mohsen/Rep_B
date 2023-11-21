package com.neolians.common.utils.selenium.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Class to Map Option WebElement
 */
public class OptionElement extends SeleniumObjectsBase {

	/**
	 * Create OptionElement only by its definition
	 * <p>
	 * element may be disabled or hidden
	 */
	OptionElement(WebElement element) {
		super(element);
	}

	public OptionElement(By elementDefinition) {
		super(elementDefinition);
	}

	/**
	 * returns the Value of an Option Element
	 *
	 * @return option value
	 */
	public String getValue() {
		return super.getAttribute("value");
	}

	/************ Fin */
	@Override
	public String toString() {
		return "OptionElement [Def=" + getBaseDefinition() + "]";
	}
}