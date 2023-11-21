package com.neolians.common.utils.selenium.elements;

import org.openqa.selenium.Keys;

public class RowElement extends SeleniumObjectsBase {
	public RowElement(String elementName, String xpathExpression) {
		super(elementName, xpathExpression);
	}

	@Override
	public String toString() {
		return "RowElement [" + (getElementName() != null ? getElementName() + ", " : "") + "Def=" + getBaseDefinition()
				+ "]";
	}

	/**
	 * Déplace une ligne vers le haut avec les actions de keyboard (Space et
	 * Arrow_Up)
	 *
	 * @param rowNb nombre de ligne à déplacer
	 */
	public void moveUp(int rowNb) {
		if (rowNb <= 0) {
			return;
		}
		displayElementName("Move Up Element '%1$s' " + rowNb + " times");
		final Keys[] keySequence = new Keys[rowNb + 2];
		keySequence[0] = Keys.SPACE;
		for (int i = 1; i <= rowNb; i++) {
			keySequence[i] = Keys.ARROW_UP;
		}
		keySequence[rowNb + 1] = Keys.SPACE;

		click(false);
		sendKeys(false, keySequence);

	}

	/**
	 * Déplace une ligne vers le bas avec les actions de keyboard (Space et
	 * Arrow_Down)
	 *
	 * @param rowNb nombre de ligne à déplacer
	 */
	public void moveDown(int rowNb) {
		if (rowNb <= 0) {
			return;
		}
		displayElementName("Move Down Element '%1$s' " + rowNb + " times");
		final Keys[] keySequence = new Keys[rowNb + 2];
		keySequence[0] = Keys.SPACE;
		for (int i = 1; i <= rowNb; i++) {
			keySequence[i] = Keys.ARROW_DOWN;
		}
		keySequence[rowNb + 1] = Keys.SPACE;

		click(false);
		sendKeys(false, keySequence);

	}
}
