package com.neolians.website.home;

import com.neolians.common.utils.report.Report;
import com.neolians.website.functions.general.Errors;
import com.neolians.website.functions.general.Footer;
import com.neolians.website.functions.general.Menu;
import com.neolians.website.functions.pages.Contact;
import com.neolians.website.functions.pages.Home;
import com.neolians.website.utils.report.NeoliansTestcase;
import io.github.artsok.RepeatedIfExceptionsTest;

public class FillInTheContactPage extends NeoliansTestcase {
    @RepeatedIfExceptionsTest(repeats = 1, suspend = 5000L, name = "Fill Contact Page")
    void FillInContactPage() {

        Report.newStep(1, "Open Home Page");
        Home.openPage();

        Report.newStep(2, "Open Contact Page");
        Home.openPageContact();

        Report.newStep(3, "Fill Form Of Contact Page");
        Contact.remplirleformulairedecontactcomplet("Farouk", "farouk@gmail.com","test logiciel","test");

        Report.newStep(4, "Fill Form Of Contact Page");
        Contact.remplirleformulaireincomplet("Farouk", "farouk@gmail.com","test logiciel","test");


        Report.newStep(5, "Verifie Menu");
        Menu.verifieMenu();

        Report.newStep(6, "Check No error");
        Errors.checkNoErrors();

        Report.newStep(7, "Verifie Footer");
        Footer.verifieFooter();

    }

}
