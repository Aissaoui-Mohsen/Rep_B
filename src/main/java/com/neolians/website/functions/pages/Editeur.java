package com.neolians.website.functions.pages;

import com.neolians.website.repository.pages.PageEditeur;


public class Editeur {



    public static void VerifyPageEditeur() {

        PageEditeur.paragraphe1.assertPresent();
        PageEditeur.paragraphe2.assertPresent();
        PageEditeur.paragraphe3.assertPresent();
        PageEditeur.img1.assertPresent();
        PageEditeur.img2.assertPresent();




    }



}
