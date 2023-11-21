package com.neolians.website.functions.pages;

import com.neolians.website.repository.general.PageMenu;
import com.neolians.website.repository.pages.PageImpactSocial;
import com.neolians.website.repository.pages.PagePresentation;

public class ImpactSocial {

    private ImpactSocial() {
        throw new IllegalStateException("Utility class");
    }
    // Ouvrir la sous section "Notre impact social" depuis la section "L'ENTREPRISE"
    public static void VerifySubsectionImpactSocial() {

        PageImpactSocial.paragraphe1.assertPresent();
        PageImpactSocial.paragraphe2.assertPresent();




    }
}




