package com.neolians.common.utils.selenium.elements;

import com.neolians.common.utils.selenium.Driver;

/**
 * Class to Map Input Text WebElement
 */
public class SfSearchElement extends InputTextElement {

	public SfSearchElement(String label) {
		super(label, String.format("//label[text()='%1$s']/following::input[1]", label));
	}

	@Override
	public void setText(String value) {
		super.setText(value);
		Driver.sleep(1000);
		final BlockElement listItem = new BlockElement("//lightning-base-combobox-formatted-text[@title='%1$s']");
		listItem.setParameterName(value);
		listItem.click(false);
		if (listItem.isDisplayed()) {
			listItem.click(false);
		}
		System.out.println("ee");
	}

	@Override
	public String toString() {
		return "SfSearchElement " + (getElementName() != null ? getElementName() + ", " : "")
				+ (parentElement != null ? "ParentElement:" + parentElement.toString() + " ," : "") + "Def="
				+ getBaseDefinition() + "]";
	}
}