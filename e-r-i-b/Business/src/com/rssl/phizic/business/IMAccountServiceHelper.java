package com.rssl.phizic.business;

import com.rssl.phizic.business.resources.external.IMAccountLink;

import java.util.List;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class IMAccountServiceHelper
{
	/**
	 * Ищет в списке нужную нам ссылку на ОМС по номеру счёта
	 * @param number - номер счёта ОМС
	 * @param imAccounLinks - список ссылок на ОМС клиента
	 * @return ссылку на ОМС клиента
	 */
	public static IMAccountLink getIMAccountLinkByNumber(String number, List<IMAccountLink> imAccounLinks)
	{
		for(IMAccountLink link : imAccounLinks)
		{
			if(link.getNumber().equals(number))
			{
				return link;
			}
		}
		return null;
	}
}
