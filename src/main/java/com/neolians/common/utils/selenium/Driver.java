package com.neolians.common.utils.selenium;

import com.neolians.common.utils.Util;
import com.neolians.common.utils.params.ConfigHelper;
import com.neolians.common.utils.report.Report;
import com.neolians.common.utils.selenium.elements.UrlElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Driver {

    public static String browserVersion;

    private enum MultiDriver {
        DRIVER1, DRIVER2, DRIVER3, DRIVER4
    }

    private static MultiDriver currentDriver = MultiDriver.DRIVER1;

    public static void useDriver(final MultiDriver driver) {
        currentDriver = driver;
    }

    /**
     * Indicate which web application we are using
     */

    private static WebDriver pDriver;
    private static WebDriver pDriver2;
    private static WebDriver pDriver3;
    private static WebDriver pDriver4;

    public static WebDriver getDriver() {
        return getDriver(false);
    }

    private static void setDriver(WebDriver pDriver) {
        if (currentDriver == MultiDriver.DRIVER1) {
            Driver.pDriver = pDriver;
        } else if ((currentDriver == MultiDriver.DRIVER2)) {
            Driver.pDriver2 = pDriver;
        } else if ((currentDriver == MultiDriver.DRIVER3)) {
            Driver.pDriver3 = pDriver;
        } else {
            Driver.pDriver4 = pDriver;
        }

    }

    public static void closeAll() {
        if (Driver.pDriver != null) {
            Driver.pDriver.quit();
            Driver.pDriver = null;
        }
        if (Driver.pDriver2 != null) {
            Driver.pDriver2.quit();
            Driver.pDriver2 = null;
        }
        if (Driver.pDriver3 != null) {
            Driver.pDriver3.quit();
            Driver.pDriver3 = null;
        }
        if (Driver.pDriver4 != null) {
            Driver.pDriver4.quit();
            Driver.pDriver4 = null;
        }
        // killRemainingProcess();
    }

    private static WebDriver getCurrentDriver() {
        WebDriver tmpDriver;

        if (currentDriver == MultiDriver.DRIVER1) {
            tmpDriver = Driver.pDriver;
        } else if (currentDriver == MultiDriver.DRIVER2) {
            tmpDriver = Driver.pDriver2;
        } else if (currentDriver == MultiDriver.DRIVER3) {
            tmpDriver = Driver.pDriver3;
        } else {
            tmpDriver = Driver.pDriver4;
        }
        return tmpDriver;
    }

    private static WebDriver getDriver(boolean incognito) {
        WebDriver tmpDriver = getCurrentDriver();
        if (tmpDriver == null) {
            Report.info("Launch Browser: " + Browser.getBrowser().toString()
                    + (ConfigHelper.BROWSER_HEADLESS.getBooleanValue() ? " ( HEADLESS Mode )" : ""));
            setDriver(Browser.getBrowser(), incognito);
            tmpDriver = getDriver();
        }
        return tmpDriver;
    }

    /**
     * @return true if a driver is started
     */
    public static boolean isDriverStarted() {
        return getCurrentDriver() != null;
    }

    private static final File defaultDownloadDirectoryPath = new File(
            (SystemUtils.IS_OS_WINDOWS ? "C:/temp/selenium" : "./temp"));

    private static void setDriver(Browser browser, boolean incognito) {
        if (browser == null) {
            browser = Browser.FIREFOX;
        }
        final boolean headless = ConfigHelper.BROWSER_HEADLESS.getBooleanValue();
        switch (browser) {
            // local browser definitions

            case FIREFOX:

                WebDriverManager.firefoxdriver().setup();

                final FirefoxOptions optionsFF = new FirefoxOptions();
                optionsFF.setProfile(getFirefoxDriverProfile(incognito));
                optionsFF.setHeadless(headless);

                if (!ConfigHelper.PROXY.getValue().equals("")) {
                    final Proxy proxy = new Proxy();
                    proxy.setHttpProxy(ConfigHelper.PROXY.getValue());
                    proxy.setSslProxy(ConfigHelper.PROXY.getValue());
                    optionsFF.setCapability("proxy", proxy);
                }

                setDriver(new FirefoxDriver(optionsFF));
                getDriver().manage().window().maximize();
                // getDriver().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
                break;

            case IE:
                Driver.TIMEOUT_TIME = 3 * ORIGINAL_TIMEOUT_TIME;

                WebDriverManager.iedriver().browserVersion("3.13").setup();
                final InternetExplorerOptions caps = new InternetExplorerOptions();
                caps.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
                caps.setCapability("unexpectedAlertBehaviour", "accept");
                caps.setCapability("ignoreProtectedModeSettings", true);
                caps.setCapability("disable-popup-blocking", true);
                caps.setCapability("acceptInsecureCertificates", true);
                caps.setCapability("enablePersistentHover", true);
                caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                caps.setCapability("javascriptEnabled", true);
                caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
                caps.setCapability("intl.accept_languages", "en-us");
                caps.setCapability("logLevel", "ERROR");
                // caps.setCapability("log_file", "ie.log");
                System.out.println("IE Driver launched Before");
                try {
                    setDriver(new InternetExplorerDriver(caps));
                } catch (final SessionNotCreatedException ex) {
                    ex.printStackTrace();
                    System.out.println("IE Session not created");
                }
                System.out.println("IE Driver launched Done");
                getDriver().manage().window().maximize();
                // System.out.println("case IE");
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();

                final EdgeOptions capsEdge = new EdgeOptions();

                capsEdge.setCapability("os", "Windows");
                capsEdge.setCapability("os_version", "10.0");
                capsEdge.setCapability("browser", "Edge");
                capsEdge.setCapability("browser_version", "16.0");
                capsEdge.setCapability("ignoreZoomSetting", true);
                capsEdge.setCapability("nativeEvents", false);
                capsEdge.addArguments("--remote-allow-origins=*");

                setDriver(new EdgeDriver(capsEdge));
                // getDriver().manage().window().maximize();
                // System.out.println("case EDGE");
                break;

            case CHROME:
            default:
                if (SystemUtils.IS_OS_UNIX) {
                    final File chromedriver = new File(
                            "/home/centos/.m2/repository/webdriver/chromedriver/linux64/81.0.4044.138/chromedriver");
                    System.setProperty("webdriver.chrome.driver", chromedriver.getAbsolutePath());
                } else {
                    WebDriverManager.chromedriver().clearResolutionCache();
                    WebDriverManager.chromedriver().setup();
                }

                final ChromeOptions options = new ChromeOptions();

                options.addArguments("--window-size=1400,600");
                if (incognito) {
                    options.addArguments("--incognito");
                }

                final Proxy proxy = new Proxy();
                if (ConfigHelper.PROXY.getValue() != null && !ConfigHelper.PROXY.getValue().equals("")) {
                    proxy.setHttpProxy(ConfigHelper.PROXY.getValue());
                    proxy.setSslProxy(ConfigHelper.PROXY.getValue());
                } else {
                    proxy.setNoProxy(getNoProxy());
                }
                options.setCapability("proxy", proxy);

                final HashMap<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("download.default_directory", defaultDownloadDirectoryPath.getAbsolutePath());
                chromePrefs.put("download.prompt_for_download", false);
                chromePrefs.put("download.directory_upgrade", true);
                chromePrefs.put("plugins.always_open_pdf_externally", true);
                chromePrefs.put("download.extensions_to_open", "applications/pdf");
                chromePrefs.put("safebrowsing.enabled", true);
                options.setHeadless(headless);
                options.setExperimentalOption("prefs", chromePrefs);
                options.setCapability("chrome.switches", Collections.singletonList("--ignore-certificate-errors"));
                options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                options.addArguments("--remote-allow-origins=*");
                setDriver(new ChromeDriver(options));

                // check chrome Version
                // String chromeVersion = ((ChromeDriver)
                // getDriver()).getCapabilities().getVersion();
                // System.out.println(chromeVersion.getCapability("chrome.chromedriverVersion"));

                getDriver().manage().window().maximize();

                // Resize the current window to the given dimension
                // getDriver().manage().window().setSize(d); //yy
                // System.out.println("case CHROME");
                break;
        }

        getDriver().manage().deleteAllCookies();
        // Wait 20 second an element
        getDriver().manage().timeouts().implicitlyWait(Driver.TIMEOUT_TIME, TimeUnit.MILLISECONDS);
        // Save browser version
        //final Capabilities caps = ((RemoteWebDriver) getDriver()).getCapabilities();
        final Capabilities caps = ((RemoteWebDriver) getDriver()).getCapabilities();
        // String browserName1 = caps.getBrowserName();
        browserVersion = caps.getBrowserVersion();

    }

    private static FirefoxProfile getFirefoxDriverProfile(boolean incognito) {
        final String mimeTypes = "text/css,application/pdf,application/vnd.adobe.xfdf,application/vnd.fdf,application/vnd.adobe.xdp+xml,application/rtf,application/octet-stream,text/csv,application/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/octet-stream,multipart/x-zip,application/zip,application/zip-compressed,application/x-zip-compressed";
        final FirefoxProfile profile = new FirefoxProfile();
        if (ConfigHelper.PROXY.getValue().equals("")) {
            // no proxy
            profile.setPreference("network.proxy.no_proxies_on", getNoProxy()); // INSTEAD use this one
        }
        if (incognito) {
            profile.setPreference("browser.privatebrowsing.autostart", true);
        }

        profile.setPreference("browser.cache.disk.enable", false);
        profile.setPreference("browser.cache.memory.enable", false);
        profile.setPreference("browser.cache.offline.enable", false);
        profile.setPreference("browser.popups.showPopupBlocker", false);
        profile.setPreference("network.http.use-cache", false);
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.download.dir", Driver.defaultDownloadDirectoryPath.getAbsolutePath());
        profile.setPreference("browser.helperApps.neverAsk.openFile", mimeTypes);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", mimeTypes);
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
        profile.setPreference("browser.download.manager.focusWhenStarting", false);
        profile.setPreference("browser.download.manager.useWindow", false);
        profile.setPreference("browser.download.manager.showAlertOnComplete", false);
        profile.setPreference("browser.download.manager.closeWhenDone", true);
        profile.setPreference("plugin.disable_full_page_plugin_for_types", mimeTypes);
        profile.setPreference("browser.download.useToolkitUI", true);
        profile.setPreference("reader.parse-on-load.enabled", false);
        profile.setPreference("intl.accept_languages", "en-us");
        profile.setPreference("logLevel", "INFO");
        // Set this to true to disable the pdf opening
        profile.setPreference("pdfjs.disabled", true);
        return profile;
    }

    private static String getNoProxy() {
        String frontUrl = ConfigHelper.getApplicationUrl();
        if (frontUrl != null && !frontUrl.equalsIgnoreCase("")) {
            final int iHost = frontUrl.indexOf("//");
            if (iHost > 0) {
                frontUrl = frontUrl.substring(iHost + 2);
                final int iport = frontUrl.indexOf(":");
                if (iport > 0) {
                    frontUrl = frontUrl.substring(0, iport);
                }
            }
        }
        return "localhost, 10.*.*.*, *.rbxd.ds, " + frontUrl;

    }

    private static boolean isBrowserLaunched() {
        return Driver.pDriver != null || Driver.pDriver2 != null || Driver.pDriver3 != null || Driver.pDriver4 != null;
    }

    /*
     * change browser ('IE,CHROME,ff)
     */

    public static void close() {
        if (getDriver() != null) {
            try {
                getDriver().quit();
            } catch (final Exception ignored) {
                // do nothing
            }
            setDriver((WebDriver) null);
        }
    }

    /**
     * Clear the current Session
     */
    public static void clearSession() {
        setDriver(null);
    }

    /**
     * return true if an Ui Selenium session is running
     *
     * @return true if a selenium session is running
     */
    public static boolean isSessionRunning() {
        return isBrowserLaunched();
    }

    /**
     * Open an Url on the current browser
     *
     * @param url Url to Open
     */
    public static void openUrl(String url) {
        openUrl(url, null);
    }

    /**
     * Open an Url on the current browser
     *
     * @param url Url to Open
     */
    public static void openUrl(UrlElement url) {
        openUrl(url, null);

    }

    /**
     * Open an Url on the current browser
     *
     * @param url     Url to Open
     * @param message Page à ouvrir
     */
    private static void openUrl(UrlElement url, String message) {
        final String fullUrl = url.getFullUrl();
        Driver.openUrl(fullUrl, message);
    }

    /**
     * Open an Url on the current browser
     *
     * @param url     Url to Open
     * @param message message to display
     */
    protected static void openUrl(String url, String message) {
        String fullUrl = url;
        if (!fullUrl.startsWith("http")) {
            fullUrl = Util.concatenatePath(ConfigHelper.getApplicationUrl(), url, "/");
        }
        Report.info((message != null ? message + " - " : "") + "Open Url: <a href='" + fullUrl + "' target='_blank'>"
                + fullUrl + "</a>", false, false);
        try {
            getDriver().get(fullUrl);
        } catch (final org.openqa.selenium.TimeoutException ex) {
            Report.warning("2nd try after Time out Exception on '" + fullUrl + "' :" + ex.getMessage());
            getDriver().get(fullUrl);
        } catch (final Exception e) {
            Report.info("Error when opening the page");
            Report.info(e.getMessage());
        }

    }

    /**
     * Refresh the current page
     */
    public static void refresh() {
        Report.info("Refresh Application");
        Driver.getDriver().navigate().refresh();
        waitForReady(Driver.TIMEOUT_TIME);
    }

    /**
     * Get the current Url on the current browser
     */
    public static String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    /**
     * Wait until Ajax query ends
     */
    public static void waitForReady() {
        waitForReady(Driver.TIMEOUT_TIME);
    }

    /**
     * Wait until Ajax query ends
     *
     * @param timeOutInSecond time Wait for ready
     */
    public static void waitForReady(int timeOutInSecond) {

        waitForJsLoaded(timeOutInSecond);
        waitForAjaxCompleted(timeOutInSecond);

    }

    public static final int VERY_SHORT_TIME = 1000;
    public static final int SHORT_TIME = 2000;
    public static final int MEDIUM_TIME = 4000;
    public static final int LONG_TIME = 6000;
    public static final int ORIGINAL_TIMEOUT_TIME = 30000;
    public static int TIMEOUT_TIME = ORIGINAL_TIMEOUT_TIME;
    public static final int ULTRA_LONG_TIME = 90000;

    /**
     * Pause current thread
     *
     * @param durationMs in Millisecond
     */
    public static void sleep(int durationMs) {
        try {
            Thread.sleep(durationMs);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wait for pending jQuery ajax queries to have completed
     *
     * @param timeOutInSecond time in second
     */
    private static void waitForAjaxCompleted(long timeOutInSecond) {
        final long endTime = System.currentTimeMillis() + timeOutInSecond * 1000;

        if (getDriver() instanceof JavascriptExecutor) {
            while (System.currentTimeMillis() < endTime) {
                try {
                    Object result;
                    result = ((JavascriptExecutor) getDriver())
                            .executeScript("return ((typeof(jQuery) == 'undefined') || (jQuery.active == 0))");
                    if ("true".equals(result.toString())) {
                        return;
                    }
                    // noinspection BusyWait
                    Thread.sleep(100);
                } catch (final Exception ignored) {
                }

            }
        }
    }

    private static void waitForJsLoaded(long timeOutInSecond) {
        final long endTime = System.currentTimeMillis() + timeOutInSecond * 1000;
        if (getDriver() instanceof JavascriptExecutor) {
            while (System.currentTimeMillis() < endTime) {
                try {
                    Object result;
                    result = ((JavascriptExecutor) getDriver()).executeScript("return document.readyState");
                    if ("complete".equals(result.toString())) {
                        return;
                    }
                    // noinspection BusyWait
                    Thread.sleep(100);
                } catch (final Exception ignored) {
                }

            }
        }
    }

    /**
     * Execute a javascript inside the browser
     *
     * @param javascript javascript to check
     */
    private static String executeJs(String javascript) {
        try {
            final JavascriptExecutor js = (JavascriptExecutor) getDriver();
            final Object obj = js.executeScript("return " + javascript);
            return obj.toString();
        } catch (final Exception ex) {
            return "";
        }
    }

    /**
     * If we used iFrame, go back to the main document when we go out the iFrame
     */
    public static void iframeGoMainDocument() {
        getDriver().switchTo().defaultContent();

    }

    /**
     * enter in the 1rst iFrame
     */
    public static void iframeSwitchToIFrame() {
        iframeSwitchToIFrame(1);
    }

    /**
     * enter in an iFrame
     *
     * @param iFrameDepth iframe's depth
     */
    public static void iframeSwitchToIFrame(int iFrameDepth) {
        iframeGoMainDocument();
        for (int i = 0; i < iFrameDepth; i++) {
            final boolean res = iframeSwitchToSubIFrame();
            if (!res) {
                Report.failed("Cannot switch to IFrame");
            }
        }
    }

    /**
     * Go inside the first iFrame
     *
     * @return true if we go in an iFrame, false if the page does not contain any
     * iFrame
     */
    private static boolean iframeSwitchToSubIFrame() {
        final By by = By.xpath("//iframe");

        final List<WebElement> elements = Driver.getDriver().findElements(by);
        if (elements.isEmpty()) {
            // no iframe found
            return false;
        }
        final WebElement iFrame = elements.get(0);
        String mes = "";
        final String src = iFrame.getAttribute("src");
        if (src != null && src.length() > 0) {
            mes += "src:" + src;
        }
        final String id = iFrame.getAttribute("id");
        if (id != null && id.length() > 0) {
            mes += " id:" + id;
        }
        Driver.getDriver().switchTo().frame(iFrame);
        final String title = Driver.executeJs("document.title;");
        if (title.length() > 0) {
            mes += " title:" + title;

        }
        if (mes.length() > 0) {
            Report.info("Switch to iframe: " + mes);

        }
        return true;

    }

    public static boolean isAlertPresent() {
        try {
            getDriver().switchTo().alert();
            return true;
        } // try
        catch (final NoAlertPresentException ignored) {
            return false;
        } // catch
    }

    public static void closeAlertIfPresent() {
        try {
            final org.openqa.selenium.Alert alert = Driver.getDriver().switchTo().alert();
            // update is executed
            alert.accept();
        } catch (final NoAlertPresentException ignored) {
            // do nothing
        }
    }

    public static void openNewTab() {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("window.open()");
        final ArrayList<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(1));
    }

    public static void closeTab() {
        Driver.getDriver().close();
    }

    /**
     * @param tabNum start at 1
     */
    public static void switchToTab(int tabNum) {
        final ArrayList<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(tabNum - 1));
    }

    /**
     * @return browser tabs number
     */
    public static int getTabsNb() {
        return getDriver().getWindowHandles().size();
    }

    /**
     * Empty the download directory
     */
    public static void clearDownloadDirectory() {
        try {
            FileUtils.deleteDirectory(defaultDownloadDirectoryPath);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        if (!defaultDownloadDirectoryPath.mkdirs()) {
            System.out.println("Cannot create Download directory");
        }
    }

    /**
     * Retourne un fichier télécharger avec une extension
     *
     * @param extension ex .pdf
     * @return File or null if not présent
     */
    public static File getDownloadedFile(String extension) throws IOException {
        List<Path> pdfs = Util.findByLastModifiedTime(Driver.defaultDownloadDirectoryPath.toPath(),
                Instant.now().minus(3, ChronoUnit.MINUTES), extension);
        final long end = System.currentTimeMillis() + 3 * 60000;
        while (pdfs.isEmpty() && System.currentTimeMillis() < end) {
            Driver.sleep(Driver.VERY_SHORT_TIME);
            pdfs = Util.findByLastModifiedTime(Driver.defaultDownloadDirectoryPath.toPath(),
                    Instant.now().minus(3, ChronoUnit.MINUTES), extension);
        }
        if (!pdfs.isEmpty()) {
            return new File(pdfs.get(0).toString());
        }
        return null;
    }

}
