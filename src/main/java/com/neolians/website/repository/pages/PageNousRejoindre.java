package com.neolians.website.repository.pages;

import com.neolians.common.utils.selenium.elements.BlockElement;
import com.neolians.common.utils.selenium.elements.UrlElement;
import org.openqa.selenium.By;

public class PageNousRejoindre {

    private PageNousRejoindre() {
        throw new IllegalStateException("Utility class");
    }

    public static final UrlElement url3 = new UrlElement("Home url", "/");

    public static final BlockElement paragraphe = new BlockElement("paragraphe de la page Nous rejoindre"
            , By.xpath("//section[@data-id='\\\"3e2425c\\\"']"));


    public static final BlockElement paragraphe1 = new BlockElement("paragraphe 1 de la page Nous rejoindre"
            , By.xpath("//section[@data-id='\\\"3e2425c\\\"']//child::p[1]"));


    public static final BlockElement paragraphe2 = new BlockElement("paragraphe 2 de la page Nous rejoindre"
            , By.xpath("//section[@data-id='\\\"3e2425c\\\"']//child::p[2]"));

    public static final BlockElement paragraphe3 = new BlockElement("paragraphe 3 de la page Nous rejoindre"
            , By.xpath("//section[@data-id='\\\"3e2425c\\\"']//child::p[3]"));

    public static final BlockElement paragraphe4 = new BlockElement("paragraphe 4 de la page Nous rejoindre"
            , By.xpath("//section[@data-id='\\\"3e2425c\\\"']//child::p[4]"));


    public static final BlockElement gif = new BlockElement("paragraphe 4 de la page Nous rejoindre"
            , By.cssSelector("div.premium-lottie-animation"));









}
