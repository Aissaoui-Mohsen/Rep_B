package com.neolians.common.utils.selenium.elements;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.neolians.common.utils.DoAssert;
import com.neolians.common.utils.Util;
import com.neolians.common.utils.report.Report;
import com.neolians.common.utils.selenium.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
//import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.neolians.common.utils.selenium.Driver;
import java.time.Duration;
public abstract class SeleniumObjectsBase implements WebElement {

	private String parameterName = null;
	private String parameter2Name = null;
	private String parameter3Name = null;
	private String parameter4Name = null;

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
		webElement = null;
	}

	public void setParameterName(int parameterName) {
		setParameterName("" + parameterName);
	}

	public void setParameter2Name(String parameter2Name) {
		this.parameter2Name = (parameter2Name == null ? "" : parameter2Name);
		webElement = null;
	}

	public void setParameter2Name(int parameter2Name) {
		setParameter2Name("" + parameter2Name);
	}

	public void setParameter3Name(String parameter3Name) {
		this.parameter3Name = parameter3Name;
		webElement = null;
	}

	public void setParameter3Name(int parameter3Name) {
		this.parameter3Name = "" + parameter3Name;
		webElement = null;
	}

	public void setParameter4Name(String parameter4Name) {
		this.parameter4Name = parameter4Name;
		webElement = null;
	}

	public void setParameter4Name(int parameter4Name) {
		this.parameter4Name = "" + parameter4Name;
		webElement = null;
	}
