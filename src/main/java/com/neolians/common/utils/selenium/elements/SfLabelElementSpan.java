package com.neolians.common.utils.selenium.elements;

public class SfLabelElementSpan extends SeleniumObjectsBase {

	public SfLabelElementSpan(String label) {
		super(label, String.format("//span[text()='%1$s']/../..//span//span[1]", label));
	}

	@Override
	public String toString() {
		return "SfLabelElementSpan " + (getElementName() != null ? getElementName() + ", " : "")
				+ (parentElement != null ? "ParentElement:" + parentElement.toString() + " ," : "") + "Def="
				+ getBaseDefinition() + "]";

	}

}
