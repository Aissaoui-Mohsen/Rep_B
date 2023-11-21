package com.neolians.website.functions.pages;

import com.neolians.website.repository.pages.PageNeoExternalisationFromNosOffres;
import com.neolians.website.repository.pages.PageNeoMobile;
import com.neolians.website.repository.pages.PageNeoTools;

public class NeoTools {

    private NeoTools() { throw new IllegalStateException("Utility class");}

    public static void checkNeoToolsPage(){

        /**
         * Verifier le paragraphe 1
         */
        PageNeoTools.paragraphe1.assertPresent();
        PageNeoTools.contenu1paragraphe1.assertPresent();
        String contnu1P1 = PageNeoTools.contenu1paragraphe1.getText();
        PageNeoTools.contenu1paragraphe1.assertEquals("Un grand nombre de nos clients, en particulier les intégrateurs et les éditeurs, font face à des charges importantes de test de recette (par exemple vérification de la conformité de fiches de paie après migration )"
                ,contnu1P1);

        /**
         * Verifier l'image 1
         */
        PageNeoTools.firstGif.assertPresent();




        /**
         * Verifier le cadre horizontal
         */
        PageNeoTools.horizontalFrame.assertPresent();


        /**
         * Verifier le premier cadre horizontal
         */

        PageNeoTools.horizontalFrame1.assertPresent();

        PageNeoTools.horizontalFrame.assertPresent();

        //String contenuFrame1 = PageNeoTools.horizontalFrame1.getText();

        //PageNeoTools.contenuhorizontalFrame1.assertEquals("15 ans",contenuFrame1);


        /**
         * Verifier le deuxième cadre horizontal
         */
        PageNeoTools.horizontalFrame2.assertPresent();

        PageNeoTools.contenuhorizontalFrame2.assertPresent();

        //String contenuFrame2 = PageNeoTools.contenuhorizontalFrame2.getText();

        //PageNeoTools.contenuhorizontalFrame2.assertEquals("20 ans",contenuFrame2);


        /**
         * Verifier le troisième cadre horizontal
         */
        PageNeoTools.horizontalFrame3.assertPresent();

        PageNeoTools.contenuhorizontalFrame3.assertPresent();

        //String contenuFrame3 = PageNeoTools.contenuhorizontalFrame3.getText();

        //PageNeoTools.contenuhorizontalFrame3.assertEquals("130 projets",contenuFrame3);


        /**
         * Verifier le quatrième cadre horizontal
         */

        PageNeoTools.horizontalFrame4.assertPresent();

        PageNeoTools.contenuhorizontalFrame4.assertPresent();

        //String contenuFrame4 = PageNeoTools.contenuhorizontalFrame4.getText();

        //PageNeoTools.contenuhorizontalFrame4.assertEquals("98,7%",contenuFrame4);


        /**
         * Verifier le paragraphe 2
         */
        PageNeoTools.paragraphe2.assertPresent();

        PageNeoTools.contenu1paragraphe2.assertPresent();

        //String contenu1P2 = PageNeoTools.contenu1paragraphe2.getText();
        //PageNeoTools.contenu1paragraphe2.assertEquals("Le gestionnaire de document vous permet d’importer la liste des documents que vous souhaitez utiliser lors de vos campagnes de test.",contenu1P2);


        /**
         * Verifier l'image 1
         */
        PageNeoTools.secondGif.assertPresent();


    }





}