// region WebElement

	/**
	 * Web element linked to the current element
	 */
	protected SeleniumObjectsBase parentElement = null;
	protected WebElement webElement = null;

	protected WebElement getWebElement() {
		return getWebElement(true);
	}

	/**
	 * Web element linked to the current element
	 *
	 * @param displayError Display an error if element not present
	 * @return the WebElement, null if displayError=false, an exception if
	 *         displayError=true
	 */
	private WebElement getWebElement(boolean displayError) {
		if (webElement != null) {
			return webElement;
		}
		WebElement mBaseElement = null;
		By tmpBaseDefinition = mBaseDefinition;
		if (parameterName != null && mBaseDefinition instanceof By.ByCssSelector) {
			final String cssSelector = mBaseDefinition.toString().substring("By.cssSelector: ".length());
			if (parameter4Name != null) {
				tmpBaseDefinition = By.cssSelector(
						String.format(cssSelector, parameterName, parameter2Name, parameter3Name, parameter4Name));
			} else if (parameter3Name != null) {
				tmpBaseDefinition = By
						.cssSelector(String.format(cssSelector, parameterName, parameter2Name, parameter3Name));
			} else if (parameter2Name != null) {
				tmpBaseDefinition = By.cssSelector(String.format(cssSelector, parameterName, parameter2Name));
			} else {
				tmpBaseDefinition = By.cssSelector(String.format(cssSelector, parameterName));
			}
		} else if (xpathExpression != null) {
			if (parameter4Name != null) {
				tmpBaseDefinition = By.xpath(
						String.format(xpathExpression, parameterName, parameter2Name, parameter3Name, parameter4Name));
			} else if (parameter3Name != null) {
				tmpBaseDefinition = By
						.xpath(String.format(xpathExpression, parameterName, parameter2Name, parameter3Name));
			} else if (parameter2Name != null) {
				tmpBaseDefinition = By.xpath(String.format(xpathExpression, parameterName, parameter2Name));
			} else if (parameterName != null) {
				tmpBaseDefinition = By.xpath(String.format(xpathExpression, parameterName));
			} else {
				tmpBaseDefinition = By.xpath(xpathExpression);
			}
		}
		if (tmpBaseDefinition != null) {
			try {
				if (parentElement != null) {
					final WebElement parentWebElement = parentElement.getWebElement(displayError);
					if (parentWebElement != null) {
						mBaseElement = parentWebElement.findElement(tmpBaseDefinition);
					}
				} else {
					mBaseElement = Driver.getDriver().findElement(tmpBaseDefinition);
				}
			} catch (final NoSuchElementException ignored) {
				// mBaseElement = null;
			}
		}
		if (displayError && mBaseElement == null) {
			String message = "Cannot find Element '" + getClass().getSimpleName() + "' "
					+ (getElementName() != null ? ": '" + getElementName() + "' => " : "")
					+ (mBaseDefinition != null ? "with definition :" + mBaseDefinition : "with xpath : " + getXpath());

			if (parentElement != null) {
				message = message + " [Parent: " + parentElement.getElementName() + " => "
						+ (parentElement.mBaseDefinition != null ? "with definition :" + parentElement.mBaseDefinition
								: "with xpath : " + parentElement.getXpath())
						+ "]";
			}

			final StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
			Report.error(message, stackTraces);
			DoAssert.fail(message);
		}
		return mBaseElement;
	}

	/**
	 * Get current element locator
	 *
	 * @return current element locator
	 */
	private By getLocator() {
		if (mBaseDefinition != null && parentElement == null) {
			return mBaseDefinition;
		} else if (mBaseDefinition instanceof By.ByCssSelector) {
			return mBaseDefinition;
		} else {
			return By.xpath(getXpath());
		}
	}

	protected String getBaseDefinition() {
		if (xpathExpression != null) {
			if (parameterName == null) {
				return xpathExpression;
			} else if (parameter2Name != null) {
				return String.format(xpathExpression, parameterName, parameter2Name);
			} else {
				return String.format(xpathExpression, parameterName);
			}

		} else if (mBaseDefinition != null) {

			return mBaseDefinition.toString();
		}
		return "";
	}

	/**
	 * Definition of the element
	 */
	private By mBaseDefinition;

	/**
	 * Xpath expression in order to use parameters and the method setParameter
	 */
	private String xpathExpression;

	/**
	 * @return display the element xpath
	 */
	public String getXpath() {
		String xpath = "";
		if (parentElement != null) {
			xpath = parentElement.getXpath();
		}

		if (xpathExpression != null) {
			String tmpXpath = xpathExpression;
			if (tmpXpath.startsWith("./")) {
				tmpXpath = tmpXpath.substring(1);
			} else if (!tmpXpath.startsWith("(") && !tmpXpath.startsWith("//") && !tmpXpath.startsWith("/")) {
				tmpXpath = "//" + tmpXpath;
			}
			xpath = xpath + tmpXpath;
		} else if (mBaseDefinition instanceof By.ByXPath) {
			if (mBaseDefinition.toString().startsWith("xpath: ")) {
				String tmpXpath = mBaseDefinition.toString().substring("xpath: ".length());
				if (tmpXpath.startsWith(".")) {
					tmpXpath = tmpXpath.substring(1);
				}
				xpath = xpath + tmpXpath;
			} else if (mBaseDefinition.toString().startsWith("By.xpath: ")) {
				String tmpXpath = mBaseDefinition.toString().substring("By.xpath: ".length());
				if (tmpXpath.startsWith(".")) {
					tmpXpath = tmpXpath.substring(1);
				}
				xpath = xpath + tmpXpath;
			}
		} else if (mBaseDefinition instanceof By.ById) {
			xpath = xpath + "//*[@id='" + mBaseDefinition.toString().substring("By.id: ".length()) + "']";
		} else if (mBaseDefinition instanceof By.ByName) {
			xpath = xpath + "//*[@name='" + mBaseDefinition.toString().substring("By.name: ".length()) + "']";
		} else if (mBaseDefinition instanceof By.ByTagName) {
			xpath = xpath + "//" + mBaseDefinition.toString().substring("By.tagName: ".length());
		} else if (mBaseDefinition instanceof By.ByCssSelector) {
			xpath = xpath
					+ Util.convertCssSelectorToXpath(mBaseDefinition.toString().substring("By.cssSelector: ".length()));
		}
		if (parameter4Name != null && parameter3Name != null && parameter2Name != null) {
			xpath = String.format(xpath, parameterName, parameter2Name, parameter3Name, parameter4Name);
		} else if (parameter3Name != null && parameter2Name != null) {
			xpath = String.format(xpath, parameterName, parameter2Name, parameter3Name);
		} else if (parameterName != null && parameter2Name != null) {
			xpath = String.format(xpath, parameterName, parameter2Name);
		} else if (parameterName != null) {
			xpath = String.format(xpath, parameterName);
		}
		return xpath;
	}

	/**
	 * element name
	 */
	private String elementName;

	public String getElementName() {
		String fullElName = elementName;
		if (elementName != null) {
			if (parentElement != null && parentElement.elementName != null) {
				fullElName = parentElement.elementName + "->" + elementName;
			}

			if (parameterName != null && parameter2Name != null && parameter3Name != null && parameter4Name != null) {
				return String.format(fullElName, this.parameterName, this.parameter2Name, this.parameter3Name,
						this.parameter4Name);
			} else if (parameterName != null && parameter2Name != null && parameter3Name != null) {
				return String.format(fullElName, this.parameterName, this.parameter2Name, this.parameter3Name);
			} else if (parameterName != null && parameter2Name != null) {
				return String.format(fullElName, this.parameterName, this.parameter2Name);
			} else if (parameterName != null) {
				return String.format(fullElName, this.parameterName);
			}
		}
		return fullElName;
	}

	public void clearElementName() {
		elementName = null;
	}

	/**
	 * Display an element name
	 *
	 * @param actionName Msg to display
	 */
	protected void displayElementName(String actionName) {
		displayElementName(actionName, true);
	}

	/**
	 * Display an element name
	 *
	 * @param actionName  Msg to display
	 * @param displayInfo true if we display elementName info
	 */
	private void displayElementName(String actionName, boolean displayInfo) {
		if (displayInfo && getElementName() != null) {
			String msg;
			if (parentElement != null && parentElement.getElementName() != null) {
				msg = String.format(actionName, parentElement.getElementName() + "->" + getElementName());
			} else {
				msg = String.format(actionName, getElementName());
			}
			Report.info(msg);
		}
	}

	String escapeElementNameCharacter(String text) {
		if (text != null) {
			return text.replace("%", "%%");
		} else {
			return "";
		}
	}
	// endregion
	// region Constructor

	/**
	 * Create object only by its definition
	 *
	 * @param elementDefinition Definition to find the IWebElement element should be
	 *                          enabled
	 */
	protected SeleniumObjectsBase(By elementDefinition) {
		this.mBaseDefinition = (elementDefinition);
	}

	/**
	 * Create object only by its definition
	 *
	 * @param elementDefinition Definition to find the IWebElement element should be
	 *                          enabled
	 */
	protected SeleniumObjectsBase(String elementName, By elementDefinition) {
		this.elementName = (elementName);
		this.mBaseDefinition = (elementDefinition);
	}

	/**
	 * Create object only by its definition
	 *
	 * @param xpathExpression Xpath of the element, should be use with $1$s syntax
	 *                        to add a parameter
	 */
	protected SeleniumObjectsBase(String xpathExpression) {
		this.xpathExpression = xpathExpression;
	}

	/**
	 * Create object only by its webElement
	 *
	 * @param webElement WebElement
	 */
	protected SeleniumObjectsBase(WebElement webElement) {
		this.webElement = webElement;
	}

	/**
	 * @param elementName     element name
	 * @param xpathExpression element xpath
	 */
	protected SeleniumObjectsBase(String elementName, String xpathExpression) {
		this.xpathExpression = xpathExpression;
		this.elementName = elementName;
	}

	/**
	 * Create Element as child of another webElement
	 *
	 * @param xpathExpression Xpath of the element, should be use with $1$s syntax
	 *                        to add a parameter
	 * @param parentElement   Parent element (ex data grid element)
	 */
	protected SeleniumObjectsBase(SeleniumObjectsBase parentElement, String xpathExpression) {
		this.xpathExpression = xpathExpression;
		this.parentElement = parentElement;
	}

	public SeleniumObjectsBase(String elementName, SeleniumObjectsBase parentElement, String xpathExpression) {
		this.elementName = elementName;
		this.parentElement = parentElement;
		this.xpathExpression = xpathExpression;
	}

	public SeleniumObjectsBase(String elementName, SeleniumObjectsBase parentElement, By elementDefinition) {
		this.elementName = elementName;
		this.parentElement = parentElement;
		this.mBaseDefinition = elementDefinition;
	}

	/**
	 * Create Element as child of another webElement
	 *
	 * @param elementDefinition Definition to find the IWebElement
	 * @param parentElement     Parent element (ex data grid element)
	 */
	protected SeleniumObjectsBase(SeleniumObjectsBase parentElement, By elementDefinition) {
		mBaseDefinition = elementDefinition;
		this.parentElement = parentElement;
	}

