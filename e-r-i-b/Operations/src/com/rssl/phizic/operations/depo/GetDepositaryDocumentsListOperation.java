package com.rssl.phizic.operations.depo;

import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonContext;

/**
 * @author mihaylov
 * @ created 28.10.2010
 * @ $Author$
 * @ $Revision$
 */
/* ѕолучение списка документов по депозитарию */
public class GetDepositaryDocumentsListOperation extends GetDepoAccountLinkOperation implements ListEntitiesOperation
{
	
	public Long getLoginId()
	{
		PersonData data = PersonContext.getPersonDataProvider().getPersonData();
		return data.getPerson().getLogin().getId();
	}

	public String getDepoAccount()
	{
		return getDepoAccountLink().getAccountNumber();

	}
}
