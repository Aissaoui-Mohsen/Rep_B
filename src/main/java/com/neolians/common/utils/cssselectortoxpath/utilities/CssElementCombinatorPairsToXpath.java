package com.neolians.common.utils.cssselectortoxpath.utilities;

import java.util.Iterator;
import java.util.List;

import com.neolians.common.utils.cssselectortoxpath.model.CssAttribute;
import com.neolians.common.utils.cssselectortoxpath.model.CssAttributePseudoClass;
import com.neolians.common.utils.cssselectortoxpath.model.CssAttributeValueType;
import com.neolians.common.utils.cssselectortoxpath.model.CssElementCombinatorPair;

public class CssElementCombinatorPairsToXpath {
	// public static final Properties VERSION_PROPERTIES=getVersionProperties();

	private final CssSelectorStringSplitter cssSelectorString = new CssSelectorStringSplitter();

	public String cssElementCombinatorPairListConversion(List<CssElementCombinatorPair> elementCombinatorPairs) {
		final StringBuilder xpathBuilder = new StringBuilder();

		xpathBuilder.append("//");
		boolean firstTime = true;
		for (final CssElementCombinatorPair elementCombinatorPair : elementCombinatorPairs) {
			if (firstTime) {
				firstTime = false;
			} else {
				xpathBuilder.append(elementCombinatorPair.getCombinatorType().getXpath());
			}
			addElementToXpathString(xpathBuilder, elementCombinatorPair);
			convertCssAttributeListToXpath(xpathBuilder, elementCombinatorPair);
		}
		return xpathBuilder.toString();
	}

	private void addElementToXpathString(StringBuilder xpathBuilder, CssElementCombinatorPair elementCombinatorPair) {
		final String element = elementCombinatorPair.getCssElementAttributes().getElement();
		if (element != null) {
			xpathBuilder.append(element);
		} else {
			xpathBuilder.append("*");
		}
	}

	private void convertCssAttributeListToXpath(StringBuilder xpathBuilder,
			CssElementCombinatorPair elementCombinatorPair) {
		final List<CssAttribute> cssAttributeList = elementCombinatorPair.getCssElementAttributes()
				.getCssAttributeList();
		// starts-with(@href, "abc")
		for (final CssAttribute cssAttribute : cssAttributeList) {
			final String name = cssAttribute.getName();
			final String value = cssAttribute.getValue();
			if (cssAttribute.getType() == CssAttributeValueType.EQUAL) {
				xpathBuilder.append("[");
				exactMatchXpath(xpathBuilder, name, value);
			} else if (cssAttribute.getType() == CssAttributeValueType.CARROT_EQUAL) {
				xpathBuilder.append("[starts-with(@");
				xpathBuilder.append(name);
				xpathBuilder.append(",\"");
				xpathBuilder.append(value);
				xpathBuilder.append("\")]");
			} else if (cssAttribute.getType() == CssAttributeValueType.DOLLAR_SIGN_EQUAL) {
				// TODO: implement this when we implement xpath 2.0
//				xpathBuilder.append("[ends-with(@");
//				xpathBuilder.append(cssAttribute.getName());
//				xpathBuilder.append(",\"");
//				xpathBuilder.append(cssAttribute.getValue());
//				xpathBuilder.append("\")]");

				xpathBuilder.append("[substring(@");
				xpathBuilder.append(name);
				xpathBuilder.append(",string-length(@");
				xpathBuilder.append(name);
				xpathBuilder.append(")-string-length(\"");
				xpathBuilder.append(value);
				xpathBuilder.append("\")+1)=\"");
				xpathBuilder.append(value);
				xpathBuilder.append("\"]");
			} else if (cssAttribute.getType() == CssAttributeValueType.STAR_EQUAL) {
				xpathBuilder.append("[contains(@");
				xpathBuilder.append(name);
				xpathBuilder.append(",\"");
				xpathBuilder.append(value);
				xpathBuilder.append("\")]");
			} else if (cssAttribute.getType() == CssAttributeValueType.TILDA_EQUAL) {
				xpathBuilder.append("[contains(concat(\" \",normalize-space(@");
				xpathBuilder.append(name);
				xpathBuilder.append("),\" \"),\" ");
				xpathBuilder.append(value);
				xpathBuilder.append(" \")]");
			} else if (cssAttribute.getType() == CssAttributeValueType.PIPE_EQUAL) {
				xpathBuilder.append("[starts-with(@");
				xpathBuilder.append(name);
				xpathBuilder.append(",concat(\"");
				xpathBuilder.append(value);
				xpathBuilder.append("\",\"-\")) or ");
				exactMatchXpath(xpathBuilder, name, value);
			} else if (cssAttribute instanceof CssAttributePseudoClass) {
				xpathBuilder.append(((CssAttributePseudoClass) cssAttribute).getXPath());
			} else if (cssAttribute.getType() == null) {
				xpathBuilder.append("[@");
				xpathBuilder.append(name);
				xpathBuilder.append("]");
			}
		}
	}