//endregion

	// region Wait Functions

	/**
	 * Waits until the given element is present
	 *
	 * @return true if element present, false otherwise
	 */
	public boolean waitForElementPresent() {
		return waitForElementPresent(Driver.TIMEOUT_TIME);
	}

	/**
	 * Waits until the given element is present
	 *
	 * @param timeoutMilliSeconds millisecond to wait until a timeout is thrown
	 * @return true if element present, false otherwise
	 */
	public boolean waitForElementPresent(int timeoutMilliSeconds) {
		String parentXpathExpression = null;
		if (parentElement != null) {
			if (parameterName != null && parentElement.parameterName == null) {
				parentElement.setParameterName(parameterName);
			}
			if (parameter2Name != null && parentElement.parameter2Name == null) {
				parentElement.setParameter2Name(parameter2Name);
			}
			if (parameter3Name != null && parentElement.parameter3Name == null) {
				parentElement.setParameter3Name(parameter3Name);
			}
			if (parameter4Name != null && parentElement.parameter4Name == null) {
				parentElement.setParameter4Name(parameter4Name);
			}
			final boolean b = parentElement.waitForElementPresent(timeoutMilliSeconds);
			if (!b) {
				return false;
			}
			parentXpathExpression = parentElement.getXpath();

		}
		if (timeoutMilliSeconds < 1000) {
			timeoutMilliSeconds = 1000;
		}
	//	final //WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeoutMilliSeconds / 1000, 300);
		final WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofMillis(1000));
		final String localXpathExpression = xpathExpression;
		By tmpBaseDefinition = mBaseDefinition;
		if (parameterName != null && mBaseDefinition instanceof By.ByCssSelector) {
			final String cssSelector = mBaseDefinition.toString().substring("By.cssSelector: ".length());
			if (parameter4Name != null) {
				tmpBaseDefinition = By.cssSelector(
						String.format(cssSelector, parameterName, parameter2Name, parameter3Name, parameter4Name));
			} else if (parameter3Name != null) {
				tmpBaseDefinition = By
						.cssSelector(String.format(cssSelector, parameterName, parameter2Name, parameter3Name));
			} else if (parameter2Name != null) {
				tmpBaseDefinition = By.cssSelector(String.format(cssSelector, parameterName, parameter2Name));
			} else {
				tmpBaseDefinition = By.cssSelector(String.format(cssSelector, parameterName));
			}
		} else if (localXpathExpression != null) {
			String compiledXpathExpression = localXpathExpression;
			if (parentXpathExpression != null) {
				if (localXpathExpression.startsWith("../")) {
					compiledXpathExpression = parentXpathExpression + "/" + localXpathExpression;
				} else if (localXpathExpression.startsWith("./")) {
					compiledXpathExpression = parentXpathExpression + localXpathExpression.substring(1);
				} else if (localXpathExpression.startsWith("//") || localXpathExpression.startsWith("/")) {
					compiledXpathExpression = parentXpathExpression + localXpathExpression;
				} else {
					compiledXpathExpression = parentXpathExpression + "//" + localXpathExpression;
				}
			}
			if (parameter4Name != null) {
				tmpBaseDefinition = By.xpath(String.format(compiledXpathExpression, parameterName, parameter2Name,
						parameter3Name, parameter4Name));
			} else if (parameter3Name != null) {
				tmpBaseDefinition = By
						.xpath(String.format(compiledXpathExpression, parameterName, parameter2Name, parameter3Name));
			} else if (parameter2Name != null) {
				tmpBaseDefinition = By.xpath(String.format(compiledXpathExpression, parameterName, parameter2Name));
			} else if (parameterName != null) {
				tmpBaseDefinition = By.xpath(String.format(compiledXpathExpression, parameterName));
			} else {
				tmpBaseDefinition = By.xpath(compiledXpathExpression);
			}
		}

		try {
			Driver.getDriver().manage().timeouts().implicitlyWait(timeoutMilliSeconds, TimeUnit.MILLISECONDS);
			wait.until(ExpectedConditions.presenceOfElementLocated(tmpBaseDefinition));
			Driver.getDriver().manage().timeouts().implicitlyWait(Driver.TIMEOUT_TIME, TimeUnit.MILLISECONDS);
			return true;
		} catch (final Exception ex) {
			Driver.getDriver().manage().timeouts().implicitlyWait(Driver.TIMEOUT_TIME, TimeUnit.MILLISECONDS);
			return false;
		}
	}

	/**
	 * Waits until the given element is is not present
	 *
	 * @return true when the element is not present, false if still present
	 */
	public boolean waitForElementNotPresent() {
		return waitForElementNotPresent(Driver.TIMEOUT_TIME);
	}

	/**
	 * Waits until the given element is is not present
	 *
	 * @param timeoutMilliSeconds millisecond to wait until a timeout is thrown
	 * @return true when the element is not present, false if still present
	 */
	public boolean waitForElementNotPresent(int timeoutMilliSeconds) {
		Driver.getDriver().manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		final long endTime = System.currentTimeMillis() + timeoutMilliSeconds;
		try {
			new WebDriverWait(Driver.getDriver(),Duration.ofMillis(1000))
					.until(ExpectedConditions.invisibilityOfElementLocated(getLocator()));
		} catch (final TimeoutException ignored) {
		}
		WebElement current;
		try {
			current = getWebElement(false);
			while (System.currentTimeMillis() < endTime && current != null && current.isDisplayed()) {
				Driver.sleep(500);
			}
		} catch (StaleElementReferenceException | NoSuchElementException e) {
			Driver.getDriver().manage().timeouts().implicitlyWait(Driver.TIMEOUT_TIME, TimeUnit.MILLISECONDS);
			return true;
		}
		Driver.getDriver().manage().timeouts().implicitlyWait(Driver.TIMEOUT_TIME, TimeUnit.MILLISECONDS);
		return current == null;
	}

	/**
	 * Waits until the given element is visible
	 *
	 * @return true if element visible, false otherwise
	 */
	public boolean waitForElementVisible() {
		return waitForElementVisible(Driver.TIMEOUT_TIME);
	}

	/**
	 * Waits until the given element is visible
	 *
	 * @param timeoutMilliSeconds millisecond to wait until a timeout is thrown
	 * @return true if element visible, false otherwise
	 */
	public boolean waitForElementVisible(int timeoutMilliSeconds) {
		if (!waitForElementPresent(timeoutMilliSeconds)) {
			return false;
		}
		if (timeoutMilliSeconds < 1000) {
			timeoutMilliSeconds = 1000;
		}
		final WebDriverWait wait = new WebDriverWait(Driver.getDriver(),Duration.ofMillis(1000));
		final String localXpathExpression = getXpath();
		By tmpBaseDefinition = mBaseDefinition;

		if (localXpathExpression != null) {
			tmpBaseDefinition = By.xpath(localXpathExpression);
		}

		try {
			Driver.getDriver().manage().timeouts().implicitlyWait(timeoutMilliSeconds, TimeUnit.MILLISECONDS);
			wait.until(ExpectedConditions.visibilityOfElementLocated(tmpBaseDefinition));
			Driver.getDriver().manage().timeouts().implicitlyWait(Driver.TIMEOUT_TIME, TimeUnit.MILLISECONDS);
			return true;
		} catch (final Exception ex) {
			Driver.getDriver().manage().timeouts().implicitlyWait(Driver.TIMEOUT_TIME, TimeUnit.MILLISECONDS);
			return false;
		}
	}

	public void waitForElementToBeClickable(int timeoutMilliSeconds) {
		try {
			final WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofMillis(1000));
			wait.until(ExpectedConditions.elementToBeClickable(getWebElement()));
		} catch (final Exception ex) {
			System.out.println("Exception: waitForElementToBeClickable");
		}
	}
	// endregion

	// region WebElement custom functions

	/**
	 * @return the inner text of an element
	 */
	public String getInnerText() {
		final String innerText = getAttribute("innerText");
		return (innerText == null ? "" : innerText.trim());
	}

	/**
	 * Set an attribute for an html element
	 *
	 * @param name  attribute name
	 * @param value attribute new value
	 */
	public void setAttribute(String name, String value) {
		final JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
		js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", getWebElement(), name, value);
	}

	/**
	 * remove an attribute for an html element
	 *
	 * @param attributeToRemove attribute name to remove
	 */
	public void removeAttribute(String attributeToRemove) {
		final JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
		js.executeScript("arguments[0].removeAttribute('" + attributeToRemove + "')", getWebElement());
	}

	/**
	 * Drag and Drop current element over another element
	 *
	 * @param toElement target Element
	 */
	public void dragAndDropOver(SeleniumObjectsBase toElement) {
		displayElementName("Drag Element '%1$s' over on : '" + toElement.getElementName() + "'", true);
		// Using Action class for drag and drop.
		final Actions act = new Actions(Driver.getDriver());
		// Dragged and dropped.
		act.dragAndDrop(getWebElement(), toElement.getWebElement()).build().perform();
	}

	/**
	 * Move to an element
	 */
	public void moveTo() {
		displayElementName("Mouse over on : '%1$s'", true);
		waitForElementVisible();
		final Actions builder = new Actions(Driver.getDriver());
		builder.moveToElement(getWebElement()).perform();
	}

	/**
	 * Scroll to an element by its location
	 *
	 * @param xOffset Pixel horizontal
	 * @param yOffset Pixel vertical
	 */
	public void moveTo(int xOffset, int yOffset) {
		final Actions builder = new Actions(Driver.getDriver());
		if (xOffset == 0) {
			xOffset = getRect().x;
		}
		if (yOffset == 0) {
			yOffset = getRect().y;
		}
		builder.moveToElement(getWebElement(), xOffset, yOffset).click().build().perform();
	}

	/**
	 * Display in the browser an element by moving scrollbar
	 */
	public void setVisible() {

		final JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
		final WebElement e = getWebElement(true);
		final int locationX = e.getLocation().getX();
		final int locationY = e.getLocation().getY() - 100;
		js.executeScript("window.scrollTo(arguments[0],arguments[1]);", locationX, locationY);
	}
	// endregion

	// region Implements Methods of IWebElement

	/**
	 * Find elements by their xpath
	 *
	 * @param by search criteria
	 * @return list of WebElement or null if not found
	 */
	@Override
	public List<WebElement> findElements(By by) {
		try {
			final WebElement el = getWebElement(false);
			if (el == null) {
				return null;
			}
			return el.findElements(by);
		} catch (final StaleElementReferenceException ignored) {
			final WebElement el = getWebElement(false);
			if (el == null) {
				return null;
			}
			return el.findElements(by);
		} catch (final Exception ignored) {
			return null;
		}
	}

	/**
	 * Get the tag name of this element. <b>Not</b> the value of the name attribute:
	 * will return <code>"input"</code> for the element
	 * <code>&lt;input name="foo" /&gt;</code>.
	 *
	 * @return The tag name of this element.
	 */
	@Override
	public String getTagName() {
		try {
			return getWebElement(true).getTagName();
		} catch (final StaleElementReferenceException ignored) {
			return getWebElement().getTagName();
		} catch (final Exception ignored) {
			return null;
		}
	}

	/**
	 * @return The visible text of this element.
	 * @see <a href=
	 *      "https://w3c.github.io/webdriver/#get-element-text">"Get Element Text"
	 *      section in W3C WebDriver Specification</a>
	 */
	@Override
	public String getText() {
		return getText(true);
	}

	/**
	 * @return The visible text of this element or null if not present with
	 *         displayError=true.
	 * @see <a href=
	 *      "https://w3c.github.io/webdriver/#get-element-text">"Get Element Text"
	 *      section in W3C WebDriver Specification</a>
	 */
	public String getText(boolean displayError) {
		try {
			waitForElementPresent(Driver.MEDIUM_TIME);
			final boolean isVisible = waitForElementVisible();
			if (!isVisible && !displayError) {
				return null;
			}
			return getWebElement(displayError).getText();
		} catch (final StaleElementReferenceException ignored) {
			return getWebElement().getText();
		}
	}

	/**
	 * get the dimension of an element (height and length)
	 *
	 * @return The size of the element on the page.
	 */
	@Override
	public Dimension getSize() {
		try {
			return getWebElement(true).getSize();
		} catch (final StaleElementReferenceException ignored) {
			return getWebElement().getSize();
		}
	}

	/**
	 * Check if an element is selected or not
	 *
	 * @return true if element is selected, false otherwise
	 */

	@Override
	public boolean isSelected() {
		try {
			return getWebElement(true).isSelected();
		} catch (final StaleElementReferenceException ignored) {
			return getWebElement().isSelected();
		} catch (final Exception ignored) {
			return false;
		}
	}

	/**
	 * Get the location of an element
	 *
	 * @return the location of an element
	 */
	@Override
	public Point getLocation() {
		try {
			return getWebElement(true).getLocation();
		} catch (final StaleElementReferenceException ignored) {
			return getWebElement().getLocation();
		} catch (final Exception ignored) {
			return null;
		}
	}

	/**
	 * Check if element is enabled or not
	 *
	 * @return if element is enabled or false
	 */

	@Override
	public boolean isEnabled() {
		try {
			return getWebElement(true).isEnabled();
		} catch (final StaleElementReferenceException ignored) {
			return getWebElement().isEnabled();
		} catch (final Exception ignored) {
			return false;
		}
	}

	/**
	 * @return true if element is displayed or false
	 */

	@Override
	public boolean isDisplayed() {
		try {
			if (!waitForElementPresent(100)) {
				return false;
			}
			final WebElement el = getWebElement(false);
			if (el == null) {
				return false;
			}
			if (el.isDisplayed()) {
				return true;
			}
			if (Browser.getBrowser() == Browser.IE) {
				scrollToElement();
			}
			return el.isDisplayed();
		} catch (final StaleElementReferenceException ignored) {
			final WebElement el = getWebElement(false);
			if (el == null) {
				return false;
			}
			return el.isDisplayed();
		} catch (final Exception ignored) {
			return false;
		}
	}

	///
	public void isDisplayed(boolean takeScreenshot) {
		isDisplayed(null, takeScreenshot);
	}

	public void isDisplayed(String message, boolean takeScreenshot) {

		if (message == null) {
			message = "Verify if element '%1$s' is displayed";
		}
		String displayMessage = String.format(message, escapeElementNameCharacter(getElementName()));
		final Boolean _IsDisplayed = isDisplayed();
		if (_IsDisplayed) {
			DoAssert.success(displayMessage, takeScreenshot);
		} else {
			displayMessage += "(identified by: " + getBaseDefinition() + " after " + Driver.TIMEOUT_TIME + "ms)";
			DoAssert.fail(displayMessage);
		}
	}

	/**
	 * Clear a text field
	 */
	@Override
	public void clear() {
		try {
			getWebElement(true).clear();
		} catch (final StaleElementReferenceException ignored) {
			getWebElement().clear();
		} catch (final Exception ignored) {
		}
	}

	/**
	 * use Ctrl-A to select the context of a text element
	 */
	public void sendCtrlA() {
		try {
			getWebElement(true).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		} catch (final StaleElementReferenceException ignored) {
			getWebElement().sendKeys(Keys.chord(Keys.CONTROL, "a"));
		}
	}

	/**
	 * use Enter to select the context of a text element
	 */
	public void sendEnter() {
		try {
			getWebElement(true).sendKeys(Keys.chord(Keys.ENTER));
		} catch (final StaleElementReferenceException ignored) {
			getWebElement().sendKeys(Keys.chord(Keys.ENTER));
		}
	}

	/**
	 * use Ctrl-A to select the context of a text element the clear it
	 */
	public void sendClear() {
		try {
			sendCtrlA();
			getWebElement(true).sendKeys(Keys.chord(Keys.CLEAR, "a"));
		} catch (final StaleElementReferenceException ignored) {
			getWebElement().sendKeys(Keys.chord(Keys.CLEAR, "a"));
		}
	}

	/**
	 * Click action using Js (for IE by default)
	 */
	@Override
	public void click() {
		click(true);
	}

	/**
	 * Click action using Js or not action
	 *
	 * @param displayInfo true if we display elementName info
	 */
	public void click(boolean displayInfo) {
		try {
			displayElementName("Click on : '%1$s'", displayInfo);

			waitForElementPresent(Driver.TIMEOUT_TIME);
			waitForElementVisible();
			getWebElement(true).click();

			Driver.waitForReady(1);
		} catch (final StaleElementReferenceException ignored) {
			Driver.sleep(Driver.MEDIUM_TIME);
			waitForElementPresent(Driver.TIMEOUT_TIME);
			waitForElementVisible();
			getWebElement(true).click();
		} catch (final ElementNotInteractableException e) {
			final String message = "Element '" + getClass().getSimpleName() + "'[" + getElementName()
					+ "] with definition '" + mBaseDefinition + "' is not visible";
			System.out.println("Exception: " + message);
		//	throw new ElementNotVisibleException(message, e);
		//} catch (final ElementNotInteractableException | JavascriptException ex) {
		//	final JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
			//executor.executeScript("arguments[0].click()", getWebElement());
		} catch (final NoSuchElementException e) {
			final String message = "Element '" + getClass().getSimpleName() + "'[" + getElementName()
					+ "] with definition '" + getBaseDefinition() + "' and xpath: '" + getXpath() + "' is not present";
			System.out.println("Exception: " + message);
			throw new NoSuchElementException(message, e);
		}
	}

	@Override
	public void submit() {
		try {
			getWebElement(true).submit();
		} catch (final StaleElementReferenceException ignored) {
			getWebElement().submit();
		} catch (final Exception ignored) {
		}
	}

	/**
	 * set a text field
	 *
	 * @param keysToSend text to send
	 */
	@Override
	public void sendKeys(CharSequence... keysToSend) {
		sendKeys(true, keysToSend);
	}

	/**
	 * set a text field
	 *
	 * @param keysToSend text to send
	 */
	void sendKeys(boolean displayInfo, CharSequence... keysToSend) {
		try {
			waitForElementVisible(Driver.SHORT_TIME);
			final StringBuilder text = new StringBuilder();
			for (final CharSequence cs : keysToSend) {
				text.append(cs.toString());
			}
			displayElementName("SetText on element '%1$s': " + escapeElementNameCharacter(text.toString()),
					displayInfo);
			getWebElement(true).sendKeys(keysToSend);
		} catch (final StaleElementReferenceException ignored) {
			getWebElement().sendKeys(keysToSend);
		} catch (final Exception ignored) {
		}
	}

	/**
	 * Get the attribute of an element tag
	 *
	 * @param name attribute name
	 * @return attribute value
	 */
	@Override
	public String getAttribute(String name) {
		try {
			return getWebElement(true).getAttribute(name);
		} catch (final StaleElementReferenceException ignored) {
			return getWebElement().getAttribute(name);
		} catch (final Exception ignored) {
			return null;
		}
	}

	/**
	 * get a child element
	 */
	@Override
	public WebElement findElement(By by) {
		try {
			return getWebElement(true).findElement(by);
		} catch (final StaleElementReferenceException ignored) {
			return getWebElement().findElement(by);
		} catch (final Exception ignored) {
			return null;
		}
	}

	/**
	 * Get the Css value of an element
	 *
	 * @param propertyName the css property name of the element
	 * @return The current, computed value of the property.
	 */
	@Override
	public String getCssValue(String propertyName) {
		try {
			return getWebElement(true).getCssValue(propertyName);
		} catch (final StaleElementReferenceException ignored) {
			return getWebElement().getCssValue(propertyName);
		} catch (final Exception ignored) {
			return null;
		}
	}

	@Override
	public Rectangle getRect() {
		try {
			return getWebElement(true).getRect();
		} catch (final StaleElementReferenceException ignored) {
			return getWebElement().getRect();
		} catch (final Exception ignored) {
			return null;
		}
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
		try {
			return getWebElement(true).getScreenshotAs(outputType);
		} catch (final StaleElementReferenceException ex) {
			return getWebElement().getScreenshotAs(outputType);
		} catch (final Exception ignored) {
			return null;
		}
	}

	/**
	 * Inside a data grid, scroll to the given element
	 *
	 * @return true if element displayed, false otherwise
	 */
	public boolean scrollToElement() {
		final JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
		int i = 0;
		while (!getWebElement().isDisplayed() && i < 30) {
			executor.executeScript("arguments[0].scrollIntoView({block: \"center\", inline: \"center\"})",
					getWebElement());
			executor.executeScript("arguments[0].scrollIntoView(true);", getWebElement());
			Driver.sleep(500);
			i++;
		}
		executor.executeScript("arguments[0].scrollIntoView()", getWebElement());
		executor.executeScript("arguments[0].scrollIntoView(false)", getWebElement());
		return getWebElement().isDisplayed();
	}

	public boolean waitForElementPresentByJS(int waitTimeInMs) {

		Driver.sleep(waitTimeInMs);
		try {
			final JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
			executor.executeScript("arguments[0].scrollIntoView()", getWebElement());
			executor.executeScript("arguments[0].scrollIntoView(false)", getWebElement());
			/*
			 * executor.executeScript("arguments[0].scrollIntoView({block: \"end\"})",
			 * getWebElement()); executor.
			 * executeScript("arguments[0].scrollIntoView({behavior: \"smooth\", block: \"end\", inline: \"nearest\"})"
			 * , getWebElement());
			 */
			Driver.waitForReady(1);
			return true;
		} catch (final Exception ignored) {
			return false;
		}

	}

	public String getTextByJS() {
		return ((JavascriptExecutor) Driver.getDriver())
				.executeScript("return arguments[0].innerHTML;", this.getWebElement()).toString();
	}

	// endregion

	@Override
	public String toString() {
		return "SeleniumObjectsBase [" + (getElementName() != null ? getElementName() + ", " : "")
				+ (parentElement != null ? "ParentElement:" + parentElement.toString() + " ," : "") + "Def="
				+ getBaseDefinition() + "]";
	}

	// region Assert
