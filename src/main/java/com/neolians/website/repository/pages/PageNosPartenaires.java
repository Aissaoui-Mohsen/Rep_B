package com.neolians.website.repository.pages;

import com.neolians.common.utils.selenium.elements.BlockElement;
import com.neolians.common.utils.selenium.elements.UrlElement;
import org.openqa.selenium.By;

public class PageNosPartenaires {



    private PageNosPartenaires() {
        throw new IllegalStateException("Utility class");
    }


    public static final UrlElement url = new UrlElement("Nos partenaire url", "/nos-partenaires/");

    public static final BlockElement titleNosPartenaires =new BlockElement("Titre Nos partenaire",
            By.xpath("//h1[@class='ekit-heading--title elementskit-section-title ']"));


    public static final BlockElement imgPartenaire1 =new BlockElement("image de Partenaire1: Ranorex",
            By.xpath("//img[@src='https://neolians.com/wp-content/uploads/2022/01/neolians_partenaire_ranorex-1-1.jpg']"));


    public static final BlockElement titlePartenaire1 =new BlockElement("titre de Partenaire1: Ranorex",
            By.xpath("//a[text()='Les tests automatiques avec Ranorex']"));


    public static final BlockElement descriptionPartenaire1 =new BlockElement("description de Partenaire1: Ranorex",
            By.xpath("//p[text()='neolians utilise depuis 2011 les solutions Ranorex pour la réalisation des tests automatisé pour des solutions Web, HTML5, Mobile, Delphi, Windows, ... La reconnaissance des objets graphiques, une organisation en plusieurs couches, et les librairies de développement du monde .Net permettent une fiabilité et une ré-utilisabilité des tests.']"));



    public static final BlockElement imgPartenaire2 =new BlockElement("image de Partenaire2: coverity",
            By.xpath("//img[@src='https://neolians.com/wp-content/uploads/2022/01/neolians_partenaire_coverty-1.jpg']"));


    public static final BlockElement titlePartenaire2 =new BlockElement("titre de Partenaire2: coverity",
            By.xpath("//a[text()='L’optimisation des tests avec Coverity']"));


    public static final BlockElement imgPartenaire3 =new BlockElement("image de Partenaire3: NEOTYS",
            By.xpath("//img[@src='https://neolians.com/wp-content/uploads/2022/01/neolians_partenaire_neotys-1.jpg']"));


    public static final BlockElement titlePartenaire3 =new BlockElement("titre de Partenaire3: NEOTYS",
            By.xpath("//a[text()='Les tests de performances avec Neotys']"));


    public static final BlockElement descriptionPartenaire3 =new BlockElement("description de Partenaire3: NEOTYS",
            By.xpath("//p[text()='Un des objectifs majeurs d’une entreprise est de garantir une non régression de la performance et de la fiabilité de ses applications. neolians s’appuie sur la solution neoload pour les tests de performance et vous aide à sécuriser vos engagements en termes de SLA et d’expérience utilisateur.']"));


    public static final BlockElement imgPartenaire4 =new BlockElement("image de Partenaire4: soapUI",
            By.xpath("//img[@src='https://neolians.com/wp-content/uploads/2022/01/neolians_partenaire_soapui-1.jpg']"));


    public static final BlockElement titlePartenaire4 =new BlockElement("titre de Partenaire4: soapUI",
            By.xpath("//a[text()='SoapUI']"));

    public static final BlockElement descriptionPartenaire4 =new BlockElement("description de Partenaire4: soapUI",
            By.xpath("//p[text()='Pour vos tests WebService, SoapUi et LoadUi proposent un environnement de tests Facile à prendre en main et maintenable.']"));


    public static final BlockElement imgPartenaire5 =new BlockElement("image de Partenaire5: Test Link",
            By.xpath("//img[@src='https://neolians.com/wp-content/uploads/2022/01/neolians-partenaire-test-link-1-1.jpg']"));


    public static final BlockElement titlePartenaire5 =new BlockElement("titre de Partenaire5: Test Link",
            By.xpath("//a[text()='Test Link']"));

    public static final BlockElement descriptionPartenaire5 =new BlockElement("description de Partenaire5: Test Link",
            By.xpath("//p[text()='Le gestionnaire de test open source qui permet de gérer vos exigences, cas de tests, planifier vos campagnes d'exécution et analyser vos résultats de test.']"));


