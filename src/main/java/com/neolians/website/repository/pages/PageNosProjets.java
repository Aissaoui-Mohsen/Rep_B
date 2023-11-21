package com.neolians.website.repository.pages;

import com.neolians.common.utils.selenium.elements.BlockElement;
import com.neolians.common.utils.selenium.elements.ButtonElement;
import com.neolians.common.utils.selenium.elements.UrlElement;
import org.openqa.selenium.By;

public class PageNosProjets {
    private PageNosProjets() {
        throw new IllegalStateException("Utility class");
    }


    public static final UrlElement url = new UrlElement("Nos projets", "/nos-projets/");
    // identifier Neolians logo
    public static final BlockElement colonnehorizantale = new BlockElement("colonne horizantale", By.xpath("//div[@data-id='66446db4']"));

    // identifier les boutons KPMG ENAblan...
    public static final ButtonElement enablon = new ButtonElement("enablon", By.xpath("//li[@id='enablon']"));
    public static final ButtonElement KPMG = new ButtonElement("KPMG", By.xpath("//li[@id='kpmg']"));
    public static final ButtonElement Sopra = new ButtonElement("Sopra", By.xpath("//li[@id='sopra']"));
    public static final ButtonElement Limonetik = new ButtonElement("limonetik", By.xpath("//li[@id='limonetik']"));
    public static final ButtonElement PackSolutions = new ButtonElement("Pack Solutions", By.xpath("//li[@id='pack-solutions']"));
    public static final ButtonElement SAP = new ButtonElement("SAP", By.xpath("//li[@id='sap']"));
    public static final ButtonElement Novapost = new ButtonElement("Novapost", By.xpath("//li[@id='novapost']"));
    public static final ButtonElement BusinessInvestigation = new ButtonElement("BusninessInvestigation", By.xpath("//li[@id='novapost']"));

    // identifier ces paragraphes
    public static final BlockElement paragraphedeenablon = new BlockElement("description de enablon", By.xpath("//img[@class='size-full wp-image-25781 aligncenter']"));
    public static final BlockElement paragraphedekpmg = new BlockElement("description de kpmg", By.xpath("//img[@class='size-full wp-image-25782 aligncenter']"));
    public static final BlockElement paragraphedesopra = new BlockElement("description de sopra", By.xpath("//img[@class='size-full wp-image-25788 aligncenter']"));
    public static final BlockElement paragraphedelimonetik = new BlockElement("description de limonetik", By.xpath("//img[@class='size-full wp-image-25784 aligncenter']"));
    public static final BlockElement paragraphedepacksolution = new BlockElement("description de Pack Solution", By.xpath("//img[@class='size-full wp-image-25786 aligncenter']"));
    public static final BlockElement paragraphedesap = new BlockElement("description de SAP", By.xpath("//img[@class='size-full wp-image-25787 aligncenter']"));
    public static final BlockElement paragraphedenovapost = new BlockElement("description de Novapost", By.xpath("//img[@class='size-full wp-image-25785 aligncenter']"));
    public static final BlockElement paragraphedebusinessinvestigation = new BlockElement("description de business Invetigation", By.xpath("//img[@class='size-full wp-image-25783 aligncenter']"));
}



