package com.neolians.website.functions.pages;

import com.neolians.website.repository.general.PageFooter;
import com.neolians.website.repository.general.PageMenu;
import com.neolians.website.repository.pages.PageHome;
import com.neolians.website.repository.pages.PageNeoServices;

public class NeoServices {

    private NeoServices() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Ouvre la page Neo Services
     */

    public static void openPageNeoServicedepuishome() {

        PageNeoServices.url.openUrl();
        PageMenu.neoliansLogo.assertPresent();
        PageNeoServices.paragraphe1.assertPresent("Vérifier la présence de paragraphe1");
        PageNeoServices.paragraphe2.assertPresent();
        PageNeoServices.imageneoediteur.assertPresent();
        PageNeoServices.neoediteur.assertPresent();
        PageNeoServices.imageneoexternalisation.assertPresent();
        PageNeoServices.neoexternalisaton.assertPresent();
        PageNeoServices.imageneomobile.assertPresent();
        PageNeoServices.neomobile.assertPresent();
        PageNeoServices.imageneotra.assertPresent();
        PageNeoServices.neoTRA.assertPresent();
        PageNeoServices.image1.assertPresent();
        PageNeoServices.image2.assertPresent();
        PageNeoServices.paragraphe3.assertPresent();
        PageNeoServices.imageneoediteur.assertPresent();
        PageNeoServices.imageneoexternalisation.assertPresent();
        PageNeoServices.imageneomobile.assertPresent();
        PageNeoServices.imageneotra.assertPresent();
        PageNeoServices.image1.assertPresent();
        PageNeoServices.image2.assertPresent();

    }
                //  Mohsen  12/02/2022
    public static void openPageNeoEditeurViaPageNeoService()
    {

      PageNeoServices.neoediteur.click();

    }

    public static void openNeoExternalisationFromNeoServices() {

        PageNeoServices.neoexternalisaton.click();
    }

    public static void openPageNeoMobileFromNeoServices() {

        PageNeoServices.neomobile.click();


    }

    public static void openneotradepuislasoussectionneoservices(){
      
        PageNeoServices.neoTRA.click();
    }
}
