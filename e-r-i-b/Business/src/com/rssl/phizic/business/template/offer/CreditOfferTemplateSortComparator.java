package com.rssl.phizic.business.template.offer;

import java.util.Comparator;

/**
 * @author Balovtsev
 * @since 08.06.2015.
 */
public class CreditOfferTemplateSortComparator implements Comparator<CreditOfferTemplate>
{
	public int compare(CreditOfferTemplate template1, CreditOfferTemplate template2)
	{
		if (template1.getStatus() == Status.OPERATE || (template1.getStatus() == Status.INTRODUCED && template2.getStatus() == Status.ARCHIVE))
		{
			return -1;
		}

		if (template2.getStatus() == Status.OPERATE || (template2.getStatus() == Status.INTRODUCED && template1.getStatus() == Status.ARCHIVE))
		{
			return 1;
		}

		if (template1.getStatus() == template2.getStatus())
		{
			return template1.getFrom().compareTo(template2.getFrom());
		}

		/*
		 * ќферта с типом CreditOfferTemplateType.PDP не имеет статуса, отодвигаем еЄ в конец списка
		 */
		if (template1.getStatus() == null)
		{
			return 1;
		}

		if (template2.getStatus() == null)
		{
			return -1;
		}

		return 0;
	}
}
