package com.neolians.website.home;

import com.neolians.common.utils.report.Report;
import com.neolians.website.functions.general.Errors;
import com.neolians.website.functions.general.Footer;
import com.neolians.website.functions.general.Menu;
import com.neolians.website.functions.pages.Home;
import com.neolians.website.functions.pages.LinkedlnFromTopBar;
import com.neolians.website.repository.general.PageFooter;
import com.neolians.website.repository.general.PageMenu;
import com.neolians.website.repository.pages.PageLinkedlnFromTopBar;
import com.neolians.website.utils.report.NeoliansTestcase;
import io.github.artsok.RepeatedIfExceptionsTest;

public class AccessTheNeoliansLinkedlnAccountFromTheBarAtTheBottomOfPage extends NeoliansTestcase {
    @RepeatedIfExceptionsTest(repeats = 1, suspend = 5000L, name = "Check Linkdeln Page From Top Bar ")
    void checkLinkdelnFromTopBarPage() {

        Report.newStep(1, "Open Home Page");
        Home.openPage();
        
        Report.newStep(2, "Verifie Menu");
        Menu.verifieMenu();

        Report.newStep(3, "Verifie Footer");
        Footer.verifieFooter();

        Report.newStep(4, "Check No error");
        Errors.checkNoErrors();

        Report.newStep(5, "Verifie le bouton linkedln en haut de page");
        Home.openLinkedlndepuislabarreenhautdepage();

        Report.newStep(6, "Verify content linkedln from Top bar page");
        LinkedlnFromTopBar.openPagelinkedlnfromtopbar();


    }
}
