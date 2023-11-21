package com.neolians.website.functions.pages;

import com.neolians.website.repository.general.PageMenu;
import com.neolians.website.repository.pages.PageNeoConseil;
import com.neolians.website.repository.pages.PageNeoTra;
import com.neolians.website.repository.pages.PageNosProjets;

public class NeoConseil {
    private NeoConseil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Ouvre la page Neo.conseil
     */

    public static void openPageNeoConseildepuisnosoffres() {


        PageNeoConseil.paragraphe1.assertPresent();
        PageNeoConseil.colonnehorizantale.assertPresent();

        PageNeoConseil.lenombredecontenu1delacolonne.assertPresent();

        PageNeoConseil.Contenu1delacolonne.assertPresent();
        String contenu1C1 = PageNeoConseil.Contenu1delacolonne.getText();
        PageNeoConseil.Contenu1delacolonne.assertEquals("d’expérience des centres de service nearshore et l’assistance sur site client",contenu1C1);


        PageNeoConseil.lenombredecontenu2delacolonne.assertPresent();

        PageNeoConseil.Contenu2delacolonne.assertPresent();
        String contenu1C2 = PageNeoConseil.Contenu2delacolonne.getText();
        PageNeoConseil.Contenu2delacolonne.assertEquals("de séniorité moyenne du management dans le test et le support",contenu1C2);

        PageNeoConseil.lenombredecontenu3delacolonne.assertPresent();

        PageNeoConseil.Contenu3delacolonne.assertPresent();
        String contenu1C3 = PageNeoConseil.Contenu3delacolonne.getText();
        PageNeoConseil.Contenu3delacolonne.assertEquals("pour près de 100 clients sur 5 continents",contenu1C3);

        PageNeoConseil.lenombredecontenu4delacolonne.assertPresent();

        PageNeoConseil.Contenu4delacolonne.assertPresent();
        String contenu1C4 = PageNeoConseil.Contenu4delacolonne.getText();
        PageNeoConseil.Contenu4delacolonne.assertEquals("de clients très satisfaits",contenu1C4);

        PageNeoConseil.paragraphe2.assertPresent();
        PageNeoConseil.image1.assertPresent();
        PageNeoConseil.image2.assertPresent();

    }
}
