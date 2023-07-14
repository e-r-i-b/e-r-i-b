package com.rssl.phizic.web.client.moneyBox;

import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author vagin
 * @ created 29.09.14
 * @ $Author$
 * @ $Revision$
 * Форма списка копилок
 */
public class MoneyBoxListForm extends EditFormBase
{
	private AccountLink accountLink;
	private List<AutoSubscriptionLink> data;

	public AccountLink getAccountLink()
	{
		return accountLink;
	}

	public void setAccountLink(AccountLink accountLink)
	{
		this.accountLink = accountLink;
	}

	public List<AutoSubscriptionLink> getData()
	{
		return data;
	}

	public void setData(List<AutoSubscriptionLink> data)
	{
		this.data = data;
	}
}
