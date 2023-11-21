package com.neolians.website.functions.pages;

import com.neolians.website.repository.pages.PageSupport;

public class Support {
    private Support() {
        throw new IllegalStateException("Utility class");
    }
    // Ouvrir la sous section "neo.support" depuis la section "Nos-Offres"

    public static void VerifyPageSupport() {
        PageSupport.paragraphe1.assertPresent();
        PageSupport.img01.assertPresent();
        PageSupport.paragraphe2.assertPresent();
        PageSupport.img02.assertPresent();
        PageSupport.paragraphe3.assertPresent();


    }






}
