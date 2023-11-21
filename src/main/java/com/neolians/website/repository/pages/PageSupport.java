package com.neolians.website.repository.pages;

import com.neolians.common.utils.selenium.elements.BlockElement;
import com.neolians.common.utils.selenium.elements.UrlElement;
import org.openqa.selenium.By;

public class PageSupport {
    private PageSupport()
    {throw new IllegalStateException("Utility class"); }

    public static final UrlElement url=new UrlElement("Home url","/neo-support/");
    public static final BlockElement paragraphe1=new BlockElement("paragraphe 1", By.xpath("//div[@data-id=\"5f19968\"]"));
    public static final BlockElement img01=new BlockElement("image 1", By.xpath("//div[@data-id=\"37ea42b\"]"));
    public static final BlockElement paragraphe2=new BlockElement("paragraphe 2", By.xpath("//section[@data-id=\"e3ab574\"]"));
    public static final BlockElement img02=new BlockElement("image 2", By.xpath("//div[@data-id='ad4ad5b']"));
    public static final BlockElement paragraphe3=new BlockElement("paragraphe 3", By.xpath("//div[@data-id='52708ad']"));









}
