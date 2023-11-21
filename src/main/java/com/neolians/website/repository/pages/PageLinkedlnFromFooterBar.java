package com.neolians.website.repository.pages;

import com.neolians.common.utils.selenium.elements.BlockElement;
import com.neolians.common.utils.selenium.elements.UrlElement;
import org.openqa.selenium.By;

public class PageLinkedlnFromFooterBar {
    private PageLinkedlnFromFooterBar() {
        throw new IllegalStateException("Utility class");
    }

    public static final UrlElement url = new UrlElement("url", "/https://www.linkedin.com/company/neolians/mycompany/");
    public static final BlockElement linkedlndepuislabarreenpieddepage=new BlockElement("la page linkedln de site neolians", By.xpath("//input[@id='password']"));
}
