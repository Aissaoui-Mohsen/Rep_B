package com.neolians.common.utils;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.lang3.reflect.FieldUtils;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.internal.AllureStorage;
import io.qameta.allure.model.Link;
import io.qameta.allure.model.TestResult;
import io.qameta.allure.util.ResultsUtils;

/**
 * @author AlbaretP Some Utils method for Allure Reporting
 */
public class AllureUtils {

	/**
	 * Add Issue Link in Allure Reporting
	 *
	 * @param bugId bug to add
	 */
	public static void addIssue(String bugId) {
		final Link link = new Link();
		link.setType(ResultsUtils.ISSUE_LINK_TYPE);
		link.setName(bugId);
		addLink(link);
	}

	/**
	 * Add TMS Link in Allure Reporting
	 *
	 * @param jiraId Jira task to link
	 */
	public static void addTMSLink(String jiraId) {
		final Link link = new Link();
		link.setType(ResultsUtils.TMS_LINK_TYPE);
		link.setName(jiraId);
		addLink(link);
	}

	/**
	 * Add Link in Allure Reporting
	 *
	 * @param link link to add
	 */
	public static void addLink(Link link) {
		final AllureLifecycle allureLifecycle = Allure.getLifecycle();
		Field field = FieldUtils.getField((allureLifecycle).getClass(), "storage", true);
		field.setAccessible(true);
		try {
			final AllureStorage storage = (AllureStorage) field.get(allureLifecycle);
			field = FieldUtils.getField((storage).getClass(), "storage", true);
			field.setAccessible(true);
			@SuppressWarnings("unchecked")
			final Map<String, Object> storageMap = (Map<String, Object>) field.get(storage);

			String testResultUuid = null;
			for (final Map.Entry<String, Object> entry : storageMap.entrySet()) {
				if (entry.getValue() instanceof TestResult) {
					testResultUuid = entry.getKey();
					break;
				}
			}
			if (testResultUuid == null) {
				return;
			}
			Allure.getLifecycle().updateTestCase(testResultUuid, testResult -> testResult.getLinks().add(link));

		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
