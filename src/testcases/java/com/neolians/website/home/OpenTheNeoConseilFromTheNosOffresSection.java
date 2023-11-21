package com.neolians.website.home;

import com.neolians.common.utils.report.Report;
import com.neolians.website.functions.general.Errors;
import com.neolians.website.functions.general.Footer;
import com.neolians.website.functions.general.Menu;
import com.neolians.website.functions.pages.Home;
import com.neolians.website.functions.pages.NeoConseil;
import com.neolians.website.functions.pages.NeoTra;
import com.neolians.website.repository.general.PageMenu;
import com.neolians.website.repository.pages.PageNeoConseil;
import com.neolians.website.repository.pages.PageNeoTra;
import com.neolians.website.utils.report.NeoliansTestcase;
import io.github.artsok.RepeatedIfExceptionsTest;

public class OpenTheNeoConseilFromTheNosOffresSection extends NeoliansTestcase {
    @RepeatedIfExceptionsTest(repeats = 1, suspend = 5000L, name = "Check Neo.conseil Page")
    void checkNeoConseilPage() {

        Report.newStep(1, "Open Home Page");
        Home.openPage();

        Report.newStep(2, "Verify Neo conseil Page From Nos offres");
        Home.NeoConseilfromNosOffres();

        Report.newStep(3, "Verify content Neo conseil page");
        NeoConseil.openPageNeoConseildepuisnosoffres();

        Report.newStep(4, "Verifie Menu");
        Menu.verifieMenu();

        Report.newStep(5, "Check No error");
        Errors.checkNoErrors();

        Report.newStep(6, "Verifie Footer");
        Footer.verifieFooter();
    }
}
