package com.neolians.common.utils.cssselectortoxpath.model;

import com.neolians.common.utils.cssselectortoxpath.utilities.CssElementAttributeParser;
import com.neolians.common.utils.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;

public class CssElementCombinatorPair {
	private final CssCombinatorType combinatorType;
	private final CssElementAttributes cssElementAttributes;

	public CssElementCombinatorPair(CssCombinatorType combinatorTypeIn, String cssElementAttributesStringIn)
			throws CssSelectorToXPathConverterException {
		this.combinatorType = combinatorTypeIn;
		this.cssElementAttributes = new CssElementAttributeParser()
				.createElementAttribute(cssElementAttributesStringIn);
	}

	public CssCombinatorType getCombinatorType() {
		return combinatorType;
	}

	public CssElementAttributes getCssElementAttributes() {
		return cssElementAttributes;
	}

	@Override
	public String toString() {
		return "(Combinator=" + this.getCombinatorType() + ", " + this.cssElementAttributes + ")";
	}

	@Override
	public boolean equals(Object cssElementCombinatorPair) {
		if (cssElementCombinatorPair == null) {
			return false;
		}
		return this.toString().equals(cssElementCombinatorPair.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
