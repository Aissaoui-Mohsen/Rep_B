package com.neolians.website.functions.pages;

import com.neolians.website.repository.pages.PageAcademy;

public class Academy {
    private Academy() {
        throw new IllegalStateException("Utility class");
    }
    // Ouvrir la sous section "neo.academy" depuis la section "Nos-Offres"

    public static void VerifyPageAcademy() {
        PageAcademy.paragraphe1.assertPresent();
        PageAcademy.paragraphe2.assertPresent();
        PageAcademy.paragraphe3.assertPresent();
        PageAcademy.img1.assertPresent();
        PageAcademy.img2.assertPresent();

    }

}
