package com.neolians.common.utils.selenium.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.neolians.common.utils.selenium.Driver;

/**
 * Class to Map Block (Div, Span) WebElement
 */
public class BlockElement extends SeleniumObjectsBase {

	public BlockElement(String elementName, String xpathExpression) {
		super(elementName, xpathExpression);
	}

	public BlockElement(String elementName, By elementDefinition) {
		super(elementName, elementDefinition);
	}

	public BlockElement(By elementDefinition) {
		super(elementDefinition);
	}

	public BlockElement(SeleniumObjectsBase parentElement, String xpathExpression) {
		super(parentElement, xpathExpression);
	}

	public BlockElement(SeleniumObjectsBase parentElement, By elementDefinition) {
		super(parentElement, elementDefinition);
	}

	public BlockElement(String xpathExpression) {
		super(xpathExpression);
	}

	public BlockElement(String elementName, SeleniumObjectsBase parentElement, String xpathExpression) {
		super(elementName, parentElement, xpathExpression);

	}

	/************ Fin */

	@Override
	public String toString() {
		return "BlockElement [" + (getElementName() != null ? getElementName() + ", " : "")
				+ (parentElement != null ? "ParentElement:" + parentElement.toString() + " ," : "") + "Def="
				+ getBaseDefinition() + "]";
	}

	/**
	 * Displays element in browser
	 */
	@Override
	public void setVisible() {

		final JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
		final WebElement e = getWebElement();
		final int locationX = e.getLocation().getX();
		final int locationY = e.getLocation().getY() - 100;
		js.executeScript("window.scrollTo(arguments[0],arguments[1]);", locationX, locationY);
	}

}