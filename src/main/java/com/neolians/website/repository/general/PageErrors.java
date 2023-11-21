package com.neolians.website.repository.general;

import com.neolians.common.utils.selenium.elements.ButtonElement;
import org.openqa.selenium.By;

public class PageErrors {
    public static final ButtonElement fatalError = new ButtonElement("Fatal Error",
            By.xpath("//b[contains(text(),'error')]"));

}