//region Assert Present

	/**
	 * Assert element is present and displayed
	 */
	public void assertPresent() {
		assertPresent(false);
	}

	/**
	 * Assert element is present and displayed
	 *
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public void assertPresent(boolean takeScreenshot) {
		assertPresent(null, takeScreenshot);
	}

	/**
	 * Assert element is present and displayed
	 *
	 * @param message Message to display
	 */
	public void assertPresent(String message) {
		assertPresent(message, false);
	}

	/**
	 * Assert element is present and displayed
	 *
	 * @param message         Message to display
	 * @param maxWaitTimeInMs max wait time in milliseconds
	 */
	public void assertPresent(String message, int maxWaitTimeInMs) {
		assertPresent(message, maxWaitTimeInMs, false, null);
	}

	/**
	 * Assert element is present and displayed
	 *
	 * @param message        Message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public void assertPresent(String message, boolean takeScreenshot) {
		assertPresent(message, takeScreenshot, null);
	}

	/**
	 * Assert element is present and displayed
	 *
	 * @param message Message to display
	 * @param bugId   bug id or JiraId linked to the verification
	 */
	public void assertPresent(String message, String bugId) {
		assertPresent(message, false, bugId);
	}

	/**
	 * Assert element is present and displayed
	 *
	 * @param message        Message to display
	 * @param takeScreenshot Take screenshot in case of error
	 * @param bugId          bug id or JiraId linked to the verification
	 */
	public void assertPresent(String message, boolean takeScreenshot, String bugId) {
		assertPresent(message, Driver.TIMEOUT_TIME, takeScreenshot, bugId);
	}

	/**
	 * Verify if an element is present
	 *
	 * @param message         Message to display
	 * @param takeScreenshot  Take screenshot in case of error
	 * @param maxWaitTimeInMs max wait time in milliseconds
	 * @param bugId           bug id or JiraId linked to the verification
	 */
	private void assertPresent(String message, int maxWaitTimeInMs, boolean takeScreenshot, String bugId) {
		if (message == null) {
			message = "Verify if element '%1$s' is present";
		}
		String displayMessage = String.format(message, escapeElementNameCharacter(getElementName()));
		final boolean isPresent = waitForElementPresent(maxWaitTimeInMs);
		if (isPresent) {
			DoAssert.success(displayMessage, takeScreenshot, bugId);
		} else {
			displayMessage += "(identified by: " + getBaseDefinition() + " after " + maxWaitTimeInMs + "ms)";
			DoAssert.fail(displayMessage, bugId);
		}
	}

	/**
	 * In case of Verification action, Verify if an element is present
	 * <p>
	 * Append information in completeMessage string
	 *
	 * @param completeMessage the complete message to display for Verify
	 * @return true if check Ok, false if verify fails
	 */
	public boolean verifyPresent(StringBuilder completeMessage) {
		return verifyPresent(completeMessage, Driver.TIMEOUT_TIME, null);
	}

	/**
	 * In case of Verification action, Verify if an element is present
	 * <p>
	 * Append information in completeMessage string
	 *
	 * @param completeMessage the complete message to display for Verify
	 * @param maxWaitTimeInMs max wait time in milliseconds
	 * @param bugId           bug id or JiraId linked to the verification
	 * @return true if check Ok, false if verify fails
	 */
	public boolean verifyPresent(StringBuilder completeMessage, int maxWaitTimeInMs, String bugId) {
		final String message = "Verify if element '%1$s' is present";
		String displayMessage = String.format(message, escapeElementNameCharacter(getElementName()));
		final boolean isPresent = waitForElementPresent(maxWaitTimeInMs);
		if (isPresent) {
			return DoAssert.verifySuccess(completeMessage, displayMessage, bugId);
		} else {
			displayMessage += "(identified by: " + getBaseDefinition() + " after " + maxWaitTimeInMs + "ms)";
			return DoAssert.verifyFail(completeMessage, displayMessage, bugId);
		}
	}
