package com.rssl.phizic.web.common.socialApi.contacts;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Необходимо ли показывать клиентов сбербанка в мобильном приложении
 * @author bogdanov
 * @ created 17.11.2014
 * @ $Author$
 * @ $Revision$
 */
public class BankContactShowForm extends ActionFormBase
{
	private boolean needShowBankClient;

	public boolean isNeedShowBankClient()
	{
		return needShowBankClient;
	}

	public void setNeedShowBankClient(boolean needShowBankClient)
	{
		this.needShowBankClient = needShowBankClient;
	}
}
