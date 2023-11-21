package com.neolians.website.home;

import com.neolians.common.utils.report.Report;
import com.neolians.website.functions.general.Errors;
import com.neolians.website.functions.general.Footer;
import com.neolians.website.functions.general.Menu;
import com.neolians.website.functions.pages.Academy;
import com.neolians.website.functions.pages.Home;
import com.neolians.website.utils.report.NeoliansTestcase;
import io.github.artsok.RepeatedIfExceptionsTest;

public class DisplaySubsectionAcademyViaSectionOffres extends NeoliansTestcase {
    @RepeatedIfExceptionsTest(repeats=1, suspend = 5000L, name = "Check Academy Page Via Section Offres")
    void CheckAcademyViaOffres(){

        Report.newStep(1, "Open Home Page");
        Home.openPage();

        Report.newStep(2, "Verify Menu");
        Menu.verifieMenu();

        Report.newStep(3, "Verify Erreur");
        Errors.checkNoErrors();

        Report.newStep(4, "Open Academy Page  via Section Offres");
        Home.openPageNeoAcademyfromNosOffres();

        Report.newStep(5, "Verify AcademyPage  via Section Offres");
        Academy.VerifyPageAcademy();

        Report.newStep(6, "Verify Footer");
        Footer.verifieFooter();


    }




}