	private void exactMatchXpath(StringBuilder xpathBuilder, String name, String value) {
		xpathBuilder.append("@");
		xpathBuilder.append(name);
		xpathBuilder.append("=\"");
		xpathBuilder.append(value);
		xpathBuilder.append("\"]");
	}

	public String cssElementCombinatorPairListListConversion(
			List<List<CssElementCombinatorPair>> cssElementCombinatorPairListList) {
		final StringBuilder xpathBuilder = new StringBuilder();
		final boolean moreThanOne = cssElementCombinatorPairListList.size() > 1;
		final Iterator<List<CssElementCombinatorPair>> cssElementCombinatorPairIterator = cssElementCombinatorPairListList
				.iterator();
		while (cssElementCombinatorPairIterator.hasNext()) {
			final List<CssElementCombinatorPair> cssElementCombinatorPairList = cssElementCombinatorPairIterator.next();
			if (moreThanOne) {
				xpathBuilder.append("(");
			}
			xpathBuilder.append(cssElementCombinatorPairListConversion(cssElementCombinatorPairList));
			if (moreThanOne) {
				xpathBuilder.append(")");
			}
			if (cssElementCombinatorPairIterator.hasNext()) {
				xpathBuilder.append("|");
			}
		}
		return xpathBuilder.toString();
	}

	public String convertCssSelectorStringToXpathString(String selectorString)
			throws CssSelectorToXPathConverterException {

		final List<List<CssElementCombinatorPair>> cssElementCombinatorPairListList = cssSelectorString
				.listSplitSelectorsIntoElementCombinatorPairs(selectorString);
		// System.out.println("CSS Selector="+selectorString+", Xpath string="+xpath);
		return cssElementCombinatorPairListListConversion(cssElementCombinatorPairListList);
	}

	public String niceConvertCssSelectorToXpathForOutput(String cssSelector)
			throws NiceCssSelectorStringForOutputException {
		try {
			return convertCssSelectorStringToXpathString(cssSelector);
		} catch (CssSelectorToXPathConverterException | RuntimeException e) {
			String error;
			if (e instanceof CssSelectorToXPathConverterException) {
				if (e.getMessage().trim().length() > 0) {
					error = "Error: " + e.getMessage();
				} else {
					error = "Error:  Invalid CSS Selector";
				}
			} else {
				error = "Unexpected Error:  " + e;
				e.printStackTrace();
			}
			throw new NiceCssSelectorStringForOutputException(error, e);
		}
	}

	public String mainGo(String[] args) throws NiceCssSelectorStringForOutputException {

		if (args.length != 1) {
			throw new RuntimeException(getUsageString());
		}
		if ("-help".equalsIgnoreCase(args[0]) || "-h".equalsIgnoreCase(args[0])) {
			return getUsageString();
		}
		return "XPath = " + niceConvertCssSelectorToXpathForOutput(args[0]);
	}

	protected String getUsageString() {
		return "\r\nUsage:\r\n\r\n"
				+ "    java -cp com.fircosoft.common.utils.java-cssSelector-to-xpath-<version number> com.fircosoft.common.utils.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath <CSS Selector String>\r\n"
				+ "    -	Converts a CSS Sector String to a Xpath String\r\n"
				+ "    java -cp com.fircosoft.common.utils.java-cssSelector-to-xpath-<version number> com.fircosoft.common.utils.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath -v{ersion}\r\n"
				+ "    -	Displays  the version number of java-cssSlector-to-xpath\r\n"
				+ "    Java -cp com.fircosoft.common.utils.java-cssSelector-to-xpath-<version number> com.fircosoft.common.utils.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath -h{elp}\r\n"
				+ "    -	Displays this help usage text \r\n";
	}
	/*
	 * public String getVersionNumber() { return
	 * VERSION_PROPERTIES.getProperty("java.cssSelector.to.xpath.version"); }
	 *
	 * public static void main(String[] args) { int exitValue=0; try { //
	 * System.out.println(new CssElementCombinatorPairsToXpath().mainGo(args)); }
	 * catch (RuntimeException | NiceCssSelectorStringForOutputException e) {
	 * System.err.println(e.getMessage()); exitValue=1; } System.exit(exitValue); }
	 */

}
