package com.neolians.website.repository.pages;

import com.neolians.common.utils.selenium.elements.BlockElement;
import com.neolians.common.utils.selenium.elements.LinkElement;
import com.neolians.common.utils.selenium.elements.UrlElement;
import org.openqa.selenium.By;

import com.neolians.common.utils.selenium.elements.ButtonElement;

public class PageHome {
	private PageHome() {
		throw new IllegalStateException("Utility class");
	}

	public static final UrlElement url = new UrlElement("Home url", "/");
	public static final BlockElement title = new BlockElement("TITLE",
			By.xpath("//h1[@class='elementor-heading-title elementor-size-xxl']"));

	public static final BlockElement paragraph1 = new BlockElement("Paragraphe1",
			By.xpath("//h2[@class='text-align-center']"));

	public static final ButtonElement enSavoirPlus = new ButtonElement("EN SAVOIR PLUS",
			By.xpath("//span[text()='En savoir plus !']"));


	public static final BlockElement titleNosOffres = new BlockElement("Nos offres",
			By.id("offers"));


	public static final BlockElement imageNeoConseil = new BlockElement("image de neo conseil",
			By.xpath("//img[@src='https://neolians.com/wp-content/uploads/2022/01/neo.conseil-1.png']"));

	public static final BlockElement titleNeoConseil = new BlockElement("neo.conseil",
			By.xpath("//span[text()='conseil']"));

	public static final BlockElement descriptionNeoConseil = new BlockElement("un accompagnement dans la mise en place ...",
			By.xpath("//p[text()= 'Un accompagnement dans la mise en place, l’évolution et la performance de vos processus de test dans une optique d’amélioration continue et d’optimisation de coûts.']"));
	public static final ButtonElement ensavoirPlusNeoConseil = new ButtonElement("En savoir plus! neo conseil",
			By.xpath("//div[@data-id='1e6ee57']//child::span//span"));


	public static final BlockElement imageNeoServices = new BlockElement("image de neo services",
			By.xpath("//img[@src='https://neolians.com/wp-content/uploads/2022/01/handshake.png']"));

	public static final BlockElement titleNeoServices = new BlockElement("neo.services",
			By.xpath("//span[text()='services']"));

	public static final BlockElement descriptionNeoServices = new BlockElement("un accompagnement dans les domaines du test ...",
			By.xpath("//p[text()='Un accompagnement dans les domaines du Test de logiciels, que vos applications soient développées en architecture traditionnelle, en mode web ou adaptés à tout support mobile.']"));

	public static final ButtonElement ensavoirPlusNeoServices = new ButtonElement("En savoir plus! neo service",
			By.xpath("//div[@data-id='0b48f9a']//child::span//span"));


	public static final BlockElement imageNeoAcademy = new BlockElement("image de neo academy",
			By.xpath("//img[@src='https://neolians.com/wp-content/uploads/2022/01/neo.academy.png']"));

	public static final BlockElement titleNeoAcademy = new BlockElement("neo.academy",
			By.xpath("//span[text()='academy']"));

	public static final BlockElement descriptionNeoAcademy = new BlockElement("Un accompagnement dans la formation de vos équipes aux certifications ISTQB",
			By.xpath("//p[text()='Un accompagnement dans la formation de vos équipes aux certifications ISTQB niveau Fondation et Test Manager. Formation ouverte aux professionnels et aux particuliers.']"));


	public static final ButtonElement ensavoirPlusNeoAcademy = new ButtonElement("En savoir plus! neo academy",
			By.xpath("//div[@data-id='d08d78e']//child::span//span"));


	public static final BlockElement imageNeoTools = new BlockElement("image de neo tools",
			By.xpath("//img[@src='https://neolians.com/wp-content/uploads/2022/01/neo.academy.png']"));


	public static final BlockElement titleNeoTools = new BlockElement("neo.tools",
			By.xpath("//span[text()='tools']"));

	public static final BlockElement descriptionNeoTools = new BlockElement("Un outil vous permettant de maîtriser la qualité de vos migrations...",
			By.xpath("//p[text()='Un outil vous permettant de maîtriser la qualité de vos migrations. Il s’occupe pour vous de tester les régressions à la suite de l’installation d’une nouvelle application ou version.']"));

	public static final ButtonElement ensavoirPlusNeoTools = new ButtonElement("En savoir plus! neo academy",
			By.xpath("//div[@data-id='0d54fbc']//child::span//span"));


	public static final BlockElement paragraphe2 = new BlockElement("paragraphe 2",
			By.xpath("//div[@class='elementor-testimonial-content']"));


	public static final BlockElement titleNosDernierePublications = new BlockElement("Nos dernière publications",
			By.xpath("//div[@data-id='f70bbd3']//h2"));

	public static final BlockElement titleNosPartenaires = new BlockElement("Nos partenaires",
			By.xpath("//h2[text()='Nos partenaires']"));


	// à ajouté : contenus de nos partenaire

	public static final BlockElement partenaires = new BlockElement("partenaires",
			By.xpath("//div[@data-id='9c82d93']"));


	public static final BlockElement titleNosClient = new BlockElement("Nos clients",
			By.xpath("//h2[text()='Nos clients']"));

	public static final BlockElement Client1 = new BlockElement("Sopra group",
			By.xpath("//img[@src='https://neolians.com/wp-content/uploads/2022/01/sopra.jpg']"));

	public static final BlockElement Client2 = new BlockElement("SAP",
			By.xpath("//img[@src='https://neolians.com/wp-content/uploads/2022/01/neolians-reference-sap.jpg']"));

	public static final BlockElement Client3 = new BlockElement("novapost",
			By.xpath("//img[@src='https://neolians.com/wp-content/uploads/2022/01/neolians-reference-noapost.jpg']"));


	public static final BlockElement Client4 = new BlockElement("Limentik",
			By.xpath("//div[@data-id='d36b859']//child::a[4]"));

	public static final BlockElement Client5 = new BlockElement("BusinessInvestigation",
			By.xpath("//div[@data-id='d36b859']//child::a[5]"));

	public static final BlockElement Client6 = new BlockElement("KPMG",
			By.xpath("//div[@data-id='d36b859']//child::a[6]"));


	public static final BlockElement Client7 = new BlockElement("enablon",
			By.xpath("//div[@data-id='d36b859']//child::a[7]"));

}




