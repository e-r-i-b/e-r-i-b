package com.rssl.phizic.business.ext.sbrf.deposits.entities;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * Компаратор для сортировки сущностей с описаниями вклада по валютам
 *
 * @author EgorovaA
 * @ created 09.04.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositDescriptionRowComparator implements Comparator<DepositDescriptionRow>
{
	private static DepositDescriptionRowComparator instance = new DepositDescriptionRowComparator();

	public static DepositDescriptionRowComparator getInstance()
	{
		return instance;
	}

	public int compare(DepositDescriptionRow first, DepositDescriptionRow second)
	{
		return DepositEntityCurrencyComparator.getInstance().compare(first.getCurrency(), second.getCurrency());
	}

}
