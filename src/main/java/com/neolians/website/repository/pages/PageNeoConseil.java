package com.neolians.website.repository.pages;

import com.neolians.common.utils.selenium.elements.BlockElement;
import com.neolians.common.utils.selenium.elements.ButtonElement;
import com.neolians.common.utils.selenium.elements.UrlElement;
import org.openqa.selenium.By;

public class PageNeoConseil {

    private PageNeoConseil() {
        throw new IllegalStateException("Utility class");
    }


    public static final UrlElement url = new UrlElement("neo.conseil", "/neo-conseil-2/");
    // identifier la première paragraphe
    public static final BlockElement paragraphe1 = new BlockElement("paragraphe1", By.xpath("//div[@data-id='5f19968']"));

    // identifier la colonne horizantale

    public static final BlockElement colonnehorizantale = new BlockElement("la colonne horizantale", By.xpath("//div[@data-id='f121e18']"));
    public static final BlockElement lenombredecontenu1delacolonne=new BlockElement("15ans",By.xpath("//strong[text()='15 ans']"));
    public static final BlockElement Contenu1delacolonne=new BlockElement("le contenu de 15 ans",By.xpath("//span[text()='d’expérience des centres de service nearshore et l’assistance sur site client']"));

    public static final BlockElement lenombredecontenu2delacolonne=new BlockElement("20ans",By.xpath("//strong[text()='20 ans']"));
    public static final BlockElement Contenu2delacolonne=new BlockElement("le contenu de 20 ans",By.xpath("//span[text()='de séniorité moyenne du management dans le test et le support']"));

    public static final BlockElement lenombredecontenu3delacolonne=new BlockElement("130projets",By.xpath("//strong[text()='130 projets']"));
    public static final BlockElement Contenu3delacolonne=new BlockElement("le contenu de 130 projets",By.xpath("//span[text()='pour près de 100 clients sur 5 continents']"));

    public static final BlockElement lenombredecontenu4delacolonne=new BlockElement("98,7",By.xpath("//strong[text()='98,7%']"));
    public static final BlockElement Contenu4delacolonne=new BlockElement("le contenu de 98,7 pourcent",By.xpath("//span[text()='de clients très satisfaits']"));


    // identifier la deuxième paragraphe
    public static final BlockElement paragraphe2 = new BlockElement("paragraphe2",By.xpath("//div[@data-id='52708ad']"));
    // identifier les Gif
    public static final ButtonElement image1=new ButtonElement("image1",By.xpath("//div[@data-id='a7ca9cb']"));
    public static final ButtonElement image2=new ButtonElement("image2",By.xpath("//div[@data-id='ad4ad5b']"));
}
