package com.rssl.phizic.utils.test;

import com.rssl.phizic.utils.test.annotation.ClassTest;

import java.util.Comparator;

/**
 * User: Balovtsev
 * Date: 06.04.2012
 * Time: 14:51:10
 *
 * ��������� ����� � ������ � ������������ � ������������� ���������� � ���������.
 *
 */
public class ClassDependenciesComparator implements Comparator<Class<?>>
{
	public int compare(Class<?> o1, Class<?> o2)
	{
		/*
		 * �������� �� ��������� � ������ ������ ������������. � ������ ���� ��������� ���, ������ ������� ����
		 * �� �������.
		 */
		ClassTest annotation = o1.getAnnotation(ClassTest.class);
		if( annotation == null )
		{
			 return 0;
		}

		/*
		 * �������� �� ������������ � ���� ����� �2 ������ ����, �� �1 ������������� � ������� ���������� ������
		 * ���� ����.
		 */
		for (Class dependency : annotation.dependencies())
		{
			if (dependency.getName().equals( o2.getName() ))
			{
				return 1;
			}
		}

		return 0;
	}
}