// endregion

	// region Assert Not Present

	/**
	 * Verify if an element is not present
	 */
	public void assertNotPresent() {
		final String actionName = "Verify if element %1$s is not present ";
		assertNotPresent(String.format(actionName, getElementName()));
	}

	/**
	 * Verify if an element is not present
	 *
	 * @param message Message to display
	 */
	public void assertNotPresent(String message) {

		assertNotPresent(message, true);
	}

	/**
	 * Verify if an element is present
	 *
	 * @param message        Message to display
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public void assertNotPresent(String message, boolean takeScreenshot) {
		DoAssert.assertTrue(waitForElementNotPresent(), message, takeScreenshot);
	}
//endregion

	// region Assert Equals

	/**
     * assert if the inner text or value of the web element
     *
     * @param expected     Expected inner text or value or null if empty
     * @param contenuTitle
     */
	public void assertEquals(String expected, String contenuTitle) {
		assertEquals(expected, true);
	}

	/**
	 * assert if the inner text or value of the web element
	 *
	 * @param expected       Expected inner text or value or null if empty
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public void assertEquals(String expected, boolean takeScreenshot) {
		assertEquals(expected, takeScreenshot, Driver.TIMEOUT_TIME);

	}

	/**
	 * assert if the inner text or value of the web element
	 *
	 * @param expected       Expected inner text or value or null if empty
	 * @param takeScreenshot Take screenshot in case of error
	 * @param ignoreCase     true if ignoreCase in comparison
	 */
	public void assertEquals(String expected, boolean takeScreenshot, boolean ignoreCase) {
		assertEquals(expected, takeScreenshot, Driver.TIMEOUT_TIME, null, ignoreCase);

	}

	/**
	 * assert if the inner text or value of the web element
	 *
	 * @param expected       expected text or null if empty
	 * @param takeScreenshot Take screenshot in case of error
	 * @param bugId          bug id or JiraId linked to the verification
	 */
	public void assertEquals(String expected, boolean takeScreenshot, String bugId) {
		assertEquals(expected, takeScreenshot, Driver.VERY_SHORT_TIME, bugId);

	}

	/**
	 * assert if the inner text or value of the web element
	 *
	 * @param expected        expected text or null if empty
	 * @param takeScreenshot  Take screenshot in case of error
	 * @param maxWaitTimeInMs max wait time
	 */
	public void assertEquals(String expected, boolean takeScreenshot, int maxWaitTimeInMs) {

		assertEquals(expected, takeScreenshot, maxWaitTimeInMs, null);
	}

	/**
	 * assert if the inner text or value of the web element
	 *
	 * @param expected        expected text or null if empty
	 * @param takeScreenshot  Take screenshot in case of error
	 * @param maxWaitTimeInMs max wait time
	 * @param bugId           bug id or JiraId linked to the verification
	 */
	public void assertEquals(String expected, boolean takeScreenshot, int maxWaitTimeInMs, String bugId) {
		assertEquals(expected, takeScreenshot, maxWaitTimeInMs, bugId, false);
	}

	/**
	 * assert if the inner text or value of the web element
	 *
	 * @param expected        expected text or null if empty
	 * @param takeScreenshot  Take screenshot in case of error
	 * @param maxWaitTimeInMs max wait time
	 * @param ignoreCase      true if ignoreCase in comparison
	 * @param bugId           bug id or JiraId linked to the verification
	 */
	public void assertEquals(String expected, boolean takeScreenshot, int maxWaitTimeInMs, String bugId,
			boolean ignoreCase) {
		final String actionName = "Verify if text of element '%1$s' is '%2$s'";
		if (expected == null) {
			expected = "";
		}
		try {
			final long endTime = System.currentTimeMillis() + maxWaitTimeInMs;
			String text = (this instanceof InputTextElement ? ((InputTextElement) this).getValue() : getInnerText());
			while (System.currentTimeMillis() < endTime && (text == null || !text.equals(expected))) {
				waitForElementPresent(maxWaitTimeInMs);
				text = (this instanceof InputTextElement ? ((InputTextElement) this).getValue() : getInnerText());
			}
			DoAssert.assertEquals(expected, text, String.format(actionName, getElementName(), expected), ignoreCase,
					takeScreenshot, bugId);
		} catch (final NoSuchElementException | JavascriptException ignored) {
			final String message = "Element '%1$s' is present";
			DoAssert.fail(String.format(message, getElementName()), bugId);
		}
	}

	// endregion

	// region Verify Equals

	/**
	 * In case of Verification action, check if the inner text or value of the web
	 * element Append information in completeMessage string
	 *
	 * @param completeMessage the complete message to display for Verify
	 * @return true if check Ok, false if verify fails
	 */
	public boolean verifyEquals(StringBuilder completeMessage, String expected) {
		final int maxWaitTimeInMs = Driver.TIMEOUT_TIME;
		final String actionName = "Verify if text of element '%1$s' is '%2$s'";
		if (expected == null) {
			expected = "";
		}
		try {
			final long endTime = System.currentTimeMillis() + maxWaitTimeInMs;
			String text = (this instanceof InputTextElement ? ((InputTextElement) this).getValue() : getInnerText());
			while (System.currentTimeMillis() < endTime && (text == null || !text.equals(expected))) {
				waitForElementPresent(maxWaitTimeInMs);
				text = (this instanceof InputTextElement ? ((InputTextElement) this).getValue() : getInnerText());
			}
			return DoAssert.verifyEquals(completeMessage, expected, text,
					String.format(actionName, getElementName(), expected));
		} catch (final NoSuchElementException | JavascriptException ignored) {
			final String message = "Element '%1$s' is present";
			return DoAssert.verifyTrue(completeMessage, false, String.format(message, getElementName()));
		}
	}

	// endregion

	// region Assert Contains Attribute

	/**
	 * Assert an element contains an attribute with a specific value
	 *
	 * @param expected  expected attribute value or null if empty
	 * @param attribute attribute name
	 */
	public void assertContainsAttribute(String expected, String attribute) {
		assertContainsAttribute(expected, attribute, false);
	}

	/**
	 * Assert an element contains an attribute with a specific value
	 *
	 * @param expected       expected attribute value or null if empty
	 * @param attribute      attribute name
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public void assertContainsAttribute(String expected, String attribute, boolean takeScreenshot) {
		final String actionName = "Verify if attribute '%3$s' of element '%1$s' contains '%2$s'";
		if (expected == null) {
			expected = "";
		}
		String actualAttribute = getAttribute(attribute);
		final long endTime = System.currentTimeMillis() + Driver.TIMEOUT_TIME;
		while ((actualAttribute == null || !actualAttribute.contains(expected))
				&& System.currentTimeMillis() < endTime) {
			actualAttribute = getAttribute(attribute);
		}
		DoAssert.assertContains(expected, actualAttribute,
				String.format(actionName, getElementName(), expected, attribute), false, takeScreenshot);

	}
//endregion

	// region Assert Contains

	/**
	 * Assert the webElement contains a specific test in its inner text
	 *
	 * @param expected text to check
	 */
	public void assertContains(String expected) {
		assertContains(expected, false);
	}

	/**
	 * Assert the webElement contains a specific test in its inner text
	 *
	 * @param expected       expected text or null if empty
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public void assertContains(String expected, boolean takeScreenshot) {
		assertContains(expected, takeScreenshot, Driver.VERY_SHORT_TIME, null);
	}

	public void assertContains(Function<String, String> expectedMapper, boolean takeScreenshot) {
		assertContains(expectedMapper, takeScreenshot, Driver.VERY_SHORT_TIME, null);
	}

	/**
	 * Assert the webElement contains a specific test in its inner text
	 *
	 * @param expected       expected text or null if empty
	 * @param takeScreenshot Take screenshot in case of error
	 * @param bugId          bug id or JiraId linked to the verification
	 */
	public void assertContains(String expected, boolean takeScreenshot, String bugId) {
		assertContains(expected, takeScreenshot, Driver.VERY_SHORT_TIME, bugId);
	}

	/**
	 * Assert the webElement contains a specific test in its inner text
	 *
	 * @param expected       expected text or null if empty
	 * @param takeScreenshot Take screenshot in case of error
	 */
	public void assertContains(String expected, boolean takeScreenshot, int maxWaitTimeInMs) {
		assertContains(expected, takeScreenshot, maxWaitTimeInMs, null);
	}

	/**
	 * Assert the webElement contains a specific test in its inner text
	 *
	 * @param expected       expected text or null if empty
	 * @param takeScreenshot Take screenshot in case of error
	 * @param bugId          bug id or JiraId linked to the verification
	 */
	private void assertContains(String expected, boolean takeScreenshot, int maxWaitTimeInMs, String bugId) {
		String finalExpected;
		if (expected == null) {
			finalExpected = "";
		} else {
			finalExpected = expected;
		}
		assertContains(text -> finalExpected, takeScreenshot, maxWaitTimeInMs, bugId);
	}

	/**
	 * Assert the webElement contains a specific test in its inner text
	 *
	 * @param bugId bug id or JiraId linked to the verification
	 */
	private void assertContains(Function<String, String> expectedMapper, boolean takeScreenshot, int maxWaitTimeInMs,
			String bugId) {
		final String actionName = "Verify if text of element '%1$s' contains '%2$s'";
		try {
			final long endTime = System.currentTimeMillis() + maxWaitTimeInMs;
			String text = getInnerText();
			String expected = expectedMapper.apply(text);
			while (System.currentTimeMillis() < endTime && !text.contains(expected)) {
				Driver.sleep(Driver.VERY_SHORT_TIME);
				text = getInnerText();
				expected = expectedMapper.apply(text);
			}
			DoAssert.assertContains(expected, text, String.format(actionName, getElementName(), expected),
					takeScreenshot, bugId);
		} catch (final NoSuchElementException | JavascriptException ignored) {
			final String message = "Element '%1$s' is present";
			DoAssert.fail(String.format(message, getElementName()), bugId);
		}
	}
//endregion

//endregion

	/**
	 * get all element wih the given definition
	 *
	 * @return list of WebElements
	 */
	public List<WebElement> findAll() {
		By currentBy;
		if (mBaseDefinition != null) {
			currentBy = mBaseDefinition;
		} else {
			currentBy = By.xpath(getXpath());
		}
		if (parentElement == null) {
			return Driver.getDriver().findElements(currentBy);
		} else {
			return parentElement.findElements(currentBy);
		}
	}

}
