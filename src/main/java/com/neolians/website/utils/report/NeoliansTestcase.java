package com.neolians.website.utils.report;

import org.junit.jupiter.api.BeforeAll;

import com.neolians.common.utils.report.JunitTestcase;

public abstract class NeoliansTestcase extends JunitTestcase {
	@BeforeAll
	public static void testsuiteBegin() {
		JunitTestcase.testsuiteBegin();
	}
}