    public static final BlockElement imgPartenaire6 =new BlockElement("image de Partenaire6: HP QC",
            By.xpath("//img[@src='https://neolians.com/wp-content/uploads/2022/01/neolians-partenaire-quality-center-1-1.jpg']"));


    public static final BlockElement titlePartenaire6 =new BlockElement("titre de Partenaire6: HP QC",
            By.xpath("//a[text()='HP QC']"));


    public static final BlockElement descriptionPartenaire6 =new BlockElement("description de Partenaire6: HP QC",
            By.xpath("//p[text()='Le leader sur le marché des framework de test, HP QC est déployé dans les plus grand groupes']"));

    //////

    public static final BlockElement imgPartenaire7 =new BlockElement("image de Partenaire7: Selenium",
            By.xpath("//img[@src='https://neolians.com/wp-content/uploads/2022/01/neolians_partenaire_selenium-1.jpg']"));


    public static final BlockElement titlePartenaire7 =new BlockElement("titre de Partenaire7: Selenium",
            By.xpath("//a[text()='Les tests Web avec Selenium']"));


    public static final BlockElement descriptionPartenaire7 =new BlockElement("description de Partenaire7: Selenium",
            By.xpath("//p[text()='Selenium est le moteur de reference Open source pour les tests d'interfaces Web. Couplé avec des framework de développements Junit, Nunit, MicrosoftTM, et avec des systèmes d'intégration continue tel que Jenkins, il offre une robustesse pour vos tests automatisés.']"));


    //////
    public static final BlockElement imgPartenaire8 =new BlockElement("image de Partenaire8: Microsoft Test Manager",
            By.xpath("//img[@src='https://neolians.com/wp-content/uploads/2022/01/neolians_partenaire_microsoft-test-manager-1.jpg']"));


    public static final BlockElement titlePartenaire8 =new BlockElement("titre de Partenaire8: Microsoft Test Manager",
            By.xpath("//a[text()='Microsoft Test Manager']"));


    public static final BlockElement descriptionPartenaire8 =new BlockElement("description de Partenaire8: Microsoft Test Manager",
            By.xpath("//p[text()='Microsoft Test Manager est la brique test de l'environnement de développement \"Team Foundation Server\". MTM stocke vos plans de test et résultats et utilise Visual Studio pour le développement des scripts automatiques.'"));

    //////////

    public static final BlockElement imgPartenaire9 =new BlockElement("image de Partenaire9: Squash",
            By.xpath("//img[@src='https://neolians.com/wp-content/uploads/2022/01/neolians_partenaire_squash.jpg']"));


    public static final BlockElement titlePartenaire9 =new BlockElement("titre de Partenaire9: Squash",
            By.xpath("//a[text()='Squash Test']"));


    public static final BlockElement descriptionPartenaire9 =new BlockElement("description de Partenaire9: Squash",
            By.xpath("//p[text()='Squash TM est le gestionnaire de référentiel de test de la suite open source Squash. Il permet de gérer les exigences, les scénarios de tests et les campagnes d'exécution, dans un contexte nativement multi-projet. il est couplé avec Squash TA, un outillage open source d'automatisation des tests fonctionnels et d'industrialisation de leurs exécutions. Compatible avec plusieurs automates open source (exemple Selenium), il permet de gérer les tests automatisés d'applications Web, de webservice et de batchs.'"));

    public static final UrlElement url1 = new UrlElement("Ranorex url", "https://www.ranorex.com/");

    public static final UrlElement url2 = new UrlElement("coverity url", "https://www.synopsys.com/software-integrity.html");

    public static final UrlElement url3 = new UrlElement("NEOTYS url", "https://www.tricentis.com/fr?utm_source=referral&utm_medium=redirect&utm_campaign=neotys");

    public static final UrlElement url4 = new UrlElement("soapUI url", "https://www.soapui.org/");

    public static final UrlElement url5 = new UrlElement("TestLink url", "https://testlink.org/");

    public static final UrlElement url6 = new UrlElement("HpQc url", "https://www.hp.com/fr-fr/solutions/business-solutions/printingsolutions/overview.html");

    public static final UrlElement url7 = new UrlElement("Selenium url", "https://www.selenium.dev/");

    public static final UrlElement url8 = new UrlElement("MicrosotTestManger url", "https://learn.microsoft.com/fr-fr/azure/devops/test/overview?view=azure-devops");

    public static final UrlElement url9 = new UrlElement("SquashTest url", "https://www.squashtest.com/");



}
