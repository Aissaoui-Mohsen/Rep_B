package com.neolians.common.utils.cssselectortoxpath.utilities;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.neolians.common.utils.cssselectortoxpath.model.CssAttribute;
import com.neolians.common.utils.cssselectortoxpath.model.CssAttributePseudoClass;
import com.neolians.common.utils.cssselectortoxpath.model.CssAttributeValueType;
import com.neolians.common.utils.cssselectortoxpath.model.CssElementAttributes;
import com.neolians.common.utils.cssselectortoxpath.model.CssPseudoClassType;

public class CssElementAttributeParser {
	public static final String ERROR_INVALID_ATTRIBUTE_VALUE = "Invalid attribute value";
	public static final String ERROR_INVALID_ELEMENT_AND_OR_ATTRIBUTES = "Invalid element and/or attributes";
	public static final String ERROR_QUOTATIONS_MISMATCHED = "Quotations mismatched";

	private static final String QUOTES_RE = "([\"\'])";
	private static final String ATTRIBUTE_VALUE_RE = "([-_.#a-zA-Z0-9:\\/ ]+)";
	private static final String ATTRIBUTE_VALUE_RE_NO_SPACES = "([-_.#a-zA-Z0-9:\\/]+)";
	private static final String ATTRIBUTE_TYPE_RE = createElementAttributeNameRegularExpression();
	private static final String ELEMENT_ATTRIBUTE_NAME_RE = "(-?[_a-zA-Z]+[_a-zA-Z0-9-]*)";
	private static final String STARTING_ELEMENT_RE = "^(" + ELEMENT_ATTRIBUTE_NAME_RE + "|([*]))?";
	public static final String pseudo_RE = "(:[a-z][a-z\\-]*([(][^)]+[)])?)";
	private static final String ATTRIBUTE_RE = "(" + pseudo_RE + "|(\\[" + "\\s*" + ELEMENT_ATTRIBUTE_NAME_RE + "\\s*"
			+ ATTRIBUTE_TYPE_RE + "\\s*" + "((" + QUOTES_RE + ATTRIBUTE_VALUE_RE + QUOTES_RE + ")|("
			+ ATTRIBUTE_VALUE_RE_NO_SPACES + "))?" + "\\s*" + "\\]))";

	private static String createElementAttributeNameRegularExpression() {
		final StringBuilder builder = new StringBuilder();
		for (final CssAttributeValueType type : CssAttributeValueType.values()) {
			if (builder.length() == 0) {
				builder.append("((");
			} else {
				builder.append(")|(");
			}
			builder.append("\\").append(type.getEqualStringName());
		}
		builder.append("))?");
		// System.out.println("elementAttributeRE="+builder);
		return builder.toString();
	}

	public void checkValid(String elementWithAttributesString) throws CssSelectorToXPathConverterException {
		final int reIndexAttributeValueType = 9;
		final int reIndexAttributeValue = 16;
		final int reIndexStartingQuote = reIndexAttributeValue + 2;
		final int reIndexEndingQuote = reIndexStartingQuote + 2;

		if (checkPseudoChild(elementWithAttributesString) != null) {
			elementWithAttributesString = removeNthChild(elementWithAttributesString);
		}
		// System.out.println("checkValid: "+elementWithAttributesString+"
		// ,re="+STARTING_ELEMENT_RE+ATTRIBUTE_RE+"*$");
		final Pattern cssElementAtributePattern = Pattern.compile(STARTING_ELEMENT_RE + ATTRIBUTE_RE + "*$");
		final Matcher match = cssElementAtributePattern.matcher(elementWithAttributesString);
		if (!match.find()) {
			throw new CssSelectorToXPathConverterException(ERROR_INVALID_ELEMENT_AND_OR_ATTRIBUTES);
		}
		// System.out.println();
		final boolean cssAttributeValueTypeExists = match.group(reIndexAttributeValueType) != null;
		final boolean cssAttributeValueExists = match.group(reIndexAttributeValue) != null;
		// System.out.println("Type="+cssAttributeValueTypeExists+",
		// Value="+cssAttributeValueExists);
		if ((cssAttributeValueTypeExists && !cssAttributeValueExists)
				|| (!cssAttributeValueTypeExists && cssAttributeValueExists)) {
			throw new CssSelectorToXPathConverterException(ERROR_INVALID_ATTRIBUTE_VALUE);
		}

		final String startQuote = match.group(reIndexStartingQuote);
		final String endQuote = match.group(reIndexEndingQuote);
		final boolean startQuoteExists = startQuote != null;
		// note the only way startQuote could be null is that there no attribute value
		if (startQuoteExists && !(startQuote.equals(endQuote))) {
			throw new CssSelectorToXPathConverterException(ERROR_QUOTATIONS_MISMATCHED);
		}
		// System.out.println("Valid: "+elementWithAttributesString);
	}

