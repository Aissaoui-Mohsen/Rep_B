package com.neolians.common.utils.selenium.elements;

/**
 * Class to Map Input Text WebElement
 */
public class SfLabelElementText extends SeleniumObjectsBase {

	public SfLabelElementText(String label) {
		super(label, String.format("//span[text()='%1$s']/../..//lightning-formatted-text[1]", label));
	}

	@Override
	public String toString() {
		return "SfLabelElementText " + (getElementName() != null ? getElementName() + ", " : "")
				+ (parentElement != null ? "ParentElement:" + parentElement.toString() + " ," : "") + "Def="
				+ getBaseDefinition() + "]";
	}
}