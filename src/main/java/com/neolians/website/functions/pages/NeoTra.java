package com.neolians.website.functions.pages;

import com.neolians.website.repository.general.PageMenu;
import com.neolians.website.repository.pages.PageNeoConseil;
import com.neolians.website.repository.pages.PageNeoMobile;
import com.neolians.website.repository.pages.PageNeoServices;
import com.neolians.website.repository.pages.PageNeoTra;

public class NeoTra {
    private NeoTra() {
        throw new IllegalStateException("Utility class");
    }


    /**
     * Ouvre la page Neo Tra depuis Nos offres
     */

    public static void openPageNeotra() {

        PageNeoTra.paragraphe1.assertPresent();

        PageNeoTra.Colonnnehorizantale.assertPresent();

        PageNeoTra.lenombredecontenu1delacolonne.assertPresent();


        PageNeoTra.Contenu1delacolonne.assertPresent();

        PageNeoTra.lenombredecontenu2delacolonne.assertPresent();


        PageNeoTra.Contenu2delacolonne.assertPresent();

        PageNeoTra.lenombredecontenu3delacolonne.assertPresent();

        PageNeoTra.Contenu3delacolonne.assertPresent();


        PageNeoTra.lenombredecontenu4delacolonne.assertPresent();

        PageNeoTra.Contenu4delacolonne.assertPresent();


        PageNeoTra.paragraphe3.assertPresent();

        PageNeoTra.image1.assertPresent();
        PageNeoTra.image2.assertPresent();

    }
}
