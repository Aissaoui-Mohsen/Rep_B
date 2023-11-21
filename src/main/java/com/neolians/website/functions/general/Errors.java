package com.neolians.website.functions.general;

import com.neolians.website.repository.general.PageErrors;

public class Errors {

    public static void checkNoErrors(){
        PageErrors.fatalError.assertNotPresent();
    }
}
