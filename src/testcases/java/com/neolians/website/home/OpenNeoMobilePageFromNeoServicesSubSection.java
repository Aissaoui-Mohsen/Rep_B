package com.neolians.website.home;

import com.neolians.common.utils.report.Report;
import com.neolians.website.functions.general.Errors;
import com.neolians.website.functions.general.Footer;
import com.neolians.website.functions.general.Menu;
import com.neolians.website.functions.pages.Home;
import com.neolians.website.functions.pages.NeoMobileFromNosOffres;
import com.neolians.website.functions.pages.NeoServices;
import com.neolians.website.utils.report.NeoliansTestcase;
import io.github.artsok.RepeatedIfExceptionsTest;

public class OpenNeoMobilePageFromNeoServicesSubSection extends NeoliansTestcase {
    @RepeatedIfExceptionsTest(repeats = 1, suspend = 5000L, name = "Check neo.mobile Page From Neo Services")
    void checkNeoMobilePageFromNeoServices() {

        Report.newStep(1, "Open Home Page");
        Home.openPage();

        Report.newStep(2, "Open NeoServices Page");
        Home.NeoServicesfromNosOffres();

        Report.newStep(3, "Open neo.mobile Page from Neoservices");
        NeoServices.openPageNeoMobileFromNeoServices();

        Report.newStep(4, "Verify Menu");
        Menu.verifieMenu();

        Report.newStep(5, "Check neo.mobile Page");
        NeoMobileFromNosOffres.verifiePageNeoMobile();

        Report.newStep(6, "Verify Footer");
        Footer.verifieFooter();

        Report.newStep(7, "Check No error");
        Errors.checkNoErrors();


    }
}