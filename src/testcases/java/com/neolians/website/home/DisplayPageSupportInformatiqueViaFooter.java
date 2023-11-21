package com.neolians.website.home;

import com.neolians.common.utils.report.Report;
import com.neolians.website.functions.general.Errors;
import com.neolians.website.functions.general.Footer;
import com.neolians.website.functions.general.Menu;
import com.neolians.website.functions.pages.Home;
import com.neolians.website.functions.pages.Integration;
import com.neolians.website.functions.pages.Support;
import com.neolians.website.utils.report.NeoliansTestcase;
import io.github.artsok.RepeatedIfExceptionsTest;

public class DisplayPageSupportInformatiqueViaFooter extends NeoliansTestcase {
    @RepeatedIfExceptionsTest(repeats = 1, suspend = 5000L, name = "Check SupportInformatique Page  via Footer")
    void CheckSupportInformatiqueViaFooter()
    {

        Report.newStep(1, "Open Home Page");
        Home.openPage();

        Report.newStep(2, "Verify Menu");
        Menu.verifieMenu();

        Report.newStep(3, "Verify Erreur");
        Errors.checkNoErrors();

        Report.newStep(4, "Open SupportInformatique Page via Footer");
        Home.openPageSupportInformatiqueViaFooter();

        Report.newStep(5, "Verify SupportInformatiquePage");
        Support.VerifyPageSupport();

        Report.newStep(6, "Verify Footer");
        Footer.verifieFooter();







    }












}
