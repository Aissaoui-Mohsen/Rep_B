package com.neolians.common.utils.cssselectortoxpath.model;

public class CssAttributePseudoClass extends CssAttribute {
	private final CssPseudoClassType pseudoClassType;
	private final String element;

	public CssAttributePseudoClass(CssPseudoClassType pseudoClassTypeIn, String elementIn, String valueIn) {
		super(null, valueIn, (CssAttributeValueType) null);
		pseudoClassType = pseudoClassTypeIn;
		element = elementIn;
	}

	public String getXPath() {
		return pseudoClassType.getXpath(element, getValue());
	}

	public CssPseudoClassType getCsspseudoClassType() {
		return pseudoClassType;
	}

	@Override
	public String toString() {
		// switch(pseudoClassType) {
//		case FIRST_OF_TYPE:
//		case LAST_OF_TYPE:
//		case ONLY_OF_TYPE:
//			val += ", Element = " + element;
//			break;
//		default:
//			//empty
//			break;
//		}
		return "pseudo Class = " + pseudoClassType;
	}

}
