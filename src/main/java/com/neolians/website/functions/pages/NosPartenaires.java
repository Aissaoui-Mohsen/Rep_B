package com.neolians.website.functions.pages;

import com.neolians.website.repository.pages.PageHome;
import com.neolians.website.repository.pages.PageNosPartenaires;
import com.neolians.website.repository.pages.PageNousRejoindre;
import org.junit.Assert;

public class NosPartenaires {

    private NosPartenaires() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Verifie page Nos Partenaire
     */


    public static void verifiePageNosPartenaires(){

    //titre Nos partenaires

        PageNosPartenaires.titleNosPartenaires.assertPresent();

        String contenuTitle9 = PageNosPartenaires.titleNosPartenaires.getText();
        PageNosPartenaires.titleNosPartenaires.assertEquals("Nos partenaires",contenuTitle9);


    //verifier l'image, titre et description de 1er partenaire

        PageNosPartenaires.titlePartenaire1.assertPresent();

        String contenuTitle10 = PageNosPartenaires.titlePartenaire1.getText();
        PageNosPartenaires.titlePartenaire1.assertEquals("Les tests automatiques avec Ranorex"
                ,contenuTitle10);

        PageNosPartenaires.descriptionPartenaire1.assertPresent();
        String descriptionP1 = PageNosPartenaires.descriptionPartenaire1.getText();
        PageNosPartenaires.descriptionPartenaire1.assertEquals("neolians utilise depuis 2011 les solutions Ranorex pour la réalisation des tests automatisé pour des solutions Web, HTML5, Mobile, Delphi, Windows, ... La reconnaissance des objets graphiques, une organisation en plusieurs couches, et les librairies de développement du monde .Net permettent une fiabilité et une ré-utilisabilité des tests."
                ,descriptionP1);

        PageNosPartenaires.imgPartenaire1.assertPresent();

        // clique sur le bouton
        PageNosPartenaires.imgPartenaire1.click();

        //verifier l'url du site Ranorex
        String UrlRanorex= PageNosPartenaires.url1.getCurrentUrl();
        Assert.assertEquals(UrlRanorex,"https://www.ranorex.com/");


        //retour a la page nos partenaires
        PageNosPartenaires.url.openUrl();






    //verifier l'image, titre de 2eme partenaire

        PageNosPartenaires.titlePartenaire2.assertPresent();

        String contenuTitle11 = PageNosPartenaires.titlePartenaire2.getText();
        PageNosPartenaires.titlePartenaire2.assertEquals("L’optimisation des tests avec Coverity"
                ,contenuTitle11);

        PageNosPartenaires.imgPartenaire2.assertPresent();

        PageNosPartenaires.imgPartenaire2.click();

    //verifier l'url du site coverity
        String UrlCoverity= PageNosPartenaires.url2.getCurrentUrl();
        Assert.assertEquals(UrlCoverity,"https://www.synopsys.com/software-integrity.html");

        //retour a la page nos partenaires
        PageNosPartenaires.url.openUrl();






        //verifier l'image, titre et description de 3eme partenaire

        PageNosPartenaires.titlePartenaire3.assertPresent();
        String contenuTitle12 = PageNosPartenaires.titlePartenaire3.getText();
        PageNosPartenaires.titlePartenaire3.assertEquals("Les tests de performances avec Neotys"
                ,contenuTitle12);

        PageNosPartenaires.descriptionPartenaire3.assertPresent();
        String descriptionP3 = PageNosPartenaires.descriptionPartenaire3.getText();
        PageNosPartenaires.descriptionPartenaire3.assertEquals("Un des objectifs majeurs d’une entreprise est de garantir une non régression de la performance et de la fiabilité de ses applications. neolians s’appuie sur la solution neoload pour les tests de performance et vous aide à sécuriser vos engagements en termes de SLA et d’expérience utilisateur."
                ,descriptionP3);

        PageNosPartenaires.imgPartenaire3.assertPresent();

        PageNosPartenaires.imgPartenaire3.click();

        //verifier l'url du site Neotys
        String UrlNEOTYS= PageNosPartenaires.url3.getCurrentUrl();
        Assert.assertEquals(UrlNEOTYS,"https://www.tricentis.com/fr?utm_source=referral&utm_medium=redirect&utm_campaign=neotys");

        //retour a la page nos partenaires
        PageNosPartenaires.url.openUrl();



    //verifier l'image, titre et description de 4eme partenaire

        PageNosPartenaires.titlePartenaire4.assertPresent();
        String contenuTitle13 = PageNosPartenaires.titlePartenaire4.getText();
        PageNosPartenaires.titlePartenaire4.assertEquals("SoapUI",contenuTitle13);

        PageNosPartenaires.descriptionPartenaire4.assertPresent();
        String descriptionP4 = PageNosPartenaires.descriptionPartenaire4.getText();
        PageNosPartenaires.descriptionPartenaire4.assertEquals("Pour vos tests WebService, SoapUi et LoadUi proposent un environnement de tests Facile à prendre en main et maintenable."
                ,descriptionP4);


        PageNosPartenaires.imgPartenaire4.assertPresent();

        PageNosPartenaires.imgPartenaire4.click();

        //verifier l'url du site soapUI
        String UrlSoapUI= PageNosPartenaires.url4.getCurrentUrl();
        Assert.assertEquals(UrlSoapUI,"https://www.soapui.org/");

        //retour a la page nos partenaires
        PageNosPartenaires.url.openUrl();


    //verifier l'image, titre et description de 5eme partenaire


        PageNosPartenaires.titlePartenaire5.assertPresent();

        PageNosPartenaires.imgPartenaire5.assertPresent();

        PageNosPartenaires.imgPartenaire5.click();


        //verifier l'url du site TestLink
        String UrlTestLink= PageNosPartenaires.url5.getCurrentUrl();
        Assert.assertEquals(UrlTestLink,"https://testlink.org/");

        //retour a la page nos partenaires
        PageNosPartenaires.url.openUrl();




        //verifier l'image, titre et description de 6eme partenaire

        PageNosPartenaires.titlePartenaire6.assertPresent();
        String contenuTitle15 = PageNosPartenaires.titlePartenaire6.getText();
        PageNosPartenaires.titlePartenaire6.assertEquals("HP QC",contenuTitle15);

        PageNosPartenaires.descriptionPartenaire6.assertPresent();
        String descriptionP6 = PageNosPartenaires.descriptionPartenaire6.getText();
        PageNosPartenaires.descriptionPartenaire6.assertEquals("Le leader sur le marché des framework de test, HP QC est déployé dans les plus grand groupes"
                ,descriptionP6);


        PageNosPartenaires.imgPartenaire6.assertPresent();

        PageNosPartenaires.imgPartenaire6.click();

        //verifier l'url du site HpQc
        String UrlHpQc= PageNosPartenaires.url6.getCurrentUrl();
        Assert.assertEquals(UrlHpQc,"https://www.hp.com/fr-fr/solutions/business-solutions/printingsolutions/overview.html");

        //retour a la page nos partenaires
        PageNosPartenaires.url.openUrl();



        //verifier l'image, titre et description de 7eme partenaire

        PageNosPartenaires.titlePartenaire7.assertPresent();
        String contenuTitle16 = PageNosPartenaires.titlePartenaire7.getText();
        PageNosPartenaires.titlePartenaire7.assertEquals("Les tests Web avec Selenium",contenuTitle16);

        PageNosPartenaires.imgPartenaire7.assertPresent();
        PageNosPartenaires.imgPartenaire7.click();

        //verifier l'url du site selenium
        String UrlSelenium= PageNosPartenaires.url7.getCurrentUrl();
        Assert.assertEquals(UrlSelenium,"https://www.selenium.dev/");

        //retour a la page nos partenaires
        PageNosPartenaires.url.openUrl();


    //verifier l'image, titre et description de 8eme partenaire


        PageNosPartenaires.titlePartenaire8.assertPresent();
        String contenuTitle17 = PageNosPartenaires.titlePartenaire8.getText();
        PageNosPartenaires.titlePartenaire8.assertEquals("Microsoft Test Manager",contenuTitle17);

        PageNosPartenaires.imgPartenaire8.assertPresent();

        PageNosPartenaires.imgPartenaire8.click();

        //verifier l'url du site MicrosoftTestManager
        String UrlMTM= PageNosPartenaires.url8.getCurrentUrl();
        Assert.assertEquals(UrlMTM,"https://learn.microsoft.com/fr-fr/azure/devops/test/overview?view=azure-devops");

        //retour a la page nos partenaires
        PageNosPartenaires.url.openUrl();


    //verifier l'image, titre et description de 9eme partenaire

        PageNosPartenaires.titlePartenaire9.assertPresent();
        String contenuTitle18 = PageNosPartenaires.titlePartenaire9.getText();
        PageNosPartenaires.titlePartenaire9.assertEquals("Squash Test",contenuTitle18);

        PageNosPartenaires.imgPartenaire9.assertPresent();

        PageNosPartenaires.imgPartenaire9.click();

        //verifier l'url du site MicrosoftTestManager
        String UrlSquash= PageNosPartenaires.url9.getCurrentUrl();
        Assert.assertEquals(UrlSquash,"https://www.squashtest.com/");

        //retour a la page nos partenaires
        PageNosPartenaires.url.openUrl();









    }




}
