package com.neolians.website.functions.pages;

import com.neolians.website.repository.general.PageMenu;
import com.neolians.website.repository.pages.PageLinkedlnFromFooterBar;
import com.neolians.website.repository.pages.PageLinkedlnFromTopBar;

public class LinkedlnFromFooterBar {
    private LinkedlnFromFooterBar() {throw new IllegalStateException("Utility class");}

    /**
     * Ouvre la page Linkedln
     */

    public static void openPagelinkedlndepuispieddepage() {


        PageLinkedlnFromFooterBar.linkedlndepuislabarreenpieddepage.assertPresent();
    }
}
