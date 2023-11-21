package com.neolians.website.functions.pages;

import com.neolians.website.repository.pages.PageHome;
import com.neolians.website.repository.pages.PageNosPartenaires;
import com.neolians.website.repository.pages.PageNousRejoindre;

public class NousRejoindre {

    private NousRejoindre() {
        throw new IllegalStateException("Utility class");
    }


    /**
     * Verifie page Nos Partenaire
     */


    public static void verifiePageNosRejoindre(){


//Verifier la présence  du paragraphe

        PageNousRejoindre.paragraphe.assertPresent();


//Verifier la présence et le contenu du paragraphe 1

        PageNousRejoindre.paragraphe1.assertPresent();
        String contenuParagraphe1 =PageNousRejoindre.paragraphe1.getText();
        PageNousRejoindre.paragraphe1.assertEquals("Nous attachons une grande importance à votre carrière et à l’évolution de vos compétences quelque-soit la voie choisie, professionnalisation ou management."
                ,contenuParagraphe1);




//Verifier la présence et le contenu du paragraphe 2

     PageNousRejoindre.paragraphe2.assertPresent();
        String contenuParagraphe2 =PageNousRejoindre.paragraphe2.getText();
        PageNousRejoindre.paragraphe2.assertEquals("Tout au long de votre carrière vous pourrez suivre des formations selon votre parcours, techniques, managériales ou comportementales."
                ,contenuParagraphe2);



//Verifier la présence et le contenu du paragraphe 3

     PageNousRejoindre.paragraphe3.assertPresent();
        String contenuParagraphe3 =PageNousRejoindre.paragraphe3.getText();
        PageNousRejoindre.paragraphe3.assertEquals("neolians recrute: Intégrez nos équipes !"
                ,contenuParagraphe3);




//Verifier la présence et le contenu du paragraphe 4

    PageNousRejoindre.paragraphe4.assertPresent();
        String contenuParagraphe4 =PageNousRejoindre.paragraphe4.getText();
        PageNousRejoindre.paragraphe4.assertEquals("Envoyez-nous votre CV, accompagné d’une lettre de motivation à emploi@neolians.com nous l’étudierons avec attention ! "
                ,contenuParagraphe4);


//Verifier la présence de gif

    PageNousRejoindre.gif.assertPresent();









    }

    }
