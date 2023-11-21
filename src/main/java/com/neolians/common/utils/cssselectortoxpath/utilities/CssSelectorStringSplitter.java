package com.neolians.common.utils.cssselectortoxpath.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.neolians.common.utils.cssselectortoxpath.model.CssCombinatorType;
import com.neolians.common.utils.cssselectortoxpath.model.CssElementCombinatorPair;

public class CssSelectorStringSplitter {
	public static final String ERROR_INVALID_SELECTOR = "Invalid Selector";
	public static final String ERROR_NO_CSS_SELECTORS = "No CSS Selectors";
	public static final String ERROR_EMPTY_CSS_SELECTOR = "Empty CSS Selector";
	public static final String ERROR_INVALID_CSS_SELECTOR_TRAILING_COMMA = "Invalid CSS Selector, trailing ','";
	public static final String ERROR_INVALID_CSS_SELECTOR_UNCLOSED = "Invalid CSS Selector, unclosed '['";
	private static final String COMBINATORS = " ~+>";
	private static final String COMBINATOR_RE = "[" + COMBINATORS + "]";

	private static final String ELEMENT_AND_ATTRIBUTE = "([^" + COMBINATORS + "\\[]*((\\[[^\\]]+\\])|"
			+ CssElementAttributeParser.pseudo_RE + ")*)";
	private static final String ELEMENT_AND_ATTRIBUTE_FOLLOWED_BY_COMBINATOR_AND_REST_OF_LINE = "^"
			+ ELEMENT_AND_ATTRIBUTE + "($|(\\s*(" + COMBINATOR_RE + ")\\s*" + "([^" + COMBINATORS + "].*)$))";

	public static final String ERROR_INVALID_CLASS_CSS_SELECTOR = "Invalid class CSS Selector";
	public static final String ERROR_INVALID_ID_CSS_SELECTOR = "Invalid id CSS Selector";
	public static final String ERROR_SELECTOR_STRING_IS_NULL = "CSS Selector String is null";
	private static final String PLACE_HOLDER = "~@_placeHolder_@";

	protected String removeNonCssSelectorWhiteSpaces(String selectorString)
			throws CssSelectorToXPathConverterException {
//	This method should perform the following
//	i.	Remove all leading and trailing white spaces
//	ii.	Remove all white spaces except tabs and actual space(" ")
//	1.	Note, very important
//	a.	'\t' corresponds to the tab character in java
//	iii.	Consolidate all consecutive tabs and " " into a single " " and single tab into a " "
//	1.	The final string should have no tabs only non consecutive spaces
//	b.	Implementation
//	i.	Check for null string and if found throw a CssSelectorStringSplitterException
//	ii.	Use String.trim() to remove leading and trailing spaces
//	iii.	Use String.replaceAll()  to manipulate the string
//	1.	The tricky part is that tab and " " are both white spaces
//	2.	Preprocess the string
//	a.	Replace all tabs with  a unique string like "~+_placeHolder_+"
//	b.	Replace all spaces with the same unique string
//	i.	Remember at the end we want only spaces
//	3.	Replace all white spaces with the empty string ""
//	4.	Replace "~+_placeHolder_+" with " "
		if (selectorString == null) {
			throw new CssSelectorToXPathConverterException(ERROR_SELECTOR_STRING_IS_NULL);
		}
		selectorString = selectorString.trim();
		selectorString = selectorString.replaceAll("[ \\t]+", PLACE_HOLDER);
		selectorString = selectorString.replaceAll("\\s+", "");
		selectorString = selectorString.replaceAll("(" + PLACE_HOLDER + ")+", " ");
		selectorString = classIdAttributeIssueHandler(selectorString, "#", "id=");
		selectorString = classIdAttributeIssueHandler(selectorString, ".", "class~=");
		// System.out.println("STRING="+selectorString);
		return selectorString;
	}

	private String classIdCombinatorRE() {
		final StringBuilder builder = new StringBuilder("([^.#\\[,");
		for (final CssCombinatorType combinatorType : CssCombinatorType.values()) {
			builder.append(combinatorType.getCombinatorChar());
		}
		builder.append("]+)");
		return builder.toString();

	}

	protected void invalidClassIdPairCheck(String selectorString, boolean testId)
			throws CssSelectorToXPathConverterException {
		final String nextSelectorIdentifier = "[.#\\[]";
		if (testId) {
			final Pattern pattern = Pattern.compile("#" + nextSelectorIdentifier);
			final Matcher match = pattern.matcher(selectorString);
			if (match.find()) {
				throw new CssSelectorToXPathConverterException(ERROR_INVALID_ID_CSS_SELECTOR);
			}
		} else {
			final Pattern pattern = Pattern.compile("[.]" + nextSelectorIdentifier);
			final Matcher match = pattern.matcher(selectorString);
			if (match.find()) {
				throw new CssSelectorToXPathConverterException(ERROR_INVALID_CLASS_CSS_SELECTOR);
			}
		}
	}

