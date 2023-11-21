package com.neolians.website.home;

import com.neolians.common.utils.report.Report;
import com.neolians.website.functions.general.Errors;
import com.neolians.website.functions.general.Footer;
import com.neolians.website.functions.general.Menu;
import com.neolians.website.functions.pages.Academy;
import com.neolians.website.functions.pages.Editeur;
import com.neolians.website.functions.pages.Home;
import com.neolians.website.functions.pages.NeoServices;
import com.neolians.website.repository.pages.PageNeoServices;
import com.neolians.website.utils.report.NeoliansTestcase;
import io.github.artsok.RepeatedIfExceptionsTest;

public class DisplayPageNeoEditeurViaPageNeoServices extends NeoliansTestcase {

    @RepeatedIfExceptionsTest(repeats = 1, suspend = 5000L, name = "Check NeoEditeur page via neo.services Page")

    void CheckNeoEditeurPageViaNeoServicesPage()
    {
        Report.newStep(1, "Open Home Page");
        Home.openPage();

        Report.newStep(2, "Verify Menu");
        Menu.verifieMenu();

        Report.newStep(3, "Verify Erreur");
        Errors.checkNoErrors();

        Report.newStep(4, "open neo.service page via nos offres");
        Home.NeoServicesfromNosOffres();

        Report.newStep(5, "open neo.editeur page via neo.services page");
        NeoServices.openPageNeoEditeurViaPageNeoService();

        Report.newStep(6, "Verify neo.editeur page");
        Editeur.VerifyPageEditeur();

        Report.newStep(7, "Verify Footer");
        Footer.verifieFooter();





    }







}
