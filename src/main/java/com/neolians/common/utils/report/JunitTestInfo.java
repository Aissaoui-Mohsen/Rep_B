package com.neolians.common.utils.report;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface JunitTestInfo {

	/**
	 * @return true if save the result in data=base
	 */
	boolean saveInDb() default true;

	/**
	 * @return test unique id
	 */
	String id();

	/**
	 * @return Test description
	 */
	String description() default "";

	/**
	 * @return if Test has a specific Dataset
	 */
	String dataset() default "";

	/**
	 * Bug id associated with the current Test
	 *
	 * @return bugId
	 */
	String bugId() default "";

	/**
	 * 2nd Bug id associated with the current Test
	 *
	 * @return bugId
	 */
	String bugId2() default "";

	/**
	 * 3rd Bug id associated with the current Test
	 *
	 * @return bugId
	 */
	String bugId3() default "";

	String xlsfile() default "";

	int duration = 0;

	/**
	 * Save in Xray
	 *
	 * @return true to save in Xray
	 */
	boolean xray() default true;
}