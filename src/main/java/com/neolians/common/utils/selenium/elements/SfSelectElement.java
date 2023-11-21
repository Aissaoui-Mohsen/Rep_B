package com.neolians.common.utils.selenium.elements;

import com.neolians.common.utils.selenium.Driver;

/**
 * Class to Map Input Text WebElement
 */
public class SfSelectElement extends SeleniumObjectsBase {

	public SfSelectElement(String label) {
		super(label, String.format(
				"//label[text()='%1$s']/following::button/following::button[@aria-haspopup='listbox'][1]", label));
	}

	public void select(String value) {
		displayElementName("Select on : '%1$s' => " + escapeElementNameCharacter(value));
		super.click(false);
		Driver.sleep(1000);
		final BlockElement listItem = new BlockElement(
				"//label[text()='%1$s']/following::lightning-base-combobox-item[@data-value='%2$s']");
		listItem.setParameterName(getElementName());
		listItem.setParameter2Name(value);
		listItem.click(false);
	}

	@Override
	public String toString() {
		return "SfSelectElement " + (getElementName() != null ? getElementName() + ", " : "")
				+ (parentElement != null ? "ParentElement:" + parentElement.toString() + " ," : "") + "Def="
				+ getBaseDefinition() + "]";
	}
}