	public CssElementAttributes createElementAttribute(String elementWithAttributesString)
			throws CssSelectorToXPathConverterException {
		final int rePseudoClass = 2;
		final int reIndexAttributeName = 5;
		final int reIndexAttributeType = reIndexAttributeName + 1;
		final int reIndexAttributeValueWithQuotes = 14;
		final int reIndexAttributeValueWithinQuotes = reIndexAttributeValueWithQuotes + 2;
		final int reIndexAttributeValueWithoutQuotes = reIndexAttributeValueWithinQuotes + 2;

		checkValid(elementWithAttributesString);
		final Pattern startingCssElementAtributePattern = Pattern.compile(STARTING_ELEMENT_RE);
		final Matcher match1 = startingCssElementAtributePattern.matcher(elementWithAttributesString);
		List<CssAttribute> attributeList = new ArrayList<>();

		String element = null;
		if (match1.find()) {
			final String possibleElement = match1.group();
			if (!possibleElement.isEmpty()) {
				element = possibleElement;
				// System.out.println(possibleElement);
			}
		}
		final String nchildValue = CssElementAttributeParser.checkPseudoChild(elementWithAttributesString);
		CssAttributePseudoClass nchildAttribute = null;
		if (nchildValue != null) {
			elementWithAttributesString = CssElementAttributeParser.removeNthChild(elementWithAttributesString);
			nchildAttribute = new CssAttributePseudoClass(CssPseudoClassType.N_CHILD, element, nchildValue);
		}
		final Pattern restOfCssElementAtributePattern = Pattern.compile(ATTRIBUTE_RE);
		// System.out.println(ATTRIBUTE_RE);
		final Matcher match = restOfCssElementAtributePattern.matcher(elementWithAttributesString);

		while (match.find()) {
			final String pseudoClass = match.group(rePseudoClass);
			if (pseudoClass != null) {
				CssPseudoClassType pseudoClassType;
				try {
					pseudoClassType = CssPseudoClassType.pseudoClassTypeString(pseudoClass, element);
				} catch (final IllegalArgumentException e) {
					throw new CssSelectorToXPathConverterUnsupportedPseudoClassException(pseudoClass);
				}
				final String pseudoValue = (pseudoClassType == CssPseudoClassType.N_CHILD
						? CssElementAttributeParser.checkPseudoChild(pseudoClass)
						: null);

				attributeList.add(new CssAttributePseudoClass(pseudoClassType, element, pseudoValue));
			} else {
				final boolean attributeValueHasQuotes = match.group(reIndexAttributeValueWithQuotes) != null;
				attributeList.add(new CssAttribute(match.group(reIndexAttributeName),
						match.group(attributeValueHasQuotes ? reIndexAttributeValueWithinQuotes
								: reIndexAttributeValueWithoutQuotes),
						match.group(reIndexAttributeType)));
			}
		}
		if (nchildAttribute != null) {
			attributeList.add(nchildAttribute);
		}
		attributeList = cleanUpAttributes(attributeList);
		// System.out.println(cssElementAttribute);
		return new CssElementAttributes(element, attributeList);
	}

