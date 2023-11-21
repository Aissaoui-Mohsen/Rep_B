package com.neolians.website.functions.pages;

import com.neolians.common.utils.DoAssert;

import com.neolians.website.repository.pages.PageSiteABC;
import org.junit.Assert;


public class SiteABC {

    private SiteABC() {
        throw new IllegalStateException("Utility class");
    }


    public static void VerifySiteABC() {
        //String actualLink= PageFooter.siteDeveloppeParAbcGroup.getAttribute("href");


        String linkABCSite=PageSiteABC.url5.getCurrentUrl();
        Assert.assertEquals(linkABCSite,"https://www.ab-consulting.co/");
        //DoAssert.assertEquals(linkABCSite,"https://www.ab-consulting.co/","Check ABC LINK Site",false,true);




    }

}
