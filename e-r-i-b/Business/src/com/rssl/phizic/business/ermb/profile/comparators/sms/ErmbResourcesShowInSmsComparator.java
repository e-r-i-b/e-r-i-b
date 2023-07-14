package com.rssl.phizic.business.ermb.profile.comparators.sms;

import com.rssl.phizic.business.resources.external.ErmbProductLink;

import java.util.*;

/**
 * Компаратор, проверяющий нужно ли оправлять СМС об изменении видимости в СМС для текущих продуктов
 * @author Puzikov
 * @ created 13.03.14
 * @ $Author$
 * @ $Revision$
 */

public class ErmbResourcesShowInSmsComparator implements ErmbSmsChangesComparator<Map<Class, List<ErmbProductLink>>>
{
	/**
	 * Сравнивает наборы продуктов клиента в контексте смс-видимости
	 *
	 * @param oldProducts старые продукты
	 * @param newProducts новые продукты
	 * @return Измененный параметры
	 */
	public Set<String> compare(Map<Class, List<ErmbProductLink>> oldProducts,  Map<Class, List<ErmbProductLink>> newProducts)
	{
		Set<String> resultFields = new HashSet<String>();

		Set<ErmbProductLink> allOldLinks = new HashSet<ErmbProductLink>();
		for (List<ErmbProductLink> ermbProductLinks : oldProducts.values())
		{
			if (ermbProductLinks != null)
				allOldLinks.addAll(ermbProductLinks);
		}

		Set<ErmbProductLink> allNewLinks = new HashSet<ErmbProductLink>();
		for (List<ErmbProductLink> ermbProductLinks : newProducts.values())
		{
			if (ermbProductLinks != null)
				allNewLinks.addAll(ermbProductLinks);
		}

		out:
		for (ErmbProductLink oldLink : allOldLinks)
		{
			for (ErmbProductLink newLink : allNewLinks)
			{
				if (oldLink.getId().equals(newLink.getId()) && oldLink.getErmbNotification() != newLink.getErmbNotification())
				{
					resultFields.add(NOTIFICATION);
					break out;
				}
			}
		}

		return resultFields;
	}
}