	public List<CssAttribute> cleanUpAttributes(List<CssAttribute> attributeList) {
		// Sets will guarantee no duplicate attributes and hashlinkset preserves order
		final LinkedHashSet<CssAttribute> attributeSet = new LinkedHashSet<>(attributeList);
		cleanUpChildOfType(attributeSet, CssPseudoClassType.FIRST_CHILD, CssPseudoClassType.ONLY_CHILD);
		cleanUpChildOfType(attributeSet, CssPseudoClassType.FIRST_OF_TYPE, CssPseudoClassType.FIRST_CHILD);
		cleanUpChildOfType(attributeSet, CssPseudoClassType.FIRST_OF_TYPE, CssPseudoClassType.ONLY_CHILD);

		cleanUpChildOfType(attributeSet, CssPseudoClassType.LAST_CHILD, CssPseudoClassType.ONLY_CHILD);
		cleanUpChildOfType(attributeSet, CssPseudoClassType.LAST_OF_TYPE, CssPseudoClassType.LAST_CHILD);
		cleanUpChildOfType(attributeSet, CssPseudoClassType.LAST_OF_TYPE, CssPseudoClassType.ONLY_CHILD);

		cleanUpChildOfType(attributeSet, CssPseudoClassType.FIRST_OF_TYPE, CssPseudoClassType.ONLY_OF_TYPE);
		cleanUpChildOfType(attributeSet, CssPseudoClassType.LAST_OF_TYPE, CssPseudoClassType.ONLY_OF_TYPE);

		cleanUpChildOfType(attributeSet, CssPseudoClassType.ONLY_OF_TYPE, CssPseudoClassType.ONLY_CHILD);
		cleanUpChildOfType(attributeSet, CssPseudoClassType.N_CHILD, CssPseudoClassType.N_CHILD);

		return new ArrayList<>(attributeSet);
	}

	private void cleanUpChildOfType(LinkedHashSet<CssAttribute> attributeSet, CssPseudoClassType candidateToRemove,
			CssPseudoClassType reasonToRemove) {
		CssAttributePseudoClass foundCandidateToRemove = null;
		CssAttributePseudoClass foundReasonToRemove = null;
		for (final CssAttribute attribute : attributeSet) {
			if (attribute instanceof CssAttributePseudoClass) {
				final CssAttributePseudoClass cssAttributePseudoClass = (CssAttributePseudoClass) attribute;
				if (cssAttributePseudoClass.getCsspseudoClassType().equals(candidateToRemove)) {
					foundCandidateToRemove = cssAttributePseudoClass;
				} else if (cssAttributePseudoClass.getCsspseudoClassType().equals(reasonToRemove)) {
					foundReasonToRemove = cssAttributePseudoClass;
				}

				if (foundCandidateToRemove != null && foundReasonToRemove != null) {
					break;
				}
			}
		}

		if (foundCandidateToRemove != null && foundReasonToRemove != null) {
			attributeSet.remove(foundCandidateToRemove);
		}
	}

	// region nth-child
	// manage String format syntax (%1$s, 1)
	final private static String PATTERN_PSEUDOCLASS_NTHCHILD = ":nth-child\\(([0-9%$s]*)\\)";
	final private static String PATTERN_PSEUDOCLASS_FIRSTCHILD = ":first-child";
	final private static String PATTERN_PSEUDOCLASS_LASTCHILD = ":last-child";

	/**
	 * Check if a pseudo xx-child is present
	 *
	 * @param selectorString selector
	 * @return null if not present, nth-child value otherwise
	 */
	private static String checkPseudoChild(String selectorString) {

		Pattern cssElementAtributePattern = Pattern.compile(PATTERN_PSEUDOCLASS_NTHCHILD);
		Matcher match = cssElementAtributePattern.matcher(selectorString);
		if (match.find()) {
			return match.group(1);
		}
		cssElementAtributePattern = Pattern.compile(PATTERN_PSEUDOCLASS_FIRSTCHILD);
		match = cssElementAtributePattern.matcher(selectorString);
		if (match.find()) {
			return "1";
		}
		cssElementAtributePattern = Pattern.compile(PATTERN_PSEUDOCLASS_LASTCHILD);
		match = cssElementAtributePattern.matcher(selectorString);
		if (match.find()) {
			return "last()";
		}
		return null;
	}

	private static String removeNthChild(String selectorString) {

		return selectorString.replaceAll(PATTERN_PSEUDOCLASS_FIRSTCHILD, "")
				.replaceAll(PATTERN_PSEUDOCLASS_NTHCHILD, "").replaceAll(PATTERN_PSEUDOCLASS_LASTCHILD, "");
	}

	// endregion
}
