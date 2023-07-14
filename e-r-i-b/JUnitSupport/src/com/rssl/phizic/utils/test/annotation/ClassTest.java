package com.rssl.phizic.utils.test.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 *
 * ����� ������������ ��� �������� ����� ����� ���������, � ����� ������������.
 * 
 * User: Balovtsev
 * Date: 21.02.2012
 * Time: 9:48:30
 */
@Target(java.lang.annotation.ElementType.TYPE)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface ClassTest
{

	/**
	 *
	 * ��-��������� ���� ����� �����������. ��� ������� �����, ������� ���������� �� ����� ���������
	 * �������� ����� ��������� � true.
	 *
	 * @return boolean
	 */
	boolean exclude() default false;       

	/**
	 *
	 * ������ ������������ ��� ������� ���������� ����� ���������. � ������, ����� �������� exclude ���������
	 * � true ���� �������� ������������.
	 *
	 * @return String[]
	 */
	String[] configurations() default {};

	/**
	 *
	 * ������������� �� ������ �������� �� ������� ���������� ����������� ������ ������.
	 * ��-��������� ��� ������ �������� � �������.
	 *
	 * @return TestExcludeType
	 */
	TestExcludeType excludeType() default TestExcludeType.DETERMINED_BY_METHOD_ANNOTATION;

	/**
	 *
	 * ��������� ������ ����������� ������� ����� �� ������ ������, ��� ���� ������� � ������� ��� ������� �� ������
	 * ����. �� ����� ����������� ����� ������ ��������� � ������ ���������. ��������� �����������! 
	 *
 	 * @return Class[]
	 */
	Class[] dependencies() default {};
}
