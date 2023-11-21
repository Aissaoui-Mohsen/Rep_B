package com.neolians.website.home;

import com.neolians.common.utils.report.Report;
import com.neolians.website.functions.general.Errors;
import com.neolians.website.functions.general.Footer;
import com.neolians.website.functions.general.Menu;
import com.neolians.website.functions.pages.Home;
import com.neolians.website.functions.pages.LinkedlnFromFooterBar;
import com.neolians.website.repository.general.PageFooter;
import com.neolians.website.repository.general.PageMenu;
import com.neolians.website.repository.pages.PageLinkedlnFromFooterBar;
import com.neolians.website.utils.report.NeoliansTestcase;
import io.github.artsok.RepeatedIfExceptionsTest;

public class AcessTheNeoliansLinkedlnAccountFromTheFooterBarAtTheBottomOfPage extends NeoliansTestcase {
    @RepeatedIfExceptionsTest(repeats = 1, suspend = 5000L, name = "Check Linkdeln Page From Footer Bar")
    void checkLinkdelnPageFromFooterBar() {

        Report.newStep(1, "Open Home Page");
        Home.openPage();

        Report.newStep(2, "Verifie Menu");
        Menu.verifieMenu();

        Report.newStep(3, "Check No error");
        Errors.checkNoErrors();

        Report.newStep(4, "Verifie le bouton linkedln");
        Home.openLinkedlndfromfooterbar();

        Report.newStep(5, "Verifie Footer");
        Footer.verifieFooter();

        Report.newStep(6, "Verify content linkedln from footer bar page");
        LinkedlnFromFooterBar.openPagelinkedlndepuispieddepage();


    }
}
