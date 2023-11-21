package com.neolians.website.functions.pages;

import com.neolians.website.repository.pages.PageEditeur;
import com.neolians.website.repository.pages.PageIntegration;

public class Integration {private Integration() {
    throw new IllegalStateException("Utility class");}

    public static void VerifyPageIntegration() {

        PageIntegration.paragraphe1.assertPresent();
        PageIntegration.paragraphe2.assertPresent();
        PageIntegration.paragraphe3.assertPresent();
        PageIntegration.img1.assertPresent();
        PageIntegration.img2.assertPresent();






    }
}
