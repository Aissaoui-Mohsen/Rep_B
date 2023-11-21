package com.neolians.website.home;

import com.neolians.common.utils.report.Report;
import com.neolians.website.functions.general.Errors;
import com.neolians.website.functions.general.Footer;
import com.neolians.website.functions.general.Menu;
import com.neolians.website.functions.pages.Home;
import com.neolians.website.functions.pages.NosProjets;
import com.neolians.website.repository.general.PageMenu;
import com.neolians.website.repository.pages.PageHome;
import com.neolians.website.repository.pages.PageNosProjets;
import com.neolians.website.utils.report.NeoliansTestcase;
import io.github.artsok.RepeatedIfExceptionsTest;

public class OpenNosprojetsSubSectionFromEntepriseSection extends NeoliansTestcase {
    @RepeatedIfExceptionsTest(repeats = 1, suspend = 5000L, name = "Check Nos projets Page")

    void checkNosprojetsPage(){

         Report.newStep(1, "Open Home Page");
        Home.openPage();

        Report.newStep(2, "Verifie Menu");
        Menu.verifieMenu();

        Report.newStep(3, "Verifie Footer");
        Footer.verifieFooter();

        Report.newStep(4, "Check No error");
        Errors.checkNoErrors();

        Report.newStep(5, "Check Nos Projets Page");
        NosProjets.openPageNosprojets();




    }
}
