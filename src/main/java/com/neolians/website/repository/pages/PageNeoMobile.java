package com.neolians.website.repository.pages;

import com.neolians.common.utils.selenium.elements.BlockElement;
import com.neolians.common.utils.selenium.elements.ButtonElement;
import com.neolians.common.utils.selenium.elements.UrlElement;
import org.openqa.selenium.By;

public class PageNeoMobile {


    private PageNeoMobile() {
        throw new IllegalStateException("Utility class");
    }


    public static final UrlElement url2 = new UrlElement("Home url", "/neo-mobile/");



    public static final BlockElement paragraphe1 = new BlockElement("paragraphe de la page Neo.mobile"
            , By.id("1250104802"));


    public static final BlockElement contenu1paragraphe1 = new BlockElement("contenu1 du paragraphe 1 de la page Neo.mobile"
            , By.xpath("//p[text()='Nous vous accompagnons dans votre activité de développement d’applications mobiles à travers :']"));


    public static final BlockElement contenu2paragraphe1 = new BlockElement("contenu2 duparagraphe 1 de la page Neo.mobile"
            , By.xpath("//p[text()='L’élaboration de votre stratégie de test d’application mobile et sa mise œuvre,']"));



    public static final BlockElement firstGif = new BlockElement("gif 1 de la page neo.mobile"
            ,By.xpath("//div[@data-id='a7ca9cb']"));



    public static final BlockElement horizontalFrame =new BlockElement("cadre au milieu de la page présentation",
            By.xpath("//section[@data-id='a1ca2a9']"));

    public static final BlockElement horizontalFrame1 =new BlockElement("le premier petit cadre",
            By.xpath("//div[@data-id='a3ba8cd']"));

   public static final BlockElement contenuhorizontalFrame1 =new BlockElement("le contenu du 1er cadre",
           By.xpath("//strong[text()='15 ans']"));


    public static final BlockElement horizontalFrame2 =new BlockElement("le deuxième petit cadre",
            By.xpath("//div[@data-id='ee0531c']"));

    public static final BlockElement contenuhorizontalFrame2 =new BlockElement("le contenu du 2eme cadre",
            By.xpath("//strong[text()='20 ans']"));


    public static final BlockElement horizontalFrame3 =new BlockElement("le troisième petit cadre",
            By.xpath("//div[@data-id='2bade6a']"));

    public static final BlockElement contenuhorizontalFrame3 =new BlockElement("le contenu du 3eme cadre",
            By.xpath("//strong[text()='130 projets']"));

    public static final BlockElement horizontalFrame4 =new BlockElement("le quatrième petit cadre",
            By.xpath("//div[@data-id='0e15dfa']"));

    public static final BlockElement contenuhorizontalFrame4 =new BlockElement("le contenu du 4er cadre",
            By.xpath("//strong[text()='98,7%']"));


    public static final BlockElement paragraphe2 = new BlockElement("paragraphe2 de la page Neo.mobile"
            , By.xpath("//div[@data-id='52708ad']"));


    public static final BlockElement contenu1paragraphe2 = new BlockElement("contenu1 du paragraphe 2 de la page Neo.mobile"
            , By.xpath("//p[text()='Nos prestations de test dédiées aux applications mobiles :']"));


    public static final BlockElement contenu2paragraphe2 = new BlockElement("contenu1 du paragraphe 2 de la page Neo.mobile"
            , By.xpath("//p[text()='Cloud Test :']"));

    //p[text()='Nos prestations de test dédiées aux applications mobiles :']
    public static final BlockElement secondGif = new BlockElement("gif 2 de la page neo.mobile"
            ,By.xpath("//div[@data-id='7673fab']"));

























}
