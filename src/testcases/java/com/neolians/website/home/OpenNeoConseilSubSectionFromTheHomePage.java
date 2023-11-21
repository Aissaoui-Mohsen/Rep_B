package com.neolians.website.home;

import com.neolians.common.utils.report.Report;
import com.neolians.website.functions.general.Errors;
import com.neolians.website.functions.general.Footer;
import com.neolians.website.functions.general.Menu;
import com.neolians.website.functions.pages.Home;
import com.neolians.website.functions.pages.NeoConseil;
import com.neolians.website.functions.pages.NeoServices;
import com.neolians.website.repository.pages.PageNeoConseil;
import com.neolians.website.repository.pages.PageNeoServices;
import com.neolians.website.utils.report.NeoliansTestcase;
import io.github.artsok.RepeatedIfExceptionsTest;

public class OpenNeoConseilSubSectionFromTheHomePage extends NeoliansTestcase {
    @RepeatedIfExceptionsTest(repeats = 1, suspend = 5000L, name = "Check Neo Conseil From En savoir Plus")
    void checkNeoConseilviaHomePage() {

        Report.newStep(1, "Open Home Page");
        Home.openPage();

        Report.newStep(2, "La page Neo.conseil");
        Home.openNeoConseilviaPageHome();

        Report.newStep(3, "Verifie le contenu de la page");
        NeoConseil.openPageNeoConseildepuisnosoffres();

        Report.newStep(4, "Verifie Menu");
        Menu.verifieMenu();

        Report.newStep(5, "Check No error");
        Errors.checkNoErrors();

        Report.newStep(6, "Verifie Footer");
        Footer.verifieFooter();



    }
}
