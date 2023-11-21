package com.neolians.common.utils.selenium.elements;

/**
 * Class to Map Input Text WebElement
 */
public class SfLabelElementNumber extends SeleniumObjectsBase {

	public SfLabelElementNumber(String label) {
		super(label, String.format("//span[text()='%1$s']/../..//lightning-formatted-number[1]", label));
	}

	@Override
	public String toString() {
		return "SfLabelElementNumber " + (getElementName() != null ? getElementName() + ", " : "")
				+ (parentElement != null ? "ParentElement:" + parentElement.toString() + " ," : "") + "Def="
				+ getBaseDefinition() + "]";
	}
}