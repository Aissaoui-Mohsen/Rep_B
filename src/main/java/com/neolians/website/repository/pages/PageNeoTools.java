package com.neolians.website.repository.pages;

import com.neolians.common.utils.selenium.elements.BlockElement;
import com.neolians.common.utils.selenium.elements.UrlElement;
import org.openqa.selenium.By;

public class PageNeoTools {

    private PageNeoTools() {
        throw new IllegalStateException("Utility class");
    }

    public static final UrlElement url = new UrlElement("Home url", "/");

    public static final BlockElement paragraphe1 = new BlockElement("paragraphe 1 de la page Neo.tools"
            , By.id("1243667655"));

    public static final BlockElement contenu1paragraphe1 = new BlockElement("contenu1 du paragraphe 1 de la page Neo.tools"
            , By.xpath("//p[text()='Un grand nombre de nos clients, en particulier les intégrateurs et les éditeurs, font face à des charges importantes de test de recette (par exemple vérification de la conformité de fiches de paie après migration )']"));


    public static final BlockElement firstGif =new BlockElement("image numéro1 "
            ,By.xpath("//div[@data-id='a7ca9cb']"));




    public static final BlockElement horizontalFrame =new BlockElement("cadre horizontal de la page neo.tools",
            By.xpath("//section[@data-id='54d5f03']"));

    public static final BlockElement horizontalFrame1 =new BlockElement("le premier petit cadre",
            By.xpath("//div[@data-id='0e4f4a0']"));

    public static final BlockElement contenuhorizontalFrame1 =new BlockElement("le contenu du 1er cadre",
            By.xpath("//strong[text()='15 ans']"));


    public static final BlockElement horizontalFrame2 =new BlockElement("le deuxième petit cadre",
            By.xpath("//div[@data-id='7f5209f']"));

    public static final BlockElement contenuhorizontalFrame2 =new BlockElement("le contenu du 2eme cadre",
            By.xpath("//strong[text()='20 ans']"));


    public static final BlockElement horizontalFrame3 =new BlockElement("le troisième petit cadre",
            By.xpath("//div[@data-id='16cf2e2']"));

    public static final BlockElement contenuhorizontalFrame3 =new BlockElement("le contenu du 3eme cadre",
            By.xpath("//strong[text()='130 projets']"));

    public static final BlockElement horizontalFrame4 =new BlockElement("le quatrième petit cadre",
            By.xpath("//div[@data-id='f7e92e9']"));

    public static final BlockElement contenuhorizontalFrame4 =new BlockElement("le contenu du 4er cadre",
            By.xpath("//strong[text()='98,7%']"));



    public static final BlockElement paragraphe2 = new BlockElement("paragraphe2 de la page Neo.tools"
            , By.id("1620106714"));


    public static final BlockElement contenu1paragraphe2 = new BlockElement("contenu1 du paragraphe 2 de la page Neo.tools"
            , By.xpath("//p[text()='Le gestionnaire de document vous permet d’importer la liste des documents que vous souhaitez utiliser lors de vos campagnes de test.']"));


    public static final BlockElement secondGif = new BlockElement("Le gestionnaire de document vous permet d’importer la liste des documents que vous souhaitez utiliser lors de vos campagnes de test."
            ,By.xpath("//div[@data-id='7673fab']"));








}
