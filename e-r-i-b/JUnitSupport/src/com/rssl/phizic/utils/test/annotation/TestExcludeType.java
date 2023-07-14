package com.rssl.phizic.utils.test.annotation;

/**
 *
 * ������ ��� �������� ������� ���������� ������� ������
 *
 * User: Balovtsev
 * Date: 21.02.2012
 * Time: 9:52:19
 */
public enum TestExcludeType
{
	/**
	 *
	 * ������ ������ ����������, ��� ������������� ��������� �� ������������ �����-���� ����� ���.  
	 *
	 */
	NONE,

	/**
	 *
	 * ������ ������ ����������, ��� ���������� ��������� �� ������������ ��� ������ ����������
	 * ������������ ����������.
	 *
	 */
	ALL_ANNOTATED,

	/**
	 *
	 * ������ ������ ����������, ��� ������ �� ���������� ������ ��������� �� ��������� ������.
	 *
	 */
	DETERMINED_BY_METHOD_ANNOTATION;
}