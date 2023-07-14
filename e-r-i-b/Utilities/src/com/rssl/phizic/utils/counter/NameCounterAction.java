package com.rssl.phizic.utils.counter;

import org.apache.commons.collections.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author osminin
 * @ created 17.04.14
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��� ������������ ������������ �� ��������� �������
 */
public abstract class NameCounterAction
{
	private final NamingStrategy namingStrategy;

	/**
	 * ctor
	 * @param namingStrategy ������ ����������
	 */
	protected NameCounterAction(NamingStrategy namingStrategy)
	{
		this.namingStrategy = namingStrategy;
	}

	/**
	 * �������� ������������
	 * @param o ������
	 * @return ������������
	 */
	public abstract String getName(Object o);

	/**
	 * ���������� ��������� �����, ��������� � �������� �����
	 * @param objects ������ ��������, �� ������� ������������ �������
	 * @param standardName ������������, ��� �������� �������� �����
	 * @return �����
	 */
	final String calcNumber(List<?> objects, String standardName)
	{
		Set<String> names = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(objects))
		{
			for (Object object : objects)
			{
				names.add(getName(object));
			}
		}
		return namingStrategy.unify(names, standardName);
	}
}
