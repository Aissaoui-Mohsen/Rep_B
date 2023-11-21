package com.neolians.website.repository.pages;

import com.neolians.common.utils.selenium.elements.*;
import org.openqa.selenium.By;

public class PageContact {
    private PageContact() {
        throw new IllegalStateException("Utility class");
    }


    public static final UrlElement url = new UrlElement("Contact", "/contact-2/");
    // identifier la première phrase
    public static final BlockElement phrase1 = new BlockElement("phrase1", By.xpath("//div[@data-id='6702aa0d']"));

    // identifier la deuxième phrase
    public static final BlockElement phrase2 = new BlockElement("phrase2",By.xpath("//div[@data-id='752e2ba5']"));

    // identifier la prèmière cadre
    public static final BlockElement cadre1 = new BlockElement("cadre1",By.xpath("//div[@data-id='22c3311a']"));

    // identifier la deuxième cadre
    public static final BlockElement cadre2 = new BlockElement("cadre2",By.xpath("//div[@data-id='d3add2b']"));

    // identifier la première champ
    public static final InputTextElement champ1 = new InputTextElement("Nom Complet",By.id("wpforms-22-field_0"));
    // identifier la deuxième champ
    public static final InputTextElement champ2 = new InputTextElement("Email",By.id("wpforms-22-field_1"));
   // identifier la troisième champ
    public static final InputTextElement champ3 = new InputTextElement("Sujet",By.id("wpforms-22-field_3"));
   // identifier la quatrième champ
    public static final InputTextElement champ4 = new InputTextElement("Comment or Message",By.id("wpforms-22-field_2"));
    //identifier le bouton "send Message"
    public static final ButtonElement sendMessage=new ButtonElement("Send Message",By.id("wpforms-submit-22"));

    //Message de confirmation
    public static final TextAreaElement ConfirmationMessage = new TextAreaElement("Confirm Message",By.id("wpforms-confirmation-22"));

    public static final TextAreaElement ObligationText = new TextAreaElement("Ce champ est nécessaire",By.id("wpforms-22-field_0-error"));

}
