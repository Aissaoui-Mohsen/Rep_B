package com.neolians.website.repository.pages;

import com.neolians.common.utils.selenium.elements.BlockElement;
import com.neolians.common.utils.selenium.elements.UrlElement;
import org.openqa.selenium.By;

public class PageIntegration {private PageIntegration()

{throw new IllegalStateException("Utility class");}


    public static final UrlElement url = new UrlElement("Home url", "/neo-integration/");

    public static final BlockElement paragraphe1=new BlockElement("paragraphe 1", By.xpath("//div[@data-id='5f19968']"));
    public static final BlockElement paragraphe2=new BlockElement("paragraphe 2", By.xpath("//section[@data-id='a6e996a']"));
    public static final BlockElement paragraphe3=new BlockElement("paragraphe 3", By.xpath("//div[@data-id='435b187']"));
    public static final BlockElement img1=new BlockElement("image 1", By.xpath("//div[@data-id='a7ca9cb']"));
    public static final BlockElement img2=new BlockElement("image 1", By.xpath("//div[@data-id='7673fab']"));






}
