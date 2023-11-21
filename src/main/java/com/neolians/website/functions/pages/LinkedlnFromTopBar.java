package com.neolians.website.functions.pages;

import com.neolians.website.repository.general.PageMenu;
import com.neolians.website.repository.pages.PageLinkedlnFromTopBar;

public class LinkedlnFromTopBar {
    private LinkedlnFromTopBar() {throw new IllegalStateException("Utility class");}

    /**
     * Ouvre la page Linkedln
     */

    public static void openPagelinkedlnfromtopbar() {

        PageLinkedlnFromTopBar.linkedlnneolians.assertPresent("la page de linkedln s'affiche");
    }
}
