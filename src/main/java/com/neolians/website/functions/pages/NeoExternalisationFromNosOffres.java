package com.neolians.website.functions.pages;

import com.neolians.website.repository.pages.PageNeoExternalisationFromNosOffres;

public class NeoExternalisationFromNosOffres {

    private NeoExternalisationFromNosOffres() { throw new IllegalStateException("Utility class");}

    public static void checkNeoExternalisationFromNosOfrres(){


        /**
         * Verifier le paragraphe 1
         */
        PageNeoExternalisationFromNosOffres.paragraphe1.assertPresent();

        PageNeoExternalisationFromNosOffres.contenu1paragraphe1.assertPresent();

        String contenu1P1 = PageNeoExternalisationFromNosOffres.contenu1paragraphe1.getText();
        PageNeoExternalisationFromNosOffres.contenu1paragraphe1.assertEquals("Dans l’exercice de vos métiers, vous devez souvent faire face à des objectifs contradictoires : innover tout en réduisant vos coûts, simplifier vos processus métier tout en adoptant les technologies les plus récentes, étendre vos activités tout en assurant des liens plus étroits avec vos clients, entre autres."
                ,contenu1P1);


        /**
         * Verifier l'image 1
         */
        PageNeoExternalisationFromNosOffres.imgGif1.assertPresent();


        /**
         * Verifier le cadre horizontal
         */
        PageNeoExternalisationFromNosOffres.horizontalFrame.assertPresent();


        /**
         * Verifier le premier cadre horizontal
         */
        PageNeoExternalisationFromNosOffres.horizontalFrame1.assertPresent();

        PageNeoExternalisationFromNosOffres.contenuhorizontalFrame1.assertPresent();

        //String contenuFrame1 = PageNeoExternalisationFromNosOffres.contenuhorizontalFrame1.getText();

        //PageNeoExternalisationFromNosOffres.contenuhorizontalFrame1.assertEquals("15 ans",contenuFrame1);


        /**
         * Verifier le deuxième cadre horizontal
         */
        PageNeoExternalisationFromNosOffres.horizontalFrame2.assertPresent();

        PageNeoExternalisationFromNosOffres.contenuhorizontalFrame2.assertPresent();

        //String contenuFrame2 = PageNeoExternalisationFromNosOffres.contenuhorizontalFrame2.getText();

       //PageNeoExternalisationFromNosOffres.contenuhorizontalFrame2.assertEquals("20 ans",contenuFrame2);


        /**
         * Verifier le troisième cadre horizontal
         */
        PageNeoExternalisationFromNosOffres.horizontalFrame3.assertPresent();

        PageNeoExternalisationFromNosOffres.contenuhorizontalFrame3.assertPresent();

        //String contenuFrame3 = PageNeoExternalisationFromNosOffres.contenuhorizontalFrame3.getText();

        //PageNeoExternalisationFromNosOffres.contenuhorizontalFrame3.assertEquals("130 projets",contenuFrame3);


        /**
         * Verifier le quatrième cadre horizontal
         */
        PageNeoExternalisationFromNosOffres.horizontalFrame4.assertPresent();

        PageNeoExternalisationFromNosOffres.contenuhorizontalFrame4.assertPresent();

        //String contenuFrame4 = PageNeoExternalisationFromNosOffres.contenuhorizontalFrame4.getText();

        //PageNeoExternalisationFromNosOffres.contenuhorizontalFrame4.assertEquals("98,7%",contenuFrame4);


        /**
         * Verifier le paragraphe 2
         */
        PageNeoExternalisationFromNosOffres.paragraphe2.assertPresent();

        PageNeoExternalisationFromNosOffres.contenu1paragraphe2.assertPresent();

        String contenu1P2 = PageNeoExternalisationFromNosOffres.contenu1paragraphe2.getText();
        PageNeoExternalisationFromNosOffres.contenu1paragraphe2.assertEquals("Environ un million de projets informatiques sont lancés chaque année. Mais seulement 16 % atteignent leurs objectifs en termes de couverture fonctionnelle, de délais et de budget, et 37 % sont tout simplement arrêtés en cours de route.",contenu1P2);


        /**
         * Verifier l'image 1
         */
        PageNeoExternalisationFromNosOffres.imgGif2.assertPresent();



    }
}
