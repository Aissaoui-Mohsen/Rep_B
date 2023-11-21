package com.neolians.common.utils.selenium.elements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Class to Map Select WebElement
 *
 * @author palbaret
 */
public class SelectElement extends SeleniumObjectsBase {

	public SelectElement(By elementDefinition) {
		super(elementDefinition);
	}

	public SelectElement(String elementName, By elementDefinition) {
		super(elementName, elementDefinition);
	}

	public SelectElement(String elementName, SeleniumObjectsBase parentWebElement, By elementDefinition) {
		super(elementName, parentWebElement, elementDefinition);
	}

	public SelectElement(SeleniumObjectsBase parentWebElement, String xpathExpression) {
		super(parentWebElement, xpathExpression);
	}

	public SelectElement(String elementName, String xpathExpression) {
		super(elementName, xpathExpression);
	}

	public enum SelectMethod {
		TEXT, INDEX, VALUE, CLICK
	}

	private Select getSelectWebElement() {
		return new Select(super.getWebElement());
	}

	public void selectItem(String itemToSelect) {
		if (itemToSelect == null) {
			// on ne set pas quelque chose de vide
			return;
		}
		selectItem(itemToSelect, SelectMethod.VALUE);
	}

	/**
	 * Select an option in a select element
	 *
	 * @param itemToSelect : item to select (Index, text or option value)
	 * @param selectMethod Method to select (default: Value
	 */
	public void selectItem(String itemToSelect, SelectMethod selectMethod) {
		displayElementName("Select on '%1$s' : " + (selectMethod == SelectMethod.INDEX ? "#" : "")
				+ itemToSelect.replace("%", "%%"));
		webElement = null;
		if (!waitForElementPresent()) {
			assertPresent();
		}
		switch (selectMethod) {
		case TEXT:
			getSelectWebElement().selectByVisibleText(itemToSelect);
			break;
		case INDEX:
			try {
				getSelectWebElement().selectByIndex(Integer.parseInt(itemToSelect));
			} catch (final NoSuchElementException ex) {
				final List<WebElement> weOptions = getSelectWebElement().getOptions();
				if (getElementName() != null) {
					final String msg = "Cannot find Item[" + itemToSelect + "] for Select: " + getElementName()
							+ " ( Only " + weOptions.size() + " available)";
					throw new NoSuchElementException(msg, ex);
				}
			}
			break;
		case VALUE:
			getSelectWebElement().selectByValue(itemToSelect);
			break;
		case CLICK:
			final List<WebElement> weOptions = getSelectWebElement().getOptions();
			for (final WebElement weOption : weOptions) {
				if (weOption.getText().contains(itemToSelect.trim())) {
					weOption.click();
					return;
				}
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Get the selected Option Element
	 *
	 * @return the selected option
	 */
	public OptionElement getSelectedOption() {
		final List<WebElement> weOptions = getSelectWebElement().getAllSelectedOptions();
		if (weOptions.size() > 0) {
			return new OptionElement(weOptions.get(0));

		}
		return null;
	}

	/**
	 * Get the list of Options Element
	 */
	public List<OptionElement> getOptions() {

		final List<WebElement> weOptions = getSelectWebElement().getOptions();
		final List<OptionElement> options = new ArrayList<>();
		for (final WebElement weOption : weOptions) {
			options.add(new OptionElement(weOption));
		}
		return options;
	}

	@Override
	public String toString() {
		return "SelectElement [" + (getElementName() != null ? getElementName() + ", " : "") + "Def="
				+ getBaseDefinition() + "]";
	}
}