	private String classIdAttributeIssueHandler(String selectorString, String classOrIdChar,
			String classOrIdPartialAttributeNameAndRelationship) throws CssSelectorToXPathConverterException {
		// very special case where string ends with '['
		if (selectorString.endsWith("[")) {
			throw new CssSelectorToXPathConverterException(ERROR_INVALID_CSS_SELECTOR_UNCLOSED);
		}
		final String classOrIdCharacterRE = "[" + classOrIdChar + "]";
		final String attributeGeneralRE = "([^\\[]*)((\\[[^\\]]*\\])*)";
		final Pattern pattern = Pattern.compile(attributeGeneralRE);
		final Matcher match = pattern.matcher(selectorString);
		// System.out.println(selectorString);
		boolean found = false;
		final StringBuilder stringBuffer = new StringBuilder();
		while (match.find()) {
			stringBuffer.append(match.group(1));
			stringBuffer.append(match.group(2).replaceAll(classOrIdCharacterRE, PLACE_HOLDER));

			found = true;
		}
		// System.out.println("SB"+stringBuffer);
		selectorString = stringBuffer.toString();

		selectorString = selectorString.replaceAll(classOrIdCharacterRE + classIdCombinatorRE(),
				"[" + classOrIdPartialAttributeNameAndRelationship + "\"$1\"]");
		invalidClassIdPairCheck(selectorString, "#".equals(classOrIdChar));

		if (found) {
			selectorString = selectorString.replaceAll(PLACE_HOLDER, classOrIdChar);
		}

		return selectorString;
	}

	public List<String> splitSelectors(String selectorString) throws CssSelectorToXPathConverterException {
		selectorString = removeNonCssSelectorWhiteSpaces(selectorString);
		// System.out.println("ADJUSTED="+selectorString);
		// selectorString=removeNonCssSelectorWhiteSpaces(selectorString);
		// split() will not error out if there is a trailing ','
		final int index = selectorString.lastIndexOf(',');
		final int cssSelectorStringLength = selectorString.length();
		if ((cssSelectorStringLength > 0) && (index == (cssSelectorStringLength - 1))) {
			throw new CssSelectorToXPathConverterException(ERROR_INVALID_CSS_SELECTOR_TRAILING_COMMA);
		}
		final String[] selectorArray = selectorString.split(",");
		final List<String> selectorList = new ArrayList<>();
		for (String selector : selectorArray) {
			selector = selector.trim();
			if (selector.isEmpty()) {
				throw new CssSelectorToXPathConverterException(ERROR_EMPTY_CSS_SELECTOR);
			}

			selectorList.add(selector);
		}
		if (selectorList.isEmpty()) {
			throw new CssSelectorToXPathConverterException(ERROR_NO_CSS_SELECTORS);
		}
		return selectorList;
	}

	protected List<CssElementCombinatorPair> splitSelectorsIntoElementCombinatorPairs(String processedSelector)
			throws CssSelectorToXPathConverterException {
		final List<CssElementCombinatorPair> selectorList = new ArrayList<>();
		recursiveSelectorSplit(null, processedSelector, selectorList);
		return selectorList;
	}

	protected void recursiveSelectorSplit(CssCombinatorType previousCombinatorType, String cssSelector,
			List<CssElementCombinatorPair> selectorList) throws CssSelectorToXPathConverterException {

		final Pattern cssCombinator = Pattern.compile(ELEMENT_AND_ATTRIBUTE_FOLLOWED_BY_COMBINATOR_AND_REST_OF_LINE);
		final Matcher match = cssCombinator.matcher(cssSelector);
		// System.out.println(XY);
		if (!match.find()) {
			throw new CssSelectorToXPathConverterException(ERROR_INVALID_SELECTOR);

		}
		final CssCombinatorType type = CssCombinatorType.combinatorTypeChar(match.group(8));
		// System.out.println("TYPE:"+type);
		if (type != null) {
			final String firstCssSelector = match.group(1);
			// System.out.println("firstcss"+firstCssSelector);
			if (firstCssSelector.isEmpty()) {
				throw new CssSelectorToXPathConverterException(ERROR_EMPTY_CSS_SELECTOR);
			}
			selectorList.add(new CssElementCombinatorPair(previousCombinatorType, firstCssSelector));
			final String nextCssSelector = match.group(9);
			// System.out.println("nextcss="+nextCssSelector+"; type"+type);

			if (nextCssSelector.isEmpty()) {
				throw new CssSelectorToXPathConverterException(ERROR_EMPTY_CSS_SELECTOR);
			}
			recursiveSelectorSplit(type, nextCssSelector, selectorList);
		} else {
			if (cssSelector.isEmpty()) {
				throw new CssSelectorToXPathConverterException(ERROR_EMPTY_CSS_SELECTOR);
			}
			selectorList.add(new CssElementCombinatorPair(previousCombinatorType, cssSelector));
		}
	}

	public List<List<CssElementCombinatorPair>> listSplitSelectorsIntoElementCombinatorPairs(String selectorString)
			throws CssSelectorToXPathConverterException {
		// System.out.println("###"+selectorString);

		final List<List<CssElementCombinatorPair>> listList = new ArrayList<>();
		final List<String> selectorList = splitSelectors(selectorString);
		for (final String selector : selectorList) {
			final List<CssElementCombinatorPair> cssElementCombinatorPairList = splitSelectorsIntoElementCombinatorPairs(
					selector);
			listList.add(cssElementCombinatorPairList);
		}

		// System.out.println("$$$$"+listList);
		// System.out.println("XXX");

		return listList;
	}

}
