package com.neolians.website.repository.pages;

import com.neolians.common.utils.selenium.elements.BlockElement;
import com.neolians.common.utils.selenium.elements.UrlElement;
import org.openqa.selenium.By;

public class PageImpactSocial {
    private PageImpactSocial()
    {throw new IllegalStateException("Utility class"); }

    public static final UrlElement url=new UrlElement("Home url","/notre-impact-social/");
    public static final BlockElement paragraphe1=new BlockElement("paragraphe1", By.xpath("//section[@data-id='5daee16']"));
    public static final BlockElement paragraphe2=new BlockElement("paragraphe2", By.xpath("//div[@data-id='aebe78f']"));

}
