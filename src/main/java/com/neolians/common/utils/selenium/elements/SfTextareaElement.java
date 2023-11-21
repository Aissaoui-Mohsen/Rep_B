package com.neolians.common.utils.selenium.elements;

public class SfTextareaElement extends InputTextElement {

	public SfTextareaElement(String label) {
		super(label, String.format("//label[text()='%1$s']/following::textarea[1]", label));
	}

	@Override
	public void setText(String value) {
		super.setText(value);

	}

	@Override
	public String toString() {
		return "SfTextareaElement " + (getElementName() != null ? getElementName() + ", " : "")
				+ (parentElement != null ? "ParentElement:" + parentElement.toString() + " ," : "") + "Def="
				+ getBaseDefinition() + "]";
	}
}