package com.neolians.website.functions.pages;

import com.neolians.website.repository.pages.PageNeoMobile;

public class NeoMobileFromNosOffres {

    private NeoMobileFromNosOffres() { throw new IllegalStateException("Utility class");}


    public static void verifiePageNeoMobile(){


       PageNeoMobile.paragraphe1.assertPresent();

       PageNeoMobile.contenu1paragraphe1.assertPresent();
       String contenu1P1 = PageNeoMobile.contenu1paragraphe1.getText();
       PageNeoMobile.contenu1paragraphe1.assertEquals("Nous vous accompagnons dans votre activité de développement d’applications mobiles à travers :",contenu1P1);



       PageNeoMobile.contenu2paragraphe1.assertPresent();
       String contenu2p1 = PageNeoMobile.contenu2paragraphe1.getText();
       PageNeoMobile.contenu2paragraphe1.assertEquals("L’élaboration de votre stratégie de test d’application mobile et sa mise œuvre,",contenu2p1);


       PageNeoMobile.firstGif.assertPresent();


       PageNeoMobile.horizontalFrame.assertPresent();

       PageNeoMobile.horizontalFrame1.assertPresent();
       PageNeoMobile.contenuhorizontalFrame1.assertPresent();


        PageNeoMobile.horizontalFrame2.assertPresent();
        PageNeoMobile.contenuhorizontalFrame2.assertPresent();



        PageNeoMobile.horizontalFrame3.assertPresent();
        PageNeoMobile.contenuhorizontalFrame3.assertPresent();




        PageNeoMobile.horizontalFrame4.assertPresent();
        PageNeoMobile.contenuhorizontalFrame4.assertPresent();




        PageNeoMobile.paragraphe2.assertPresent();

        PageNeoMobile.contenu1paragraphe2.assertPresent();
        String contenu1P2 = PageNeoMobile.contenu1paragraphe2.getText();
        PageNeoMobile.contenu1paragraphe2.assertEquals("Nos prestations de test dédiées aux applications mobiles :"
                ,contenu1P2);



        PageNeoMobile.contenu2paragraphe2.assertPresent();
        String contenu2p2 = PageNeoMobile.contenu2paragraphe2.getText();
        PageNeoMobile.contenu2paragraphe2.assertEquals("Cloud Test :"
                ,contenu2p2);


        PageNeoMobile.secondGif.assertPresent();

    }



}
