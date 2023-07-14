package com.rssl.phizic.web.util;

import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * @author mihaylov
 * @ created 14.04.2011
 * @ $Author$
 * @ $Revision$
 */

public class PersonProductUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * @param productName - тип продукта клиента
	 * @return возвращает количество продуктов клиента заданного тип
	 */
	public static int getClientProductLinksCount(String productName)
	{		
		try
		{
			ResourceType product = ResourceType.valueOf(productName);
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			switch (product)
			{
				case CARD       : return personData.getCards().size();
				case LOAN       : return personData.getLoans().size();
				case ACCOUNT    : return personData.getAccounts().size();
				case DEPO_ACCOUNT:return personData.getDepoAccounts().size();
				case IM_ACCOUNT : return personData.getIMAccountLinks().size();
			}
		}
		catch (Exception e)
		{
			log.error("ќшибка определени€ количества продуктов клиента. ѕродукт : " + productName, e);
		}
		return 0;
	}

}
