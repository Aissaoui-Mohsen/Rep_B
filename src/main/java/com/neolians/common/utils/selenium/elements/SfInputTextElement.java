package com.neolians.common.utils.selenium.elements;

/**
 * Class to Map Input Text WebElement
 */
public class SfInputTextElement extends InputTextElement {

	public SfInputTextElement(String label) {
		super(label, String.format("//label[text()='%1$s']/following::input[1]", label));
	}

	@Override
	public void setText(String value) {
		super.setText(value);

	}

	@Override
	public String toString() {
		return "SfInputTextElement " + (getElementName() != null ? getElementName() + ", " : "")
				+ (parentElement != null ? "ParentElement:" + parentElement.toString() + " ," : "") + "Def="
				+ getBaseDefinition() + "]";
	}
}