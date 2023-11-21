package com.neolians.common.utils.cssselectortoxpath.model;

import com.neolians.common.utils.cssselectortoxpath.utilities.CssSelectorToXPathConverterInvalidFirstLastOnlyOfType;

public enum CssPseudoClassType {

	EMPTY(":empty", "[not(*) and .=\"\"]"), FIRST_OF_TYPE(":first-of-type", "_FIRST_OF_TYPE_PLACEHOLDER_"),
	LAST_OF_TYPE(":last-of-type", "_LAST_OF_TYPE_PLACEHOLDER_"),
	ONLY_OF_TYPE(":only-of-type", "_ONLY_OF_TYPE_PLACEHOLDER_"),
	FIRST_CHILD(":first-child", "[count(preceding-sibling::*)=0]"),
	LAST_CHILD(":last-child", "[count(following-sibling::*)=0]"), N_CHILD(":n-child", "[_NTH_CHILD_PLACEHOLDER_]"),
	ONLY_CHILD(":only-child", FIRST_CHILD.getXpath("", "") + LAST_CHILD.getXpath("", ""));

	private final String typeString;
	private final String xpath;

	CssPseudoClassType(String typeStringIn, String xpathIn) {
		this.typeString = typeStringIn;
		this.xpath = xpathIn;
	}

	public String getpseudoString() {
		return typeString;
	}

	public String getXpath(String element, String value) {
		if (this == FIRST_OF_TYPE) {
			return "[count(preceding-sibling::" + element + ")=0]";
		}
		if (this == LAST_OF_TYPE) {
			return "[count(following-sibling::" + element + ")=0]";
		} else if (this == ONLY_OF_TYPE) {
			return FIRST_OF_TYPE.getXpath(element, value) + LAST_OF_TYPE.getXpath(element, value);
		} else if (this == N_CHILD) {
			return "[" + value + "]";
		}
		return xpath;
	}

	public static CssPseudoClassType pseudoClassTypeString(String unknownString, String element)
			throws CssSelectorToXPathConverterInvalidFirstLastOnlyOfType {
		if (unknownString == null) {
			return null;
		}
		if (unknownString.equals(":empty")) {
			return EMPTY;
		}
		if (unknownString.equals(":first-of-type")) {
			return getOfType(FIRST_OF_TYPE, element);
		} else if (unknownString.equals(":last-of-type")) {
			return getOfType(LAST_OF_TYPE, element);
		} else if (unknownString.equals(":only-of-type")) {
			return getOfType(ONLY_OF_TYPE, element);
		} else if (unknownString.equals(":first-child")) {
			return FIRST_CHILD;
		} else if (unknownString.startsWith(":nth-child")) {
			return N_CHILD;
		} else if (unknownString.equals(":last-child")) {
			return LAST_CHILD;
		} else if (unknownString.equals(":only-child")) {
			return ONLY_CHILD;
		}
		throw new IllegalArgumentException(unknownString);
	}

	private static CssPseudoClassType getOfType(CssPseudoClassType ofType, String element)
			throws CssSelectorToXPathConverterInvalidFirstLastOnlyOfType {
		if (element == null || element.equals("*")) {
			throw new CssSelectorToXPathConverterInvalidFirstLastOnlyOfType();
		}
		return ofType;
	}
}
