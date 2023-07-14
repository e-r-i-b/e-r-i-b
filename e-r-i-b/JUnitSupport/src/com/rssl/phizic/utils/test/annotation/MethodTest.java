package com.rssl.phizic.utils.test.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 *
 * ����� ������������ ��� ���������� ������� ������� ���������� ��������� �� ������� ���������� ������.
 *
 * User: Balovtsev
 * Date: 21.02.2012
 * Time: 9:56:04
 */
@Target(java.lang.annotation.ElementType.METHOD)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface MethodTest
{

	/**
	 *
	 * ������ ������������ ��� ������� ���������� ������ ����� ���������.
	 *
	 * @return String[]
	 */
	String[] configurations()  default {};

	/**
	 *
	 * ��������� ����� �� ������������
	 *
	 * @return boolean
	 */
	boolean  exclude() default false;
}
