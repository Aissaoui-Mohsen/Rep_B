package com.neolians.website.functions.pages;

import com.neolians.website.repository.pages.PageContact;
import com.neolians.website.repository.pages.PageNeoConseil;
import org.junit.Assert;

public class Contact {
    private Contact() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Ouvre la page Contact
     */

    public static void VerifyPageContact() {

        PageContact.phrase1.assertPresent();
        PageContact.phrase2.assertPresent();
        PageContact.cadre1.assertPresent();
        PageContact.cadre2.assertPresent();
        PageContact.champ1.assertPresent();
        PageContact.champ2.assertPresent();
        PageContact.champ3.assertPresent();
        PageContact.champ4.assertPresent();
        PageContact.sendMessage.assertPresent();

    }

    public static void remplirleformulairedecontactcomplet(String user1, String email, String sujet, String comment) {

        PageContact.champ1.sendKeys(user1);
        PageContact.champ2.sendKeys(email);
        PageContact.champ3.sendKeys(sujet);
        PageContact.champ4.sendKeys(comment);
        PageContact.sendMessage.click();
        PageContact.ConfirmationMessage.assertPresent();
        Assert.assertEquals("message de confirmation", PageContact.ConfirmationMessage.getText(), "Thanks for contacting us! We will be in touch with you shortly.");


    }

    public static void remplirleformulaireincomplet(String user1, String email, String sujet, String comment) {


        PageContact.url.openUrl();
        PageContact.champ1.sendKeys();
        PageContact.champ2.sendKeys(email);
        PageContact.champ3.sendKeys();
        PageContact.champ4.sendKeys(comment);
        PageContact.sendMessage.click();
        PageContact.ObligationText.assertPresent();

    }

}