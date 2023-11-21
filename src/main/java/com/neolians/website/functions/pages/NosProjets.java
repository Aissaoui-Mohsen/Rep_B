package com.neolians.website.functions.pages;

import com.neolians.website.repository.general.PageMenu;
import com.neolians.website.repository.pages.PageNosProjets;

public class NosProjets {
    private NosProjets() {throw new IllegalStateException("Utility class");}

    /**
     * Ouvre la page Nos projets
     */

    public static void openPageNosprojets() {

        PageNosProjets.url.openUrl();
        PageNosProjets.enablon.click();
        PageNosProjets.KPMG.click();
        PageNosProjets.Sopra.click();
        PageNosProjets.Limonetik.click();
        PageNosProjets.PackSolutions.click();
        PageNosProjets.SAP.click();
        PageNosProjets.Novapost.click();
        PageNosProjets.BusinessInvestigation.click();


        PageNosProjets.paragraphedeenablon.assertPresent();
        PageNosProjets.paragraphedekpmg.assertPresent();
        PageNosProjets.paragraphedesopra.assertPresent();
        PageNosProjets.paragraphedelimonetik.assertPresent();
        PageNosProjets.paragraphedepacksolution.assertPresent();
        PageNosProjets.paragraphedesap.assertPresent();
        PageNosProjets.paragraphedenovapost.assertPresent();
        PageNosProjets.paragraphedebusinessinvestigation.assertPresent();
    }

}
