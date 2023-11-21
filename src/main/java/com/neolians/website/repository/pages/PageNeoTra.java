package com.neolians.website.repository.pages;

import com.neolians.common.utils.selenium.elements.BlockElement;
import com.neolians.common.utils.selenium.elements.ButtonElement;
import com.neolians.common.utils.selenium.elements.UrlElement;
import org.openqa.selenium.By;

public class PageNeoTra {
    private PageNeoTra() {
        throw new IllegalStateException("Utility class");
    }

    public static final UrlElement url = new UrlElement("url", "/neo-tra/");
    // identifier le block 1
    public static final BlockElement paragraphe1 = new BlockElement("paragraphe 1", By.xpath("//div[@data-id='5f19968']"));
    // identifier le block 2
    public static final BlockElement Colonnnehorizantale = new BlockElement("la colonne horizantale", By.xpath("//div[@data-id='65f2e72']"));
    public static final BlockElement lenombredecontenu1delacolonne=new BlockElement("15ans",By.xpath("//strong[text()='15 ans']"));
    public static final BlockElement Contenu1delacolonne=new BlockElement("le contenu de 15 ans",By.xpath("//span[text()='d’expérience des centres de service nearshore et l’assistance sur site client']"));

    public static final BlockElement lenombredecontenu2delacolonne=new BlockElement("20ans",By.xpath("//strong[text()='20 ans']"));
    public static final BlockElement Contenu2delacolonne=new BlockElement("le contenu de 20 ans",By.xpath("//span[text()='de séniorité moyenne du management dans le test et le support']"));

    public static final BlockElement lenombredecontenu3delacolonne=new BlockElement("130projets",By.xpath("//strong[text()='130 projets']"));
    public static final BlockElement Contenu3delacolonne=new BlockElement("le contenu de 130 projets",By.xpath("//span[text()='pour près de 100 clients sur 5 continents']"));

    public static final BlockElement lenombredecontenu4delacolonne=new BlockElement("98,7",By.xpath("//strong[text()='98,7%']"));
    public static final BlockElement Contenu4delacolonne=new BlockElement("le contenu de 98,7 pourcent",By.xpath("//span[text()='de clients très satisfaits']"));


    // identifier le block 3
    public static final BlockElement paragraphe3 = new BlockElement("paragraphe 3", By.xpath("//div[@data-id='52708ad']"));
    public static final BlockElement image1 = new BlockElement("image 1",By.xpath("//div[@data-id='a7ca9cb']"));
    public static final BlockElement image2 = new BlockElement("image 2",By.xpath("//div[@data-id='7673fab']"));
}
