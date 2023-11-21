package com.neolians.common.utils.params;

/**
 * This Class Manage the tests.properties and the tests.{user.name} name
 * properties file located in the conf folder
 * <p>
 * this file
 */
public class ConfigHelper {

	// region execution.properties
	public final static StringParam BROWSER = new StringParam("browser");
	public final static StringParam BROWSER_HEADLESS = new StringParam("browser.headless");
	public final static StringParam PROXY = new StringParam("proxy");

	// region softwareUnderTest.properties
	public final static StringParam SUT_TEST_ENVIRONMENT = new StringParam("sut.test.environment");

	private final static StringParam SUT_FRONT_URL = new StringParam("sut.front.url");
	public final static StringParam SUT_LOGIN_URL = new StringParam("sut.login.url");

	public final static StringParam SUT_USER_ADMIN_LOGIN = new StringParam("sut.user.adm.login");
	public final static StringParam SUT_USER_ADMIN_PWD = new StringParam("sut.user.adm.pwd");
	public final static StringParam SUT_Prameter_FactureSimple = new StringParam("sut.Parameter.offreFactureSimple");
	public final static StringParam SUT_Prameter_FactureDetaille = new StringParam(
			"sut.Parameter.offreFactureDetaille");

	public final static StringParam SUT_Prameter_FactureJalon = new StringParam("sut.Parameter.offreFactureJalon");
	public final static StringParam SUT_Prameter_FactureJalon01213 = new StringParam(
			"sut.Parameter.offreFactureJalon-O1213");
	public final static StringParam SUT_Prameter_GenerationEcheancierDepuisOffre_TC01664 = new StringParam(
			"sut.Parameter.generationEcheancierDepuisOffre-TC01664");
	public final static StringParam SUT_Prameter_FactureJalon01211 = new StringParam(
			"sut.Parameter.offreFactureJalon-O1211");
	public final static StringParam SUT_Prameter_opportunite_FactureSimple = new StringParam(
			"sut.Parameter.opportuniteFactureSimple");

	public final static StringParam SUT_Prameter_opportunite_FactureEcheancier = new StringParam(
			"sut.Parameter.opportuniteFactureEcheancier");
	public final static StringParam SUT_Prameter_opportunite_FactureEcheancierTC01660 = new StringParam(
			"sut.Parameter.opportuniteAvecProduitFactureEcheancierTC01660");
	public final static StringParam SUT_Prameter_opportunite_FactureEcheancierTC01661 = new StringParam(
			"sut.Parameter.opportuniteAvecProduitFactureEcheancierTC01661");
	public final static StringParam SUT_Prameter_opportuniteAvecProduits_FactureEcheancier = new StringParam(
			"sut.Parameter.opportuniteAvecProduitsFactureEcheancier");

	public final static StringParam SUT_Prameter_FactureDetailleDeuxProduits = new StringParam(
			"sut.Parameter.opportuniteFactureDetailleDeuxProduits");
	public final static StringParam SUT_Prameter_FactureDetailleUnProduit = new StringParam(
			"sut.Parameter.opportuniteFactureDetailleUnProduit");
	public final static StringParam SUT_Prameter_Opp_RS_FactureSimple = new StringParam(
			"sut.Parameter.opportunitedepuisCompteFactureSimpleRC-OO1223");
	public final static StringParam SUT_Prameter_OppOO1224_RS_FactureSimple = new StringParam(
			"sut.Parameter.opportunitedepuisCompteFactureSimpleRC-OO1224");

	public final static StringParam SUT_Prameter_FactureSimpleUnProduit = new StringParam(
			"sut.Parameter.opportuniteFactureSimpleUnProduit");
	// region CI
	public final static StringParam GIT_ROOT_FOLDER = new StringParam("git.root.folder");
	public final static StringParam REPORT_JOBEXECUTIONID = new StringParam("report.job.executionId");

	public final static StringParam SUT_Prameter_UrlSofacto = new StringParam("sut.url.sofacto");

	public static String getApplicationUrl() {
		return SUT_FRONT_URL.getValue();

	}

	// endregion
	public static void main(String[] args) {
		System.out.println(SUT_FRONT_URL.getValue());
	}
}