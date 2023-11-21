package com.neolians.website.home;

import com.neolians.common.utils.report.Report;
import com.neolians.website.functions.general.Errors;
import com.neolians.website.functions.general.Footer;
import com.neolians.website.functions.general.Menu;
import com.neolians.website.functions.pages.Home;
import com.neolians.website.functions.pages.NeoServices;
import com.neolians.website.functions.pages.NeoTra;
import com.neolians.website.utils.report.NeoliansTestcase;
import io.github.artsok.RepeatedIfExceptionsTest;

public class AccessTheNeoTraSubSubSectionFromTheNeoServicesSubSectionPage extends NeoliansTestcase {

    @RepeatedIfExceptionsTest(repeats = 1, suspend = 5000L, name = "Check Neo TRA Page From The Neo Services Sub Section Page")
    void openneotradepuislasoussectionneoservices  () {

        Report.newStep(1, "Open Home Page");
        Home.openPage();

        Report.newStep(2, "Open Page Neo Services");
        Home.NeoServicesfromNosOffres();

        Report.newStep(3, "Open Neo TRA Page From Neo Services");
        NeoServices.openneotradepuislasoussectionneoservices();

        Report.newStep(4, "Verify content Neo TRA page");
        NeoTra.openPageNeotra();

        Report.newStep(5, "Verifie Menu");
        Menu.verifieMenu();

        Report.newStep(6, "Check No error");
        Errors.checkNoErrors();

        Report.newStep(7, "Verifie Footer");
        Footer.verifieFooter();

    }
}
