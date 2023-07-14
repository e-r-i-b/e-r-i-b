package com.rssl.phizic.business.resources.external.insurance;

import java.util.List;

/**
 * @author lukina
 * @ created 14.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class InsuranceServiceHelper
{
	/**
	 * Поиск ссылки на страховой продукт по номеру
	 * @param insuranceLinks - список ссылок
	 * @param number - номер(reference) страховки
	 * @return - ссылка на страховой продукт или null если не нашли
	 */
	public static InsuranceLink findInsuranceLinkByNumber(List<InsuranceLink> insuranceLinks, String number)
	{
		for (InsuranceLink link : insuranceLinks)
		{
			if(link.getNumber().equals(number))
				return link;
		}
		return null;
	}

}
