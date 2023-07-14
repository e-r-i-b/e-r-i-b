package com.rssl.auth.csa.back.comparator;

import com.rssl.phizic.utils.StringHelper;

/**
 * @author osminin
 * @ created 06.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Базовый класс для сравнения двух объектов, содержащих информацию о пользователе
 */
public class UserInfoComparatorBase
{
	protected int compareToNull(Object o1, Object o2)
	{
		if (o1 == null && o2 == null)
		{
			return 0;
		}
		if (o1 == null && o2 != null)
		{
			return -1;
		}
		if (o1 != null && o2 == null)
		{
			return 1;
		}
		throw new IllegalArgumentException("Хотя бы один из двух объектов должен быть null");
	}

	protected String getFIOToCompare(String firstName, String surName, String patrName)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(surName.trim()).append(" ")
			    .append(firstName.trim()).append(" ")
				.append(StringHelper.getEmptyIfNull(patrName).trim());

		return builder.toString();
	}
}
