package com.neolians.website.functions.pages;

import com.neolians.website.repository.pages.PagePresentation;

public class Presentation {
    private Presentation() {
        throw new IllegalStateException("Utility class");
    }
    // Ouvrir la sous section "Présentation" depuis la section "L'ENTREPRISE"
    public static void VerifyPagePresentation() {

        PagePresentation.paragraphe1.assertPresent();
        PagePresentation.paragraphe2.assertPresent();
        PagePresentation.paragraphe3.assertPresent();
        PagePresentation.paragraphe4.assertPresent();


    }
}
