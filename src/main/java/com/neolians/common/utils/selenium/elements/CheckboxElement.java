package com.neolians.common.utils.selenium.elements;

import java.util.List;

import com.neolians.common.utils.DoAssert;
import com.neolians.common.utils.selenium.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
//import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;

/**
 * Class to Checkbox WebElement
 */
public class CheckboxElement extends SeleniumObjectsBase {

	public CheckboxElement(String elementName, SeleniumObjectsBase parentWebElement, By elementDefinition) {
		super(elementName, parentWebElement, elementDefinition);
	}

	public CheckboxElement(String elementName, String xpathExpression) {
		super(elementName, xpathExpression);
	}

	/**
	 * @param parentElement elemen parent
	 * @return list des checkbox selectionnées au sein d'un element
	 */
	public static List<WebElement> findCheckedElements(BlockElement parentElement) {
		return parentElement.findElements(By.cssSelector("svg.checked"));
	}
	/// endregion

	/**
	 * @return true if checked
	 */
	public boolean isChecked() {
		waitForElementPresent(Driver.LONG_TIME);
		String checked = getAttribute("checked");
		return checked != null && checked.equalsIgnoreCase("true");
	}

	/**
	 * set a checkbox
	 *
	 * @param checkboxState true if checked, false if unchecked
	 */
	public void setState(boolean checkboxState) {
		if (checkboxState) {
			check();
		} else {
			uncheck();
		}

	}

	/**
	 * Check input checkbox element
	 */
	public void check() {
		displayElementName("Tick '%1$s'");
		if (!isChecked()) {
			try {
				click(false);
			} catch (final ElementNotInteractableException ex) {
				if (waitForElementPresent(Driver.LONG_TIME)) {
					click(false);
				}
			}
		}
	}

	/**
	 * unCheck input checkbox element
	 */
	public void uncheck() {
		displayElementName("Untick '%1$s'");
		if (isChecked()) {
			click(false);
		}
	}

	@Override
	public String toString() {
		return "CheckboxElement [" + (getElementName() != null ? getElementName() + ", " : "")
				+ (parentElement != null ? "ParentElement:" + parentElement.toString() + " ," : "") + "Def="
				+ getBaseDefinition() + "]";
	}

	/**
	 * Check if a checkbox element is Checked or not
	 *
	 * @param expectedCheckedState expected checked state
	 */
	public void assertState(boolean expectedCheckedState) {
		assertState(expectedCheckedState, false);
	}

	/**
	 * Check if a checkbox element is Checked or not
	 *
	 * @param expectedCheckedState expected checked state
	 * @param takeScreenshot       Take screenshot in case of error
	 */
	public void assertState(boolean expectedCheckedState, boolean takeScreenshot) {
		final boolean actualState;
		final String actionName;
		if (expectedCheckedState) {
			actionName = "Verify if '%1$s' is checked";
			actualState = isChecked();
		} else {
			actionName = "Verify if '%1$s' is unchecked";
			actualState = !isChecked();
		}
		DoAssert.assertTrue(actualState, String.format(actionName, getElementName()), takeScreenshot);
	}

	public boolean verifyState(StringBuilder completeMessage, boolean expectedCheckedState) {
		final boolean actualState;
		final String actionName;
		if (expectedCheckedState) {
			actionName = "Verify if '%1$s' is checked";
			actualState = isChecked();
		} else {
			actionName = "Verify if '%1$s' is unchecked";
			actualState = !isChecked();
		}
		return DoAssert.verifyTrue(completeMessage, actualState, String.format(actionName, getElementName()));
	}
}