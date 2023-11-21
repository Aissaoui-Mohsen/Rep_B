package com.neolians.website.functions.general;

import com.neolians.website.repository.general.PageMenu;

public class Menu {

    /**
     * Vérifie l'affichage du menu
     */
    public static void verifieMenu(){
        PageMenu.neoliansLogo.assertPresent();

        PageMenu.home.assertPresent();
        PageMenu.entreprise.assertPresent();
        PageMenu.nosOffres.assertPresent();
        PageMenu.NeoServicesButton.assertPresent();
        PageMenu.NeoTRAButton.assertPresent();
        PageMenu.nousRejoindre.assertPresent();
        PageMenu.contact.assertPresent();
        PageMenu.iconLinkedIn1.assertPresent();
        PageMenu.NosPartenairesButton.assertPresent();
        PageMenu.nosprojets.assertPresent();
    }
}
