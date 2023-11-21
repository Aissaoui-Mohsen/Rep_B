package com.neolians.website.repository.pages;

import com.neolians.common.utils.selenium.elements.BlockElement;
import com.neolians.common.utils.selenium.elements.UrlElement;
import org.openqa.selenium.By;

public class PageNeoExternalisationFromNosOffres {


    private PageNeoExternalisationFromNosOffres() {
        throw new IllegalStateException("Utility class");
    }


    public static final UrlElement url4 = new UrlElement("Home url", "/neo-externalisation/");

    public static final BlockElement paragraphe1 = new BlockElement("paragraphe 1 de la page Neo.externalisation"
            , By.id("1243667655"));

    public static final BlockElement contenu1paragraphe1 = new BlockElement("contenu1 du paragraphe 1 de la page Neo.externalisation"
            , By.xpath("//p[text()='Dans l’exercice de vos métiers, vous devez souvent faire face à des objectifs contradictoires : innover tout en réduisant vos coûts, simplifier vos processus métier tout en adoptant les technologies les plus récentes, étendre vos activités tout en assurant des liens plus étroits avec vos clients, entre autres.']"));


    public static final BlockElement imgGif1 =new BlockElement("image numéro1 "
            ,By.xpath("//div[@data-id='a7ca9cb']"));


    public static final BlockElement horizontalFrame =new BlockElement("cadre au milieu de la page neo.externalisation",
            By.xpath("//section[@data-id='d888e11']"));

    public static final BlockElement horizontalFrame1 =new BlockElement("le premier petit cadre",
            By.xpath("//div[@data-id='95351c2']"));

    public static final BlockElement contenuhorizontalFrame1 =new BlockElement("le contenu du 1er cadre",
            By.xpath("//strong[text()='15 ans']"));


    public static final BlockElement horizontalFrame2 =new BlockElement("le deuxième petit cadre",
            By.xpath("//div[@data-id='907fb53']"));

    public static final BlockElement contenuhorizontalFrame2 =new BlockElement("le contenu du 2eme cadre",
            By.xpath("//strong[text()='20 ans']"));


    public static final BlockElement horizontalFrame3 =new BlockElement("le troisième petit cadre",
            By.xpath("//div[@data-id='b48b979']"));

    public static final BlockElement contenuhorizontalFrame3 =new BlockElement("le contenu du 3eme cadre",
            By.xpath("//strong[text()='130 projets']"));

    public static final BlockElement horizontalFrame4 =new BlockElement("le quatrième petit cadre",
            By.xpath("//div[@data-id='8ec50a8']"));

    public static final BlockElement contenuhorizontalFrame4 =new BlockElement("le contenu du 4er cadre",
            By.xpath("//strong[text()='98,7%']"));


    public static final BlockElement paragraphe2 = new BlockElement("paragraphe 2 de la page Neo.externalisation"
            , By.id("1243667655"));


    public static final BlockElement contenu1paragraphe2 = new BlockElement("contenu1 du paragraphe 2 de la page Neo.externalisation"
            , By.xpath("//p[text()='Environ un million de projets informatiques sont lancés chaque année. Mais seulement 16 % atteignent leurs objectifs en termes de couverture fonctionnelle, de délais et de budget, et 37 % sont tout simplement arrêtés en cours de route.']"));


    public static final BlockElement imgGif2 =new BlockElement("image numéro2 "
            ,By.xpath("//div[@data-id='7673fab']"));


}
