package com.neolians.website.home;

import com.neolians.common.utils.report.Report;
import com.neolians.website.functions.general.Errors;
import com.neolians.website.functions.general.Footer;
import com.neolians.website.functions.general.Menu;
import com.neolians.website.functions.pages.Home;
import com.neolians.website.functions.pages.Presentation;
import com.neolians.website.utils.report.NeoliansTestcase;
import io.github.artsok.RepeatedIfExceptionsTest;

public class DisplaySubsectionPresentationViaSectionEntreprise extends NeoliansTestcase {

    @RepeatedIfExceptionsTest(repeats = 1, suspend = 5000L, name = "Check Presentation Page Via Section Entreprise")
    void CheckPresentationViaEntreprise()
    {


        Report.newStep(1, "Open Home Pge");
        Home.openPage();

        Report.newStep(2, "Verify Menu");
        Menu.verifieMenu();

        Report.newStep(3, "Verify Erreur");
        Errors.checkNoErrors();


        Report.newStep(4, "Open Presentation Page via Section Entreprise");
        Home.openPagePresentationfromlentreprise();

        Report.newStep(5, "Verify Presentation Page");
        Presentation.VerifyPagePresentation();

        Report.newStep(6, "Verify Footer");
        Footer.verifieFooter();








    }


}
